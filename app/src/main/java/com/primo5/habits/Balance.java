package com.primo5.habits;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
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
 public int dimensionPhysical= 1;
    public int dimensionMental= 1;
    public int dimensionSocial= 1;
    public int dimensionSpirituality= 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        openDB();
        dimensionPhysical = myDb.getCount("Physical");
        dimensionMental = myDb.getCount("Mental");
        dimensionSocial = myDb.getCount("Social");
        dimensionSpirituality = myDb.getCount("Spiritual");
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



    public Intent getIntent(Context context) {




        int[] values = {dimensionPhysical, dimensionMental, dimensionSocial, dimensionSpirituality};
        CategorySeries series = new CategorySeries("Pie Graph");
        int k = 0;
        for (int value : values) {
            series.add("Section " + ++k, value);
        }

        int[] colors = new int[]{Color.rgb(144, 238, 144), Color.rgb(173,216,230), Color.rgb(255,204,153), Color.rgb(240,204,153)};

        DefaultRenderer renderer = new DefaultRenderer();
        for (int color : colors) {
            SimpleSeriesRenderer r = new SimpleSeriesRenderer();
            r.setColor(color);
            renderer.addSeriesRenderer(r);
        }
        renderer.setChartTitle("Pie Chart Demo");
        renderer.setChartTitleTextSize(7);
        renderer.setZoomButtonsVisible(true);

        Intent intent = ChartFactory.getPieChartIntent(context, series, renderer, "Pie");
        return intent;
    }
}
