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
    // [0] = total value, [1] = number of questions
    int[][] traits = {{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}};
    double[] traitsAvg = {0, 0, 0, 0, 0, 0, 0, 0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // load data from database, reset choices
        databaseHelper = new DatabaseHelper(this);
        // TODO: close this cursor
        databaseHelper.clearChoices();
        Cursor questionListCursor = databaseHelper.cursorQuery(questionQuery);
        databaseHelper.resetChoices(questionListCursor, 2);

        // set adapter
        questionCursorAdapter = new QuestionCursorAdapter(this, questionListCursor, 0);
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

        System.out.println("\n" + Arrays.deepToString(traits));
        System.out.println(Arrays.toString(traitsAvg));
    }
}
