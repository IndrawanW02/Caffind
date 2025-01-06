package id.ac.binus.caffind.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import id.ac.binus.caffind.R;
import id.ac.binus.caffind.models.CoffeeShopModel;
import id.ac.binus.caffind.models.UserModel;

public class DatabaseHelper extends SQLiteOpenHelper {
    private final Context context;
    private static final String coffeeShop_table_name = "coffeeSpot";
    private static final String user_table_name = "user";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "Caffind", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query1 = "CREATE TABLE IF NOT EXISTS " + coffeeShop_table_name +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "description TEXT, " +
                "location TEXT, " +
                "operationHours TEXT, " +
                "priceRange TEXT, " +
                "image BLOB, " +
                "creatorID INTEGER)";
        sqLiteDatabase.execSQL(query1);

        String query2 = "CREATE TABLE IF NOT EXISTS " + user_table_name +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "email TEXT, " +
                "password TEXT)";
        sqLiteDatabase.execSQL(query2);
        Log.d("DatabaseCheck", "Database Created");

        insertDummyData(sqLiteDatabase);
        Log.d("DummyData", "Dummy Data Inserted");
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

    public UserModel authenticateUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] params = {email, password};
        String query = "SELECT * FROM " + user_table_name + " WHERE email = ? AND password = ?";
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(query, params);

            if (cursor.moveToNext()){
                return new UserModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)); // user exist
            } else {
                return null; // user don't exist
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

    public void insertDummyData(SQLiteDatabase db){
        ContentValues coffeeShopValues1 = new ContentValues();
        coffeeShopValues1.put("name", "C for Cupcakes and Coffee Karawaci");
        coffeeShopValues1.put("description", "C for Cupcakes and Coffee adalah tempat ngopi dengan perpaduan cupcake manis dan kopi harum. Suasana cozy dan dekorasi ceria cocok untuk berkumpul atau me-time. Menu favoritnya cappuccino dan cupcake red velvet, tempat sempurna untuk pecinta kopi dan dessert manis.");
        coffeeShopValues1.put("location", "Jl. Taman Permata No.161, Binong, Kec. Curug, Kabupaten Tangerang, Banten 15810");
        coffeeShopValues1.put("operationHours", "10.00AM - 9.00PM");
        coffeeShopValues1.put("priceRange", "Rp50.000 - Rp75.000");
        coffeeShopValues1.put("image", getImageAsByteArray(R.drawable.sample_shop_image_1));
        coffeeShopValues1.put("creatorID", 1);

        ContentValues coffeeShopValues2 = new ContentValues();
        coffeeShopValues2.put("name", "Tanamera Coffee Serpong");
        coffeeShopValues2.put("description", "Tanamera Coffee Indonesia menyajikan kopi berkualitas tinggi dari biji kopi lokal dengan konsep modern dan ramah lingkungan. Menawarkan pengalaman ngopi premium dengan rasa autentik dari biji kopi Indonesia seperti Gayo, Flores, dan Kintamani.");
        coffeeShopValues2.put("location", "Scientia Square Park, Blok GV No. 09, Kel, Curug Sangereng, Kelapa Dua, Tangerang Regency, Banten 15810");
        coffeeShopValues2.put("operationHours", "07.00AM - 07.00PM");
        coffeeShopValues2.put("priceRange", "Rp50.000 - Rp75.000");
        coffeeShopValues2.put("image", getImageAsByteArray(R.drawable.sample_shop_image_2));
        coffeeShopValues1.put("creatorID", 1);

        ContentValues coffeeShopValues3 = new ContentValues();
        coffeeShopValues3.put("name", "Roemah Koffie");
        coffeeShopValues3.put("description", "Roemah Koffie mengusung nuansa klasik dengan dekorasi vintage dan suasana homey. Menyajikan kopi tradisional khas Indonesia seperti kopi tubruk, serta kudapan khas seperti pisang goreng dan kue tradisional. Tempat yang pas untuk menikmati kopi santai dan nostalgia.");
        coffeeShopValues3.put("location", "Carstensz Mall, Jl. Jenderal Sudirman No.1 GF #01, Cihuni, Kec. Pagedangan, Kabupaten Tangerang, Banten 15332");
        coffeeShopValues3.put("operationHours", "10.00AM - 10.00PM");
        coffeeShopValues3.put("priceRange", "Rp30.000 - Rp60.000");
        coffeeShopValues3.put("image", getImageAsByteArray(R.drawable.sample_shop_image_3));
        coffeeShopValues1.put("creatorID", 1);

        db.insert(coffeeShop_table_name, null, coffeeShopValues1);
        db.insert(coffeeShop_table_name, null, coffeeShopValues2);
        db.insert(coffeeShop_table_name, null, coffeeShopValues3);

        ContentValues userValues = new ContentValues();
        userValues.put("name", "admin");
        userValues.put("email", "admin@binus.ac.id");
        userValues.put("password", "admin");

        db.insert(user_table_name, null, userValues);
    }

    private byte[] getImageAsByteArray(int drawableId) {
        Drawable drawable = context.getResources().getDrawable(drawableId);

        // Convert drawable to bitmap
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

        // Convert bitmap to byte[]
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.WEBP, 80, stream);
        return stream.toByteArray();
    }
}
