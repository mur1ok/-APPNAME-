package com.example.testapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class DBHelper extends SQLiteOpenHelper {

    static String name = "users";
    static int version = 1;

    String createTableUsers = "CREATE TABLE if not exists 'users' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'username' TEXT," +
            " 'password' TEXT, 'email' TEXT, 'strength' REAL)";

    public DBHelper(Context context) {
        super(context, name, null, version);
        getWritableDatabase().execSQL(createTableUsers);
    }

    public void insertUser(ContentValues contentValues) {
        getWritableDatabase().insert("users", null, contentValues);
    }

    public boolean isLoginValid(String email, String password) {
        String sql = "Select count(*) from users where email='"+email+"' and password='" +password+ "'";
        SQLiteStatement statement = getReadableDatabase().compileStatement(sql);
        long l = statement.simpleQueryForLong();
        statement.close();
        return l == 1;
    }

    public boolean isLoginUnique(String email) {
        String sql = "Select count(*) from users where email='"+email+"'";
        SQLiteStatement statement = getReadableDatabase().compileStatement(sql);
        long l = statement.simpleQueryForLong();
        statement.close();
        return l == 0;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
