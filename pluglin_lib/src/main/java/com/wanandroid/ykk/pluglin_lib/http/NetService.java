package com.wanandroid.ykk.pluglin_lib.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Beck on 2018/3/31.
 */

public interface NetService {

    @GET("article/list/{page}/json")
    Call<ResponseBody> getBlog(@Path("page")  int page);

    @GET("hotkey/json")
    Call<ResponseBody> getHotKet();

    @GET("friend/json")
    Call<ResponseBody> getCommon();


}
