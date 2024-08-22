package br.edu.ifsuldeminas.mch.GGL.model.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsuldeminas.mch.GGL.model.FuelRegister;

public class FuelRegisterDAO extends DAO {

    public FuelRegisterDAO(Context context) {
        super(context);
    }

    public boolean save(FuelRegister fuelRegister){
        SQLiteDatabase dataBase = openToWrite();

        ContentValues contentValues = new ContentValues();
        contentValues.put("fuel_qty", fuelRegister.getFuelQty());
        contentValues.put("fuel_price", fuelRegister.getFuelPrice());

        dataBase.insert("fuel_registry", null, contentValues);

        dataBase.close();

        return true;
    }

    public List<FuelRegister> loadFuelRegistry() {
        SQLiteDatabase dataBase = openToRead();
        List<FuelRegister> fuelRegistry = new ArrayList<FuelRegister>();
        String sql = "SELECT * FROM fuel_registry;";
        Cursor cursor = dataBase.rawQuery(sql, null);

        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            Double fuelQty = cursor.getDouble(
                    cursor.getColumnIndexOrThrow("fuel_qty"));
            Double fuelPrice = cursor.getDouble(
                    cursor.getColumnIndexOrThrow("fuel_price"));
            FuelRegister fuelRegister = new FuelRegister(id, fuelQty, fuelPrice);
            fuelRegistry.add(fuelRegister);
        }
        cursor.close();
        dataBase.close();
        return fuelRegistry;
    }

    public void delete(FuelRegister fuelRegister) {
        SQLiteDatabase dataBase = openToWrite();

        String[] params = {fuelRegister.getId().toString()};
        dataBase.delete("fuel_registry", "id = ?", params);

        dataBase.close();
    }

    public void update(FuelRegister fuelRegister) {
        SQLiteDatabase dataBase = openToWrite();

        ContentValues contentValues = new ContentValues();
        contentValues.put("fuel_qty", fuelRegister.getFuelQty());
        contentValues.put("fuel_price", fuelRegister.getFuelPrice());

        String[] params = {fuelRegister.getId().toString()};
        dataBase.update("fuel_registry", contentValues, "id = ?", params);

        dataBase.close();
    }
}
