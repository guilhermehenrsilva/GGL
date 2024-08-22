package br.edu.ifsuldeminas.mch.GGL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import br.edu.ifsuldeminas.mch.GGL.model.FuelRegister;
import br.edu.ifsuldeminas.mch.GGL.model.db.FuelRegisterDAO;

public class FuelHistoryActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private ListView fuelRegistryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_history);

        fuelRegistryList = findViewById(R.id.fuel_registry_list);
        registerForContextMenu(fuelRegistryList);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentForm = new Intent(FuelHistoryActivity.this, FuelRegisterActivity.class);
                startActivity(intentForm);
            }
        });

        fuelRegistryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                FuelRegister fuelRegister = (FuelRegister) fuelRegistryList.getItemAtPosition(position);

                Intent intent = new Intent(FuelHistoryActivity.this, FuelRegisterActivity.class);
                intent.putExtra("fuel_register", fuelRegister);

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateHistory();
    }

    private void updateHistory() {
        FuelRegisterDAO dao = new FuelRegisterDAO(this);
        List<FuelRegister> fuelRegisterList = dao.loadFuelRegistry();

        ArrayAdapter<FuelRegister> arrayAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, fuelRegisterList);

        fuelRegistryList.setAdapter(arrayAdapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        // super.onCreateContextMenu(menu, v, menuInfo);
        MenuItem itemDelete = menu.add("Deletar registro");
        itemDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                AdapterView.AdapterContextMenuInfo itemClicado = (AdapterView.AdapterContextMenuInfo) menuInfo;

                FuelRegister fuelRegister = (FuelRegister) fuelRegistryList.getItemAtPosition(itemClicado.position);

                FuelRegisterDAO dao = new FuelRegisterDAO(FuelHistoryActivity.this);
                dao.delete(fuelRegister);
                updateHistory();

                Toast toast = Toast.makeText(FuelHistoryActivity.this, "Registro excluído com sucesso!", Toast.LENGTH_LONG);
                toast.show();

                return true;
            }
        });
    }
}