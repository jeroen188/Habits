package com.primo5.habits;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
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




    private void populateListViewFromDB() {
        Cursor cursor = myDb.getAllRows();
        ListView myList = (ListView) findViewById(R.id.taskView);
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



                    if (type.equals(currentDateandTime)) {
                        ((View) view.getParent().getParent()).setBackgroundColor(Color.LTGRAY );
                    }

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
            String clicked = cursor.getString(DBAdapter.COL_CLICKED);
            int total = cursor.getInt(DBAdapter.COL_TOTAL);
            int streak = cursor.getInt(DBAdapter.COL_STREAK);
            String description = cursor.getString(DBAdapter.COL_DESCRIPTION);
            String subdimension = cursor.getString(DBAdapter.COL_SUBDIMENSION);


            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String datetoday = df.format(new Date());
            total= total + 1;
            if(getYesterdayDateString() == clicked) {
                streak = streak + 1;
            }
            else {
                streak = 0;
            }

            myDb.updateRow(idInDB, task, dimension, datetoday, total, streak, description, subdimension);
        }
        cursor.close();
        populateListViewFromDB();
    }

    public String getYesterdayDateString() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return dateFormat.format(cal.getTime());
    }

    private void displayToastForId(long idInDB) {
        Cursor cursor = myDb.getRow(idInDB);
        if (cursor.moveToFirst()) {
            long idDB = cursor.getLong(DBAdapter.COL_ROWID);
            String task = cursor.getString(DBAdapter.COL_TASK);
            String dimension = cursor.getString(DBAdapter.COL_DIMENSION);
            String description = cursor.getString(DBAdapter.COL_DESCRIPTION);
            String subdimension = cursor.getString(DBAdapter.COL_SUBDIMENSION);
            String total = cursor.getString(DBAdapter.COL_TOTAL);
            String streak = cursor.getString(DBAdapter.COL_STREAK);

            // get your custom_toast.xml layout
            LayoutInflater inflater = getLayoutInflater();

            View layout = inflater.inflate(R.layout.custom_toast,
                    (ViewGroup) findViewById(R.id.custom_toast_layout_id));

            // set a dummy image
            ImageView image = (ImageView) layout.findViewById(R.id.image);
            image.setImageResource(R.drawable.ic_check_image);

            // set a message
            TextView text = (TextView) layout.findViewById(R.id.textTotal);
            text.setText("Well done!"+"\n"+
                    "Total check ins: "+total+ "\n"
                   +"Streak: "+streak);

            // Toast...
            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();

            /*String message = "ID: " + idDB + "\n"
                    + "Task: " + task + "\n"
                    + "Dimension: " + dimension + "\n"
                    + "Subdimension: " + subdimension + "\n"
                    + "Description: " + description;
            Toast.makeText(DailyTasks.this, message, Toast.LENGTH_LONG).show();*/
        }
        cursor.close();
    }


   // We don't use the code below but it might become useful in the future so I keep it there

    public void stringDate(String tempdate) throws ParseException{

        DateFormat myDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date tempDate = myDateFormat.parse("24/12/2011");
    }


    public void printDifference(Date startDate, Date endDate){

        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : "+ endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays,
                elapsedHours, elapsedMinutes, elapsedSeconds);

    }
}

