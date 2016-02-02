package com.neuranode.neuranodeapp;

import android.app.Activity;
import android.view.View;

import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;

/**
 * Created by William Yen on 1/31/2016.
 */
public class QuestionListAdapter extends FirebaseListAdapter {
    public QuestionListAdapter(Activity activity, Class modelClass, int modelLayout, Query ref) {
        super(activity, modelClass, modelLayout, ref);
    }

    @Override
    protected void populateView(View v, Object model, int position) {
        super.populateView(v, model, position);
    }
}
