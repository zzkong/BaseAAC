package com.zzkong.example.di.module;

import com.zzkong.example.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by lico on 2018/6/1
 */
@Module
public abstract class BuildersModule {


    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();
}
