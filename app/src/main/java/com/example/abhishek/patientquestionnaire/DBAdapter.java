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
    public static final int NUM_SCORE_FIELDS = 3;
    public static final int NUM_GDS_FIELDS = 8;
    public static final int NUM_PDQ_FIELDS = 20;
    public static final int NUM_VAHS_FIELDS = 8;

    public static final int ORDER_GDS = 1;
    public static final int ORDER_PDQ = 2;
    public static final int ORDER_VAHS = 3;

    public static final String KEY_FIRSTNAME = "FirstName";
    public static final String KEY_LASTNAME = "LastName";
    public static final String KEY_HOSPITALID = "HospitalId";
    public static final String KEY_GDS_SCORE = "GDSscore";
    public static final String KEY_PDQ_SCORE = "PDQscore";
    public static final String KEY_VAHS_SCORE = "VAHSscore";

    // TODO: Setup your identification fields here:

    public static final String KEY_GDS1 = "GDS1";
    public static final String KEY_GDS2 = "GDS2";
    public static final String KEY_GDS3 = "GDS3";
    public static final String KEY_GDS4 = "GDS4";
    public static final String KEY_GDS5 = "GDS5";
    public static final String KEY_GDS6 = "GDS6";
    public static final String KEY_GDS7 = "GDS7";
    public static final String KEY_GDS8 = "GDS8";

    public static final String KEY_PDQ1 = "PDQ1";
    public static final String KEY_PDQ2 = "PDQ2";
    public static final String KEY_PDQ3 = "PDQ3";
    public static final String KEY_PDQ4 = "PDQ4";
    public static final String KEY_PDQ5 = "PDQ5";
    public static final String KEY_PDQ6 = "PDQ6";
    public static final String KEY_PDQ7 = "PDQ7";
    public static final String KEY_PDQ8 = "PDQ8";
    public static final String KEY_PDQ9 = "PDQ9";
    public static final String KEY_PDQ10 = "PDQ10";
    public static final String KEY_PDQ11 = "PDQ11";
    public static final String KEY_PDQ12 = "PDQ12";
    public static final String KEY_PDQ13 = "PDQ13";
    public static final String KEY_PDQ14 = "PDQ14";
    public static final String KEY_PDQ15 = "PDQ15";
    public static final String KEY_PDQ16 = "PDQ16";
    public static final String KEY_PDQ17 = "PDQ17";
    public static final String KEY_PDQ18 = "PDQ18";
    public static final String KEY_PDQ19 = "PDQ19";
    public static final String KEY_PDQ20 = "PDQ20";

    public static final String KEY_VAHS1 = "VAHS1";
    public static final String KEY_VAHS2 = "VAHS2";
    public static final String KEY_VAHS3 = "VAHS3";
    public static final String KEY_VAHS4 = "VAHS4";
    public static final String KEY_VAHS5 = "VAHS5";
    public static final String KEY_VAHS6 = "VAHS6";
    public static final String KEY_VAHS7 = "VAHS7";
    public static final String KEY_VAHS8 = "VAHS8";

    // TODO: Setup your data field numbers here (0 = KEY_ROWID, 1=...)
    public static final int COL_FIRSTNAME = 1;
    public static final int COL_LASTNAME = 2;
    public static final int COL_HOSPITALID = 3;
    public static final int COL_GDS_SCORE = 4;
    public static final int COL_PDQ_SCORE = 5;
    public static final int COL_VAHS_SCORE = 6;

    public static final int COL_GDS1 = 1 + NUM_ID_FIELDS + NUM_SCORE_FIELDS;
    public static final int COL_GDS2 = 2 + NUM_ID_FIELDS + NUM_SCORE_FIELDS;
    public static final int COL_GDS3 = 3 + NUM_ID_FIELDS + NUM_SCORE_FIELDS;
    public static final int COL_GDS4 = 4 + NUM_ID_FIELDS + NUM_SCORE_FIELDS;
    public static final int COL_GDS5 = 5 + NUM_ID_FIELDS + NUM_SCORE_FIELDS;
    public static final int COL_GDS6 = 6 + NUM_ID_FIELDS + NUM_SCORE_FIELDS;
    public static final int COL_GDS7 = 7 + NUM_ID_FIELDS + NUM_SCORE_FIELDS;
    public static final int COL_GDS8 = 8 + NUM_ID_FIELDS + NUM_SCORE_FIELDS;


    public static final int COL_PDQ1 = 1 + NUM_ID_FIELDS + NUM_SCORE_FIELDS + NUM_GDS_FIELDS;
    public static final int COL_PDQ2 = 2 + NUM_ID_FIELDS + NUM_SCORE_FIELDS + NUM_GDS_FIELDS;
    public static final int COL_PDQ3 = 3 + NUM_ID_FIELDS + NUM_SCORE_FIELDS + NUM_GDS_FIELDS;
    public static final int COL_PDQ4 = 4 + NUM_ID_FIELDS + NUM_SCORE_FIELDS + NUM_GDS_FIELDS;
    public static final int COL_PDQ5 = 5 + NUM_ID_FIELDS + NUM_SCORE_FIELDS + NUM_GDS_FIELDS;
    public static final int COL_PDQ6 = 6 + NUM_ID_FIELDS + NUM_SCORE_FIELDS + NUM_GDS_FIELDS;
    public static final int COL_PDQ7 = 7 + NUM_ID_FIELDS + NUM_SCORE_FIELDS + NUM_GDS_FIELDS;
    public static final int COL_PDQ8 = 8 + NUM_ID_FIELDS + NUM_SCORE_FIELDS + NUM_GDS_FIELDS;
    public static final int COL_PDQ9 = 9 + NUM_ID_FIELDS + NUM_SCORE_FIELDS + NUM_GDS_FIELDS;
    public static final int COL_PDQ10 = 10 + NUM_ID_FIELDS + NUM_SCORE_FIELDS + NUM_GDS_FIELDS;
    public static final int COL_PDQ11 = 11 + NUM_ID_FIELDS + NUM_SCORE_FIELDS + NUM_GDS_FIELDS;
    public static final int COL_PDQ12 = 12 + NUM_ID_FIELDS + NUM_SCORE_FIELDS + NUM_GDS_FIELDS;
    public static final int COL_PDQ13 = 13 + NUM_ID_FIELDS + NUM_SCORE_FIELDS + NUM_GDS_FIELDS;
    public static final int COL_PDQ14 = 14 + NUM_ID_FIELDS + NUM_SCORE_FIELDS + NUM_GDS_FIELDS;
    public static final int COL_PDQ15 = 15 + NUM_ID_FIELDS + NUM_SCORE_FIELDS + NUM_GDS_FIELDS;
    public static final int COL_PDQ16 = 16 + NUM_ID_FIELDS + NUM_SCORE_FIELDS + NUM_GDS_FIELDS;
    public static final int COL_PDQ17 = 17 + NUM_ID_FIELDS + NUM_SCORE_FIELDS + NUM_GDS_FIELDS;
    public static final int COL_PDQ18 = 18 + NUM_ID_FIELDS + NUM_SCORE_FIELDS + NUM_GDS_FIELDS;
    public static final int COL_PDQ19 = 19 + NUM_ID_FIELDS + NUM_SCORE_FIELDS + NUM_GDS_FIELDS;
    public static final int COL_PDQ20 = 20 + NUM_ID_FIELDS + NUM_SCORE_FIELDS + NUM_GDS_FIELDS;

    public static final int COL_VAHS1 = 1 + NUM_ID_FIELDS + NUM_SCORE_FIELDS + NUM_GDS_FIELDS + NUM_PDQ_FIELDS;
    public static final int COL_VAHS2 = 2 + NUM_ID_FIELDS + NUM_SCORE_FIELDS + NUM_GDS_FIELDS + NUM_PDQ_FIELDS;
    public static final int COL_VAHS3 = 3 + NUM_ID_FIELDS + NUM_SCORE_FIELDS + NUM_GDS_FIELDS + NUM_PDQ_FIELDS;
    public static final int COL_VAHS4 = 4 + NUM_ID_FIELDS + NUM_SCORE_FIELDS + NUM_GDS_FIELDS + NUM_PDQ_FIELDS;
    public static final int COL_VAHS5 = 5 + NUM_ID_FIELDS + NUM_SCORE_FIELDS + NUM_GDS_FIELDS + NUM_PDQ_FIELDS;
    public static final int COL_VAHS6 = 6 + NUM_ID_FIELDS + NUM_SCORE_FIELDS + NUM_GDS_FIELDS + NUM_PDQ_FIELDS;
    public static final int COL_VAHS7 = 7 + NUM_ID_FIELDS + NUM_SCORE_FIELDS + NUM_GDS_FIELDS + NUM_PDQ_FIELDS;
    public static final int COL_VAHS8 = 8 + NUM_ID_FIELDS + NUM_SCORE_FIELDS + NUM_GDS_FIELDS + NUM_PDQ_FIELDS;




    //TODO: Set all keys for data table
    public static final String[] ALL_DATA_KEYS = new String[] {KEY_ROWID, KEY_FIRSTNAME, KEY_LASTNAME, KEY_HOSPITALID,
            KEY_GDS_SCORE, KEY_PDQ_SCORE, KEY_VAHS_SCORE,
            KEY_GDS1, KEY_GDS2, KEY_GDS3, KEY_GDS4, KEY_GDS5, KEY_GDS6, KEY_GDS7, KEY_GDS8,
            KEY_PDQ1, KEY_PDQ2, KEY_PDQ3, KEY_PDQ4, KEY_PDQ5, KEY_PDQ6, KEY_PDQ7, KEY_PDQ8, KEY_PDQ9, KEY_PDQ10, KEY_PDQ11, KEY_PDQ12, KEY_PDQ13, KEY_PDQ14, KEY_PDQ15, KEY_PDQ16, KEY_PDQ17, KEY_PDQ18, KEY_PDQ19, KEY_PDQ20,
            KEY_VAHS1, KEY_VAHS2, KEY_VAHS3, KEY_VAHS4, KEY_VAHS5, KEY_VAHS6, KEY_VAHS7, KEY_VAHS8
    };

    // DB info: its name, and the tables we are using
    public static final String DATABASE_NAME = "PatientQuestionnaire";
    public static final String DATA_TABLE = "dataTable";

    // Track DB version if a new version of your app changes the format.
    public static final int DATABASE_VERSION = 16;


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

                    + KEY_GDS_SCORE + " text, "
                    + KEY_PDQ_SCORE + " text, "
                    + KEY_VAHS_SCORE + " text, "

                    + KEY_GDS1 + " text, "
                    + KEY_GDS2 + " text, "
                    + KEY_GDS3 + " text, "
                    + KEY_GDS4 + " text, "
                    + KEY_GDS5 + " text, "
                    + KEY_GDS6 + " text, "
                    + KEY_GDS7 + " text, "
                    + KEY_GDS8 + " text, "

                    + KEY_PDQ1 + " text, "
                    + KEY_PDQ2 + " text, "
                    + KEY_PDQ3 + " text, "
                    + KEY_PDQ4 + " text, "
                    + KEY_PDQ5 + " text, "
                    + KEY_PDQ6 + " text, "
                    + KEY_PDQ7 + " text, "
                    + KEY_PDQ8 + " text, "
                    + KEY_PDQ9 + " text, "
                    + KEY_PDQ10 + " text, "
                    + KEY_PDQ11 + " text, "
                    + KEY_PDQ12 + " text, "
                    + KEY_PDQ13 + " text, "
                    + KEY_PDQ14 + " text, "
                    + KEY_PDQ15 + " text, "
                    + KEY_PDQ16 + " text, "
                    + KEY_PDQ17 + " text, "
                    + KEY_PDQ18 + " text, "
                    + KEY_PDQ19 + " text, "
                    + KEY_PDQ20 + " text, "

                    + KEY_VAHS1 + " text, "
                    + KEY_VAHS2 + " text, "
                    + KEY_VAHS3 + " text, "
                    + KEY_VAHS4 + " text, "
                    + KEY_VAHS5 + " text, "
                    + KEY_VAHS6 + " text, "
                    + KEY_VAHS7 + " text, "
                    + KEY_VAHS8 + " text"


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
    public boolean updateAnswer(long rowId, int sqlColumn, String answer) {

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
        String key = c.getColumnName(sqlColumn);

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
            Log.v(TAG,DATA_CREATE_SQL);
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
