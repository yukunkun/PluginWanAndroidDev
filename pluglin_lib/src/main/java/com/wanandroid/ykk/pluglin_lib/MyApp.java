package com.wanandroid.ykk.pluglin_lib;

import android.content.Context;

import com.wanandroid.ykk.pluglin_lib.cornpage.CorePageManager;

import org.litepal.LitePalApplication;

/**
 * Created by Beck on 2018/3/24.
 */

public class MyApp  extends LitePalApplication{
    public static MyApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
        CorePageManager.getInstance().initTemplate(this,"app_page_config_template.json");
    }

}
