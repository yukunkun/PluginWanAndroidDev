package com.wanandroid.ykk.pluglin_lib.http;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Created by Beck on 2018/3/26.
 */

public class RetrifitNetUtils {

    Context context;

    OkHttpClient httpClient = setCookies(new OkHttpClient.Builder());

    public Retrofit getRetrifitUtils(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("")
                .client(httpClient)
                .build();
        return retrofit;
    }


    public OkHttpClient setCookies(OkHttpClient.Builder builder) {
        builder.addInterceptor(new AddCookiesInterceptor(context));
        builder.addInterceptor(new SaveCookiesInterceptor(context));
        builder.addInterceptor(getHttpLoggingInterceptor());
        builder.retryOnConnectionFailure(true);
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        return builder.build();
    }


    public static HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }
}
