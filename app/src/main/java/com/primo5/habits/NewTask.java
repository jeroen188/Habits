package com.primo5.habits;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.database.Cursor;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class NewTask extends Activity {
    DBAdapter myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        openDB();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
    }


    private void openDB() {
        myDb = new DBAdapter(this);
        myDb.open();
    }
    private void closeDB() {
        myDb.close();
    }

    public void Onclick_task(View v) {
        EditText task = (EditText)findViewById(R.id.editTask);
        EditText dimension = (EditText)findViewById(R.id.editDimension);


        String getTask = task.getText().toString();
        String getDimension= dimension.getText().toString();


        long newId = myDb.insertRow(getTask, getDimension, "none", 0);
        Toast.makeText(getApplicationContext(), "Task added!!! =)",
                Toast.LENGTH_LONG).show();

    }

    }

