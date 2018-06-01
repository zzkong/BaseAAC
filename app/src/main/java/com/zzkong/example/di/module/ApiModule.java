package com.zzkong.example.di.module;

import android.content.Context;
import android.util.Log;

import com.zzkong.example.constant.ApiConstant;
import com.zzkong.example.data.HttpApi;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lico on 2018/6/1
 */
@Module
public class ApiModule {

    @Provides
    @Singleton
    OkHttpClient provideOkHttp(Context context) {
        return new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)  //打印相关
                //.addNetworkInterceptor(logInterceptor)  //打印LOG相关
                .build();
    }

    @Provides
    @Singleton
    public HttpApi provideHttpApi(OkHttpClient okHttpClient){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstant.HTTP_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            //    .addConverterFactory(new ToStringConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpApi httpApi = retrofit.create(HttpApi.class);
        return httpApi;
    }
}
