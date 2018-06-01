package com.zzkong.example.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.zzkong.example.di.ViewModelKey;
import com.zzkong.example.viewmodel.BaseViewModelFactory;
import com.zzkong.example.viewmodel.MainViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by lico on 2018/6/1
 */
@Module
public abstract class ViewModelModule {


    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel bindMainViewModel(MainViewModel mainViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(BaseViewModelFactory factory);
}
