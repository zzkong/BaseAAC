package com.zzkong.example.di.module;

import android.content.Context;

import com.zzkong.example.BaseApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lico on 2018/6/1
 */
@Module(includes = {
        ApiModule.class,
        ViewModelModule.class,
})
public class AppModule {

    @Provides
    @Singleton
    Context provideContent(BaseApp application){
        return application.getApplicationContext();
    }
}
