package com.example.abhishek.patientquestionnaire;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class NewPatientActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_patient);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_patient, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_start) {
            buttonCreatePatient();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void buttonCreatePatient() {
        EditText editText = (EditText)findViewById(R.id.edit_first_name);
        String firstName = editText.getText().toString();

        editText = (EditText)findViewById(R.id.edit_last_name);
        String lastName = editText.getText().toString();

        editText = (EditText)findViewById(R.id.edit_hospital_id);
        String hospitalId = editText.getText().toString();

        long rowId = MainActivity.myDb.createData(firstName, lastName, hospitalId);
        MainActivity.currentPatientId = rowId;

        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.PREFS_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putLong("rowId", rowId);
        editor.apply();

        MainActivity.currentCell++;
        MainActivity.mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        finish();
    }
}
