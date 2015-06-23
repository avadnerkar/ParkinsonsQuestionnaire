package com.example.abhishek.patientquestionnaire;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Abhishek on 22/06/2015.
 */
public class DBAdapter {

    /////////////////////////////////////////////////////////////////////
    //	Constants & Data
    /////////////////////////////////////////////////////////////////////
    // For logging:
    private static final String TAG = "DBAdapter";


    // Common fields Fields
    public static final String KEY_ROWID = "_id";
    public static final int COL_ROWID = 0;


    // TODO: Setup your identification fields here:
    public static final int NUM_ID_FIELDS = 3;
    public static final String KEY_FIRSTNAME = "FirstName";
    public static final String KEY_LASTNAME = "LastName";
    public static final String KEY_HOSPITALID = "HospitalId";


    // TODO: Setup your identification fields here:
    public static final String KEY_Q1 = "Q1";
    public static final String KEY_Q2 = "Q2";
    public static final String KEY_Q3 = "Q3";
    public static final String KEY_Q4 = "Q4";
    public static final String KEY_Q5 = "Q5";
    public static final String KEY_Q6 = "Q6";
    public static final String KEY_Q7 = "Q7";
    public static final String KEY_Q8 = "Q8";

    // TODO: Setup your data field numbers here (0 = KEY_ROWID, 1=...)
    public static final int COL_FIRSTNAME = 1;
    public static final int COL_LASTNAME = 2;
    public static final int COL_HOSPITALID = 3;
    public static final int COL_Q1 = 1 + NUM_ID_FIELDS;
    public static final int COL_Q2 = 2 + NUM_ID_FIELDS;
    public static final int COL_Q3 = 3 + NUM_ID_FIELDS;
    public static final int COL_Q4 = 4 + NUM_ID_FIELDS;
    public static final int COL_Q5 = 5 + NUM_ID_FIELDS;
    public static final int COL_Q6 = 6 + NUM_ID_FIELDS;
    public static final int COL_Q7 = 7 + NUM_ID_FIELDS;
    public static final int COL_Q8 = 8 + NUM_ID_FIELDS;


    //TODO: Set all keys for data table
    public static final String[] ALL_DATA_KEYS = new String[] {KEY_ROWID, KEY_FIRSTNAME, KEY_LASTNAME, KEY_HOSPITALID,
            KEY_Q1, KEY_Q2, KEY_Q3, KEY_Q4, KEY_Q5, KEY_Q6, KEY_Q7, KEY_Q8};

    // DB info: its name, and the tables we are using
    public static final String DATABASE_NAME = "PatientQuestionnaire";
    public static final String DATA_TABLE = "dataTable";

    // Track DB version if a new version of your app changes the format.
    public static final int DATABASE_VERSION = 4;


    //Table Create Statements

    private static final String DATA_CREATE_SQL =
            "create table " + DATA_TABLE
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
                    // NOTE: All must be comma separated (end of line!) Last one must have NO comma!

                    + KEY_FIRSTNAME + " text, "
                    + KEY_LASTNAME + " text, "
                    + KEY_HOSPITALID + " text, "
                    + KEY_Q1 + " text, "
                    + KEY_Q2 + " text, "
                    + KEY_Q3 + " text, "
                    + KEY_Q4 + " text, "
                    + KEY_Q5 + " text, "
                    + KEY_Q6 + " text, "
                    + KEY_Q7 + " text, "
                    + KEY_Q8 + " text"


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


    /////////////////////////////////////////////////////////////////////
    //	Data methods:
    /////////////////////////////////////////////////////////////////////

    // Add a new set of values to the database.


    public long createData(String firstName, String lastName, String hospitalId){
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_FIRSTNAME, firstName);
        initialValues.put(KEY_LASTNAME, lastName);
        initialValues.put(KEY_HOSPITALID, hospitalId);

        return db.insert(DATA_TABLE, null, initialValues);
    }

    // Delete a row from the database, by rowId (primary key)
    public boolean deleteRowData(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        return db.delete(DATA_TABLE, where, null) != 0;
    }

    public void deleteAllData() {
        Cursor c = getAllRowData();
        long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
        if (c.moveToFirst()) {
            do {
                deleteRowData(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }

    // Return all data in the database.
    public Cursor getAllRowData() {
        String where = null;
        Cursor c = 	db.query(true, DATA_TABLE, ALL_DATA_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Get a specific row (by rowId)
    // Get a specific row (by rowId)
    public Cursor getRowData(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        Cursor c = 	db.query(true, DATA_TABLE, ALL_DATA_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Change an existing row to be equal to new data.
    public boolean updateAnswer(long rowId, int questionNum, String answer) {

		/*
		 * CHANGE 4:
		 */

        String where = KEY_ROWID + "=" + rowId;

		/*
		 * CHANGE 4:
		 */

        // Create row's data:
        ContentValues newValues = new ContentValues();
        Cursor c = getRowData(rowId);
        String key = c.getColumnName(questionNum + NUM_ID_FIELDS);

        newValues.put(key, answer);

        // Insert it into the database.
        return db.update(DATA_TABLE, newValues, where, null) != 0;
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
            _db.execSQL(DATA_CREATE_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading application's database from version " + oldVersion
                    + " to " + newVersion + ", which will destroy all old data!");

            // Destroy old database:
            _db.execSQL("DROP TABLE IF EXISTS " + DATA_TABLE);

            // Recreate new database:
            onCreate(_db);
        }
    }

}
