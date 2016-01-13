package com.neuranode.neuranodeapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by William on 1/4/2016.
 */
public class DatabaseHelper extends SQLiteAssetHelper {
    private static String DATABASE_NAME = "questions.db";
    private static String TABLE_NAME = "questions_table";
    private static int DATABASE_VERSION = 1;
    SQLiteDatabase database;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = this.getReadableDatabase();
    }

    /**
     * <code>cursorQuery</code> queries the attached database with specified query.
     *
     * @param query     SQLite query
     * @return          Cursor object with selected information
     */
    public Cursor cursorQuery(String query){
        return database.rawQuery(query, null);
    }

    public Cursor cursorQueryRow(int id){
        return database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE _id=" + id, null);
    }

    public void clearChoices(){
        database.execSQL("UPDATE " + TABLE_NAME  + " SET choice=NULL");
    }

    public void updateChoice(int id, int choice) {
        database.execSQL("UPDATE " + TABLE_NAME + " SET choice=" + choice + " WHERE _id=" + id);
    }

    public void resetChoices(Cursor cursor, int choice){
        int idIndex = cursor.getColumnIndex("_id");
        while (cursor.moveToNext()){
            database.execSQL("UPDATE " + TABLE_NAME + " SET choice=" + choice + " WHERE _id=" + cursor.getInt(idIndex));
            System.out.println("id: " + cursor.getInt(idIndex) + " choice: " + choice);
        }
    }
}
