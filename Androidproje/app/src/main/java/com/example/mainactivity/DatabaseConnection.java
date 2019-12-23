package com.example.mainactivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class DatabaseConnection extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "E-Commerce";
    private static final String UsersTable = "Users";
    String createUserTable = "CREATE TABLE " + UsersTable + "( usersID INTEGER PRIMARY KEY AUTOINCREMENT," +
            " userName TEXT ," +
            " userSurname TEXT ," +
            " userEmail TEXT ," +
            " userPassword TEXT )";


    public DatabaseConnection(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createUserTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UsersTable);
        onCreate(db);
    }

    public void insertUser(String UserName,String UserSurname,String UserEmail,String UserPassword){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("userName", UserName);
        values.put("userSurname", UserSurname);
        values.put("userEmail",UserEmail);
        values.put("userPassword",UserPassword);

        db.insert(UsersTable, null, values);
        db.close();
    }

    public boolean authenticateUser(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        String SQL = "SELECT * FROM " + UsersTable;
        Cursor cursor = db.rawQuery(SQL,null);
        if(cursor != null && cursor.moveToFirst() && cursor.getCount() > 0){
            return true;
        }
        return false;
    }
}
