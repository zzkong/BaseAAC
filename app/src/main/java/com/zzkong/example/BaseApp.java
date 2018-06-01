package com.zzkong.example;

import com.zzkong.example.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

/**
 * Created by lico on 2018/6/1
 */
public class BaseApp extends DaggerApplication{

    private static BaseApp mApp;


    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
    }

    public static BaseApp getApplication(){
        return mApp;
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }
}
