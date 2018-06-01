package com.zzkong.example.repository;

import com.zzkong.example.beans.GankBean;
import com.zzkong.example.data.HttpApi;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lico on 2018/6/1
 */
public class Repository {

    private HttpApi mHttpApi;

    @Inject
    public Repository(HttpApi httpApi){
      mHttpApi = httpApi;
    }

    public Flowable<List<GankBean.Gank>> getGankData(String type, int page){
        return mHttpApi.getGankData(type, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<GankBean, List<GankBean.Gank>>() {
                    @Override
                    public List<GankBean.Gank> apply(GankBean gankBean) throws Exception {
                        return gankBean.results;
                    }
                });
    }
}
