package com.neuranode.neuranodeapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by William on 1/7/2016.
 */
public class QuestionCursorAdapter extends CursorAdapter {
    private LayoutInflater cursorInflater;

    public QuestionCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
        cursorInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        TextView textView = (TextView) ((LinearLayout) view).getChildAt(0);
        textView.setText(cursor.getString(questionIndex));

        // set content description of SeekBar
        int traitIndex = cursor.getColumnIndex("trait");
        SeekBar seekBar = (SeekBar) ((LinearLayout) view).getChildAt(1);
        seekBar.setContentDescription(Integer.toString(cursor.getInt(traitIndex)));
    }

    public View newView(Context context, Cursor cursor, ViewGroup parent){
        return cursorInflater.inflate(R.layout.question, parent, false);
    }
}
