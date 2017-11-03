package com.edonoxako.easytranslate.di.module;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by eugene on 03.11.17.
 */
@Module
public class ThreadMaanagmentModule {

    @Provides
    public Scheduler mainThreadScheduler() {
        return AndroidSchedulers.mainThread();
    }

}
