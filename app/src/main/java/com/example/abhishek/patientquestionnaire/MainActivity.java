package com.example.abhishek.patientquestionnaire;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Environment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends ActionBarActivity {

    //public ViewFlipper vf;
    Locale myLocale;
    //public static int count;
    public static int numIntroScreens = 2;
    public static DBAdapter myDb;
    public static long currentPatientId;
    private String[] mSectionTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    public CharSequence mTitle;
    public List<Cell_Item> cellItems;
    public static int currentCell;
    public static List<Cell_Item.CellType> nonQuestionSubset;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openDB();
        cellItems = new ArrayList<>();
        cellItems.add(new Cell_Item(this, null, null, Cell_Item.CellType.INTRO_LANGUAGE, -1,null));
        cellItems.add(new Cell_Item(this, null, null, Cell_Item.CellType.INTRO_DETAILS, myDb.COL_FIRSTNAME,null));

        cellItems.add(new Cell_Item(this, getString(R.string.gdsTitle),getString(R.string.gdsPreamble), Cell_Item.CellType.INTRO_QUESTIONNAIRE, -1,null));
        cellItems.add(new Cell_Item(this, getString(R.string.gds1), null, Cell_Item.CellType.QUESTION_YES_NO, myDb.COL_GDS1,null));
        cellItems.add(new Cell_Item(this, getString(R.string.gds2), null, Cell_Item.CellType.QUESTION_YES_NO, myDb.COL_GDS2,null));
        cellItems.add(new Cell_Item(this, getString(R.string.gds3), null, Cell_Item.CellType.QUESTION_YES_NO, myDb.COL_GDS3,null));
        cellItems.add(new Cell_Item(this, getString(R.string.gds4), null, Cell_Item.CellType.QUESTION_YES_NO, myDb.COL_GDS4,null));
        cellItems.add(new Cell_Item(this, getString(R.string.gds5), null, Cell_Item.CellType.QUESTION_YES_NO, myDb.COL_GDS5,null));
        cellItems.add(new Cell_Item(this, getString(R.string.gds6), null, Cell_Item.CellType.QUESTION_YES_NO, myDb.COL_GDS6,null));
        cellItems.add(new Cell_Item(this, getString(R.string.gds7), null, Cell_Item.CellType.QUESTION_YES_NO, myDb.COL_GDS7,null));
        cellItems.add(new Cell_Item(this, getString(R.string.gds8), null, Cell_Item.CellType.QUESTION_YES_NO, myDb.COL_GDS8,null));

        cellItems.add(new Cell_Item(this, getString(R.string.pdqTitle),getString(R.string.pdqPreamble), Cell_Item.CellType.INTRO_QUESTIONNAIRE, -1,null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq1), getString(R.string.pdqIntro), Cell_Item.CellType.QUESTION_5_POINT, myDb.COL_PDQ1,null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq2), getString(R.string.pdqIntro), Cell_Item.CellType.QUESTION_5_POINT, myDb.COL_PDQ2,null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq3), getString(R.string.pdqIntro), Cell_Item.CellType.QUESTION_5_POINT, myDb.COL_PDQ3,null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq4), getString(R.string.pdqIntro), Cell_Item.CellType.QUESTION_5_POINT, myDb.COL_PDQ4,null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq5), getString(R.string.pdqIntro), Cell_Item.CellType.QUESTION_5_POINT, myDb.COL_PDQ5,null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq6), getString(R.string.pdqIntro), Cell_Item.CellType.QUESTION_5_POINT, myDb.COL_PDQ6,null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq7), getString(R.string.pdqIntro), Cell_Item.CellType.QUESTION_5_POINT, myDb.COL_PDQ7,null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq8), getString(R.string.pdqIntro), Cell_Item.CellType.QUESTION_5_POINT, myDb.COL_PDQ8,null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq9), getString(R.string.pdqIntro), Cell_Item.CellType.QUESTION_5_POINT, myDb.COL_PDQ9,null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq10), getString(R.string.pdqIntro), Cell_Item.CellType.QUESTION_5_POINT, myDb.COL_PDQ10,null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq11), getString(R.string.pdqIntro), Cell_Item.CellType.QUESTION_5_POINT, myDb.COL_PDQ11,null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq12), getString(R.string.pdqIntro), Cell_Item.CellType.QUESTION_5_POINT, myDb.COL_PDQ12,null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq13), getString(R.string.pdqIntro), Cell_Item.CellType.QUESTION_5_POINT, myDb.COL_PDQ13,null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq14), getString(R.string.pdqIntro), Cell_Item.CellType.QUESTION_5_POINT, myDb.COL_PDQ14,null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq15), getString(R.string.pdqIntro), Cell_Item.CellType.QUESTION_5_POINT, myDb.COL_PDQ15,null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq16), getString(R.string.pdqIntro), Cell_Item.CellType.QUESTION_5_POINT, myDb.COL_PDQ16,null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq17), getString(R.string.pdqIntro), Cell_Item.CellType.QUESTION_5_POINT, myDb.COL_PDQ17,null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq18), getString(R.string.pdqIntro), Cell_Item.CellType.QUESTION_5_POINT, myDb.COL_PDQ18,null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq19), getString(R.string.pdqIntro), Cell_Item.CellType.QUESTION_5_POINT, myDb.COL_PDQ19,null));
        cellItems.add(new Cell_Item(this, getString(R.string.pdq20), getString(R.string.pdqIntro), Cell_Item.CellType.QUESTION_5_POINT, myDb.COL_PDQ20,null));

        cellItems.add(new Cell_Item(this, getString(R.string.vahsTitle), getString(R.string.vahsPreamble), Cell_Item.CellType.INTRO_QUESTIONNAIRE, -1, null));
        cellItems.add(new Cell_Item(this, getString(R.string.vahs1), null, Cell_Item.CellType.QUESTION_10_POINT, myDb.COL_VAHS1, new String[]{"Poor","Excellent"}));
        cellItems.add(new Cell_Item(this, getString(R.string.vahs2), null, Cell_Item.CellType.QUESTION_10_POINT, myDb.COL_VAHS2, new String[]{"Poor","Excellent"}));
        cellItems.add(new Cell_Item(this, getString(R.string.vahs3), null, Cell_Item.CellType.QUESTION_10_POINT, myDb.COL_VAHS3, new String[]{"Much distress","None"}));
        cellItems.add(new Cell_Item(this, getString(R.string.vahs4), null, Cell_Item.CellType.QUESTION_10_POINT, myDb.COL_VAHS4, new String[]{"Much pain","None"}));
        cellItems.add(new Cell_Item(this, getString(R.string.vahs5), null, Cell_Item.CellType.QUESTION_10_POINT, myDb.COL_VAHS5, new String[]{"Much fatigue","None"}));
        cellItems.add(new Cell_Item(this, getString(R.string.vahs6), null, Cell_Item.CellType.QUESTION_10_POINT, myDb.COL_VAHS6, new String[]{"Much depression","None"}));
        cellItems.add(new Cell_Item(this, getString(R.string.vahs7), null, Cell_Item.CellType.QUESTION_10_POINT, myDb.COL_VAHS7, new String[]{"Much anxiety","None"}));
        cellItems.add(new Cell_Item(this, getString(R.string.vahs8), null, Cell_Item.CellType.QUESTION_10_POINT, myDb.COL_VAHS8, new String[]{"Poor","Excellent"}));

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

        if (id == R.id.action_view_database) {
            Intent intent = new Intent(MainActivity.this,ViewDatabase.class);
            startActivity(intent);
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
        String testString = String.valueOf(value);
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

            String questionStr[] = {"", "", "", "",
                    "", "", "",

                    getResources().getString(R.string.gds1),
                    getResources().getString(R.string.gds2),
                    getResources().getString(R.string.gds3),
                    getResources().getString(R.string.gds4),
                    getResources().getString(R.string.gds5),
                    getResources().getString(R.string.gds6),
                    getResources().getString(R.string.gds7),
                    getResources().getString(R.string.gds8),

                    getResources().getString(R.string.pdq1),
                    getResources().getString(R.string.pdq2),
                    getResources().getString(R.string.pdq3),
                    getResources().getString(R.string.pdq4),
                    getResources().getString(R.string.pdq5),
                    getResources().getString(R.string.pdq6),
                    getResources().getString(R.string.pdq7),
                    getResources().getString(R.string.pdq8),
                    getResources().getString(R.string.pdq9),
                    getResources().getString(R.string.pdq10),
                    getResources().getString(R.string.pdq11),
                    getResources().getString(R.string.pdq12),
                    getResources().getString(R.string.pdq13),
                    getResources().getString(R.string.pdq14),
                    getResources().getString(R.string.pdq15),
                    getResources().getString(R.string.pdq16),
                    getResources().getString(R.string.pdq17),
                    getResources().getString(R.string.pdq18),
                    getResources().getString(R.string.pdq19),
                    getResources().getString(R.string.pdq20),

                    getResources().getString(R.string.vahs1),
                    getResources().getString(R.string.vahs2),
                    getResources().getString(R.string.vahs3),
                    getResources().getString(R.string.vahs4),
                    getResources().getString(R.string.vahs5),
                    getResources().getString(R.string.vahs6),
                    getResources().getString(R.string.vahs7),
                    getResources().getString(R.string.vahs8)
            };


            writer.writeNext(questionStr);



            if (c.moveToFirst()){
                do {
                    String arrStr[] ={c.getString(myDb.COL_ROWID), c.getString(myDb.COL_FIRSTNAME), c.getString(myDb.COL_LASTNAME), c.getString(myDb.COL_HOSPITALID),
                            "", "", "",
                            c.getString(myDb.COL_GDS1), c.getString(myDb.COL_GDS2), c.getString(myDb.COL_GDS3), c.getString(myDb.COL_GDS4), c.getString(myDb.COL_GDS5), c.getString(myDb.COL_GDS6), c.getString(myDb.COL_GDS7), c.getString(myDb.COL_GDS8),
                            c.getString(myDb.COL_PDQ1), c.getString(myDb.COL_PDQ2), c.getString(myDb.COL_PDQ3), c.getString(myDb.COL_PDQ4), c.getString(myDb.COL_PDQ5), c.getString(myDb.COL_PDQ6), c.getString(myDb.COL_PDQ7), c.getString(myDb.COL_PDQ8), c.getString(myDb.COL_PDQ9), c.getString(myDb.COL_PDQ10), c.getString(myDb.COL_PDQ11), c.getString(myDb.COL_PDQ12), c.getString(myDb.COL_PDQ13), c.getString(myDb.COL_PDQ14), c.getString(myDb.COL_PDQ15), c.getString(myDb.COL_PDQ16), c.getString(myDb.COL_PDQ17), c.getString(myDb.COL_PDQ18), c.getString(myDb.COL_PDQ19), c.getString(myDb.COL_PDQ20),
                            c.getString(myDb.COL_VAHS1), c.getString(myDb.COL_VAHS2), c.getString(myDb.COL_VAHS3), c.getString(myDb.COL_VAHS4), c.getString(myDb.COL_VAHS5), c.getString(myDb.COL_VAHS6), c.getString(myDb.COL_VAHS7), c.getString(myDb.COL_VAHS8)
                    };
                    writer.writeNext(arrStr);
                } while(c.moveToNext());
            }

            writer.close();
            c.close();


        } catch (IOException e){
            System.err.println("Caught IOException: " + e.getMessage());
        }


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
