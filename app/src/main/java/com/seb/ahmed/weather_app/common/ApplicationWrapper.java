package com.seb.ahmed.weather_app.common;

import android.app.Application;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class ApplicationWrapper extends Application {

    private Scheduler scheduler;
    private static ApplicationWrapper application=new ApplicationWrapper();

    public static ApplicationWrapper getInstance()
    {
        return application;
    }


    @Override
    public void onCreate() {
        super.onCreate();

    }

    public Scheduler subscribeScheduler() {
        if (scheduler == null) {
            scheduler = Schedulers.io();
        }

        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

}
