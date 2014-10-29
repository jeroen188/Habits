package com.primo5.habits;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MainActivity extends Activity {
    final Context context = this;
    static DBAdapter myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openDB();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, DailyTasks.class);
        startActivity(intent);
    }



    public void sendMental(View view) {
        Intent intent = new Intent(this, DimensionMental.class);
        startActivity(intent);
    }

    public void sendPhysical(View view) {
        Intent intent = new Intent(this, DimensionPhysical.class);
        startActivity(intent);
    }

    public void sendSpirituality(View view) {
        Intent intent = new Intent(this, DimensionSpirituality.class);
        startActivity(intent);
    }

    public void sendSocial(View view) {
        Intent intent = new Intent(this, DimensionSocial.class);
        startActivity(intent);
    }

    public void sendAbout(View view) {
        Intent intent = new Intent(this, About.class);
        startActivity(intent);
    }

    public void sendBalance(View view) {
        Intent intent = new Intent(this, Balance.class);
        startActivity(intent);
    }

    public void addTask(View view) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.activity_new_task);
        dialog.setTitle("Add a new goal");

        // set the custom dialog components - text, image and button
        ImageView image = (ImageView) dialog.findViewById(R.id.imageView);
        image.setImageResource(R.drawable.ic_launcher);
        Spinner text = (Spinner) dialog.findViewById(R.id.editDimension);
        text.setSelection(2);

        Button dialogButton = (Button) dialog.findViewById(R.id.buttonCreateTask);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                EditText task = (EditText) dialog.findViewById(R.id.editTask);
                String getTask = task.getText().toString();

                Spinner dimension = (Spinner) dialog.findViewById(R.id.editDimension);
                String getDimension = dimension.getSelectedItem().toString();

                EditText description = (EditText) dialog.findViewById(R.id.editDescription);
                String getDescription = description.getText().toString();

                Spinner subdimension = (Spinner) dialog.findViewById(R.id.editSubDimension);
                String getSubdimension = subdimension.getSelectedItem().toString();






                myDb.insertRow(getTask, getDimension, getYesterdayDateString(), 0, 0, getDescription, getSubdimension);
                Toast.makeText(getApplicationContext(), "Task added!!! =)",
                        Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });

        dialog.show();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
       // closeDB();
    }
    private void openDB() {
        myDb = new DBAdapter(this);
        myDb.open();


    }

    private void closeDB() {
        myDb.close();
    }
    public void writingDetail(View view) {
        Intent intent = new Intent(this, DimensionPhysical.class);
        startActivity(intent);
    }

    public String getYesterdayDateString() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return dateFormat.format(cal.getTime());
    }
}