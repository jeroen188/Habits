package com.primo5.habits;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class DimensionPhysical extends Activity {
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
        double dimensionTotals = 100 *(dimensionPhysical / (dimensionMental + dimensionPhysical + dimensionSocial + dimensionSpirituality));
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
        Intent intent = new Intent(this, DimensionPhysical.class);
        startActivity(intent);
    }
    public void addTask(View view) {
        Intent intent = new Intent(this, NewTask.class);
        startActivity(intent);
    }

}