package com.imer.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 丶 on 2017/2/26.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_INVITATION="create table Invitation ("
            +"id integer primary key autoincrement,"
            +"username text,"
            +"fromusername text,"
            +"date text,"
            +"reason text)";

    public static final String CREATE_MESSAGE="create table Message ("
            +"id integer primary key autoincrement,"
            +"username text,"
            +"fromusername text,"
            +"date text,"
            +"msg text,"
            +"type integer)";

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MESSAGE);
        db.execSQL(CREATE_INVITATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
