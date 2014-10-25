package com.primo5.habits;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
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
        ListView myList = (ListView) findViewById(R.id.taskView);
        // Allow activity to manage lifetime of the cursor.
        // DEPRECATED! Runs on the UI thread, OK for small/short queries.
        startManagingCursor(cursor);

        // Setup mapping from cursor to view fields:
        String[] fromFieldNames = new String[]
                {DBAdapter.KEY_TASK, DBAdapter.KEY_DIMENSION, DBAdapter.KEY_MONTH, DBAdapter.KEY_TOTAL};
        int[] toViewIDs = new int[]
                {R.id.textTask,     R.id.textDimension, R.id.textMonth, R.id.textTotal};

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
                if (view.getId() == R.id.textMonth)
                {
                    String type = cursor.getString(columnIndex);

                    ((View) view.getParent().getParent()).setBackgroundColor(Color.WHITE );    // I added this line

                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    String currentDateandTime = sdf.format(new Date());

                    if (type.equals(currentDateandTime)) {
                        ((View) view.getParent().getParent()).setBackgroundColor(Color.LTGRAY );
                    }

                }
                return false;}

        });
        // Set the adapter for the list view
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
            int total = cursor.getInt(DBAdapter.COL_TOTAL);


            //what is today's date?
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String currentDateandTime = sdf.format(new Date());

            //check if update date is today. If not add +1 to total
            if(month== currentDateandTime){
                //do nothing
            }
            else {
            month = month +1;
            }
            myDb.updateRow(idInDB, task, dimension, currentDateandTime, total);
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

