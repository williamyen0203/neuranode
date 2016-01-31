package com.neuranode.neuranodeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;

public class SelfFragment extends Fragment {
    final static int QUIZ_REQUEST = 1;
    final static String QUIZ_CODE = "quizArray";

    Button takeQuizButton;
    TextView traitsAvgTextView;
    double[] traitsAvg;

    public SelfFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_self, container, false);
        takeQuizButton = (Button) view.findViewById(R.id.takeQuizButton);
        traitsAvgTextView = (TextView) view.findViewById(R.id.traitsAvgTextView);
        traitsAvg = getArguments().getDoubleArray("traitsAvg");

        takeQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), QuizActivity.class);
                startActivityForResult(intent, QUIZ_REQUEST);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case QUIZ_REQUEST:
                if (resultCode == getActivity().RESULT_OK) {
                    // profileButton.setVisibility(View.VISIBLE);
                    // TODO: set quizTaken = true;
                    traitsAvg = data.getDoubleArrayExtra(QUIZ_CODE);
                    traitsAvgTextView.setText(Arrays.toString(traitsAvg));
                }
                break;
        }
    }
}
