package br.edu.ifsuldeminas.mch.GGL;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MenuActivity extends AppCompatActivity {

    FirebaseAuth auth;
    Button buttonLogout;
    FirebaseUser user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        auth = FirebaseAuth.getInstance();
        buttonLogout = findViewById(R.id.buttonLogout);
        user = auth.getCurrentUser();

        if(user == null) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }

            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            // Defina um título se necessário
            getSupportActionBar().setTitle("GGL Supply Control");

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void openFuelHistory(View view) {
        Intent intent = new Intent(this, FuelHistoryActivity.class);
        startActivity(intent);
    }

    public void openFuelComparacao(View view) {
        Intent intent = new Intent(this, ComparacaoActivity.class);
        startActivity(intent);
    }

    public void openFuelPrices(View view) {
        Intent intent = new Intent(this, GasPriceListActivity.class);
        startActivity(intent);
    }
}
