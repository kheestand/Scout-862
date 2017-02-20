package com.lightningrobotics.scout_862;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.io.FileNotFoundException;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                AutoActivity aa = new AutoActivity();
                return aa;
            case 1:
                TeleopActivity ta = new TeleopActivity();
                return ta;
            case 2:
                EndActivity ea = new EndActivity();
                return ea;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}

