package com.wanandroid.ykk.pluglin_lib.utils;

import android.widget.Toast;

import com.wanandroid.ykk.pluglin_lib.MyApp;

/**
 * Created by yukun on 18-1-4.
 */

public class ToastUtils {
    public static void showToast(String txt){
        Toast.makeText(MyApp.mInstance, txt, Toast.LENGTH_SHORT).show();
    }
}
