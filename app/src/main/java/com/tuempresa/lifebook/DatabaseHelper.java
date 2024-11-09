package com.tuempresa.lifebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "LifeBook.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_ENTRIES = "entries";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_MESSAGE = "message";
    public static final String COLUMN_TIMESTAMP = "timestamp";  // Para almacenar la fecha y hora

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ENTRIES_TABLE = "CREATE TABLE " + TABLE_ENTRIES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DATE + " TEXT,"
                + COLUMN_MESSAGE + " TEXT,"
                + COLUMN_TIMESTAMP + " TEXT" + ")";
        db.execSQL(CREATE_ENTRIES_TABLE);
    }

    // Método para agregar una entrada
    public boolean addEntry(String date, String message) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_MESSAGE, message);
        values.put(COLUMN_TIMESTAMP, String.valueOf(System.currentTimeMillis())); // Timestamp

        long result = db.insert(TABLE_ENTRIES, null, values);
        return result != -1;
    }

    // Método para obtener todas las entradas de un mes
    public Cursor getEntriesByMonth(String month) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_ENTRIES, null, "date LIKE ?", new String[]{ "%" + month + "%" }, null, null, null);
    }

    // Método para eliminar todas las entradas
    public void deleteAllEntries() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENTRIES);
        onCreate(db);
    }
}
