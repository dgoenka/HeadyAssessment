package com.divyanshgoenka.headyassessment.android.rx;

import com.divyanshgoenka.headyassessment.rx.SchedulersFacade;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Provides various threading schedulers.
 */

public class AndroidSchedulersFacade implements SchedulersFacade {

    @Inject
    public AndroidSchedulersFacade() {
    }

    /**
     * IO thread pool scheduler
     */
    @Override
    public Scheduler io() {
        return Schedulers.io();
    }

    /**
     * Computation thread pool scheduler
     */
    @Override
    public Scheduler computation() {
        return Schedulers.computation();
    }

    /**
     * Main Thread scheduler
     */
    @Override
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }
}