package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favorites.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_FAVORITES = "favorites";

    // Columns
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_BRAND = "brand";
    private static final String COLUMN_MODEL = "model";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_IMAGE_URL = "image_url";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the favorites table
        String createTableQuery = "CREATE TABLE " + TABLE_FAVORITES + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_BRAND + " TEXT, "
                + COLUMN_MODEL + " TEXT, "
                + COLUMN_PRICE + " REAL, "
                + COLUMN_IMAGE_URL + " TEXT"
                + ")";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade the database if needed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        onCreate(db);
    }

    // Method to add a favorite car to the database
    public long addFavoriteCar(Car car) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BRAND, car.getBrand());
        values.put(COLUMN_MODEL, car.getModel());
        values.put(COLUMN_PRICE, car.getPrice());
        values.put(COLUMN_IMAGE_URL, car.getImageUrl());
        long result = db.insert(TABLE_FAVORITES, null, values);
        db.close();
        return result;
    }

    // Method to get all favorite cars from the database
    public List<Car> getAllFavoriteCars() {
        List<Car> favoriteCars = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_FAVORITES, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") Car car = new Car(
                        cursor.getString(cursor.getColumnIndex(COLUMN_BRAND)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_MODEL)),
                        cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_URL))
                );
                favoriteCars.add(car);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return favoriteCars;
    }
}
