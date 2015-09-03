package com.example.abhishek.patientquestionnaire;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class MainActivity extends ActionBarActivity {

    Locale myLocale;
    public static int numIntroScreens = 2;
    public static DBAdapter myDb;
    public static long currentPatientId;
    private String[] mSectionTitles;
    public static DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    public List<Cell_Item> cellItems;
    public static int currentCell;
    public static List<Cell_Item.CellType> nonQuestionSubset;
    public static int numIntroFields = 3;
    public static int numScoreFields = 13;
    public static int numPGIFields = 18;
    public static String PREFS_KEY = "prefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openDB();

        setCellItems();


        setContentView(R.layout.activity_main);
        currentCell = 0;
        mSectionTitles = getResources().getStringArray(R.array.section_titles);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);


        //Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mSectionTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        loadCell(0);

        currentPatientId = -1;

        nonQuestionSubset = new ArrayList<>();
        nonQuestionSubset.add(Cell_Item.CellType.INTRO_DETAILS);
        nonQuestionSubset.add(Cell_Item.CellType.INTRO_LANGUAGE);
        nonQuestionSubset.add(Cell_Item.CellType.INTRO_QUESTIONNAIRE);
        nonQuestionSubset.add(Cell_Item.CellType.CONCLUSION);
        nonQuestionSubset.add(Cell_Item.CellType.QUESTION_PGI);



    }

    private void setCellItems() {
        cellItems = new ArrayList<>();
        cellItems.add(new Cell_Item(this, null, null, Cell_Item.CellType.INTRO_LANGUAGE, -1, null));
        cellItems.add(new Cell_Item(this, null, null, Cell_Item.CellType.INTRO_NEW_OR_CONTINUE, -1, null));

        cellItems.add(new Cell_Item(this, null, null, Cell_Item.CellType.QUESTION_PGI, -1, null));

        int sqlColumnNum = numIntroFields + numScoreFields + numPGIFields + 1;

        cellItems.add(new Cell_Item(this, getString(R.string.vahsTitle), getString(R.string.vahsPreamble), Cell_Item.CellType.INTRO_QUESTIONNAIRE, -1, null));
        cellItems.add(new Cell_Item(this, getString(R.string.vahs1), null, Cell_Item.CellType.QUESTION_10_POINT, sqlColumnNum++,  new String[]{getString(R.string.a_poor),getString(R.string.a_excellent)}));
        cellItems.add(new Cell_Item(this, getString(R.string.vahs2), null, Cell_Item.CellType.QUESTION_10_POINT, sqlColumnNum++,  new String[]{getString(R.string.a_poor),getString(R.string.a_excellent)}));
        cellItems.add(new Cell_Item(this, getString(R.string.vahs3), null, Cell_Item.CellType.QUESTION_10_POINT, sqlColumnNum++,  new String[]{getString(R.string.a_much_distress),getString(R.string.a_no_distress)}));
        cellItems.add(new Cell_Item(this, getString(R.string.vahs4), null, Cell_Item.CellType.QUESTION_10_POINT, sqlColumnNum++, new String[]{getString(R.string.a_much_pain),getString(R.string.a_no_pain)}));
        cellItems.add(new Cell_Item(this, getString(R.string.vahs5), null, Cell_Item.CellType.QUESTION_10_POINT, sqlColumnNum++, new String[]{getString(R.string.a_much_fatigue),getString(R.string.a_no_fatigue)}));
        cellItems.add(new Cell_Item(this, getString(R.string.vahs6), null, Cell_Item.CellType.QUESTION_10_POINT, sqlColumnNum++,  new String[]{getString(R.string.a_much_depression),getString(R.string.a_no_depression)}));
        cellItems.add(new Cell_Item(this, getString(R.string.vahs7), null, Cell_Item.CellType.QUESTION_10_POINT, sqlColumnNum++,  new String[]{getString(R.string.a_much_anxiety),getString(R.string.a_no_anxiety)}));
        cellItems.add(new Cell_Item(this, getString(R.string.vahs8), null, Cell_Item.CellType.QUESTION_10_POINT, sqlColumnNum++,  new String[]{getString(R.string.a_poor),getString(R.string.a_excellent)}));

        cellItems.add(new Cell_Item(this, getString(R.string.sf36_title), getString(R.string.sf36_preamble), Cell_Item.CellType.INTRO_QUESTIONNAIRE, -1, null));
        cellItems.add(new Cell_Item(this, getString(R.string.sf36_1), null, Cell_Item.CellType.QUESTION_3_POINT_SF36, sqlColumnNum++,  null));
        cellItems.add(new Cell_Item(this, getString(R.string.sf36_2), null, Cell_Item.CellType.QUESTION_3_POINT_SF36, sqlColumnNum++,  null));
        cellItems.add(new Cell_Item(this, getString(R.string.sf36_3), null, Cell_Item.CellType.QUESTION_3_POINT_SF36, sqlColumnNum++,  null));
        cellItems.add(new Cell_Item(this, getString(R.string.sf36_4), null, Cell_Item.CellType.QUESTION_3_POINT_SF36, sqlColumnNum++,  null));
        cellItems.add(new Cell_Item(this, getString(R.string.sf36_5), null, Cell_Item.CellType.QUESTION_3_POINT_SF36, sqlColumnNum++,  null));
        cellItems.add(new Cell_Item(this, getString(R.string.sf36_6), null, Cell_Item.CellType.QUESTION_3_POINT_SF36, sqlColumnNum++,  null));
        cellItems.add(new Cell_Item(this, getString(R.string.sf36_7), null, Cell_Item.CellType.QUESTION_3_POINT_SF36, sqlColumnNum++,  null));
        cellItems.add(new Cell_Item(this, getString(R.string.sf36_8), null, Cell_Item.CellType.QUESTION_3_POINT_SF36, sqlColumnNum++,  null));
        cellItems.add(new Cell_Item(this, getString(R.string.sf36_9), null, Cell_Item.CellType.QUESTION_3_POINT_SF36, sqlColumnNum++,  null));
        cellItems.add(new Cell_Item(this, getString(R.string.sf36_10), null, Cell_Item.CellType.QUESTION_3_POINT_SF36, sqlColumnNum++,  null));

        cellItems.add(new Cell_Item(this, getString(R.string.shapsTitle),getString(R.string.shapsPreamble), Cell_Item.CellType.INTRO_QUESTIONNAIRE, -1,null));
        cellItems.add(new Cell_Item(this, getString(R.string.shaps1), null, Cell_Item.CellType.QUESTION_SHAPS, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.shaps2), null, Cell_Item.CellType.QUESTION_SHAPS, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.shaps3), null, Cell_Item.CellType.QUESTION_SHAPS, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.shaps4), null, Cell_Item.CellType.QUESTION_SHAPS, sqlColumnNum++,null));

        cellItems.add(new Cell_Item(this, getString(R.string.nmsTitle),getString(R.string.nmsPreamble), Cell_Item.CellType.INTRO_QUESTIONNAIRE, -1,null));
        cellItems.add(new Cell_Item(this, getString(R.string.nms1), getString(R.string.nmsIntro), Cell_Item.CellType.QUESTION_YES_NO_NMS, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.nms2), getString(R.string.nmsIntro), Cell_Item.CellType.QUESTION_YES_NO_NMS, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.nms3), getString(R.string.nmsIntro), Cell_Item.CellType.QUESTION_YES_NO_NMS, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.nms4), getString(R.string.nmsIntro), Cell_Item.CellType.QUESTION_YES_NO_NMS, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.nms5), getString(R.string.nmsIntro), Cell_Item.CellType.QUESTION_YES_NO_NMS, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.nms6), getString(R.string.nmsIntro), Cell_Item.CellType.QUESTION_YES_NO_NMS, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.nms7), getString(R.string.nmsIntro), Cell_Item.CellType.QUESTION_YES_NO_NMS, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.nms8), getString(R.string.nmsIntro), Cell_Item.CellType.QUESTION_YES_NO_NMS, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.nms9), getString(R.string.nmsIntro), Cell_Item.CellType.QUESTION_YES_NO_NMS, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.nms10), getString(R.string.nmsIntro), Cell_Item.CellType.QUESTION_YES_NO_NMS, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.nms11), getString(R.string.nmsIntro), Cell_Item.CellType.QUESTION_YES_NO_NMS, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.nms12), getString(R.string.nmsIntro), Cell_Item.CellType.QUESTION_YES_NO_NMS, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.nms13), getString(R.string.nmsIntro), Cell_Item.CellType.QUESTION_YES_NO_NMS, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.nms14), getString(R.string.nmsIntro), Cell_Item.CellType.QUESTION_YES_NO_NMS, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.nms15), getString(R.string.nmsIntro), Cell_Item.CellType.QUESTION_YES_NO_NMS, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.nms16), getString(R.string.nmsIntro), Cell_Item.CellType.QUESTION_YES_NO_NMS, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.nms17), getString(R.string.nmsIntro), Cell_Item.CellType.QUESTION_YES_NO_NMS, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.nms18), getString(R.string.nmsIntro), Cell_Item.CellType.QUESTION_YES_NO_NMS, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.nms19), getString(R.string.nmsIntro), Cell_Item.CellType.QUESTION_YES_NO_NMS, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.nms20), getString(R.string.nmsIntro), Cell_Item.CellType.QUESTION_YES_NO_NMS, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.nms21), getString(R.string.nmsIntro), Cell_Item.CellType.QUESTION_YES_NO_NMS, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.nms22), getString(R.string.nmsIntro), Cell_Item.CellType.QUESTION_YES_NO_NMS, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.nms23), getString(R.string.nmsIntro), Cell_Item.CellType.QUESTION_YES_NO_NMS, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.nms24), getString(R.string.nmsIntro), Cell_Item.CellType.QUESTION_YES_NO_NMS, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.nms25), getString(R.string.nmsIntro), Cell_Item.CellType.QUESTION_YES_NO_NMS, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.nms26), getString(R.string.nmsIntro), Cell_Item.CellType.QUESTION_YES_NO_NMS, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.nms27), getString(R.string.nmsIntro), Cell_Item.CellType.QUESTION_YES_NO_NMS, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.nms28), getString(R.string.nmsIntro), Cell_Item.CellType.QUESTION_YES_NO_NMS, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.nms29), getString(R.string.nmsIntro), Cell_Item.CellType.QUESTION_YES_NO_NMS, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.nms30), getString(R.string.nmsIntro), Cell_Item.CellType.QUESTION_YES_NO_NMS, sqlColumnNum++,null));

        cellItems.add(new Cell_Item(this, getString(R.string.pdq20_Title),getString(R.string.pdq20_Preamble), Cell_Item.CellType.INTRO_QUESTIONNAIRE, -1,null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq20_1), getString(R.string.pdq20_Intro), Cell_Item.CellType.QUESTION_5_POINT, sqlColumnNum++, null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq20_2), getString(R.string.pdq20_Intro), Cell_Item.CellType.QUESTION_5_POINT, sqlColumnNum++, null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq20_3), getString(R.string.pdq20_Intro), Cell_Item.CellType.QUESTION_5_POINT, sqlColumnNum++, null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq20_4), getString(R.string.pdq20_Intro), Cell_Item.CellType.QUESTION_5_POINT, sqlColumnNum++, null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq20_5), getString(R.string.pdq20_Intro), Cell_Item.CellType.QUESTION_5_POINT, sqlColumnNum++, null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq20_6), getString(R.string.pdq20_Intro), Cell_Item.CellType.QUESTION_5_POINT, sqlColumnNum++, null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq20_7), getString(R.string.pdq20_Intro), Cell_Item.CellType.QUESTION_5_POINT, sqlColumnNum++, null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq20_8), getString(R.string.pdq20_Intro), Cell_Item.CellType.QUESTION_5_POINT, sqlColumnNum++, null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq20_9), getString(R.string.pdq20_Intro), Cell_Item.CellType.QUESTION_5_POINT, sqlColumnNum++, null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq20_10), getString(R.string.pdq20_Intro), Cell_Item.CellType.QUESTION_5_POINT, sqlColumnNum++, null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq20_11), getString(R.string.pdq20_Intro), Cell_Item.CellType.QUESTION_5_POINT, sqlColumnNum++, null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq20_12), getString(R.string.pdq20_Intro), Cell_Item.CellType.QUESTION_5_POINT, sqlColumnNum++, null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq20_13), getString(R.string.pdq20_Intro), Cell_Item.CellType.QUESTION_5_POINT, sqlColumnNum++, null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq20_14), getString(R.string.pdq20_Intro), Cell_Item.CellType.QUESTION_5_POINT, sqlColumnNum++, null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq20_15), getString(R.string.pdq20_Intro), Cell_Item.CellType.QUESTION_5_POINT, sqlColumnNum++, null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq20_16), getString(R.string.pdq20_Intro), Cell_Item.CellType.QUESTION_5_POINT, sqlColumnNum++, null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq20_17), getString(R.string.pdq20_Intro), Cell_Item.CellType.QUESTION_5_POINT, sqlColumnNum++, null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq20_18), getString(R.string.pdq20_Intro), Cell_Item.CellType.QUESTION_5_POINT, sqlColumnNum++, null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq20_19), getString(R.string.pdq20_Intro), Cell_Item.CellType.QUESTION_5_POINT, sqlColumnNum++, null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq20_20), getString(R.string.pdq20_Intro), Cell_Item.CellType.QUESTION_5_POINT, sqlColumnNum++, null));

        cellItems.add(new Cell_Item(this, getString(R.string.lsmTitle),getString(R.string.lsmPreamble), Cell_Item.CellType.INTRO_QUESTIONNAIRE, -1,null));
        cellItems.add(new Cell_Item(this, getString(R.string.lsm1), getString(R.string.lsmIntro), Cell_Item.CellType.QUESTION_LSM, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.lsm2), getString(R.string.lsmIntro), Cell_Item.CellType.QUESTION_LSM, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.lsm3), getString(R.string.lsmIntro), Cell_Item.CellType.QUESTION_LSM, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.lsm4), getString(R.string.lsmIntro), Cell_Item.CellType.QUESTION_LSM, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.lsm5), getString(R.string.lsmIntro), Cell_Item.CellType.QUESTION_LSM, sqlColumnNum++,null));

        cellItems.add(new Cell_Item(this, getString(R.string.pasATitle),getString(R.string.pasAPreamble), Cell_Item.CellType.INTRO_QUESTIONNAIRE, -1,null));
        cellItems.add(new Cell_Item(this, getString(R.string.pasA1), getString(R.string.pasAIntro), Cell_Item.CellType.QUESTION_5_POINT_A, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.pasA2), getString(R.string.pasAIntro), Cell_Item.CellType.QUESTION_5_POINT_A, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.pasA3), getString(R.string.pasAIntro), Cell_Item.CellType.QUESTION_5_POINT_A, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.pasA4), getString(R.string.pasAIntro), Cell_Item.CellType.QUESTION_5_POINT_A, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.pasA5), getString(R.string.pasAIntro), Cell_Item.CellType.QUESTION_5_POINT_A, sqlColumnNum++,null));

        cellItems.add(new Cell_Item(this, getString(R.string.pasBTitle),getString(R.string.pasBPreamble), Cell_Item.CellType.INTRO_QUESTIONNAIRE, -1,null));
        cellItems.add(new Cell_Item(this, getString(R.string.pasB1), getString(R.string.pasBIntro), Cell_Item.CellType.QUESTION_5_POINT, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.pasB2), getString(R.string.pasBIntro), Cell_Item.CellType.QUESTION_5_POINT, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.pasB3), getString(R.string.pasBIntro), Cell_Item.CellType.QUESTION_5_POINT, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.pasB4), getString(R.string.pasBIntro), Cell_Item.CellType.QUESTION_5_POINT, sqlColumnNum++,null));

        cellItems.add(new Cell_Item(this, getString(R.string.pasCTitle),getString(R.string.pasCPreamble), Cell_Item.CellType.INTRO_QUESTIONNAIRE, -1,null));
        cellItems.add(new Cell_Item(this, getString(R.string.pasC1), getString(R.string.pasCIntro), Cell_Item.CellType.QUESTION_5_POINT, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.pasC2), getString(R.string.pasCIntro), Cell_Item.CellType.QUESTION_5_POINT, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.pasC3), getString(R.string.pasCIntro), Cell_Item.CellType.QUESTION_5_POINT, sqlColumnNum++,null));

        cellItems.add(new Cell_Item(this, getString(R.string.asTitle), getString(R.string.asPreamble), Cell_Item.CellType.INTRO_QUESTIONNAIRE, -1, null));
        cellItems.add(new Cell_Item(this, getString(R.string.as1), null, Cell_Item.CellType.QUESTION_AS, sqlColumnNum++,  new String[]{"forward"}));
        cellItems.add(new Cell_Item(this, getString(R.string.as2), null, Cell_Item.CellType.QUESTION_AS, sqlColumnNum++,  new String[]{"forward"}));
        cellItems.add(new Cell_Item(this, getString(R.string.as3), null, Cell_Item.CellType.QUESTION_AS, sqlColumnNum++,  new String[]{"forward"}));
        cellItems.add(new Cell_Item(this, getString(R.string.as4), null, Cell_Item.CellType.QUESTION_AS, sqlColumnNum++,  new String[]{"forward"}));
        cellItems.add(new Cell_Item(this, getString(R.string.as5), null, Cell_Item.CellType.QUESTION_AS, sqlColumnNum++,  new String[]{"forward"}));
        cellItems.add(new Cell_Item(this, getString(R.string.as6), null, Cell_Item.CellType.QUESTION_AS, sqlColumnNum++,  new String[]{"forward"}));
        cellItems.add(new Cell_Item(this, getString(R.string.as7), null, Cell_Item.CellType.QUESTION_AS, sqlColumnNum++,  new String[]{"forward"}));
        cellItems.add(new Cell_Item(this, getString(R.string.as8), null, Cell_Item.CellType.QUESTION_AS, sqlColumnNum++,  new String[]{"forward"}));
        cellItems.add(new Cell_Item(this, getString(R.string.as9), null, Cell_Item.CellType.QUESTION_AS, sqlColumnNum++,  new String[]{"reverse"}));
        cellItems.add(new Cell_Item(this, getString(R.string.as10), null, Cell_Item.CellType.QUESTION_AS, sqlColumnNum++,  new String[]{"reverse"}));
        cellItems.add(new Cell_Item(this, getString(R.string.as11), null, Cell_Item.CellType.QUESTION_AS, sqlColumnNum++,  new String[]{"reverse"}));
        cellItems.add(new Cell_Item(this, getString(R.string.as12), null, Cell_Item.CellType.QUESTION_AS, sqlColumnNum++,  new String[]{"reverse"}));
        cellItems.add(new Cell_Item(this, getString(R.string.as13), null, Cell_Item.CellType.QUESTION_AS, sqlColumnNum++,  new String[]{"reverse"}));
        cellItems.add(new Cell_Item(this, getString(R.string.as14), null, Cell_Item.CellType.QUESTION_AS, sqlColumnNum++,  new String[]{"reverse"}));

        cellItems.add(new Cell_Item(this, getString(R.string.pdq8_Title),getString(R.string.pdq8_Preamble), Cell_Item.CellType.INTRO_QUESTIONNAIRE, -1,null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq8_1), getString(R.string.pdq8_Intro), Cell_Item.CellType.QUESTION_5_POINT_PDQ8, sqlColumnNum++, null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq8_2), getString(R.string.pdq8_Intro), Cell_Item.CellType.QUESTION_5_POINT_PDQ8, sqlColumnNum++, null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq8_3), getString(R.string.pdq8_Intro), Cell_Item.CellType.QUESTION_5_POINT_PDQ8, sqlColumnNum++, null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq8_4), getString(R.string.pdq8_Intro), Cell_Item.CellType.QUESTION_5_POINT_PDQ8, sqlColumnNum++, null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq8_5), getString(R.string.pdq8_Intro), Cell_Item.CellType.QUESTION_5_POINT_PDQ8, sqlColumnNum++, null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq8_6), getString(R.string.pdq8_Intro), Cell_Item.CellType.QUESTION_5_POINT_PDQ8, sqlColumnNum++, null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq8_7), getString(R.string.pdq8_Intro), Cell_Item.CellType.QUESTION_5_POINT_PDQ8, sqlColumnNum++, null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq8_8), getString(R.string.pdq8_Intro), Cell_Item.CellType.QUESTION_5_POINT_PDQ8, sqlColumnNum++, null));

        cellItems.add(new Cell_Item(this, getString(R.string.gdsTitle),getString(R.string.gdsPreamble), Cell_Item.CellType.INTRO_QUESTIONNAIRE, -1,null));
        cellItems.add(new Cell_Item(this, getString(R.string.gds1), null, Cell_Item.CellType.QUESTION_YES_NO_GDS, sqlColumnNum++,new String[]{"no1"}));
        cellItems.add(new Cell_Item(this, getString(R.string.gds2), null, Cell_Item.CellType.QUESTION_YES_NO_GDS, sqlColumnNum++,new String[]{"yes1"}));
        cellItems.add(new Cell_Item(this, getString(R.string.gds3), null, Cell_Item.CellType.QUESTION_YES_NO_GDS, sqlColumnNum++,new String[]{"yes1"}));
        cellItems.add(new Cell_Item(this, getString(R.string.gds4), null, Cell_Item.CellType.QUESTION_YES_NO_GDS, sqlColumnNum++,new String[]{"no1"}));
        cellItems.add(new Cell_Item(this, getString(R.string.gds5), null, Cell_Item.CellType.QUESTION_YES_NO_GDS, sqlColumnNum++,new String[]{"no1"}));
        cellItems.add(new Cell_Item(this, getString(R.string.gds6), null, Cell_Item.CellType.QUESTION_YES_NO_GDS, sqlColumnNum++,new String[]{"yes1"}));
        cellItems.add(new Cell_Item(this, getString(R.string.gds7), null, Cell_Item.CellType.QUESTION_YES_NO_GDS, sqlColumnNum++,new String[]{"no1"}));
        cellItems.add(new Cell_Item(this, getString(R.string.gds8), null, Cell_Item.CellType.QUESTION_YES_NO_GDS, sqlColumnNum++,new String[]{"yes1"}));

        cellItems.add(new Cell_Item(this, null, null, Cell_Item.CellType.CONCLUSION, -1, null));
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

    @Override
    protected void onResume() {
        super.onResume();
        loadCell(currentCell);
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mSectionTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        if (currentCell<=numIntroScreens){
            menu.findItem(R.id.action_undo).setVisible(false);
            menu.findItem(R.id.action_skip).setVisible(false);
        } else {
            menu.findItem(R.id.action_undo).setVisible(true);
            menu.findItem(R.id.action_skip).setVisible(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_undo) {
            buttonPrevious();
            return true;
        }

        if (id == R.id.action_skip) {
            Cell_Item cellItem = cellItems.get(currentCell);
            if (!nonQuestionSubset.contains(cellItem.cellType)){
                AppUtils.showTwoButtonDialog(getResources().getString(R.string.skip_title), getResources().getString(R.string.not_applicable), getResources().getString(R.string.no_answer), this, new DialogTwoButton.ClickHandler() {
                    @Override
                    public void onPositiveClick() {
                        myDb.updateAnswer(currentPatientId, cellItems.get(currentCell).sqlColumn, "notApplicable");
                        nextScreen();
                    }

                    @Override
                    public void onNegativeClick() {
                        myDb.updateAnswer(currentPatientId, cellItems.get(currentCell).sqlColumn, "noAnswer");
                        nextScreen();
                    }
                });
            } else {
                nextScreen();
            }


            return true;
        }

        if (id == R.id.action_delete_all) {

            myDb.deleteAllData();
            SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.PREFS_KEY, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putLong("rowId", -1);
            editor.apply();
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    public void buttonPrevious(){

        if (currentCell>numIntroScreens){
            currentCell--;
            loadCell(currentCell);
            Cell_Item cellItem = cellItems.get(currentCell);

            if (!nonQuestionSubset.contains(cellItem.cellType)){
                myDb.updateAnswer(currentPatientId, cellItem.sqlColumn, null);
            }
        }

    }

    public void buttonPGI(View view){



        EditText editText;
        editText = (EditText) findViewById(R.id.pgi_points1);
        int score1 = editText.getText().toString().equals("") ? 0 : Integer.valueOf(editText.getText().toString());
        editText = (EditText) findViewById(R.id.pgi_points2);
        int score2 = editText.getText().toString().equals("") ? 0 : Integer.valueOf(editText.getText().toString());
        editText = (EditText) findViewById(R.id.pgi_points3);
        int score3 = editText.getText().toString().equals("") ? 0 : Integer.valueOf(editText.getText().toString());
        editText = (EditText) findViewById(R.id.pgi_points4);
        int score4 = editText.getText().toString().equals("") ? 0 : Integer.valueOf(editText.getText().toString());
        editText = (EditText) findViewById(R.id.pgi_points5);
        int score5 = editText.getText().toString().equals("") ? 0 : Integer.valueOf(editText.getText().toString());
        editText = (EditText) findViewById(R.id.pgi_points6);
        int score6 = editText.getText().toString().equals("") ? 0 : Integer.valueOf(editText.getText().toString());

        int totalScore = score1 + score2 + score3 + score4 + score5 + score6;

        editText = (EditText) findViewById(R.id.pgi_score6);
        int rating6 = editText.getText().toString().equals("") ? 0 : Integer.valueOf(editText.getText().toString());

        if (score6==0 | rating6 ==0){
            AppUtils.showSimpleDialog(getResources().getString(R.string.other_areas_warning), this);
            return;
        }

        if (totalScore>12){
            AppUtils.showSimpleDialog(getResources().getString(R.string.maximum_points), this);
            return;
        }

        editText = (EditText) findViewById(R.id.pgi_area1);
        myDb.updateAnswer(currentPatientId, numIntroFields + numScoreFields + 1, editText.getText().toString());
        editText = (EditText) findViewById(R.id.pgi_score1);
        myDb.updateAnswer(currentPatientId, numIntroFields + numScoreFields + 2, editText.getText().toString());
        editText = (EditText) findViewById(R.id.pgi_points1);
        myDb.updateAnswer(currentPatientId, numIntroFields + numScoreFields + 3, editText.getText().toString());

        editText = (EditText) findViewById(R.id.pgi_area2);
        myDb.updateAnswer(currentPatientId, numIntroFields + numScoreFields + 4, editText.getText().toString());
        editText = (EditText) findViewById(R.id.pgi_score2);
        myDb.updateAnswer(currentPatientId, numIntroFields + numScoreFields + 5, editText.getText().toString());
        editText = (EditText) findViewById(R.id.pgi_points2);
        myDb.updateAnswer(currentPatientId, numIntroFields + numScoreFields + 6, editText.getText().toString());

        editText = (EditText) findViewById(R.id.pgi_area3);
        myDb.updateAnswer(currentPatientId, numIntroFields + numScoreFields + 7, editText.getText().toString());
        editText = (EditText) findViewById(R.id.pgi_score3);
        myDb.updateAnswer(currentPatientId, numIntroFields + numScoreFields + 8, editText.getText().toString());
        editText = (EditText) findViewById(R.id.pgi_points3);
        myDb.updateAnswer(currentPatientId, numIntroFields + numScoreFields + 9, editText.getText().toString());

        editText = (EditText) findViewById(R.id.pgi_area4);
        myDb.updateAnswer(currentPatientId, numIntroFields + numScoreFields + 10, editText.getText().toString());
        editText = (EditText) findViewById(R.id.pgi_score4);
        myDb.updateAnswer(currentPatientId, numIntroFields + numScoreFields + 11, editText.getText().toString());
        editText = (EditText) findViewById(R.id.pgi_points4);
        myDb.updateAnswer(currentPatientId, numIntroFields + numScoreFields + 12, editText.getText().toString());

        editText = (EditText) findViewById(R.id.pgi_area5);
        myDb.updateAnswer(currentPatientId, numIntroFields + numScoreFields + 13, editText.getText().toString());
        editText = (EditText) findViewById(R.id.pgi_score5);
        myDb.updateAnswer(currentPatientId, numIntroFields + numScoreFields + 14, editText.getText().toString());
        editText = (EditText) findViewById(R.id.pgi_points5);
        myDb.updateAnswer(currentPatientId, numIntroFields + numScoreFields + 15, editText.getText().toString());


        myDb.updateAnswer(currentPatientId, numIntroFields + numScoreFields + 16, "other");
        editText = (EditText) findViewById(R.id.pgi_score6);
        myDb.updateAnswer(currentPatientId, numIntroFields + numScoreFields + 17, editText.getText().toString());
        editText = (EditText) findViewById(R.id.pgi_points6);
        myDb.updateAnswer(currentPatientId, numIntroFields + numScoreFields + 18, editText.getText().toString());

        currentCell++;
        loadCell(currentCell);
        invalidateOptionsMenu();
    }

    public void nextScreen(){
        currentCell++;
        loadCell(currentCell);
    }

    public void buttonNext(View view){
        nextScreen();
    }

    public void buttonCreateNewPatient(View view){
        Intent intent = new Intent(this, NewPatientActivity.class);
        startActivity(intent);
    }

    public void buttonResumePrevious(View view){
        SharedPreferences sharedpreferences = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        long rowId = sharedpreferences.getLong("rowId", -1);
        if (rowId == -1){
            Toast toast = Toast.makeText(this, getResources().getString(R.string.message_no_existing_patient), Toast.LENGTH_LONG);
            toast.show();
        } else {
            currentPatientId = rowId;
            currentCell++;
            loadCell(currentCell);
            invalidateOptionsMenu();
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    public void selectLanguageEn(View view){
        setLocale("en");
    }

    public void selectLanguageFr(View view){
        setLocale("fr");
    }

    public void setLocale(String lang) {

        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        setCellItems();
        currentCell ++;
        loadCell(currentCell);

    }

    public void buttonClick(View view){
        Button b = (Button) view;
        if (cellItems.get(currentCell).cellType == Cell_Item.CellType.QUESTION_AS){
            if (cellItems.get(currentCell).additionalInfo[0].equals("forward")){
                myDb.updateAnswer(currentPatientId, cellItems.get(currentCell).sqlColumn, b.getTag().toString());
            } else {
                myDb.updateAnswer(currentPatientId, cellItems.get(currentCell).sqlColumn, String.valueOf(3 - Integer.valueOf(b.getTag().toString())));
            }
        } else if (cellItems.get(currentCell).cellType == Cell_Item.CellType.QUESTION_YES_NO_GDS){
            if (cellItems.get(currentCell).additionalInfo[0].equals("yes1")){
                myDb.updateAnswer(currentPatientId, cellItems.get(currentCell).sqlColumn, b.getTag().toString());
            } else {
                myDb.updateAnswer(currentPatientId, cellItems.get(currentCell).sqlColumn, String.valueOf(1 - Integer.valueOf(b.getTag().toString())));
            }

        } else {
            myDb.updateAnswer(currentPatientId, cellItems.get(currentCell).sqlColumn, b.getTag().toString());
        }

        currentCell++;
        loadCell(currentCell);

    }

    public void seekbarNext(View view){
        SeekBar seekBar = (SeekBar) findViewById(R.id.intensitySlider);
        final int value = seekBar.getProgress();
        if (value == 0){
            AppUtils.showThreeButtonDialog(getResources().getString(R.string.confirm_answer), getResources().getString(R.string.zero), getResources().getString(R.string.not_applicable), getResources().getString(R.string.no_answer), this, new DialogThreeButton.ClickHandler() {
                @Override
                public void onPositiveClick() {
                    myDb.updateAnswer(currentPatientId, cellItems.get(currentCell).sqlColumn, String.valueOf(value));
                    currentCell++;
                    loadCell(currentCell);
                }

                @Override
                public void onNegativeClick() {
                    myDb.updateAnswer(currentPatientId, cellItems.get(currentCell).sqlColumn, "notApplicable");
                    currentCell++;
                    loadCell(currentCell);
                }

                @Override
                public void onThirdClick() {
                    myDb.updateAnswer(currentPatientId, cellItems.get(currentCell).sqlColumn, "noAnswer");
                    currentCell++;
                    loadCell(currentCell);
                }
            });
        } else {
            myDb.updateAnswer(currentPatientId, cellItems.get(currentCell).sqlColumn, String.valueOf(value));
            currentCell++;
            loadCell(currentCell);
        }

    }


    public void buttonStart(View view){
        EditText editText = (EditText)findViewById(R.id.edit_first_name);
        String firstName = editText.getText().toString();

        editText = (EditText)findViewById(R.id.edit_last_name);
        String lastName = editText.getText().toString();

        editText = (EditText)findViewById(R.id.edit_hospital_id);
        String hospitalId = editText.getText().toString();

        long rowId = myDb.createData(firstName, lastName, hospitalId);
        currentPatientId = rowId;

        currentCell++;
        loadCell(currentCell);
        invalidateOptionsMenu();
    }

    public void loadCell(int position){
        Cell_Item cellItem = cellItems.get(position);

        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_layout);
        mainLayout.removeAllViews();
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(cellItem.layoutId, mainLayout, true);


        if (cellItem.titleId != -1){
            TextView titleView = (TextView)findViewById(cellItem.titleId);
            titleView.setText(cellItem.title);
        }

        if (cellItem.subtitleId != -1){
            TextView subtitleView = (TextView)findViewById(cellItem.subtitleId);
            subtitleView.setText(Html.fromHtml(cellItem.subtitle));
        }

        if (cellItem.cellType == Cell_Item.CellType.QUESTION_10_POINT){
            TextView textView1 = (TextView)findViewById(R.id.descriptor1);
            TextView textView2 = (TextView)findViewById(R.id.descriptor2);
            textView1.setText(cellItem.additionalInfo[0]);
            textView2.setText(cellItem.additionalInfo[1]);
        }

        if (cellItem.cellType == Cell_Item.CellType.QUESTION_LSM){
            SeekBar seekBar = (SeekBar) findViewById(R.id.intensitySlider);
            final TextView textView = (TextView) findViewById(R.id.seekbarDays);
            textView.setText(String.valueOf(seekBar.getProgress()));
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    int value = seekBar.getProgress();
                    textView.setText(String.valueOf(value));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }


    }

    public void concludeQuestionnaire(View view){

        myLocale = new Locale("en");
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        setCellItems();
        exportToCSV();

        finish();
    }

    public void exportToCSV(){
        File path = Environment.getExternalStorageDirectory();
        File filename = new File(path, "/questionnaireAnswers-" + currentPatientId +".csv");

        try {
            CSVWriter writer = new CSVWriter(new FileWriter(filename), '\t');
            writer.writeNext(new String[]{"sep=\t"});
            Cursor c = myDb.getRowData(currentPatientId);
            writer.writeNext(c.getColumnNames());

//            String emptyStr[] = {"", "", "", "",
//                    "", "", "", "", "", "", "", "", "", "", "", "", "",
//                    "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", };
            int sqlCount = numPGIFields;
            for (int i =0; i<cellItems.size(); i++){
                if (cellItems.get(i).sqlColumn != -1){
                    sqlCount++;
                }
            }
//            String[] questionStr = new String[sqlCount];
//            int count = 0;
//            for (int i =0; i<cellItems.size(); i++){
//                if (cellItems.get(i).sqlColumn != -1){
//                    questionStr[count] = cellItems.get(i).title;
//                    count++;
//                }
//            }
//
//
//            String combinedStr[] = concat(emptyStr, questionStr);
//            writer.writeNext(combinedStr);



            if (c.moveToFirst()){
                do {
                    String answerStringsIntro[] ={c.getString(myDb.COL_ROWID), c.getString(myDb.COL_FIRSTNAME), c.getString(myDb.COL_LASTNAME), c.getString(myDb.COL_HOSPITALID), "", "", "", "", "", "", "", "", "", "", "", "", ""};

                    String answerStrings[] = concat(answerStringsIntro, new String[sqlCount]);
                    for (int i = numIntroFields + numScoreFields + 1; i<sqlCount + numIntroFields + numScoreFields + 1; i++){
                        answerStrings[i] = c.getString(i);
                    }
                    writer.writeNext(answerStrings);

                } while(c.moveToNext());
            }

            writer.close();
            c.close();


        } catch (IOException e){
            System.err.println("Caught IOException: " + e.getMessage());
        }


    }

    public static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {

            mDrawerList.setItemChecked(position, true);

            if (position == 0){
                currentCell = numIntroScreens;
                loadCell(currentCell);
            } else if (position == 1){
                currentCell = numIntroScreens + 1;
                loadCell(currentCell);
            } else if (position == 2){
                currentCell = numIntroScreens + 10;
                loadCell(currentCell);
            }else if (position == 3){
                currentCell = numIntroScreens + 21;
                loadCell(currentCell);
            }else if (position == 4){
                currentCell = numIntroScreens + 26;//31
                loadCell(currentCell);
            }else if (position == 5){
                currentCell = numIntroScreens + 57;//21
                loadCell(currentCell);
            }else if (position == 6){
                currentCell = numIntroScreens + 78;//6
                loadCell(currentCell);
            }else if (position == 7){
                currentCell = numIntroScreens + 84;//4
                loadCell(currentCell);
            }else if (position == 8){
                currentCell = numIntroScreens + 90;//15
                loadCell(currentCell);
            }else if (position == 9){
                currentCell = numIntroScreens + 95;//9
                loadCell(currentCell);
            }else if (position == 10){
                currentCell = numIntroScreens + 99;
                loadCell(currentCell);
            }else if (position == 11){
                currentCell = numIntroScreens + 114;
                loadCell(currentCell);
            }else if (position == 12){
                currentCell = numIntroScreens + 123;
                loadCell(currentCell);
            }else {

            }

            mDrawerLayout.closeDrawer(mDrawerList);

        }
    }



}
