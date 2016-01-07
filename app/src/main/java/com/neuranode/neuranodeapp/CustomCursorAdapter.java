package com.neuranode.neuranodeapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by William on 1/7/2016.
 */
public class CustomCursorAdapter extends CursorAdapter {
    private Context context;
    private int layout;
    private LayoutInflater cursorInflater;

    public CustomCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
        cursorInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor){
        // set text of text view
        int questionIndex = cursor.getColumnIndex("question");
        TextView textView = (TextView) ((LinearLayout) view).getChildAt(0);
        textView.setText(cursor.getString(questionIndex));

        // set content description of seekbar
        int traitIndex = cursor.getColumnIndex("trait");
        SeekBar seekBar = (SeekBar) ((LinearLayout) view).getChildAt(1);
        seekBar.setContentDescription(Integer.toString(cursor.getInt(traitIndex)));
    }

    public View newView(Context context, Cursor cursor, ViewGroup parent){
        return cursorInflater.inflate(R.layout.question, parent, false);
    }
}
