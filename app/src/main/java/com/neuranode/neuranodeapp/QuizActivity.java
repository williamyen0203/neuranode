package com.neuranode.neuranodeapp;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.Arrays;

public class QuizActivity extends AppCompatActivity {
    QuestionCursorAdapter questionCursorAdapter;
    DatabaseHelper databaseHelper;
    String questionQuery = "SELECT * FROM questions_table a WHERE a._id IN (SELECT b._id FROM questions_table b WHERE a.trait = b.trait LIMIT 4) ORDER BY random()";
    String calculateQuery = "SELECT trait, choice FROM questions_table WHERE choice IS NOT NULL";
    ListView questionContainer;
    Button submitButton;
    // traits[i][0] = total value
    // traits[i][1] = number of questions
    int[][] traits = {{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}};
    double[] traitsAvg = {0, 0, 0, 0, 0, 0, 0, 0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // load data from database
        databaseHelper = new DatabaseHelper(this);

        questionCursorAdapter = new QuestionCursorAdapter(this, databaseHelper.cursorQuery(questionQuery), 0);
        questionContainer = (ListView) findViewById(R.id.questionContainer);
        questionContainer.setAdapter(questionCursorAdapter);

        // set footer
        View footerView = getLayoutInflater().inflate(R.layout.question_container_footer, null, false);
        questionContainer.addFooterView(footerView);

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
     * <code>updateTraits</code> will iterate through non null trait and choice columns and update
     * the <code>traits</code> array accordingly.
     */
    public void updateTraits(){
        System.out.println("updateTraits pressed");
        Cursor calculateCursor = databaseHelper.cursorQuery(calculateQuery);
        while (calculateCursor.moveToNext()){
            int trait = calculateCursor.getInt(0) - 1;
            int choice = calculateCursor.getInt(1);
            traits[trait][0] += choice;
            traits[trait][1]++;
        }
        calculateCursor.close();

        for (int i = 0; i < traits.length; i++){
            traitsAvg[i] = 1.0 * traits[i][0] / traits[i][1];
        }

        // FIXME debug
        System.out.println(Arrays.deepToString(traits));
        System.out.println(Arrays.toString(traitsAvg));
    }

}
