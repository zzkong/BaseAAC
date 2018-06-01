package com.zzkong.example.ui.base;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;


import com.zzkong.example.utils.TUtil;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasFragmentInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Created by lico on 2018/5/7
 */
public abstract class BaseAacActivity<M extends ViewModel> extends BaseActivity
        implements HasFragmentInjector, HasSupportFragmentInjector {
    @Inject
    DispatchingAndroidInjector<Fragment> supportFragmentInjector;
    @Inject
    DispatchingAndroidInjector<android.app.Fragment> frameworkFragmentInjector;
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private Class<M> viewModelClass;
    protected M mViewmodel;


    protected abstract @LayoutRes
    int getLayoutId();

    protected abstract void create(Bundle savedInstanceState);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        viewModelClass = TUtil.getT1(this, 0);
        AndroidInjection.inject(this);
        if (viewModelClass != null) {
            mViewmodel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass);
        }
        create(savedInstanceState);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return supportFragmentInjector;
    }

    @Override
    public AndroidInjector<android.app.Fragment> fragmentInjector() {
        return frameworkFragmentInjector;
    }

}
