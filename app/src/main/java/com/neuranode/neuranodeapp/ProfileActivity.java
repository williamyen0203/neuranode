package com.neuranode.neuranodeapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Arrays;

public class ProfileActivity extends AppCompatActivity {
    TextView traitsAvg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        traitsAvg = (TextView) findViewById(R.id.traitsAvg);

        traitsAvg.setText(Arrays.toString(getIntent().getDoubleArrayExtra("traitsAvg")));
    }
}
