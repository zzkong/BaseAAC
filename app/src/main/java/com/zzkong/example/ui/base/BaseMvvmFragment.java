package com.zzkong.example.ui.base;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.zzkong.example.utils.TUtil;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Created by lico on 2018/5/5
 */
public abstract class BaseMvvmFragment<T extends ViewDataBinding, M extends ViewModel> extends BaseFragment
        implements HasSupportFragmentInjector {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    DispatchingAndroidInjector<Fragment> childFragmentInjector;

    private Class<M> viewModelClass;
    protected T mBinding;
    protected M mViewmodel;

    protected abstract int getLayoutId();
    protected abstract void initData();
    protected abstract void lazyLoadData();

//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        super.onCreateView(inflater, container, savedInstanceState);
//        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
//        View view = mBinding.getRoot();
//        viewModelClass = TUtil.getT1(this, 1);
//        if (viewModelClass != null) {
//            AndroidSupportInjection.inject(this);
//            mViewmodel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass);
//        }
//        return view;
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState, int FOR_OVERRIDE) {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        View view = mBinding.getRoot();
        viewModelClass = TUtil.getT1(this, 1);
        if (viewModelClass != null) {
            AndroidSupportInjection.inject(this);
            mViewmodel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass);
        }
        return view;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return childFragmentInjector;
    }

}
