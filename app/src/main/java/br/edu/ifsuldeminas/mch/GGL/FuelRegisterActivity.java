package br.edu.ifsuldeminas.mch.GGL;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import br.edu.ifsuldeminas.mch.GGL.model.FuelRegister;
import br.edu.ifsuldeminas.mch.GGL.model.db.FuelRegisterDAO;

public class FuelRegisterActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText fuelQty;
    private EditText fuelPrice;
    private FuelRegister fuelRegister;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_register);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent chooserIntent = getIntent();
        fuelRegister = (FuelRegister) chooserIntent.getSerializableExtra("fuel_register");

        if (fuelRegister != null) {
            TextInputEditText textInputEditTextQtd = findViewById(R.id.fuel_qtd);
            textInputEditTextQtd.setText(fuelRegister.getFuelQty().toString());

            TextInputEditText textInputEditTextPrice = findViewById(R.id.fuel_price);
            textInputEditTextPrice.setText(fuelRegister.getFuelPrice().toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return super.onCreateOptionsMenu(menu);
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
//        switch (item.getItemId()) {
//
//            case R.id.save_fuel_register: {
//                fuelQty = findViewById(R.id.fuel_qtd);
//                Editable editableQty = fuelQty.getText();
//                String descQty = editableQty != null ? editableQty.toString() : "";
//
//                fuelPrice = findViewById(R.id.fuel_price);
//                Editable editablePrice = fuelPrice.getText();
//                String descPrice = editablePrice != null ? editablePrice.toString() : "";
//
//                if(descQty.equals("")) {
//                    Toast toast = Toast.makeText(this, "Quantidade de combustível não pode ser vazia!", Toast.LENGTH_LONG);
//                    toast.show();
//                    return false;
//                }
//
//                if(descPrice.equals("")) {
//                    Toast toast = Toast.makeText(this, "Preço de combustível não pode ser vazio!", Toast.LENGTH_LONG);
//                    toast.show();
//                    return false;
//                }
//
//                FuelRegisterDAO dao = new FuelRegisterDAO(this);
//
//                if(this.fuelRegister == null) {
//                    fuelRegister = new FuelRegister();
//                    fuelRegister.setFuelQty(Double.parseDouble(descQty));
//                    fuelRegister.setFuelPrice(Double.parseDouble(descPrice));
//                    dao.save(fuelRegister);
//                    Toast toast = Toast.makeText(this, "Registro salvo com sucesso!", Toast.LENGTH_LONG);
//                    toast.show();
//                } else {
//                    fuelRegister.setFuelQty(Double.parseDouble(descQty));
//                    fuelRegister.setFuelPrice(Double.parseDouble(descPrice));
//                    dao.update(fuelRegister);
//                    Toast toast = Toast.makeText(this, "Registro atualizado com sucesso!", Toast.LENGTH_LONG);
//                    toast.show();
//                }
//
//                finish();
//                break;
//            }
//        };
//
//        return super.onOptionsItemSelected(item);
//    }
}
