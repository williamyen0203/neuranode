package com.neuranode.neuranodeapp;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ProfileActivity extends AppCompatActivity {
    CustomSwipeAdapter customSwipeAdapter;
    ViewPager viewPager;
    PagerTitleStrip pagerTitleStrip;
    double[] traitsAvg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        traitsAvg = getIntent().getDoubleArrayExtra("traitsAvg");
        customSwipeAdapter = new CustomSwipeAdapter(getSupportFragmentManager(), this);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        Bundle bundle = new Bundle();

        // set fragment state pager adapter
        bundle.putDoubleArray("traitsAvg", traitsAvg);
        customSwipeAdapter.addBundle(bundle);

        viewPager.setAdapter(customSwipeAdapter);

        // style pager title strip
        pagerTitleStrip = (PagerTitleStrip) findViewById(R.id.pager_title_strip);
        pagerTitleStrip.setTextColor(ContextCompat.getColor(this, R.color.colorBG));
        pagerTitleStrip.setTextSpacing(150);
    }
}
