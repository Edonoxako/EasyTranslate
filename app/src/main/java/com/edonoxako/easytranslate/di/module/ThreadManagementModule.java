package com.edonoxako.easytranslate.di.module;

import com.edonoxako.easytranslate.core.DefaultSchedulerFactory;
import com.edonoxako.easytranslate.core.SchedulerFactory;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by eugene on 03.11.17.
 */
@Module
public class ThreadManagementModule {

    @Provides
    public SchedulerFactory mainThreadScheduler() {
        return new DefaultSchedulerFactory();
    }

}
