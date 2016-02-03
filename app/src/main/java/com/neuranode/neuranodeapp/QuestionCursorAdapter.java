package com.neuranode.neuranodeapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by William on 1/7/2016.
 */
public class QuestionCursorAdapter extends CursorAdapter {
    private LayoutInflater cursorInflater;
    DatabaseHelper databaseHelper;

    public QuestionCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
        cursorInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        databaseHelper = new DatabaseHelper(context);
    }

    public View newView(Context context, Cursor cursor, ViewGroup parent){
        return cursorInflater.inflate(R.layout.question, parent, false);
    }

    /**
     * <code>bindView</code> binds the <code>cursor</code>'s data with the selected <code>view</code>.
     * <code>TextView</code> with id of <code>questionText</code> will bind to column <code>question</code>.
     * <code>SeekBar</code> with id of <code>questionChoice</code> will bind to column <code>trait</code>.
     *
     * @param view      existing view
     * @param context   context
     * @param cursor    cursor to get data from
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor){
        // set text of TextView
        int questionIndex = cursor.getColumnIndex("question");
        TextView textView = (TextView) view.findViewById(R.id.questionText);
        textView.setText(cursor.getInt(cursor.getColumnIndex("_id")) + ". " + cursor.getString(questionIndex));

        // set seek bar
        SeekBar seekBar = (SeekBar) view.findViewById(R.id.questionChoice);

        // set content description to id
        int rowId = cursor.getColumnIndex("_id");
        seekBar.setContentDescription(Integer.toString(cursor.getInt(rowId)));

        // set choice
        Cursor tempCursor = databaseHelper.cursorQueryRow(Integer.parseInt(seekBar.getContentDescription().toString()));
        tempCursor.moveToFirst();
        seekBar.setProgress(tempCursor.getInt(tempCursor.getColumnIndex("choice")));

        // seek bar listener
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /**
             * <code>onProgressChanged</code> updates the choice column of the database with its progress.
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                databaseHelper.updateChoice(Integer.parseInt(seekBar.getContentDescription().toString()), progress);
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
