package com.hikki.sergey_natalenko.testapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.hikki.sergey_natalenko.testapp.database.AppDbSchema.*;

public class AppBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;

    private static final String DATABASE_NAME = "appDatabase.db";

    public AppBaseHelper(Context context){
        super(context, DATABASE_NAME,null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table " + NoteTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                NoteTable.Cols.UUID + ", " +
                NoteTable.Cols.AUTHOR_UUID + ", " +
                NoteTable.Cols.TEXT + ", " +
                NoteTable.Cols.HAS_FILES + ", " +
                NoteTable.Cols.DATE + ")"
        );

        db.execSQL("create table " + UserTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                UserTable.Cols.UUID + ", " +
                UserTable.Cols.EMAIL + ", " +
                UserTable.Cols.NAME + ", " +
                UserTable.Cols.PASSWORD+ ")"
        );

        db.execSQL("create table " + AttachedFilesTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                AttachedFilesTable.Cols.ADDRESS_OBJECT_UUID + ", " +
                AttachedFilesTable.Cols.NAME + ", " +
                AttachedFilesTable.Cols.URL+ ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
