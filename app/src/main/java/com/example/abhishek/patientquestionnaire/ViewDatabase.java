package com.example.abhishek.patientquestionnaire;

import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class ViewDatabase extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_database);

        TextView textView = (TextView) findViewById(R.id.text_view_database);

        Cursor c = MainActivity.myDb.getAllRowData();
        String displayText = "";
        if (c.moveToFirst()){
            do {
                displayText = displayText + c.getString(MainActivity.myDb.COL_FIRSTNAME) + " " + c.getString(MainActivity.myDb.COL_LASTNAME) + " " + c.getString(MainActivity.myDb.COL_HOSPITALID) + " " +
                        c.getString(MainActivity.myDb.COL_GDS1) + " " + c.getString(MainActivity.myDb.COL_GDS2) + " " + c.getString(MainActivity.myDb.COL_GDS3) + "\n";
            } while(c.moveToNext());
        }

        textView.setText(displayText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_database, menu);
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

        return super.onOptionsItemSelected(item);
    }
}
