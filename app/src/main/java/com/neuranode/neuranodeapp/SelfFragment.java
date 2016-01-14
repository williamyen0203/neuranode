package com.neuranode.neuranodeapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;

public class SelfFragment extends Fragment {
    TextView traitsAvgTextView;
    double[] traitsAvg;

    public SelfFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_self, container, false);
        traitsAvg = getArguments().getDoubleArray("traitsAvg");

        traitsAvgTextView = (TextView) view.findViewById(R.id.traitsAvgTextView);
        traitsAvgTextView.setText(Arrays.toString(traitsAvg));
        return view;
    }
}
