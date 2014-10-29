package com.primo5.habits;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;


// TO USE:
// Change the package (at top) to match your project.
// Search for "TODO", and make the appropriate changes.
public class DBAdapter {

    /////////////////////////////////////////////////////////////////////
    //	Constants & Data
    /////////////////////////////////////////////////////////////////////
    // For logging:
    private static final String TAG = "DBAdapter";

    // DB Fields
    public static final String KEY_ROWID = "_id";
    public static final int COL_ROWID = 0;
    /*
     * CHANGE 1:
     */
    // TODO: Setup your fields here:
    public static final String KEY_TASK = "task";
    public static final String KEY_DIMENSION = "dimension";
    public static final String KEY_CLICKED = "clicked";
    public static final String KEY_TOTAL = "total";
    public static final String KEY_STREAK = "streak";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_SUBDIMENSION = "subdimension";

    // TODO: Setup your field numbers here (0 = KEY_ROWID, 1=...)
    public static final int COL_TASK = 1;
    public static final int COL_DIMENSION = 2;
    public static final int COL_CLICKED = 3;
    public static final int COL_TOTAL = 4;
    public static final int COL_STREAK = 5;
    public static final int COL_DESCRIPTION = 6;
    public static final int COL_SUBDIMENSION = 7;

    public static final String[] ALL_KEYS = new String[] {KEY_ROWID, KEY_TASK, KEY_DIMENSION, KEY_CLICKED, KEY_TOTAL, KEY_STREAK, KEY_DESCRIPTION, KEY_SUBDIMENSION};

    // DB info: it's name, and the table we are using (just one).
    public static final String DATABASE_NAME = "MyDb";
    public static final String DATABASE_TABLE = "mainTable";
    // Track DB version if a new version of your app changes the format.
    public static final int DATABASE_VERSION = 11;

    private static final String DATABASE_CREATE_SQL =
            "create table " + DATABASE_TABLE
                    + " (" + KEY_ROWID + " integer primary key autoincrement, "

			/*
			 * CHANGE 2:
			 */
                    // TODO: Place your fields here!
                    // + KEY_{...} + " {type} not null"
                    //	- Key is the column name you created above.
                    //	- {type} is one of: text, integer, real, blob
                    //		(http://www.sqlite.org/datatype3.html)
                    //  - "not null" means it is a required field (must be given a value).
                    // NOTE: All must be comma separated (end of line!) Last one must have NO comma!!
                    + KEY_TASK + " text not null, "
                    + KEY_DIMENSION + " string not null, "
                    + KEY_CLICKED + " string not null, "
                    + KEY_TOTAL + " integer not null, "
                    + KEY_STREAK + " integer not null, "
                    + KEY_DESCRIPTION + " string not null, "
                    + KEY_SUBDIMENSION + " string not null"

                    // Rest  of creation:
                    + ");";

    // Context of application who uses us.
    private final Context context;

    private DatabaseHelper myDBHelper;
    private SQLiteDatabase db;

    /////////////////////////////////////////////////////////////////////
    //	Public methods:
    /////////////////////////////////////////////////////////////////////

    public DBAdapter(Context ctx) {
        this.context = ctx;
        myDBHelper = new DatabaseHelper(context);
    }

    // Open the database connection.
    public DBAdapter open() {
        db = myDBHelper.getWritableDatabase();
        return this;
    }

    // Close the database connection.
    public void close() {
        myDBHelper.close();
    }

    // Add a new set of values to the database.
    public long insertRow(String task, String dimension, String clicked, int total, int streak, String description, String subdimension) {
		/*
		 * CHANGE 3:
		 */
        // TODO: Update data in the row with new fields.
        // TODO: Also change the function's arguments to be what you need!
        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TASK, task);
        initialValues.put(KEY_DIMENSION, dimension);
        initialValues.put(KEY_CLICKED, clicked);
        initialValues.put(KEY_TOTAL, total);
        initialValues.put(KEY_STREAK, streak);
        initialValues.put(KEY_DESCRIPTION, description);
        initialValues.put(KEY_SUBDIMENSION, subdimension);

        // Insert it into the database.
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    // Delete a row from the database, by rowId (primary key)
    public boolean deleteRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        return db.delete(DATABASE_TABLE, where, null) != 0;
    }

    public void deleteAll() {
        Cursor c = getAllRows();
        long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
        if (c.moveToFirst()) {
            do {
                deleteRow(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }

    // Return all data in the database.
    public Cursor getAllRows2() {
        String where = null;
        Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    //try 1
    public int getCount(String rowId) {
     Cursor C =  db.rawQuery("select count {*} from mainTable where " + KEY_DIMENSION + " = " + rowId, null);
    return C.getCount();
    }
    //try 2
    public int GetTasksCountByCategory(String cat)
    {

        Cursor cur = db.rawQuery("SELECT Count(*) FROM mainTable where dimension = ?", new String[] { cat } );
        int x = 0;
        if (cur.moveToFirst())
        {
            x = cur.getInt(0);
        }

        cur.close();
        return x;
    }

    public Cursor getAllRows() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String today = sdf.format(new Date());
        String dimension= today;
        String where = KEY_CLICKED + " !=?";
        String[] args = { dimension };
        Log.d(dimension,"the date of today");
        Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS,
                where, args, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;

    }

    public Cursor getAllRows2(String dimension) {
        String where = KEY_DIMENSION + " =?";
        String[] args = { dimension };
        Log.d(dimension,"the dimension");
        Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS,
                where, args, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;

    }




    // Get a specific row (by rowId)
    public Cursor getRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Change an existing row to be equal to new data.
    public boolean updateRow(long rowId, String task, String dimension, String clicked, int total, int streak, String description, String subdimension) {
        String where = KEY_ROWID + "=" + rowId;

		/*
		 * CHANGE 4:
		 */
        // TODO: Update data in the row with new fields.
        // TODO: Also change the function's arguments to be what you need!
        // Create row's data:
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_TASK, task);
        newValues.put(KEY_DIMENSION, dimension);
        newValues.put(KEY_CLICKED, clicked);
        newValues.put(KEY_TOTAL, total);
        newValues.put(KEY_STREAK, streak);
        newValues.put(KEY_DESCRIPTION, description);
        newValues.put(KEY_SUBDIMENSION, subdimension);

        // Insert it into the database.
        return db.update(DATABASE_TABLE, newValues, where, null) != 0;
    }



    /////////////////////////////////////////////////////////////////////
    //	Private Helper Classes:
    /////////////////////////////////////////////////////////////////////

    /**
     * Private class which handles database creation and upgrading.
     * Used to handle low-level database access.
     */
    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(DATABASE_CREATE_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading application's database from version " + oldVersion
                    + " to " + newVersion + ", which will destroy all old data!");

            // Destroy old database:
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);

            // Recreate new database:
            onCreate(_db);
        }
    }
}

