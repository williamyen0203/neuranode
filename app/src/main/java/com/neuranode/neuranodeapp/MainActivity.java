package com.neuranode.neuranodeapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    QuestionCursorAdapter questionCursorAdapter;
    DatabaseHelper databaseHelper;
    ListView questionContainer;
    Button submitButton;
    String questionQuery = "SELECT * FROM questions_table GROUP BY trait LIMIT 11";
    Cursor cursor;
    // traits[i][0] = total value
    // traits[i][1] = number of questions
    int[][] traits = {{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}};

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
        cursor = databaseHelper.cursorQuery(questionQuery);
        questionCursorAdapter = new QuestionCursorAdapter(this, databaseHelper.cursorQuery(questionQuery), 0);
        questionContainer = (ListView) findViewById(R.id.questionContainer);
        questionContainer.setAdapter(questionCursorAdapter);

        // on submit button click
        submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTraits();
            }
        });
    }

    /**
     * <code>calculateTraits</code> will update the <code>traits</code> array with the values
     * depending on the user's choice (<code>SeekBar</code> values).
     */
    public void updateTraits(){
        System.out.println(questionContainer.getChildCount());
//        int numChildren = cursor.getCount();
//        for (int i = 0; i < numChildren; i++){
//            SeekBar seekBar = (SeekBar) (((LinearLayout) questionContainer.getChildAt(i)).getChildAt(1));
//            int traitAffected = Integer.parseInt(seekBar.getContentDescription().toString()) - 1;
//            traits[traitAffected][0] += seekBar.getProgress();
//            traits[traitAffected][1]++;
//        }
        // FIXME debug
        System.out.println(Arrays.deepToString(traits));
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
