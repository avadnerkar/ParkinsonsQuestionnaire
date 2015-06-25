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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;


public class MainActivity extends ActionBarActivity {

    public ViewFlipper vf;
    Locale myLocale;
    public static int count;
    public static int numIntroScreens = 2;
    public static DBAdapter myDb;
    public static long currentPatientId;
    private String[] mSectionTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    public CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSectionTitles = getResources().getStringArray(R.array.section_titles);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mSectionTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        vf = (ViewFlipper) findViewById(R.id.ViewFlipper);
        count = vf.getChildCount();
        System.out.println(count);
        currentPatientId = -1;
        openDB();


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
            btnPrevious();
            return true;
        }

        if (id == R.id.action_skip) {
            btnNext();
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

    public void btnPrevious(){
        int currentScreen = vf.getDisplayedChild();
        if (currentScreen>numIntroScreens){
            vf.showPrevious();
            int questionNum = currentScreen - numIntroScreens +1;
            myDb.updateAnswer(currentPatientId, questionNum, null);
        }

    }

    public void btnNext(){
        int currentScreen = vf.getDisplayedChild();
        if (currentScreen>numIntroScreens-1){
            vf.showNext();
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
        setContentView(R.layout.activity_main);
        vf = (ViewFlipper) findViewById(R.id.ViewFlipper);
        vf.showNext();
    }

    public void buttonYes(View view){
        int currentScreen = vf.getDisplayedChild();

        int questionNum = currentScreen - numIntroScreens +1;
        myDb.updateAnswer(currentPatientId, questionNum, "Yes");


        if (currentScreen!=count-1){
            vf.showNext();
        }

    }

    public void buttonNo(View view){
        int currentScreen = vf.getDisplayedChild();

        int questionNum = currentScreen - numIntroScreens +1;
        myDb.updateAnswer(currentPatientId, questionNum, "No");

        if (currentScreen!=count-1){
            vf.showNext();
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

        vf.showNext();

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
                    getResources().getString(R.string.gds1),
                    getResources().getString(R.string.gds2),
                    getResources().getString(R.string.gds3),
                    getResources().getString(R.string.gds4),
                    getResources().getString(R.string.gds5),
                    getResources().getString(R.string.gds6),
                    getResources().getString(R.string.gds7),
                    getResources().getString(R.string.gds8)};

            writer.writeNext(questionStr);



            if (c.moveToFirst()){
                do {
                    String arrStr[] ={c.getString(myDb.COL_ROWID), c.getString(myDb.COL_FIRSTNAME), c.getString(myDb.COL_LASTNAME), c.getString(myDb.COL_HOSPITALID),
                            c.getString(myDb.COL_Q1), c.getString(myDb.COL_Q2), c.getString(myDb.COL_Q3), c.getString(myDb.COL_Q4),
                            c.getString(myDb.COL_Q5), c.getString(myDb.COL_Q6), c.getString(myDb.COL_Q7), c.getString(myDb.COL_Q8)};
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


            mDrawerLayout.closeDrawer(mDrawerList);



        }
    }



}
