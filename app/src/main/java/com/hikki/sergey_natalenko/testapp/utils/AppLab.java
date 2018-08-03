package com.hikki.sergey_natalenko.testapp.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hikki.sergey_natalenko.testapp.classes.Note;
import com.hikki.sergey_natalenko.testapp.classes.User;
import com.hikki.sergey_natalenko.testapp.database.AppBaseHelper;
import com.hikki.sergey_natalenko.testapp.database.AppCursorWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.hikki.sergey_natalenko.testapp.database.AppDbSchema.*;

public class AppLab {

    private static AppLab sAppLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    private AppLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new AppBaseHelper(mContext.getApplicationContext()).getWritableDatabase();
    }

    public static AppLab get(Context context) {
        if (sAppLab == null){
            sAppLab = new AppLab(context);
        }
        return sAppLab;
    }

//    **********************************************************************
//    NOTE'S METHODS
//    **********************************************************************

    public boolean createNote(Note note){
        if (getNote(note.getId()) == null) {
            ContentValues values = getNoteContentValues(note);
            mDatabase.insert(NoteTable.NAME, null, values);
            return true;
        } else {
            return false;
        }
    }

    public void deleteNote(Note note){
        if (AppLab.get(mContext).getNote(note.getId()) != null)
            mDatabase.delete(NoteTable.NAME,NoteTable.Cols.UUID +"= ?",new String[]{note.getId().toString()});
    }

    public void updateNote(Note note){
        String uuidString = note.getId().toString();
        ContentValues values = getNoteContentValues(note);

        mDatabase.update(NoteTable.NAME, values,
                NoteTable.Cols.UUID + "= ?",
                new String[]{ uuidString });
    }

    private Note getNote(UUID id){
        AppCursorWrapper cursor = queryItems(NoteTable.NAME,
                NoteTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );
        try{
            if (cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getNote();
        } finally {
            cursor.close();
        }
    }

    public List<Note> getNotes() {
        List<Note> notes = new ArrayList<>();

        AppCursorWrapper cursor = queryItems(NoteTable.NAME, null, null,NoteTable.Cols.DATE+ " DESC");

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                notes.add(cursor.getNote());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return notes;
    }

//    **********************************************************************
//    USER'S METHODS
//    **********************************************************************

    public boolean createUser(User user){
        if (getUser(user.getEmail()) == null){
            ContentValues values = getUserContentValues(user);
            mDatabase.insert(UserTable.NAME, null, values);
            Log.i("AppLab", "User was added");
            return true;
        } else {
            return false;
        }
    }

    public void deleteUser(User user){
        if (AppLab.get(mContext).getUser(user.getId()) != null)
            mDatabase.delete(UserTable.NAME, UserTable.Cols.UUID + "= ?", new String[]{user.getId().toString()});
    }

    public User getUser(UUID id){
        AppCursorWrapper cursor = queryItems(UserTable.NAME,
                UserTable.Cols.UUID + " = ?",
                new String[]{id.toString()});
        try {
            if (cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            return  cursor.getUser();
        } finally {
            cursor.close();
        }
    }

    public User getUser(String email){
        AppCursorWrapper cursor = queryItems(UserTable.NAME,
                UserTable.Cols.EMAIL + " = ?",
                new String[]{email});
        try {
            if (cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getUser();
        } finally {
            cursor.close();
        }
    }

    public List<User> getUsers(){
        List<User> users = new ArrayList<>();

        AppCursorWrapper cursor = queryItems(UserTable.NAME, null, null);

        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                users.add(cursor.getUser());
                cursor.moveToNext();
            }

        } finally {
            cursor.close();
        }
        Log.i("Chats",users.size()+"");
        return users;
    }

    public User getSelf(){
        return getUser(UserPreferences.getUserLoggedId(mContext));
    }

    private static ContentValues getNoteContentValues(Note note){
        ContentValues values = new ContentValues();

        values.put(NoteTable.Cols.UUID, note.getId().toString());
        values.put(NoteTable.Cols.AUTHOR_UUID, note.getAuthor().toString());
        values.put(NoteTable.Cols.DATE, note.getDate().getTime());
        values.put(NoteTable.Cols.HAS_FILES, note.getHasFiles() ? 1 : 0);
        values.put(NoteTable.Cols.TEXT, note.getText());

        return values;
    }

    private static ContentValues getUserContentValues(User user){
        ContentValues values = new ContentValues();

        values.put(UserTable.Cols.UUID, user.getId().toString());
        values.put(UserTable.Cols.EMAIL, user.getEmail());
        values.put(UserTable.Cols.NAME, user.getName());
        values.put(UserTable.Cols.PASSWORD, user.getPassword());

        return values;
    }

    private AppCursorWrapper queryItems(String tableName, String whereClause, String[] whereArgs, String orderBy){
        Cursor cursor = mDatabase.query(
                tableName,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                orderBy
        );
        return new AppCursorWrapper(cursor);
    }
    private AppCursorWrapper queryItems(String tableName, String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                tableName,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new AppCursorWrapper(cursor);
    }
}
