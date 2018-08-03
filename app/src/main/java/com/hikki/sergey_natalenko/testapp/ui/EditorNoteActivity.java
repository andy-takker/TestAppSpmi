package com.hikki.sergey_natalenko.testapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.hikki.sergey_natalenko.testapp.R;
import com.hikki.sergey_natalenko.testapp.classes.Note;
import com.hikki.sergey_natalenko.testapp.utils.AppLab;

public class EditorNoteActivity extends AppCompatActivity {

    private AppLab mAppLab;
    private EditText mEditText;

    private static final String TAG = "EditorNoteActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_note);
        mAppLab = AppLab.get(this);
        mEditText = findViewById(R.id.edit_text_note);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote(){
        try{
            String text = mEditText.getText().toString().trim();
            if (text.length() > 0){
                Note note = new Note(mAppLab.getSelf().getId(), text,false);
                mAppLab.createNote(note);
                Toast.makeText(this, "Note was created :)", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "You are trying to create an empty note!", Toast.LENGTH_SHORT).show();
            }
        } catch (Throwable throwable){
            Toast.makeText(this, "ERRRRRROR!", Toast.LENGTH_SHORT).show();
        }

        finish();
    }
}
