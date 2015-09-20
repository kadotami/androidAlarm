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
public class AlarmList {
    public static String TABLE_NAME = "alarm";
    public static String COLUMN_ID = "_id";
    public static String COLUMN_TIME = "time";
    public static String COLUMN_IS_ACTIVE = "is_active";


    public static String create() {
        return "create table " + TABLE_NAME + "("+COLUMN_ID+" integer primary key autoincrement not null, "
                + COLUMN_TIME + " text not null, " + COLUMN_IS_ACTIVE + "boolean default 1 );";
    }

    public static Alarm findById(Context context, long id) {
        SQLiteDatabase db = getReadableDB(context);
        Alarm alarm = new Alarm();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME +"where _id = ?", new String[] {String.valueOf(id)});
        if (cursor.moveToFirst()) {
            alarm.id = cursor.getLong(0);
            alarm.time = cursor.getString(1);
            alarm.is_active = cursor.getInt(2) > 0;
        }
        cursor.close();
        return alarm;
    }

    public static List<Alarm> findAll(Context context) {
        SQLiteDatabase db = getReadableDB(context);

        List<Alarm> listItem = new ArrayList<Alarm>();

        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " order by " + COLUMN_ID , null);
        if (cursor.moveToFirst()) {
            do {
                Alarm alarm = new Alarm();
                alarm.id= cursor.getLong(0);
                alarm.time = cursor.getString(1);
                listItem.add(alarm);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listItem;
    }

    public static long insert(Context context, Alarm alarm) {
        SQLiteDatabase db = getWritableDB(context);
        ContentValues values = new ContentValues();
        values.put(COLUMN_TIME, alarm.time);
        return db.insert(TABLE_NAME, null, values);
    }

    public static long update(Context context, Alarm alarm) {
        SQLiteDatabase db = getWritableDB(context);
        ContentValues values = new ContentValues();
        values.put(COLUMN_TIME, alarm.time);
        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(alarm.id)});
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

