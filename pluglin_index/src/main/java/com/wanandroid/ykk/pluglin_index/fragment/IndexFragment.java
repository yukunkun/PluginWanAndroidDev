package com.wanandroid.ykk.pluglin_index.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wanandroid.ykk.pluglin_index.R;
import com.wanandroid.ykk.pluglin_index.R2;
import com.wanandroid.ykk.pluglin_index.adapter.IndexAdapter;
import com.wanandroid.ykk.pluglin_lib.activity.BaseFragment;
import com.wanandroid.ykk.pluglin_lib.enerty.FeedInfo;
import com.wanandroid.ykk.pluglin_lib.http.CustomCallBack;
import com.wanandroid.ykk.pluglin_lib.http.NetService;
import com.wanandroid.ykk.pluglin_lib.http.RetrifitNetUtils;
import com.wanandroid.ykk.pluglin_lib.impl.ActivityConfig;
import com.wanandroid.ykk.pluglin_lib.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.ResponseBody;

/**
 * Created by yukun on 18-1-4.
 */

public class IndexFragment extends BaseFragment implements ActivityConfig{
    @BindView(R2.id.iv_search)
    ImageView mIvSearch;
    @BindView(R2.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R2.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    int page=0;
    List<FeedInfo> feedLists=new ArrayList<>();
    private IndexAdapter mIndexAdapter;

    public static IndexFragment getInstance() {
        return new IndexFragment();
    }

    @Override
    public int getLayout() {
        return R.layout.index_fragment;
    }

    @Override
    public void initView(View inflate, Bundle savedInstanceState) {
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        mRecyclerview.setLayoutManager(layoutManager);
        mIndexAdapter = new IndexAdapter(feedLists,getContext());
        mRecyclerview.setAdapter(mIndexAdapter);
        setListener();
        mRefreshLayout.setPrimaryColors(Color.WHITE, Color.BLUE);
        mRefreshLayout.setBackgroundResource(R.color.colorPrimary);
        getInfo();
    }

    private void getInfo() {
            RetrifitNetUtils.getRetrifitUtils() .getBlog(page).enqueue(new CustomCallBack() {
                @Override
                public void onSuccess(String respond) {
                    try {
                        JSONObject data=new JSONObject(respond);
                        JSONArray jsonArray = data.optJSONArray("datas");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                            FeedInfo feedInfo=new FeedInfo();
                            feedInfo.setId(jsonObject1.getInt("id"));
                            feedInfo.setTitle(jsonObject1.getString("title"));
                            feedInfo.setChapterId(jsonObject1.getInt("chapterId"));
                            feedInfo.setChapterName(jsonObject1.getString("chapterName"));
                            feedInfo.setLink(jsonObject1.getString("link"));
                            feedInfo.setAuthor(jsonObject1.getString("author"));
                            feedInfo.setNiceDate(jsonObject1.getString("niceDate"));
                            feedInfo.setCollect(jsonObject1.getBoolean("collect"));
                            feedLists.add(feedInfo);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mIndexAdapter.notifyDataSetChanged();
                    if(page==0){
                        mRefreshLayout.finishRefresh();
                    }else {
                        mRefreshLayout.finishLoadmore();
                    }
                }

                @Override
                public void onFail(String errMsg) {
                    ToastUtils.showToast(errMsg);
                }

                @Override
                public void onFinish() {

                }
            });
    }

    private void setListener() {

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page=0;
                feedLists.clear();
                getInfo();
            }
        });

        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                getInfo();

            }
        });

        mIndexAdapter.setOnClickListener(new IndexAdapter.OnClickListener() {
            @Override
            public void onClick(int pos, String url, String title) {
                Bundle bundle=new Bundle();
                bundle.putString("url",url);
                bundle.putString("title",title);
                startActivity(getContext(),DetailActivity,bundle);
            }
        });
    }

    @SuppressLint("InvalidR2Usage")
    @OnClick({R2.id.iv_search, R2.id.refreshLayout,R2.id.iv_me})
    public void onClick(View view) {
        int viewId = view.getId();
        if(viewId==R.id.iv_search){
            Bundle bundle=new Bundle();
            bundle.putString("key","");
            startActivity(getContext(),SearchActivity,bundle);
        }else if(viewId==R.id.iv_me){
            ToastUtils.showToast("之后完善。。。");
        }

//        switch (view.getId()) {
//            case R.id.iv_search:
//                ActivityUtils.startSearchkActivity(getContext(),"");
//                break;
//            case R.id.iv_me:
//                ActivityUtils.startMeActivity(getContext());
//                break;
//        }
    }
}
