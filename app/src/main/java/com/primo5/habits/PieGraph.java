package com.primo5.habits;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

public class PieGraph extends Activity {

    DBAdapter myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

    /*
     * UI Button Callbacks
     */




    public Intent getIntent(Context context) {



        int[] values = {1, 2, 3, 4};
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