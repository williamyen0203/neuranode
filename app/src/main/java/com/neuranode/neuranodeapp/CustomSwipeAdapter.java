package com.neuranode.neuranodeapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Yen on 1/14/2016.
 */
public class CustomSwipeAdapter extends FragmentStatePagerAdapter {
    final int COUNT = 3;
    Bundle bundle;
    Context context;


    public CustomSwipeAdapter(FragmentManager fragmentManager, Context context){
        super(fragmentManager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                SelfFragment selfFragment = new SelfFragment();
                selfFragment.setArguments(bundle);
                return selfFragment;
            case 1:
                return new CaptureFragment();
            case 2:
                return new NetworkFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return context.getResources().getString(R.string.self_fragment_header);
            case 1:
                return context.getResources().getString(R.string.capture_fragment_header);
            case 2:
                return context.getResources().getString(R.string.network_fragment_header);
        }
        return null;
    }

    public void addBundle(Bundle bundle){
        this.bundle = bundle;
    }
}
