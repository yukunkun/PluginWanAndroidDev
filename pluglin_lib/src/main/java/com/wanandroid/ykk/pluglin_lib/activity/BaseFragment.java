package com.wanandroid.ykk.pluglin_lib.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wanandroid.ykk.pluglin_lib.cornpage.CorePageManager;

import butterknife.ButterKnife;

/**
 * Created by Beck on 2018/3/24.
 */

public abstract class BaseFragment extends Fragment {

    protected static final String TAG = BaseFragment.class.getSimpleName();

    protected abstract int getLayout();

    protected abstract void initView(View view,Bundle savedInstanceState);
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(getLayout(), null);
        ButterKnife.bind(this, inflate);
        initView(inflate,savedInstanceState);
        return inflate;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void invalidate(Bundle arguments) {

    }

    public void startActivity(Context context, String mName, Bundle bundle){
        CorePageManager.getInstance().openPage(context,mName,bundle);
    }

    public void startActivity(Context context,String mName){
        CorePageManager.getInstance().openPage(context,mName,null);
    }

    public void startResultActivity(Activity context, String mName){
        CorePageManager.getInstance().openPageForResult(context,mName,null,1);
    }

    public void startFragment(int fragmentId , FragmentManager fragmentManager, String pageName){
        CorePageManager.getInstance().gotoPage(fragmentId,fragmentManager,pageName,null,null);
    }

    public void startFragment(int fragmentId , FragmentManager fragmentManager, String pageName,Bundle bundle){
        CorePageManager.getInstance().gotoPage(fragmentId,fragmentManager,pageName,bundle,null);
    }


}
