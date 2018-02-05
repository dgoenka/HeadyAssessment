package com.divyanshgoenka.headyassessment.android.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.divyanshgoenka.headyassessment.R;


/**
 * Created by divyanshgoenka on 04/02/18.
 */

public class BaseAcitivity extends AppCompatActivity{

    private static final String BACK_STACK_ROOT_TAG = "root_fragment";


    protected void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment)
        .addToBackStack(BACK_STACK_ROOT_TAG).commit();
        invalidateOptionsMenu();
    }
}
