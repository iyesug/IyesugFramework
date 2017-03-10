package com.test.iyesug.iyesugframework.http;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/2/24.
 */

public class MyRetrofit {
    public static  String GANHUO_API = "http://gank.io/";
    private static Retrofit retrofit;

    public static OkHttpClient getOkHttpClient(){

        HttpLoggingInterceptor logging= new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient client=new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5,TimeUnit.SECONDS)
                .writeTimeout(5,TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        return client;
    }
    public static  Retrofit getRetrofit(){
        if(retrofit==null){
            synchronized (MyRetrofit.class){

                if(retrofit==null){
                    retrofit= new Retrofit.Builder()
                            .baseUrl(GANHUO_API)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(getOkHttpClient())
                            .build();

                }
            }
        }
        return retrofit;
    }
    public static void changeApiBaseUrl(String newApiBaseUrl) {
        String BASE_URL =newApiBaseUrl;

        retrofit= new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(getOkHttpClient())
                .build();
    }
}
