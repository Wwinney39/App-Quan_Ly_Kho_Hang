package com.example.quan_ly_kho_hang.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.quan_ly_kho_hang.model.Product;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "warehouse.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT NOT NULL UNIQUE, password TEXT NOT NULL)");
        db.execSQL("CREATE TABLE products (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, price INTEGER NOT NULL, quantity INTEGER NOT NULL, category TEXT NOT NULL, active INTEGER NOT NULL, description TEXT)");

        db.execSQL("INSERT INTO users(username, password) VALUES ('admin', '123456')");
        db.execSQL("INSERT INTO products(name, price, quantity, category, active, description) VALUES ('Chuột không dây', 150000, 20, 'Điện tử', 1, '')");
        db.execSQL("INSERT INTO products(name, price, quantity, category, active, description) VALUES ('Bàn phím cơ', 450000, 3, 'Điện tử', 1, 'Bàn phím cơ switch xanh, có đèn nền')");
        db.execSQL("INSERT INTO products(name, price, quantity, category, active, description) VALUES ('Sổ tay A5', 25000, 40, 'Văn phòng phẩm', 1, '')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS products");
        onCreate(db);
    }

    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?", new String[]{username, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public long insertProduct(Product p) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", p.getName());
        values.put("price", p.getPrice());
        values.put("quantity", p.getQuantity());
        values.put("category", p.getCategory());
        values.put("active", p.isActive() ? 1 : 0);
        values.put("description", p.getDescription());
        return db.insert("products", null, values);
    }

    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM products", null);
        if (c.moveToFirst()) {
            do {
                list.add(new Product(c.getInt(0), c.getString(1), c.getInt(2), c.getInt(3), c.getString(4), c.getInt(5) == 1, c.getString(6)));
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    public Product getProductById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM products WHERE id = ?", new String[]{String.valueOf(id)});
        Product p = null;
        if (c.moveToFirst()) {
            p = new Product(c.getInt(0), c.getString(1), c.getInt(2), c.getInt(3), c.getString(4), c.getInt(5) == 1, c.getString(6));
        }
        c.close();
        return p;
    }

    public void updateQuantity(int id, int qty) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put("quantity", qty);
        db.update("products", v, "id = ?", new String[]{String.valueOf(id)});
    }

    public void deleteProduct(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("products", "id = ?", new String[]{String.valueOf(id)});
    }

    public long getTotalValue() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT SUM(price * quantity) FROM products", null);
        long t = 0;
        if (c.moveToFirst()) t = c.getLong(0);
        c.close();
        return t;
    }

    public int countLowStock() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT COUNT(*) FROM products WHERE quantity < 5", null);
        int count = 0;
        if (c.moveToFirst()) count = c.getInt(0);
        c.close();
        return count;
    }
}