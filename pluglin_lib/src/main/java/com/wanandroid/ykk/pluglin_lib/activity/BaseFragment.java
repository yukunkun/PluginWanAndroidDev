package com.wanandroid.ykk.pluglin_lib.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

}
