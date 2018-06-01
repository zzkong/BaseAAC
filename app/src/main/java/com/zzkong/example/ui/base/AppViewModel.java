package com.zzkong.example.ui.base;

import android.arch.lifecycle.ViewModel;

import com.zzkong.example.utils.RxLiveData;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Kilnn on 2018/1/2.
 */

public abstract class AppViewModel extends ViewModel {

    private List<RxLiveData> mRxLiveDatas;

    protected <T> RxLiveData<T> createRxLiveData() {
        if (mRxLiveDatas == null) {
            mRxLiveDatas = new ArrayList<>(5);
        }
        RxLiveData<T> rxLiveData = new RxLiveData<>();
        mRxLiveDatas.add(rxLiveData);
        return rxLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mRxLiveDatas != null) {
            for (RxLiveData rxLiveData : mRxLiveDatas) {
                rxLiveData.cancel();
            }
            mRxLiveDatas.clear();
        }
    }

}
