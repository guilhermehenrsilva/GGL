package br.edu.ifsuldeminas.mch.GGL;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MenuActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            // Defina um título se necessário
            getSupportActionBar().setTitle("GGL Suply_Controll");
    }

    public void openFuelHistory(View view) {
        Intent intent = new Intent(this, FuelHistoryActivity.class);
        startActivity(intent);
    }
}
