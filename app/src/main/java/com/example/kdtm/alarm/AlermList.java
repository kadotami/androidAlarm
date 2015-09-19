package com.example.kdtm.alarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kdtm on 2015/09/19.
 */
public class AlermList {
    public static String TABLE_NAME = "alerm";
    public static String COLUMN_ID = "_id";
    public static String COLUMN_TIME = "time";
    public static String COLUMN_ISACTIVE = "is_active";


    public static String create() {
        return "create table " + TABLE_NAME + "("+COLUMN_ID+" integer primary key autoincrement not null, "
                + COLUMN_TIME + " text not null, " + COLUMN_ISACTIVE + "boolean default 'true' );";
    }

    public static Alerm findById(Context context, long id) {
        SQLiteDatabase db = getReadableDB(context);
        Alerm alerm = new Alerm();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME +"where _id = ?", new String[] {String.valueOf(id)});
        if (cursor.moveToFirst()) {
            alerm.id = cursor.getLong(0);
            alerm.time = cursor.getString(1);
        }
        cursor.close();
        return alerm;
    }

    public static List<Alerm> findAll(Context context) {
        SQLiteDatabase db = getReadableDB(context);

        List<Alerm> listItem = new ArrayList<Alerm>();

        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " order by " + COLUMN_ID , null);
        if (cursor.moveToFirst()) {
            do {
                Alerm alerm = new Alerm();
                alerm.id= cursor.getLong(0);
                alerm.time = cursor.getString(1);
                listItem.add(alerm);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listItem;
    }

    public static long insert(Context context, Alerm alerm) {
        SQLiteDatabase db = getWritableDB(context);
        ContentValues values = new ContentValues();
        values.put(COLUMN_TIME, alerm.time);
        return db.insert(TABLE_NAME, null, values);
    }

    public static long update(Context context, Alerm alerm) {
        SQLiteDatabase db = getWritableDB(context);
        ContentValues values = new ContentValues();
        values.put(COLUMN_TIME, alerm.time);
        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(alerm.id)});
    }

    public static long delete(Context context, long id) {
        SQLiteDatabase db = getWritableDB(context);
        return db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    private static SQLiteDatabase getWritableDB(Context context) {
        DatabaseOpenHelper helper = new DatabaseOpenHelper(context);
        return helper.getWritableDatabase();
    }

    private static SQLiteDatabase getReadableDB(Context context) {
        DatabaseOpenHelper helper = new DatabaseOpenHelper(context);
        return helper.getReadableDatabase();
    }
}
