package com.zzkong.example.di;

import com.zzkong.example.BaseApp;
import com.zzkong.example.di.module.AppModule;
import com.zzkong.example.di.module.BuildersModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by lico on 2018/6/1
 */
@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AndroidInjectionModule.class,
        AppModule.class,
        BuildersModule.class,

})
public interface AppComponent extends AndroidInjector<BaseApp>{

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<BaseApp>{}
}
