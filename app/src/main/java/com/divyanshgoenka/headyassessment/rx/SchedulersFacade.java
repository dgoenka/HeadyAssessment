package com.divyanshgoenka.headyassessment.rx;

import io.reactivex.Scheduler;

/**
 * Created by divyanshgoenka on 05/02/18.
 */

public interface SchedulersFacade {
    Scheduler io();

    Scheduler computation();

    Scheduler ui();
}
