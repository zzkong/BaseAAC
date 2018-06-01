package com.zzkong.example.data;

import com.zzkong.example.beans.GankBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by lico on 2018/6/1
 */
public interface HttpApi {


    @GET("{type}/20/{page}")
    Flowable<GankBean> getGankData(@Path("type") String type, @Path("page") int page);
}
