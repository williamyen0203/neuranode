package com.neuranode.neuranodeapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.io.*;
import java.sql.SQLException;

/**
 * Created by Yen on 1/4/2016.
 */
public class DatabaseHelper extends SQLiteAssetHelper {
    private static String DATABASE_NAME = "questions.db";
    private static int DATABASE_VERSION = 1;
    private Context context;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public Cursor getQuestions(){
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM questions_table LIMIT 10";
        Cursor cursor = database.rawQuery(query, null);
        return cursor;
    }
}
