package com.divyanshgoenka.headyassessment.android.fragment;

import android.support.v4.app.FragmentManager;

/**
 * Created by divyanshgoenka on 25/02/18.
 */
public class KeepClearBackStackListener implements FragmentManager.OnBackStackChangedListener {
    FragmentManager fragmentManager;

    public KeepClearBackStackListener(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void onBackStackChanged() {
        while (fragmentManager.getBackStackEntryCount() > 1) { // user pressed back
            fragmentManager.popBackStackImmediate();
        }
    }

}
