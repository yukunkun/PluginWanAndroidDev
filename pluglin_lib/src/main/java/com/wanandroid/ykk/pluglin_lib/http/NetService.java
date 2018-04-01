package com.wanandroid.ykk.pluglin_lib.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

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

    @GET("tree/json")
    Call<ResponseBody> getKnowledge();

    @GET("article/list/{page}/json")
    Call<ResponseBody> getKnowledgeList(@Path("page")int page,@Query("cid") String cid);

    @POST("article/query/{page}/json")
    @FormUrlEncoded
    Call<ResponseBody> getSearchInfo(@Path("page") int page,@Field("k") String key);

}
