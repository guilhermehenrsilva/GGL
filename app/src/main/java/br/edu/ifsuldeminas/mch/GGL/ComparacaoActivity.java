package br.edu.ifsuldeminas.mch.GGL;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.google.android.material.textfield.TextInputEditText;

import com.google.android.material.textfield.TextInputEditText;

public class ComparacaoActivity extends AppCompatActivity {

    private TextInputEditText textInputEditTextEtanol;
    private TextInputEditText textInputEditTextGas;

    private TextInputEditText textInputEditTextKmDestino;
    private TextInputEditText textInputEditTextKmPorLitro;
    private Button btnCalcular;
    private ImageView imageViewResult;
    private ImageView imageViewShare;
    private TextView textViewResult;
    private String tip;
    private Double gasPrice;
    private Double etanolPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparacao);

        textInputEditTextEtanol = findViewById(R.id.textInputEditTextEtanol);
        textInputEditTextGas = findViewById(R.id.textInputEditTextGas);
        textInputEditTextKmDestino = findViewById(R.id.textInputEditTextKmDestino);
        textInputEditTextKmPorLitro = findViewById(R.id.textInputEditTextKmPorLitro);
        btnCalcular = findViewById(R.id.buttonCalcular);
        imageViewResult = findViewById(R.id.imageViewFuel);
        imageViewShare = findViewById(R.id.imageViewShare);
        textViewResult = findViewById(R.id.textViewMessage);

        btnCalcular.setOnClickListener(view -> {
            String etanolPriceStr = textInputEditTextEtanol.getText().toString();
            String gasPriceStr = textInputEditTextGas.getText().toString();
            String kmDestinoStr = textInputEditTextKmDestino.getText().toString();
            String kmPorLitroStr = textInputEditTextKmPorLitro.getText().toString();

            if (etanolPriceStr.equals("")) {
                Toast.makeText(getApplicationContext(), "O valor do etanol não foi informado", Toast.LENGTH_LONG).show();
                return;
            }
            if (gasPriceStr.equals("")) {
                Toast.makeText(getApplicationContext(), "O valor da gasolina não foi informado", Toast.LENGTH_LONG).show();
                return;
            }
            if (kmDestinoStr.equals("")) {
                Toast.makeText(getApplicationContext(), "A quilometragem do destino não foi informada", Toast.LENGTH_LONG).show();
                return;
            }
            if (kmPorLitroStr.equals("")) {
                Toast.makeText(getApplicationContext(), "A eficiência do carro (km/l) não foi informada", Toast.LENGTH_LONG).show();
                return;
            }

            etanolPrice = Double.parseDouble(etanolPriceStr);
            gasPrice = Double.parseDouble(gasPriceStr);
            double kmDestino = Double.parseDouble(kmDestinoStr);
            double kmPorLitro = Double.parseDouble(kmPorLitroStr);

            double litrosNecessarios = kmDestino / kmPorLitro;
            String litrosNecessariosStr = String.format("%.2f", litrosNecessarios);

            if (etanolPrice / gasPrice < 0.7) {
                imageViewResult.setImageResource(R.drawable.ethanol);
                tip = "Melhor usar etanol. Você precisará de " + litrosNecessariosStr + " L para a viagem.";
            } else {
                imageViewResult.setImageResource(R.drawable.gas);
                tip = "Melhor usar gasolina. Você precisará de " + litrosNecessariosStr + " L para a viagem.";
            }

            textViewResult.setText(tip);
            imageViewResult.setVisibility(ImageView.VISIBLE);
            textViewResult.setVisibility(TextView.VISIBLE);
            imageViewShare.setVisibility(ImageView.VISIBLE);

            // Criar notificação push
            createNotification(tip);
        });

        imageViewShare.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Preços de qual posto?");
            LayoutInflater inflater = getLayoutInflater();
            View layoutDialogView = inflater.inflate(R.layout.alert_dialog_gas_station_view, null);
            builder.setView(layoutDialogView);
            builder.setNegativeButton("Cancelar", null);
            builder.setPositiveButton("Compartilhar", (dialogInterface, button) -> {
                EditText editText = layoutDialogView.findViewById(R.id.editTextAlertDialogId);
                String posto = editText.getText().toString();

                if (posto.equals("")) {
                    Toast toast = Toast.makeText(view.getContext(), "Nome do posto não pode ser vazio", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                // Verifique se os preços são válidos e não nulos
                if (gasPrice == null || etanolPrice == null) {
                    Toast toast = Toast.makeText(view.getContext(), "Valores de preço não definidos", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                double gasPriceValue = gasPrice;
                double etanolPriceValue = etanolPrice;

                // Calcula a relação de preços
                double ratio = (etanolPriceValue / gasPriceValue) * 100;
                String message = String.format(
                        "Preço do posto '%s'. Gasolina: %.2f, Etanol: %.2f. %s.",
                        posto, gasPriceValue, etanolPriceValue, tip, ratio
                );

                Toast toast = Toast.makeText(view.getContext(), message, Toast.LENGTH_LONG);
                toast.show();

                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, message);
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, "Compartilhar via");
                try {
                    view.getContext().startActivity(shareIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(view.getContext(), "Nenhuma aplicação disponível para compartilhar o conteúdo", Toast.LENGTH_SHORT).show();
                }
            });
            builder.create().show();
        });
    }

    private void createNotification(String message) {
        String channelId = "fuel_comparison_channel";
        String channelName = "Fuel Comparison Notifications";

        // Criação do canal de notificação para Android O e superior
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }

        // Configuração da notificação
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Resultado da Comparação de Combustível")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        // Exibir a notificação
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(1, builder.build());
    }

    @Override
    protected void onStart() {
        super.onStart();
        imageViewResult.setVisibility(ImageView.INVISIBLE);
        textViewResult.setVisibility(TextView.INVISIBLE);
        imageViewShare.setVisibility(ImageView.INVISIBLE);
    }
}
