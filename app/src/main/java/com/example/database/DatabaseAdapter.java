package com.example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAdapter extends SQLiteOpenHelper {
    private static final String DB_NAME = "phone.db";
    private static final String DB_TABLE = "phones";
    private static final int DB_VERSION = 1;

    private static final String DB_CREATE_TABLE = "CREATE TABLE "+DB_TABLE+"(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, model TEXT NOT NULL, description TEXT NOT NULL);";

    public DatabaseAdapter(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DB_TABLE);
        onCreate(db);
    }

    public boolean insertPhone(Phone phone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", phone.getName());
        contentValues.put("model", phone.getModel());
        contentValues.put("description", phone.getDescription());
        return db.insert(DB_TABLE, null, contentValues) > 0;
    }

    public SQLiteCursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        return (SQLiteCursor) db.rawQuery("SELECT * FROM "+DB_TABLE,null);
    }
    public boolean deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        if (db.delete(DB_TABLE,"id = ?", new String[]{ id }) >0 )
            return true;
        else
            return false;
    }
}