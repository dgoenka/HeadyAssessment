package com.divyanshgoenka.headyassessment.android.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.divyanshgoenka.headyassessment.presenter.BasePresenter;

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
