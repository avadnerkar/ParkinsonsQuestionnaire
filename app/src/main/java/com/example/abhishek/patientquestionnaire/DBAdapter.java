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

    public static final String KEY_FIRSTNAME = "FirstName";
    public static final String KEY_LASTNAME = "LastName";
    public static final String KEY_HOSPITALID = "HospitalId";
    public static final String KEY_PGI_SCORE = "PGIscore";
    public static final String KEY_GDS_SCORE = "GDSscore";
    public static final String KEY_PDQ20_SCORE = "PDQ20score";
    public static final String KEY_VAHS_SCORE = "VAHSscore";
    public static final String KEY_SF36_SCORE = "SF36score";
    public static final String KEY_SHAPS_SCORE = "SHAPSscore";
    public static final String KEY_NMS_SCORE = "NMSscore";
    public static final String KEY_LSM_SCORE = "LSMscore";
    public static final String KEY_PASA_SCORE = "PASAscore";
    public static final String KEY_PASB_SCORE = "PASBscore";
    public static final String KEY_PASC_SCORE = "PASCscore";
    public static final String KEY_AS_SCORE = "ASscore";
    public static final String KEY_PDQ8_SCORE = "PDQ8Score";

    // TODO: Setup your identification fields here:

    public static final String KEY_PGI_AREA1 = "PGIArea1";
    public static final String KEY_PGI_SCORE1 = "PGIScore1";
    public static final String KEY_PGI_POINTS1 = "PGIPoints1";
    public static final String KEY_PGI_AREA2 = "PGIArea2";
    public static final String KEY_PGI_SCORE2 = "PGIScore2";
    public static final String KEY_PGI_POINTS2 = "PGIPoints2";
    public static final String KEY_PGI_AREA3 = "PGIArea3";
    public static final String KEY_PGI_SCORE3 = "PGIScore3";
    public static final String KEY_PGI_POINTS3 = "PGIPoints3";
    public static final String KEY_PGI_AREA4 = "PGIArea4";
    public static final String KEY_PGI_SCORE4 = "PGIScore4";
    public static final String KEY_PGI_POINTS4 = "PGIPoints4";
    public static final String KEY_PGI_AREA5 = "PGIArea5";
    public static final String KEY_PGI_SCORE5 = "PGIScore5";
    public static final String KEY_PGI_POINTS5 = "PGIPoints5";
    public static final String KEY_PGI_AREA6 = "PGIArea6";
    public static final String KEY_PGI_SCORE6 = "PGIScore6";
    public static final String KEY_PGI_POINTS6 = "PGIPoints6";

    public static final String KEY_GDS1 = "GDS1";
    public static final String KEY_GDS2 = "GDS2";
    public static final String KEY_GDS3 = "GDS3";
    public static final String KEY_GDS4 = "GDS4";
    public static final String KEY_GDS5 = "GDS5";
    public static final String KEY_GDS6 = "GDS6";
    public static final String KEY_GDS7 = "GDS7";
    public static final String KEY_GDS8 = "GDS8";

    public static final String KEY_PDQ20_1 = "PDQ20_1";
    public static final String KEY_PDQ20_2 = "PDQ20_2";
    public static final String KEY_PDQ20_3 = "PDQ20_3";
    public static final String KEY_PDQ20_4 = "PDQ20_4";
    public static final String KEY_PDQ20_5 = "PDQ20_5";
    public static final String KEY_PDQ20_6 = "PDQ20_6";
    public static final String KEY_PDQ20_7 = "PDQ20_7";
    public static final String KEY_PDQ20_8 = "PDQ20_8";
    public static final String KEY_PDQ20_9 = "PDQ20_9";
    public static final String KEY_PDQ20_10 = "PDQ20_10";
    public static final String KEY_PDQ20_11 = "PDQ20_11";
    public static final String KEY_PDQ20_12 = "PDQ20_12";
    public static final String KEY_PDQ20_13 = "PDQ20_13";
    public static final String KEY_PDQ20_14 = "PDQ20_14";
    public static final String KEY_PDQ20_15 = "PDQ20_15";
    public static final String KEY_PDQ20_16 = "PDQ20_16";
    public static final String KEY_PDQ20_17 = "PDQ20_17";
    public static final String KEY_PDQ20_18 = "PDQ20_18";
    public static final String KEY_PDQ20_19 = "PDQ20_19";
    public static final String KEY_PDQ20_20 = "PDQ20_20";

    public static final String KEY_VAHS1 = "VAHS1";
    public static final String KEY_VAHS2 = "VAHS2";
    public static final String KEY_VAHS3 = "VAHS3";
    public static final String KEY_VAHS4 = "VAHS4";
    public static final String KEY_VAHS5 = "VAHS5";
    public static final String KEY_VAHS6 = "VAHS6";
    public static final String KEY_VAHS7 = "VAHS7";
    public static final String KEY_VAHS8 = "VAHS8";

    public static final String KEY_SF36_1 = "SF36_1";
    public static final String KEY_SF36_2 = "SF36_2";
    public static final String KEY_SF36_3 = "SF36_3";
    public static final String KEY_SF36_4 = "SF36_4";
    public static final String KEY_SF36_5 = "SF36_5";
    public static final String KEY_SF36_6 = "SF36_6";
    public static final String KEY_SF36_7 = "SF36_7";
    public static final String KEY_SF36_8 = "SF36_8";
    public static final String KEY_SF36_9 = "SF36_9";
    public static final String KEY_SF36_10 = "SF36_10";

    public static final String KEY_SHAPS1 = "SHAPS1";
    public static final String KEY_SHAPS2 = "SHAPS2";
    public static final String KEY_SHAPS3 = "SHAPS3";
    public static final String KEY_SHAPS4 = "SHAPS4";

    public static final String KEY_NMS1 = "NMS1";
    public static final String KEY_NMS2 = "NMS2";
    public static final String KEY_NMS3 = "NMS3";
    public static final String KEY_NMS4 = "NMS4";
    public static final String KEY_NMS5 = "NMS5";
    public static final String KEY_NMS6 = "NMS6";
    public static final String KEY_NMS7 = "NMS7";
    public static final String KEY_NMS8 = "NMS8";
    public static final String KEY_NMS9 = "NMS9";
    public static final String KEY_NMS10 = "NMS10";
    public static final String KEY_NMS11 = "NMS11";
    public static final String KEY_NMS12 = "NMS12";
    public static final String KEY_NMS13 = "NMS13";
    public static final String KEY_NMS14 = "NMS14";
    public static final String KEY_NMS15 = "NMS15";
    public static final String KEY_NMS16 = "NMS16";
    public static final String KEY_NMS17 = "NMS17";
    public static final String KEY_NMS18 = "NMS18";
    public static final String KEY_NMS19 = "NMS19";
    public static final String KEY_NMS20 = "NMS20";
    public static final String KEY_NMS21 = "NMS21";
    public static final String KEY_NMS22 = "NMS22";
    public static final String KEY_NMS23 = "NMS23";
    public static final String KEY_NMS24 = "NMS24";
    public static final String KEY_NMS25 = "NMS25";
    public static final String KEY_NMS26 = "NMS26";
    public static final String KEY_NMS27 = "NMS27";
    public static final String KEY_NMS28 = "NMS28";
    public static final String KEY_NMS29 = "NMS29";
    public static final String KEY_NMS30 = "NMS30";

    public static final String KEY_LSM1 = "LSM1";
    public static final String KEY_LSM2 = "LSM2";
    public static final String KEY_LSM3 = "LSM3";
    public static final String KEY_LSM4 = "LSM4";
    public static final String KEY_LSM5 = "LSM5";

    public static final String KEY_PASA1 = "PASA1";
    public static final String KEY_PASA2 = "PASA2";
    public static final String KEY_PASA3 = "PASA3";
    public static final String KEY_PASA4 = "PASA4";
    public static final String KEY_PASA5 = "PASA5";

    public static final String KEY_PASB1 = "PASB1";
    public static final String KEY_PASB2 = "PASB2";
    public static final String KEY_PASB3 = "PASB3";
    public static final String KEY_PASB4 = "PASB4";

    public static final String KEY_PASC1 = "PASC1";
    public static final String KEY_PASC2 = "PASC2";
    public static final String KEY_PASC3 = "PASC3";

    public static final String KEY_AS1 = "AS1";
    public static final String KEY_AS2 = "AS2";
    public static final String KEY_AS3 = "AS3";
    public static final String KEY_AS4 = "AS4";
    public static final String KEY_AS5 = "AS5";
    public static final String KEY_AS6 = "AS6";
    public static final String KEY_AS7 = "AS7";
    public static final String KEY_AS8 = "AS8";
    public static final String KEY_AS9 = "AS9";
    public static final String KEY_AS10 = "AS10";
    public static final String KEY_AS11 = "AS11";
    public static final String KEY_AS12 = "AS12";
    public static final String KEY_AS13 = "AS13";
    public static final String KEY_AS14 = "AS14";

    public static final String KEY_PDQ8_1 = "PDQ8_1";
    public static final String KEY_PDQ8_2 = "PDQ8_2";
    public static final String KEY_PDQ8_3 = "PDQ8_3";
    public static final String KEY_PDQ8_4 = "PDQ8_4";
    public static final String KEY_PDQ8_5 = "PDQ8_5";
    public static final String KEY_PDQ8_6 = "PDQ8_6";
    public static final String KEY_PDQ8_7 = "PDQ8_7";
    public static final String KEY_PDQ8_8 = "PDQ8_8";


    // TODO: Setup your data field numbers here (0 = KEY_ROWID, 1=...)
    public static final int COL_FIRSTNAME = 1;
    public static final int COL_LASTNAME = 2;
    public static final int COL_HOSPITALID = 3;


    //TODO: Set all keys for data table
    public static final String[] ALL_DATA_KEYS = new String[] {KEY_ROWID, KEY_FIRSTNAME, KEY_LASTNAME, KEY_HOSPITALID,
            KEY_PGI_SCORE, KEY_VAHS_SCORE, KEY_SF36_SCORE, KEY_SHAPS_SCORE, KEY_NMS_SCORE, KEY_PDQ20_SCORE, KEY_LSM_SCORE, KEY_PASA_SCORE, KEY_PASB_SCORE, KEY_PASC_SCORE, KEY_AS_SCORE, KEY_PDQ8_SCORE, KEY_GDS_SCORE,
            KEY_PGI_AREA1, KEY_PGI_SCORE1, KEY_PGI_POINTS1, KEY_PGI_AREA2, KEY_PGI_SCORE2, KEY_PGI_POINTS2, KEY_PGI_AREA3, KEY_PGI_SCORE3, KEY_PGI_POINTS3, KEY_PGI_AREA4, KEY_PGI_SCORE4, KEY_PGI_POINTS4, KEY_PGI_AREA5, KEY_PGI_SCORE5, KEY_PGI_POINTS5, KEY_PGI_AREA6, KEY_PGI_SCORE6, KEY_PGI_POINTS6,
            KEY_VAHS1, KEY_VAHS2, KEY_VAHS3, KEY_VAHS4, KEY_VAHS5, KEY_VAHS6, KEY_VAHS7, KEY_VAHS8,
            KEY_SF36_1, KEY_SF36_2, KEY_SF36_3, KEY_SF36_4, KEY_SF36_5, KEY_SF36_6, KEY_SF36_7, KEY_SF36_8, KEY_SF36_9, KEY_SF36_10,
            KEY_SHAPS1, KEY_SHAPS2, KEY_SHAPS3, KEY_SHAPS4,
            KEY_NMS1, KEY_NMS2, KEY_NMS3, KEY_NMS4, KEY_NMS5, KEY_NMS6, KEY_NMS7, KEY_NMS8, KEY_NMS9, KEY_NMS10, KEY_NMS11, KEY_NMS12, KEY_NMS13, KEY_NMS14, KEY_NMS15, KEY_NMS16, KEY_NMS17, KEY_NMS18, KEY_NMS19, KEY_NMS20, KEY_NMS21, KEY_NMS22, KEY_NMS23, KEY_NMS24, KEY_NMS25, KEY_NMS26, KEY_NMS27, KEY_NMS28, KEY_NMS29, KEY_NMS30,
            KEY_PDQ20_1, KEY_PDQ20_2, KEY_PDQ20_3, KEY_PDQ20_4, KEY_PDQ20_5, KEY_PDQ20_6, KEY_PDQ20_7, KEY_PDQ20_8, KEY_PDQ20_9, KEY_PDQ20_10, KEY_PDQ20_11, KEY_PDQ20_12, KEY_PDQ20_13, KEY_PDQ20_14, KEY_PDQ20_15, KEY_PDQ20_16, KEY_PDQ20_17, KEY_PDQ20_18, KEY_PDQ20_19, KEY_PDQ20_20,
            KEY_LSM1, KEY_LSM2, KEY_LSM3, KEY_LSM4, KEY_LSM5,
            KEY_PASA1, KEY_PASA2, KEY_PASA3, KEY_PASA4, KEY_PASA5,
            KEY_PASB1, KEY_PASB2, KEY_PASB3, KEY_PASB4,
            KEY_PASC1, KEY_PASC2, KEY_PASC3,
            KEY_AS1, KEY_AS2, KEY_AS3, KEY_AS4, KEY_AS5, KEY_AS6, KEY_AS7, KEY_AS8, KEY_AS9, KEY_AS10, KEY_AS11, KEY_AS12, KEY_AS13, KEY_AS14,
            KEY_PDQ8_1, KEY_PDQ8_2, KEY_PDQ8_3, KEY_PDQ8_4, KEY_PDQ8_5, KEY_PDQ8_6, KEY_PDQ8_7, KEY_PDQ8_8,
            KEY_GDS1, KEY_GDS2, KEY_GDS3, KEY_GDS4, KEY_GDS5, KEY_GDS6, KEY_GDS7, KEY_GDS8
    };

    // DB info: its name, and the tables we are using
    public static final String DATABASE_NAME = "PatientQuestionnaire";
    public static final String DATA_TABLE = "dataTable";

    // Track DB version if a new version of your app changes the format.
    public static final int DATABASE_VERSION = 31;


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

                    + KEY_PGI_SCORE + " text, "
                    + KEY_VAHS_SCORE + " text, "
                    + KEY_SF36_SCORE + " text, "
                    + KEY_SHAPS_SCORE + " text, "
                    + KEY_NMS_SCORE + " text, "
                    + KEY_PDQ20_SCORE + " text, "
                    + KEY_LSM_SCORE + " text, "
                    + KEY_PASA_SCORE + " text, "
                    + KEY_PASB_SCORE + " text, "
                    + KEY_PASC_SCORE + " text, "
                    + KEY_AS_SCORE + " text, "
                    + KEY_PDQ8_SCORE + " text, "
                    + KEY_GDS_SCORE + " text, "

                    + KEY_PGI_AREA1 + " text, "
                    + KEY_PGI_SCORE1 + " text, "
                    + KEY_PGI_POINTS1 + " text, "
                    + KEY_PGI_AREA2 + " text, "
                    + KEY_PGI_SCORE2 + " text, "
                    + KEY_PGI_POINTS2 + " text, "
                    + KEY_PGI_AREA3 + " text, "
                    + KEY_PGI_SCORE3 + " text, "
                    + KEY_PGI_POINTS3 + " text, "
                    + KEY_PGI_AREA4 + " text, "
                    + KEY_PGI_SCORE4 + " text, "
                    + KEY_PGI_POINTS4 + " text, "
                    + KEY_PGI_AREA5 + " text, "
                    + KEY_PGI_SCORE5 + " text, "
                    + KEY_PGI_POINTS5 + " text, "
                    + KEY_PGI_AREA6 + " text, "
                    + KEY_PGI_SCORE6 + " text, "
                    + KEY_PGI_POINTS6 + " text, "

                    + KEY_VAHS1 + " text, "
                    + KEY_VAHS2 + " text, "
                    + KEY_VAHS3 + " text, "
                    + KEY_VAHS4 + " text, "
                    + KEY_VAHS5 + " text, "
                    + KEY_VAHS6 + " text, "
                    + KEY_VAHS7 + " text, "
                    + KEY_VAHS8 + " text, "

                    + KEY_SF36_1 + " text, "
                    + KEY_SF36_2 + " text, "
                    + KEY_SF36_3 + " text, "
                    + KEY_SF36_4 + " text, "
                    + KEY_SF36_5 + " text, "
                    + KEY_SF36_6 + " text, "
                    + KEY_SF36_7 + " text, "
                    + KEY_SF36_8 + " text, "
                    + KEY_SF36_9 + " text, "
                    + KEY_SF36_10 + " text, "

                    + KEY_SHAPS1 + " text, "
                    + KEY_SHAPS2 + " text, "
                    + KEY_SHAPS3 + " text, "
                    + KEY_SHAPS4 + " text, "

                    + KEY_NMS1 + " text, "
                    + KEY_NMS2 + " text, "
                    + KEY_NMS3 + " text, "
                    + KEY_NMS4 + " text, "
                    + KEY_NMS5 + " text, "
                    + KEY_NMS6 + " text, "
                    + KEY_NMS7 + " text, "
                    + KEY_NMS8 + " text, "
                    + KEY_NMS9 + " text, "
                    + KEY_NMS10 + " text, "
                    + KEY_NMS11 + " text, "
                    + KEY_NMS12 + " text, "
                    + KEY_NMS13 + " text, "
                    + KEY_NMS14 + " text, "
                    + KEY_NMS15 + " text, "
                    + KEY_NMS16 + " text, "
                    + KEY_NMS17 + " text, "
                    + KEY_NMS18 + " text, "
                    + KEY_NMS19 + " text, "
                    + KEY_NMS20 + " text, "
                    + KEY_NMS21 + " text, "
                    + KEY_NMS22 + " text, "
                    + KEY_NMS23 + " text, "
                    + KEY_NMS24 + " text, "
                    + KEY_NMS25 + " text, "
                    + KEY_NMS26 + " text, "
                    + KEY_NMS27 + " text, "
                    + KEY_NMS28 + " text, "
                    + KEY_NMS29 + " text, "
                    + KEY_NMS30 + " text, "

                    + KEY_PDQ20_1 + " text, "
                    + KEY_PDQ20_2 + " text, "
                    + KEY_PDQ20_3 + " text, "
                    + KEY_PDQ20_4 + " text, "
                    + KEY_PDQ20_5 + " text, "
                    + KEY_PDQ20_6 + " text, "
                    + KEY_PDQ20_7 + " text, "
                    + KEY_PDQ20_8 + " text, "
                    + KEY_PDQ20_9 + " text, "
                    + KEY_PDQ20_10 + " text, "
                    + KEY_PDQ20_11 + " text, "
                    + KEY_PDQ20_12 + " text, "
                    + KEY_PDQ20_13 + " text, "
                    + KEY_PDQ20_14 + " text, "
                    + KEY_PDQ20_15 + " text, "
                    + KEY_PDQ20_16 + " text, "
                    + KEY_PDQ20_17 + " text, "
                    + KEY_PDQ20_18 + " text, "
                    + KEY_PDQ20_19 + " text, "
                    + KEY_PDQ20_20 + " text, "

                    + KEY_LSM1 + " text, "
                    + KEY_LSM2 + " text, "
                    + KEY_LSM3 + " text, "
                    + KEY_LSM4 + " text, "
                    + KEY_LSM5 + " text, "

                    + KEY_PASA1 + " text, "
                    + KEY_PASA2 + " text, "
                    + KEY_PASA3 + " text, "
                    + KEY_PASA4 + " text, "
                    + KEY_PASA5 + " text, "

                    + KEY_PASB1 + " text, "
                    + KEY_PASB2 + " text, "
                    + KEY_PASB3 + " text, "
                    + KEY_PASB4 + " text, "

                    + KEY_PASC1 + " text, "
                    + KEY_PASC2 + " text, "
                    + KEY_PASC3 + " text, "

                    + KEY_AS1 + " text, "
                    + KEY_AS2 + " text, "
                    + KEY_AS3 + " text, "
                    + KEY_AS4 + " text, "
                    + KEY_AS5 + " text, "
                    + KEY_AS6 + " text, "
                    + KEY_AS7 + " text, "
                    + KEY_AS8 + " text, "
                    + KEY_AS9 + " text, "
                    + KEY_AS10 + " text, "
                    + KEY_AS11 + " text, "
                    + KEY_AS12 + " text, "
                    + KEY_AS13 + " text, "
                    + KEY_AS14 + " text, "

                    + KEY_PDQ8_1 + " text, "
                    + KEY_PDQ8_2 + " text, "
                    + KEY_PDQ8_3 + " text, "
                    + KEY_PDQ8_4 + " text, "
                    + KEY_PDQ8_5 + " text, "
                    + KEY_PDQ8_6 + " text, "
                    + KEY_PDQ8_7 + " text, "
                    + KEY_PDQ8_8 + " text, "

                    + KEY_GDS1 + " text, "
                    + KEY_GDS2 + " text, "
                    + KEY_GDS3 + " text, "
                    + KEY_GDS4 + " text, "
                    + KEY_GDS5 + " text, "
                    + KEY_GDS6 + " text, "
                    + KEY_GDS7 + " text, "
                    + KEY_GDS8 + " text"

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
