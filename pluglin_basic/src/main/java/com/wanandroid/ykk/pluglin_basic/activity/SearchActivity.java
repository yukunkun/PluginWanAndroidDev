package com.wanandroid.ykk.pluglin_basic.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wanandroid.ykk.pluglin_basic.R;
import com.wanandroid.ykk.pluglin_basic.R2;
import com.wanandroid.ykk.pluglin_basic.adapter.SearchAdapterAdapter;
import com.wanandroid.ykk.pluglin_lib.activity.BaseActivity;
import com.wanandroid.ykk.pluglin_lib.enerty.SearchInfo;
import com.wanandroid.ykk.pluglin_lib.http.CustomCallBack;
import com.wanandroid.ykk.pluglin_lib.http.RetrifitNetUtils;
import com.wanandroid.ykk.pluglin_lib.impl.ActivityConfig;
import com.wanandroid.ykk.pluglin_lib.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class SearchActivity extends BaseActivity implements ActivityConfig {

    @BindView(R2.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R2.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R2.id.smartLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R2.id.ed_text)
    EditText mEdText;
    private int page;
    private List<SearchInfo.DatasBean> searchInfos=new ArrayList<>();
    private SearchAdapterAdapter mIndexAdapter;


    @Override
    public int getLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected void creatView(Bundle savedInstanceState) {

    }

    @Override
    public void initView() {
        String key = getIntent().getStringExtra("key");
        if ("null".equals(key)||key.equals("")) {

        } else {
            mEdText.setText(key);
            search(key);
        }
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(layoutManager);
        mIndexAdapter = new SearchAdapterAdapter(searchInfos,this);
        mRecyclerview.setAdapter(mIndexAdapter);
        mRecyclerview.setAdapter(mIndexAdapter);
        mRefreshLayout.setPrimaryColors(Color.GRAY, Color.BLUE);
        mRefreshLayout.setBackgroundResource(R.color.colorPrimary);
//        mRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        mRefreshLayout.setRefreshHeader(new BezierCircleHeader(this));
        setListener();
    }

    private void setListener() {
        mEdText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //完成自己的事件
                    String edMsg = mEdText.getText().toString();
                    if(edMsg.length()>0){
                        searchInfos.clear();
                        mIndexAdapter.notifyDataSetChanged();
                        search(edMsg);
                    }
                }
                return false;
            }
        });

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                String edMsg = mEdText.getText().toString();
                if(edMsg.length()>0){
                    searchInfos.clear();
                    mIndexAdapter.notifyDataSetChanged();
                    search(edMsg);
                }
            }
        });
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                String edMsg = mEdText.getText().toString();
                if(edMsg.length()>0){
                    search(edMsg);
                }

            }
        });
        mIndexAdapter.setOnItemClickListener(new SearchAdapterAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(String title, String url) {
                Bundle bundle=new Bundle();
                bundle.putString("url",url);
                bundle.putString("title",title);
                startActivity(SearchActivity.this,DetailActivity,bundle);
            }
        });
    }

    private void search(final String edMsg) {
        RetrifitNetUtils.getRetrifitUtils().getSearchInfo(page,edMsg).enqueue(new CustomCallBack() {
            @Override
            public void onSuccess(String respond) {
                Gson gson=new Gson();
                SearchInfo search = gson.fromJson(respond, SearchInfo.class);
                searchInfos.addAll(search.getDatas());
                mIndexAdapter.notifyDataSetChanged();
                if(page==0){
                    mRefreshLayout.finishRefresh();
                }else {
                    mRefreshLayout.finishLoadmore();
                }
            }

            @Override
            public void onFail(String errMsg) {
                ToastUtils.showToast(edMsg);
            }

            @Override
            public void onFinish() {
                ToastUtils.showToast("没有相关数据");
            }
        });
    }


    @OnClick({R2.id.tv_cancel})
    public void onClick(View view) {
        if(view.getId()==R.id.tv_cancel) {
            finish();
        }
    }

}
