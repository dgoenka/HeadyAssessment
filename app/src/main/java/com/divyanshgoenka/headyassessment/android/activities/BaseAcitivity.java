package com.divyanshgoenka.headyassessment.android.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.divyanshgoenka.headyassessment.R;
import com.divyanshgoenka.headyassessment.presenter.BasePresenter;
import com.divyanshgoenka.headyassessment.view.BaseView;

import javax.inject.Inject;

import dagger.android.AndroidInjection;


/**
 * Base Activity class.
 *
 * Parameter P is the Presenter Class
 * Parameter V is the View Class
 *
 * Created by divyanshgoenka on 04/02/18.
 */

public abstract class BaseAcitivity<P extends BasePresenter> extends AppCompatActivity{

    @Inject
    P Presenter;

    private static final String BACK_STACK_ROOT_TAG = "root_fragment";


    protected void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment)
        .addToBackStack(BACK_STACK_ROOT_TAG).commit();
        invalidateOptionsMenu();
        setTitle(R.string.app_name);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setPresenter();
    }


    abstract void setPresenter();

    @Override
    protected void onStop() {
        super.onStop();
        unsetPresenter();
    }

    protected abstract void unsetPresenter();
}
