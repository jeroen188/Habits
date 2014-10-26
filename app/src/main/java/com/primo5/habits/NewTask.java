package com.primo5.habits;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.database.Cursor;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.view.View;
import android.widget.AdapterView;
import java.util.ArrayList;
import java.util.List;

public class NewTask extends Activity  {
    DBAdapter myDb;
    private Spinner spinner1, spinner2;

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
        Spinner dimension = (Spinner)findViewById(R.id.editDimension);
        EditText description = (EditText)findViewById(R.id.editDescription);
        Spinner subdimension = (Spinner)findViewById(R.id.editSubDimension);

        String getTask = task.getText().toString();
        String getDimension= dimension.getSelectedItem().toString();
        String getDescription= description.getText().toString();
        String getSubdimension= subdimension.getSelectedItem().toString();

        myDb.insertRow(getTask, getDimension, "", 1, 1, getDescription, getSubdimension);
        Toast.makeText(getApplicationContext(), "Task added!!! =)",
                Toast.LENGTH_LONG).show();

    }


    }


