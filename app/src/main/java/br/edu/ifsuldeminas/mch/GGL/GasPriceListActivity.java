package br.edu.ifsuldeminas.mch.GGL;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import br.edu.ifsuldeminas.mch.GGL.model.GasPriceAPI;
import br.edu.ifsuldeminas.mch.GGL.model.GasPriceResponse;
import br.edu.ifsuldeminas.mch.GGL.model.GasolinePrice;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GasPriceListActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_price_list);

        listView = (ListView) findViewById(R.id.pagination_list);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.collectapi.com/gasPrice/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        GasPriceAPI api = retrofit.create(GasPriceAPI.class);

        Call<GasPriceResponse> call = api.gasPrices("apikey 7mGGIzhO8Jz8epAdGmJTwf:2Ti4eBbmx2VeSnC4a3m1XT");

        call.enqueue(new Callback<GasPriceResponse>() {
            @Override
            public void onResponse(Call<GasPriceResponse> call, Response<GasPriceResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    GasPriceResponse gasPriceResponse = response.body();
                    List<GasolinePrice> gasolinePrices = gasPriceResponse.getResults();

                    if (gasolinePrices != null && !gasolinePrices.isEmpty()) {
                        listView.setAdapter(new GasolinePriceAdapter(GasPriceListActivity.this, gasolinePrices));
                    } else {
                        Toast.makeText(GasPriceListActivity.this, "Nenhum dado disponível.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(GasPriceListActivity.this, "Erro ao obter dados da API", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GasPriceResponse> call, Throwable t) {
                Toast.makeText(GasPriceListActivity.this, "Erro na chamada de API!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}