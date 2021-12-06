package com.dataevolve.digiyathra;

import android.content.Context;

import com.dataevolve.digiyathra.di.dragger.components.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class DigiYathraApp extends DaggerApplication {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        DigiYathraApp.context = getApplicationContext();

    }
    public static Context getAppContext() {
        return DigiYathraApp.context;
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }

}