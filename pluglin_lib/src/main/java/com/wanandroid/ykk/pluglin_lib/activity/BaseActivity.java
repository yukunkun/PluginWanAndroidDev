package com.wanandroid.ykk.pluglin_lib.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.wanandroid.ykk.pluglin_lib.cornpage.CorePageManager;
import com.wanandroid.ykk.pluglin_lib.viewinject.utils.MasonViewUtils;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    protected abstract int getLayout();
    protected abstract void creatView(Bundle savedInstanceState);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getLayout()!=0){
            setContentView(getLayout());
        }
        ButterKnife.bind(this);

//        MasonViewUtils.getInstance(this).inject(this);

        creatView(savedInstanceState);

        initView();
    }


    protected  void initView(){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public void startActivity(Context context,String mName,Bundle bundle){
        CorePageManager.getInstance().openPage(context,mName,bundle);
    }

    public void startActivity(Context context,String mName){
        CorePageManager.getInstance().openPage(context,mName,null);
    }

    public void startResultActivity(Activity context,String mName){
        CorePageManager.getInstance().openPageForResult(context,mName,null,1);
    }

    public void startFragment(int fragmentId , FragmentManager fragmentManager, String pageName){
        CorePageManager.getInstance().gotoPage(fragmentId,fragmentManager,pageName,null,null);
    }

    public void startFragment(int fragmentId , FragmentManager fragmentManager, String pageName,Bundle bundle){
        CorePageManager.getInstance().gotoPage(fragmentId,fragmentManager,pageName,bundle,null);
    }


}
