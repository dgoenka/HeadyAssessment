package com.divyanshgoenka.headyassessment.android;

import android.app.Activity;
import android.app.Application;

import com.divyanshgoenka.headyassessment.BuildConfig;
import com.divyanshgoenka.headyassessment.android.di.DaggerAppComponent;
import com.divyanshgoenka.headyassessment.android.log.TimberLoggerImplementation;
import com.divyanshgoenka.headyassessment.log.Logger;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import timber.log.Timber;

public class App extends Application implements HasActivityInjector {
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();

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
}
