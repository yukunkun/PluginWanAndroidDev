package com.wanandroid.ykk.pluglin_basic.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wanandroid.ykk.pluglin_basic.R;
import com.wanandroid.ykk.pluglin_basic.R2;
import com.wanandroid.ykk.pluglin_lib.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class DetailActivity extends BaseActivity {

    @BindView(R2.id.tv_title)
    TextView mTvTitle;
    @BindView(R2.id.iv_me)
    ImageView mIvback;
    @BindView(R2.id.iv_share)
    ImageView mIvShare;
    @BindView(R2.id.rl)
    RelativeLayout mRl;
    @BindView(R2.id.webview)
    WebView mWebview;
    @BindView(R2.id.progressbar)
    ProgressBar mProgressbar;
    private String mUrl;

    @Override
    public int getLayout() {
        return R.layout.activity_detail;
    }

    @Override
    protected void creatView(Bundle savedInstanceState) {

    }

    @Override
    public void initView() {
        String title = getIntent().getStringExtra("title");
        mTvTitle.setText(title);
        mTvTitle.setSelected(true);
        mUrl = getIntent().getStringExtra("url");
        WebSettings webSettings = mWebview.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        mWebview.loadUrl(mUrl);
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mProgressbar.setVisibility(View.GONE);
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mWebview.canGoBack()) {
            mWebview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @OnClick({R2.id.iv_me, R2.id.iv_share})
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.iv_me) {
            finish();
        } else if (viewId == R.id.iv_share) {
            Intent textIntent = new Intent(Intent.ACTION_SEND);
            textIntent.setType("text/plain");
            textIntent.putExtra(Intent.EXTRA_TEXT, mUrl);
            startActivity(Intent.createChooser(textIntent, "分享"));
        }
    }
}

