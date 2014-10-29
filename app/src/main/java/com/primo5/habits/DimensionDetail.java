package com.primo5.habits;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DimensionDetail extends Activity {
    DBAdapter myDb;
String dimension;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dimension_detail);

        Intent intent = getIntent();
        dimension = intent.getStringExtra("dimensionString");
        Log.d(dimension, "this is the dimension");

        openDB();
        populateListViewFromDB();
        registerListClickCallback();
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




    private void populateListViewFromDB() {
        Cursor cursor = myDb.getAllRows2(dimension);
        ListView myList = (ListView) findViewById(R.id.dimensionView);
        // Allow activity to manage lifetime of the cursor.
        // DEPRECATED! Runs on the UI thread, OK for small/short queries.
        startManagingCursor(cursor);

        // Setup mapping from cursor to view fields:
        String[] fromFieldNames = new String[]
                {DBAdapter.KEY_TASK, DBAdapter.KEY_DIMENSION, DBAdapter.KEY_CLICKED, DBAdapter.KEY_TOTAL, DBAdapter.KEY_STREAK, DBAdapter.KEY_SUBDIMENSION};
        int[] toViewIDs = new int[]
                {R.id.textTask,     R.id.textDimension, R.id.textClicked, R.id.textTotal, R.id.textStreak, R.id.textSubdimension};

        // Create adapter to may columns of the DB onto elements in the UI.
        SimpleCursorAdapter myCursorAdapter =
                new SimpleCursorAdapter(
                        this,		// Context
                        R.layout.activity_daily_detail,	// Row layout template
                        cursor,					// cursor (set of DB records to map)
                        fromFieldNames,			// DB Column names
                        toViewIDs				// View IDs to put information in
                );
        ///Bind view to simpleCursorAdapter
        myCursorAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if (view.getId() == R.id.textClicked)
                {
                    String type = cursor.getString(columnIndex);

                    //((View) view.getParent().getParent()).setBackgroundColor(Color.WHITE );

                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    String currentDateandTime = sdf.format(new Date());



                   // if (type.equals(currentDateandTime)) {
                   //     ((View) view.getParent().getParent()).setBackgroundColor(Color.LTGRAY );
                    //}

                }
                if (view.getId() == R.id.textDimension)
                {
                    String type = cursor.getString(columnIndex);

                    ((View) view.getParent().getParent()).setBackgroundColor(Color.WHITE );

                    if (type.equals("Physical")) {
                        ((View) view.getParent().getParent()).setBackgroundColor(Color.rgb(216,253,210) );
                    }
                    if (type.equals("Mental")) {
                        ((View) view.getParent().getParent()).setBackgroundColor(Color.rgb(255,183,125) );
                    }
                    if (type.equals("Social")) {
                        ((View) view.getParent().getParent()).setBackgroundColor(Color.rgb(254,244,185) );
                    }
                    if (type.equals("Spiritual")) {
                        ((View) view.getParent().getParent()).setBackgroundColor(Color.rgb(173,253,236) );
                    }

                }
                return false;}


        });
        // Set the adapter for the list view
        myList.setAdapter(myCursorAdapter);
    }


    private void registerListClickCallback() {
        ListView myList = (ListView) findViewById(R.id.dimensionView);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                    int position, long idInDB) {


            }
        });
    }







}
