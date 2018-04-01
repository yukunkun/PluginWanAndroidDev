package com.wanandroid.ykk.pluglin_lib.http;


import android.util.Log;

import com.wanandroid.ykk.pluglin_lib.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Beck on 2018/3/31.
 */

public abstract class CustomCallBack implements Callback<ResponseBody>{

    private String result;

    public abstract void onSuccess(String respond);
    public abstract void onFail(String errMsg);
    public abstract void onFinish();

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

        if(response.isSuccessful()){
            try {
                result = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if("null".equals(result)||result.length()==0){
                onFinish();
            }else {
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    if(jsonObject.optInt("errorCode")==0){
                        onSuccess(jsonObject.optString("data"));
                    }else {
                        onFail(jsonObject.optString("errorMsg"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else {
            try {
                Log.i("error",response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
            ToastUtils.showToast("error:network err");
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        ToastUtils.showToast(t.toString());
    }

}
