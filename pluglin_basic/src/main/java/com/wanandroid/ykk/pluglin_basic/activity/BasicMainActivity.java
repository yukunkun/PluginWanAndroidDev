package com.wanandroid.ykk.pluglin_basic.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.wanandroid.ykk.pluglin_basic.R;
import com.wanandroid.ykk.pluglin_basic.R2;
import com.wanandroid.ykk.pluglin_lib.activity.BaseActivity;
import com.wanandroid.ykk.pluglin_lib.impl.ActivityConfig;
import com.wanandroid.ykk.pluglin_lib.views.BMoveView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class BasicMainActivity extends BaseActivity implements ActivityConfig{

    @BindView(R2.id.rg)
    RadioGroup mRg;
    @BindView(R2.id.bmoveview)
    BMoveView mBMoveView;
    private int mFirstPos;
    private int mLastPos;
    private List<Fragment> mFragments = new ArrayList<>();
    private int lastPos = 0;

    @Override
    public int getLayout() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        return R.layout.activity_main;
    }

    @Override
    protected void creatView(Bundle savedInstanceState) {

    }

    @Override
    public void initView() {
        mFirstPos = 0;
        mBMoveView.startAnim();
        ((RadioButton) (mRg.getChildAt(0))).setChecked(true);
        setListener();
        //默认的第一个fragment
        startFragment(R.id.fl_layout,getSupportFragmentManager(),IndexFragment);
    }

    private void setListener() {
        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                for (int i = 0; i < group.getChildCount(); i++) {
                    boolean checked = ((RadioButton) (group.getChildAt(i))).isChecked();
                    if(checked){
                        mLastPos = i;
                        mBMoveView.setTwoPos(mFirstPos, mLastPos);
                        mFirstPos = mLastPos;
                        //show fragment
                        switch (i){
                            case 0:
                                startFragment(R.id.fl_layout,getSupportFragmentManager(),IndexFragment);
                                break;
                            case 1:
                                startFragment(R.id.fl_layout,getSupportFragmentManager(),KnowledgeFragment);
                                break;
                            case 2:
                                startFragment(R.id.fl_layout,getSupportFragmentManager(),HotFragment);
                                break;
                        }
                    }
                }
            }
        });
    }

    //双击退出
    private long firstTime=0;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                long secondTime=System.currentTimeMillis();

                if(secondTime-firstTime>2000){
                    Toast.makeText(this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
                    firstTime=secondTime;
                    return true;
                }else{
                    finish();
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }
}
