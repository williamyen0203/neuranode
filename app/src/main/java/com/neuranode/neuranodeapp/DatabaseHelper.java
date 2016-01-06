package com.neuranode.neuranodeapp;

import android.content.Context;
import android.database.sqlite.*;

import java.io.*;
import java.sql.SQLException;

/**
 * Created by Yen on 1/4/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "questions";
    private static String DATABASE_PATH;
    private static String FULL_PATH = DATABASE_PATH + DATABASE_NAME;
    private static SQLiteDatabase myDatabase;
    private final Context context;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
//        DATABASE_PATH = "/data/data/com.neuranode.neuranodeapp/databases/";
        DATABASE_PATH = context.getFilesDir().getParentFile().getPath() + "/databases/";
    }

    @Override
    public void onCreate(SQLiteDatabase db){
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
    }

    public void createDatabase() throws IOException {
        boolean dbExist = checkDatabase();

        SQLiteDatabase readDb = this.getWritableDatabase();
        readDb.close();

        try{
            if (!dbExist){
                copyDatabase();
            }
        } catch (IOException e){
            throw new Error("Error copying database");
        }
    }

    public boolean checkDatabase(){
        SQLiteDatabase checkDB = null;
        try{
            checkDB = SQLiteDatabase.openDatabase(FULL_PATH, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e){
            // database doesn't exist yet
        }
        if (checkDB != null){
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    public void copyDatabase() throws IOException{
        InputStream inputStream = context.getAssets().open(DATABASE_NAME);
        OutputStream outputStream = new FileOutputStream(FULL_PATH);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0){
            outputStream.write(buffer, 0, length);
        }

        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    public void openDatabase() throws SQLException{
        myDatabase = SQLiteDatabase.openDatabase(FULL_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close(){
        if (myDatabase != null){
            myDatabase.close();
        }
        super.close();
    }
}
