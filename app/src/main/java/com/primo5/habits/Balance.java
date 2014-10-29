package com.primo5.habits;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;



public class Balance extends Activity {
    static DBAdapter myDb;
    public int dimensionPhysical;
    public int dimensionMental;
    public int dimensionSocial;
    public int dimensionSpirituality;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);

        openDB();

        dimensionPhysical = myDb.GetTasksCountByCategory("Physical");
        dimensionMental = myDb.GetTasksCountByCategory("Mental");
        dimensionSocial = myDb.GetTasksCountByCategory("Social");
        dimensionSpirituality = myDb.GetTasksCountByCategory("Spiritual");
        openChart();
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

    /*
     * UI Button Callbacks
     */

    public void onClick_ClearAll(View v) {
        myDb.deleteAll();
    }


    private void openChart() {

        // Pie Chart Section Names
        String[] code = new String[]{
                "Physical", "Mental", "Social", "Spirituality"
        };

        // Pie Chart Section Value
        int[] distribution = {dimensionPhysical, dimensionMental, dimensionSocial, dimensionSpirituality};

        // Color of each Pie Chart Sections
        int[] colors = {Color.rgb(216,253,210), Color.rgb(255,183,125), Color.rgb(254,244,185), Color.rgb(173,253,236)};

        // Instantiating CategorySeries to plot Pie Chart
        CategorySeries distributionSeries = new CategorySeries(" Android version distribution as on October 1, 2012");
        for (int i = 0; i < distribution.length; i++) {
            // Adding a slice with its values and name to the Pie Chart
            distributionSeries.add(code[i], distribution[i]);

            }

        // Instantiating a renderer for the Pie Chart
        DefaultRenderer defaultRenderer = new DefaultRenderer();
        for (int i = 0; i < distribution.length; i++) {
            SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
            seriesRenderer.setColor(colors[i]);
            seriesRenderer.setDisplayChartValues(true);
            // Adding a renderer for a slice
            defaultRenderer.addSeriesRenderer(seriesRenderer);

            defaultRenderer.setLabelsTextSize(60);
            defaultRenderer.setLegendTextSize(40);
                }

        defaultRenderer.setChartTitle("Android version distribution as on October 1, 2012 ");
        defaultRenderer.setChartTitleTextSize(20);
        defaultRenderer.setZoomButtonsVisible(true);


        // Creating an intent to plot bar chart using dataset and multipleRenderer
        Intent intent = ChartFactory.getPieChartIntent(getBaseContext(), distributionSeries, defaultRenderer, "Your LifeBalance");

        // Start Activity
        startActivity(intent);

    }
}


