package com.primo5.habits;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DailyTasks extends Activity {
//
    DBAdapter myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_tasks);

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

    public void onClick_ClearAll(View v) {
        myDb.deleteAll();
        populateListViewFromDB();
    }


    private void populateListViewFromDB() {
        Cursor cursor = myDb.getAllRows();

        // Allow activity to manage lifetime of the cursor.
        // DEPRECATED! Runs on the UI thread, OK for small/short queries.
        startManagingCursor(cursor);

        // Setup mapping from cursor to view fields:
        String[] fromFieldNames = new String[]
                {DBAdapter.KEY_TASK, DBAdapter.KEY_DIMENSION, DBAdapter.KEY_MONTH};
        int[] toViewIDs = new int[]
                {R.id.textTask,     R.id.textDimension,           R.id.textMonth};

        // Create adapter to may columns of the DB onto elemesnt in the UI.
        SimpleCursorAdapter myCursorAdapter =
                new SimpleCursorAdapter(
                        this,		// Context
                        R.layout.activity_daily_detail,	// Row layout template
                        cursor,					// cursor (set of DB records to map)
                        fromFieldNames,			// DB Column names
                        toViewIDs				// View IDs to put information in
                );


        // Set the adapter for the list view
        ListView myList = (ListView) findViewById(R.id.taskView);
        myList.setAdapter(myCursorAdapter);


}

    private void registerListClickCallback() {
        ListView myList = (ListView) findViewById(R.id.taskView);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                    int position, long idInDB) {

                updateItemForId(idInDB);
                displayToastForId(idInDB);
            }
        });
    }

    private void updateItemForId(long idInDB) {
        Cursor cursor = myDb.getRow(idInDB);
        if (cursor.moveToFirst()) {
            long idDB = cursor.getLong(DBAdapter.COL_ROWID);
            String task = cursor.getString(DBAdapter.COL_TASK);
            String dimension = cursor.getString(DBAdapter.COL_DIMENSION);
            String month = cursor.getString(DBAdapter.COL_MONTH);

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String currentDateandTime = sdf.format(new Date());
            myDb.updateRow(idInDB, task, dimension, currentDateandTime);
        }
        cursor.close();
        populateListViewFromDB();
    }

    private void displayToastForId(long idInDB) {
        Cursor cursor = myDb.getRow(idInDB);
        if (cursor.moveToFirst()) {
            long idDB = cursor.getLong(DBAdapter.COL_ROWID);
            String task = cursor.getString(DBAdapter.COL_TASK);
            String dimension = cursor.getString(DBAdapter.COL_DIMENSION);
            String month = cursor.getString(DBAdapter.COL_MONTH);

            String message = "ID: " + idDB + "\n"
                    + "Task: " + task + "\n"
                    + "Dimension: " + dimension + "\n"
                    + "Month: " + month;
            Toast.makeText(DailyTasks.this, message, Toast.LENGTH_LONG).show();
        }
        cursor.close();
    }
}

