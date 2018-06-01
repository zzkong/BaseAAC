package com.zzkong.example.ui.main;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.zzkong.example.R;
import com.zzkong.example.beans.GankBean;
import com.zzkong.example.ui.base.BaseAacActivity;
import com.zzkong.example.viewmodel.MainViewModel;

import java.util.List;

/**
 * Created by lico on 2018/6/1
 */
public class MainActivity extends BaseAacActivity<MainViewModel>{

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void create(Bundle savedInstanceState) {
        mViewmodel.getGank("Android", 1);

        addObserve();
    }

    private void addObserve(){
        mViewmodel.mRxLiveData.observeData(this, new Observer<List<GankBean.Gank>>() {
            @Override
            public void onChanged(@Nullable List<GankBean.Gank> ganks) {
                Log.e("zzkong", "获取数据成功: " + ganks.toString());
                Toast.makeText(mActivity, "获取数据成功", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
