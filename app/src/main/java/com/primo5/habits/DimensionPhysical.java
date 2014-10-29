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


public class DimensionPhysical extends Activity {
    final Context context = this;
    static DBAdapter myDb;
    public double dimensionPhysical;
    public double dimensionMental;
    public double dimensionSocial;
    public double dimensionSpirituality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dimension_physical);

        openDB();
        dimensionPhysical = myDb.GetTasksCountByCategory("Physical");
        dimensionMental = myDb.GetTasksCountByCategory("Mental");
        dimensionSocial = myDb.GetTasksCountByCategory("Social");
        dimensionSpirituality = myDb.GetTasksCountByCategory("Spiritual");

        final TextView balance= (TextView) findViewById(R.id.balancePhysical);
        balance.setText("Currently " + String.valueOf(dimensionPhysical) + " active goals");

        final TextView balancePercentage= (TextView) findViewById(R.id.balancePhysicalPercentage);
        double dimensionTotals = 100 *Math.ceil((dimensionPhysical / (dimensionMental + dimensionPhysical + dimensionSocial + dimensionSpirituality)));
        balancePercentage.setText("That is " + String.valueOf(dimensionTotals) + "% of your total goals");

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
                //Toast.makeText(getApplicationContext(), "Task added!!! =)",
                // Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });

        dialog.show();
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
