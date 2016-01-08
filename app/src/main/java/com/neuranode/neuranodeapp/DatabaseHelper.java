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
    private static int DATABASE_VERSION = 1;
    private Context context;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    /**
     * <code>cursorQuery</code> queries the attached database with specified query.
     *
     * @param query     SQLite query
     * @return          Cursor object with selected information
     */
    public Cursor cursorQuery(String query){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        return cursor;
    }
}
