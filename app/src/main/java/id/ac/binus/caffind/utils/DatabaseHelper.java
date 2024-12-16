package id.ac.binus.caffind.utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import id.ac.binus.caffind.models.CoffeeShopModel;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String coffeeShop_table_name = "coffeeSpot";
    private static final String user_table_name = "user";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "Caffind", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query1 = "CREATE TABLE IF NOT EXISTS " + coffeeShop_table_name +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, description TEXT, location TEXT, operationHours TEXT, priceRange TEXT, image BLOB)";
        sqLiteDatabase.execSQL(query1);

        String query2 = "CREATE TABLE IF NOT EXISTS " + user_table_name +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT, password TEXT)";
        sqLiteDatabase.execSQL(query2);

        Log.d("DatabaseCheck", "Database Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query1 = "DROP TABLE IF EXISTS " + coffeeShop_table_name;
        sqLiteDatabase.execSQL(query1);

        String query2 = "DROP TABLE IF EXISTS " + user_table_name;
        sqLiteDatabase.execSQL(query2);
    }

    public boolean registerNewAccount(String name, String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues userValues = new ContentValues();
        userValues.put("name", name);
        userValues.put("email", email);
        userValues.put("password", password);

        long result = db.insert(user_table_name, null, userValues);

        if(result == -1){
            Log.e("Database", "Insert failed");
            return false;
        } else {
            Log.d("Database", "User insert successful, row ID: " + result);
            return true;
        }
    }

    public boolean addNewCoffeeSpot(String name, String description, String location, String operationHours, String priceRange, byte[] image){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues coffeeShopValues = new ContentValues();
        coffeeShopValues.put("name", name);
        coffeeShopValues.put("description", description);
        coffeeShopValues.put("location", location);
        coffeeShopValues.put("operationHours", operationHours);
        coffeeShopValues.put("priceRange", priceRange);
        coffeeShopValues.put("image", image);

        long result = db.insert(coffeeShop_table_name, null, coffeeShopValues);

        if(result == -1){
            Log.e("Database", "Insert failed");
            return false;
        } else {
            Log.d("Database", "Coffee Spot insert successful, row ID: " + result);
            return true;
        }
    }

    public ArrayList<CoffeeShopModel> getCoffeeShopList(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CoffeeShopModel> SpotList = new ArrayList<>();

        String query = "SELECT * FROM " + coffeeShop_table_name;
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(query, null);

            while(cursor.moveToNext()){
                Log.d("Database", "Coffee Shop generated");
                SpotList.add(new CoffeeShopModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getBlob(6)));
            }
            return SpotList;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

    public CoffeeShopModel getCoffeeShopById(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] params = {Integer.toString(id)};
        String query = "SELECT * FROM " + coffeeShop_table_name + " WHERE id = ?";
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(query, params);

            if (cursor.moveToNext()){
                return new CoffeeShopModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getBlob(6));
            } else {
                return null;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

    public boolean isUsernameUnique(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] params = {username};
        String query = "SELECT id FROM " + user_table_name + " WHERE name = ?";
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(query, params);

            if (cursor.moveToNext()){
                return false; // username exist
            } else {
                return true; // username don't exist
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

    }

    public boolean isEmailUnique(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] params = {email};
        String query = "SELECT id FROM " + user_table_name + " WHERE email = ?";
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(query, params);

            if (cursor.moveToNext()){
                return false; // email exist
            } else {
                return true; // email don't exist
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

    public boolean authenticateUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] params = {email, password};
        String query = "SELECT * FROM " + user_table_name + " WHERE email = ? AND password = ?";
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(query, params);

            if (cursor.moveToNext()){
                return true; // user exist
            } else {
                return false; // user don't exist
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }
}
