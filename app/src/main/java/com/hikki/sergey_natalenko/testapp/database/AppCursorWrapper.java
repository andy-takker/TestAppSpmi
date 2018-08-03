package com.hikki.sergey_natalenko.testapp.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.util.Log;

import com.hikki.sergey_natalenko.testapp.classes.File;
import com.hikki.sergey_natalenko.testapp.classes.Note;
import com.hikki.sergey_natalenko.testapp.classes.User;

import java.util.Date;
import java.util.UUID;

import static com.hikki.sergey_natalenko.testapp.database.AppDbSchema.*;

public class AppCursorWrapper extends CursorWrapper {

    public AppCursorWrapper(Cursor cursor){
        super(cursor);
    }
    public Note getNote(){

        String uuidId = getString(getColumnIndex(NoteTable.Cols.UUID));
        String authorId = getString(getColumnIndex(NoteTable.Cols.AUTHOR_UUID));
        String text = getString(getColumnIndex(NoteTable.Cols.TEXT));
        int hasFiles = getInt(getColumnIndex(NoteTable.Cols.HAS_FILES));
        long date = getLong(getColumnIndex(NoteTable.Cols.DATE));
        Log.i("AppCursorWrapper", "Note: "+uuidId+" "+authorId+" "+text+" "+date);
        return new Note(UUID.fromString(authorId),UUID.fromString(uuidId), new Date(date), text, hasFiles != 0);
    }

    public User getUser(){
        String uuid = getString(getColumnIndex(UserTable.Cols.UUID));
        String email = getString(getColumnIndex(UserTable.Cols.EMAIL));
        String name = getString(getColumnIndex(UserTable.Cols.NAME));
        String password = getString(getColumnIndex(UserTable.Cols.PASSWORD));
        Log.i("AppCursorWrapper", "User: "+uuid+" "+email+" "+name+" "+password);
        return new User(name, email, UUID.fromString(uuid), password);
    }

    public File getFile(){
        String uuid = getString(getColumnIndex(AttachedFilesTable.Cols.ADDRESS_OBJECT_UUID));
        String name = getString(getColumnIndex(AttachedFilesTable.Cols.NAME));
        String url = getString(getColumnIndex(AttachedFilesTable.Cols.URL));
        Log.i("AppCursorWrapper", "File: "+uuid+" "+" "+name+" "+url);
        return new File(UUID.fromString(uuid), name, url);
    }
}
