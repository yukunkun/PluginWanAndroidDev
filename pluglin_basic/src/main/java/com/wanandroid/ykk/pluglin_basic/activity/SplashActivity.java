package com.wanandroid.ykk.pluglin_basic.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.wanandroid.ykk.pluglin_basic.R;
import com.wanandroid.ykk.pluglin_basic.R2;
import com.wanandroid.ykk.pluglin_lib.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {

    @BindView(R2.id.iv_bg)
    ImageView mIvBg;
    @BindView(R2.id.tv_remind)
    TextView mTvRemind;

    @Override
    public int getLayout() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
        List<Integer> mImages = new ArrayList<>();
        mImages.add(R.drawable.bg_1);
        mImages.add(R.drawable.bg_2);
        mImages.add(R.drawable.bg_3);
        mImages.add(R.drawable.bg_4);
        mImages.add(R.drawable.bg_5);
        mImages.add(R.drawable.bg_6);
        mImages.add(R.drawable.bg_7);
        mImages.add(R.drawable.bg_8);
        Random random = new Random();
        int ran = random.nextInt(8);
        mIvBg.setBackgroundResource(mImages.get(ran));

        setAnimSet(mTvRemind, mIvBg);
        startMainActyivity();

//        List<UesrInfo> all = DataSupport.findAll(UesrInfo.class);
//        if(all.size()!=0){
//            MyApp.setUesrInfo(all.get(0));
//        }else {
//
//        }
    }

    @Override
    protected void creatView(Bundle savedInstanceState) {

    }

    /**
     * 位移动画
     *
     * @param view
     */
    public void setAnimSet(View view, View viewScale) {
        ObjectAnimator//
                .ofFloat(view, "alpha", 0F, 1F)//
                .setDuration(1500)//
                .start();

        ObjectAnimator
                .ofFloat(view, "translationY", 360F, 0F)//
                .setDuration(3000)//
                .start();
        //放大
        ScaleAnimation animation = new ScaleAnimation(
                1.0f, 1.2f, 1.0f, 1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        );
        animation.setDuration(4000);
        viewScale.startAnimation(animation);
    }

    public void startMainActyivity() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    Intent intent = new Intent(SplashActivity.this, BasicMainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
