package com.example.abhishek.patientquestionnaire;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Environment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    public List<Cell_Item> cellItems;
    public static int currentCell;
    public static List<Cell_Item.CellType> nonQuestionSubset;
    public static int numIntroFields = 3;
    public static int numScoreFields = 6;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openDB();
        cellItems = new ArrayList<>();
        cellItems.add(new Cell_Item(this, null, null, Cell_Item.CellType.INTRO_LANGUAGE, -1, null));
        cellItems.add(new Cell_Item(this, null, null, Cell_Item.CellType.INTRO_DETAILS, -1,null));

        int sqlColumnNum = numIntroFields + numScoreFields + 1;

        cellItems.add(new Cell_Item(this, getString(R.string.vahsTitle), getString(R.string.vahsPreamble), Cell_Item.CellType.INTRO_QUESTIONNAIRE, -1, null));
        cellItems.add(new Cell_Item(this, getString(R.string.vahs1), null, Cell_Item.CellType.QUESTION_10_POINT, sqlColumnNum++,  new String[]{"Poor","Excellent"}));
        cellItems.add(new Cell_Item(this, getString(R.string.vahs2), null, Cell_Item.CellType.QUESTION_10_POINT, sqlColumnNum++,  new String[]{"Poor","Excellent"}));
        cellItems.add(new Cell_Item(this, getString(R.string.vahs3), null, Cell_Item.CellType.QUESTION_10_POINT, sqlColumnNum++,  new String[]{"Much distress","None"}));
        cellItems.add(new Cell_Item(this, getString(R.string.vahs4), null, Cell_Item.CellType.QUESTION_10_POINT, sqlColumnNum++,  new String[]{"Much pain","None"}));
        cellItems.add(new Cell_Item(this, getString(R.string.vahs5), null, Cell_Item.CellType.QUESTION_10_POINT, sqlColumnNum++,  new String[]{"Much fatigue","None"}));
        cellItems.add(new Cell_Item(this, getString(R.string.vahs6), null, Cell_Item.CellType.QUESTION_10_POINT, sqlColumnNum++,  new String[]{"Much depression","None"}));
        cellItems.add(new Cell_Item(this, getString(R.string.vahs7), null, Cell_Item.CellType.QUESTION_10_POINT, sqlColumnNum++,  new String[]{"Much anxiety","None"}));
        cellItems.add(new Cell_Item(this, getString(R.string.vahs8), null, Cell_Item.CellType.QUESTION_10_POINT, sqlColumnNum++,  new String[]{"Poor","Excellent"}));

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

        cellItems.add(new Cell_Item(this, getString(R.string.gdsTitle),getString(R.string.gdsPreamble), Cell_Item.CellType.INTRO_QUESTIONNAIRE, -1,null));
        cellItems.add(new Cell_Item(this, getString(R.string.gds1), null, Cell_Item.CellType.QUESTION_YES_NO_GDS, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.gds2), null, Cell_Item.CellType.QUESTION_YES_NO_GDS, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.gds3), null, Cell_Item.CellType.QUESTION_YES_NO_GDS, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.gds4), null, Cell_Item.CellType.QUESTION_YES_NO_GDS, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.gds5), null, Cell_Item.CellType.QUESTION_YES_NO_GDS, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.gds6), null, Cell_Item.CellType.QUESTION_YES_NO_GDS, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.gds7), null, Cell_Item.CellType.QUESTION_YES_NO_GDS, sqlColumnNum++,null));
        cellItems.add(new Cell_Item(this, getString(R.string.gds8), null, Cell_Item.CellType.QUESTION_YES_NO_GDS, sqlColumnNum++,null));

        cellItems.add(new Cell_Item(this, null, null, Cell_Item.CellType.CONCLUSION, -1,null));

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

        loadCell(0);

        currentPatientId = -1;

        nonQuestionSubset = new ArrayList<>();
        nonQuestionSubset.add(Cell_Item.CellType.INTRO_DETAILS);
        nonQuestionSubset.add(Cell_Item.CellType.INTRO_LANGUAGE);
        nonQuestionSubset.add(Cell_Item.CellType.INTRO_QUESTIONNAIRE);
        nonQuestionSubset.add(Cell_Item.CellType.CONCLUSION);



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

        if (currentCell<=numIntroScreens-1){
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
            nextScreen();
            return true;
        }

        if (id == R.id.action_delete_all) {

            myDb.deleteAllData();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    public void buttonPrevious(){

        if (currentCell>numIntroScreens+1){
            currentCell--;
            loadCell(currentCell);
            Cell_Item cellItem = cellItems.get(currentCell);

            if (!nonQuestionSubset.contains(cellItem.cellType)){
                myDb.updateAnswer(currentPatientId, cellItem.sqlColumn, null);
            }
        }

    }

    public void nextScreen(){
        currentCell++;
        loadCell(currentCell);
    }

    public void buttonNext(View view){
        nextScreen();
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
        currentCell ++;
        loadCell(currentCell);

    }

    public void buttonClick(View view){
        Button b = (Button) view;
        myDb.updateAnswer(currentPatientId, cellItems.get(currentCell).sqlColumn, b.getText().toString());
        currentCell++;
        loadCell(currentCell);

    }

    public void seekbarNext(View view){
        SeekBar seekBar = (SeekBar) findViewById(R.id.intensitySlider);
        int value = seekBar.getProgress();
        myDb.updateAnswer(currentPatientId, cellItems.get(currentCell).sqlColumn, String.valueOf(value));
        currentCell++;
        loadCell(currentCell);
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
            subtitleView.setText(cellItem.subtitle);
        }

        if (cellItem.additionalInfo != null){
            TextView textView1 = (TextView)findViewById(R.id.descriptor1);
            TextView textView2 = (TextView)findViewById(R.id.descriptor2);
            textView1.setText(cellItem.additionalInfo[0]);
            textView2.setText(cellItem.additionalInfo[1]);
        }

        if (cellItem.cellType == Cell_Item.CellType.QUESTION_LSM){
            SeekBar seekBar = (SeekBar) findViewById(R.id.intensitySlider);
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    int value = seekBar.getProgress();
                    TextView textView = (TextView) findViewById(R.id.seekbarDays);
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

        exportToCSV();

        finish();
    }

    public void exportToCSV(){
        File path = Environment.getExternalStorageDirectory();
        File filename = new File(path, "/questionnaireAnswers.csv");

        try {
            CSVWriter writer = new CSVWriter(new FileWriter(filename), '\t');
            Cursor c = myDb.getAllRowData();
            writer.writeNext(c.getColumnNames());

            String emptyStr[] = {"", "", "", "",
                    "", "", "", "", "", ""};
            int sqlCount = 0;
            for (int i =0; i<cellItems.size(); i++){
                if (cellItems.get(i).sqlColumn != -1){
                    sqlCount++;
                }
            }
            String[] questionStr = new String[sqlCount];
            int count = 0;
            for (int i =0; i<cellItems.size(); i++){
                if (cellItems.get(i).sqlColumn != -1){
                    questionStr[count] = cellItems.get(i).title;
                    count++;
                }
            }


            String combinedStr[] = concat(emptyStr, questionStr);
            writer.writeNext(combinedStr);



            if (c.moveToFirst()){
                do {
                    String answerStringsIntro[] ={c.getString(myDb.COL_ROWID), c.getString(myDb.COL_FIRSTNAME), c.getString(myDb.COL_LASTNAME), c.getString(myDb.COL_HOSPITALID), "", "", "", "", "", ""};

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
                currentCell = 11;
                loadCell(currentCell);
            } else if (position == 2){
                currentCell = 32;
                loadCell(currentCell);
            } else {

            }

            mDrawerLayout.closeDrawer(mDrawerList);

        }
    }



}
