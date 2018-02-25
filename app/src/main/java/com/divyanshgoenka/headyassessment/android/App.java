package com.divyanshgoenka.headyassessment.android;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.divyanshgoenka.headyassessment.BuildConfig;
import com.divyanshgoenka.headyassessment.android.di.DaggerAppComponent;
import com.divyanshgoenka.headyassessment.android.log.TimberLoggerImplementation;
import com.divyanshgoenka.headyassessment.di.Implementor;
import com.divyanshgoenka.headyassessment.log.Logger;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import timber.log.Timber;

public class App extends Application implements HasActivityInjector, Implementor.Implementation {
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        Implementor.setCurrentImplementation(this);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            Logger.setLoggerImplementation(new TimberLoggerImplementation());
        }

        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public boolean isNetworkAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
