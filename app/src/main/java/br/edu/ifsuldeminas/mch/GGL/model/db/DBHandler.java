package br.edu.ifsuldeminas.mch.GGL.model.db;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DBHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "fuelRegistry.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_TASKS_CREATE_SQL =
            " CREATE TABLE " +
            " IF NOT EXISTS fuel_registry ( " +
            " id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            " fuel_qty DOUBLE, " +
            " fuel_price DOUBLE); ";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_TASKS_CREATE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) { }
}
