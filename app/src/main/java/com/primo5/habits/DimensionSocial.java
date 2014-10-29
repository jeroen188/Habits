package com.primo5.habits;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class DimensionSocial extends Activity {
    final Context context = this;
    static DBAdapter myDb;
    public double dimensionPhysical;
    public double dimensionMental;
    public double dimensionSocial;
    public double dimensionSpirituality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dimension_social);

        openDB();
        dimensionPhysical = myDb.GetTasksCountByCategory("Physical");
        dimensionMental = myDb.GetTasksCountByCategory("Mental");
        dimensionSocial = myDb.GetTasksCountByCategory("Social");
        dimensionSpirituality = myDb.GetTasksCountByCategory("Spiritual");

        final TextView balance= (TextView) findViewById(R.id.balanceSocial);
        balance.setText("Currently " + String.valueOf(dimensionSocial) + " active goals");

        final TextView balancePercentage= (TextView) findViewById(R.id.balanceSocialPercentage);
        double dimensionTotals = Math.ceil(100 *(dimensionSocial / (dimensionMental + dimensionPhysical + dimensionSocial + dimensionSpirituality)));
        balancePercentage.setText("That is " + String.valueOf(dimensionTotals) + "% of your total goals");
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

    public void writingDetail(View view) {
        Intent intent = new Intent(this, DimensionSocial.class);
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
        text.setSelection(1);

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
                //Toast.makeText(getApplicationContext(), "Task added!!! =)",
                // Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    public String getYesterdayDateString() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return dateFormat.format(cal.getTime());
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
}



