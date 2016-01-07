package com.neuranode.neuranodeapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleCursorAdapter;

import java.io.IOException;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    ListView questionContainer;
    Button submitButton;
    CustomCursorAdapter customCursorAdapter;
    int[] traits = {0, 0, 0, 0, 0, 0, 0, 0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // load data from database
        databaseHelper = new DatabaseHelper(this);
        databaseHelper.getReadableDatabase();

        // set CustomCursorAdapter to questionContainer
        customCursorAdapter = new CustomCursorAdapter(this, databaseHelper.getQuestions(), 0);
        questionContainer = (ListView) findViewById(R.id.questionContainer);
        questionContainer.setAdapter(customCursorAdapter);

        // on submit button click
        submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(calculateTraits());
            }
        });
    }

    public String calculateTraits(){
        int numChildren = questionContainer.getChildCount();
        for (int i = 0; i < numChildren; i++){
            SeekBar seekBar = (SeekBar) (((LinearLayout) questionContainer.getChildAt(i)).getChildAt(1));
            System.out.println("Progress: " + seekBar.getProgress());
            System.out.println("Trait: " + seekBar.getContentDescription());
        }
        return "";
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

        return super.onOptionsItemSelected(item);
    }
}
