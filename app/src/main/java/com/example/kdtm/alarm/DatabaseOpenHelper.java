package com.example.kdtm.alarm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kdtm on 2015/09/19.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "alermList.db";

    private static final int DB_VERSION = 1;

    public DatabaseOpenHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AlermList.create());
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //hedgehogs
    }
}
