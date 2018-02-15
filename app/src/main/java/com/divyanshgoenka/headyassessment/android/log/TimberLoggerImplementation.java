package com.divyanshgoenka.headyassessment.android.log;

import com.divyanshgoenka.headyassessment.log.Logger.LoggerImplementation;

import timber.log.Timber;

/**
 * Created by divyanshgoenka on 15/02/18.
 */

public class TimberLoggerImplementation implements LoggerImplementation {

    @Override
    public void d(String s) {
        Timber.d(s);
    }

    @Override
    public void e(Throwable e) {
        Timber.e(e);
    }
}
