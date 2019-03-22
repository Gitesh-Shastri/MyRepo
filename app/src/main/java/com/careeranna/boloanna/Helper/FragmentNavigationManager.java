package com.careeranna.boloanna.Helper;

import android.support.v4.app.FragmentManager;

import com.careeranna.boloanna.Interface.NavigationManager;
import com.careeranna.boloanna.LandingActivity;

public class FragmentNavigationManager implements NavigationManager {

    private static FragmentNavigationManager mInstance;

    private FragmentManager mFragmentManager;
    private LandingActivity mLandingActivity;

    public static FragmentNavigationManager getInstance(LandingActivity mLandingActivity) {
        if(mInstance == null)
            mInstance = new FragmentNavigationManager();
        mInstance.configure(mLandingActivity);
        return mInstance;
    }

    private void configure(LandingActivity mLandingActivity) {
        mLandingActivity = mLandingActivity;
        mFragmentManager = mLandingActivity.getSupportFragmentManager();

    }

    @Override
    public void showFragement(String Title) {

    }
}
