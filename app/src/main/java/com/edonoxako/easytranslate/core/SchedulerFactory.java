package com.edonoxako.easytranslate.core;

import io.reactivex.Scheduler;

/**
 * Created by eugene on 05.11.17.
 */

public interface SchedulerFactory {

    Scheduler io();

    Scheduler mainThread();

}
