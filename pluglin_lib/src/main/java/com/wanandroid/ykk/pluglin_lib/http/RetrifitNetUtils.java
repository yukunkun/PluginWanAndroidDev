package com.wanandroid.ykk.pluglin_lib.http;

import android.content.Context;

import com.wanandroid.ykk.pluglin_lib.MyApp;
import com.wanandroid.ykk.pluglin_lib.enerty.FeedInfo;
import com.wanandroid.ykk.pluglin_lib.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Beck on 2018/3/26.
 */

public class RetrifitNetUtils {

    static Context context= MyApp.mInstance;

    public static String FEEDURL="http://www.wanandroid.com/";

    public static OkHttpClient httpClient = setCookies(new OkHttpClient.Builder());

    public static NetService getRetrifitUtils(){

        NetService netService = new Retrofit.Builder()
                .baseUrl(FEEDURL)
                .client(httpClient)
                .build().create(NetService.class);
        return netService;
    }


    public static OkHttpClient setCookies(OkHttpClient.Builder builder) {
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
