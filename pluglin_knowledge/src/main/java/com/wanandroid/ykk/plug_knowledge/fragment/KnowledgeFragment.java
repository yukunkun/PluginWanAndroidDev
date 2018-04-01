package com.wanandroid.ykk.plug_knowledge.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wanandroid.ykk.plug_knowledge.R;
import com.wanandroid.ykk.plug_knowledge.R2;
import com.wanandroid.ykk.plug_knowledge.adapter.KnowledgeAdapter;
import com.wanandroid.ykk.pluglin_lib.activity.BaseFragment;
import com.wanandroid.ykk.pluglin_lib.enerty.CommonUrl;
import com.wanandroid.ykk.pluglin_lib.enerty.KnowledgeInfo;
import com.wanandroid.ykk.pluglin_lib.http.CustomCallBack;
import com.wanandroid.ykk.pluglin_lib.http.RetrifitNetUtils;
import com.wanandroid.ykk.pluglin_lib.utils.ActivityUtils;
import com.wanandroid.ykk.pluglin_lib.utils.ToastUtils;
import com.wanandroid.ykk.pluglin_lib.views.SwipeItemLayout;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import okhttp3.Call;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;

import static com.wanandroid.ykk.pluglin_lib.impl.ActivityConfig.SearchActivity;

/**
 * Created by yukun on 18-1-4.
 */

public class KnowledgeFragment extends BaseFragment {
    @BindView(R2.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R2.id.swipeItemLayout)
    SwipeRefreshLayout mSwipeItemLayout;
    @BindView(R2.id.iv_search)
    ImageView mIvSearch;
    @BindView(R2.id.iv_me)
    ImageView mIvMe;
    private KnowledgeAdapter mKnowledgeAdapter;
    private List<KnowledgeInfo> mKnowledgeInfos = new ArrayList<>();

    public static KnowledgeFragment getInstance() {
        return new KnowledgeFragment();
    }

    @Override
    public int getLayout() {
        return R.layout.knowledge_fragment;
    }

    @Override
    public void initView(View inflate, Bundle savedInstanceState) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerview.setLayoutManager(layoutManager);

        setListener();
        mRecyclerview.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(getContext()));
        mRecyclerview.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        getInfo();
        OverScrollDecoratorHelper.setUpOverScroll(mRecyclerview, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);

    }

    private void getInfo() {
        RetrifitNetUtils.getRetrifitUtils().getKnowledge().enqueue(new CustomCallBack() {
            @Override
            public void onSuccess(String respond) {
                Gson gson = new Gson();
                mKnowledgeInfos=gson.fromJson(respond, new TypeToken<List<KnowledgeInfo>>() {}.getType());
                mKnowledgeAdapter = new KnowledgeAdapter(mKnowledgeInfos, getContext());
                mRecyclerview.setAdapter(mKnowledgeAdapter);
            }

            @Override
            public void onFail(String errMsg) {
                ToastUtils.showToast(errMsg);
            }

            @Override
            public void onFinish() {

            }
        });

//        OkHttpUtils.get().url(Constanct.TREEURL).build().execute(new StringCallback() {
//            @Override
//            public void onError(Call call, Exception e, int id) {
//
//            }
//
//            @Override
//            public void onResponse(String response, int id) {
//                Gson gson = new Gson();
//                KnowledgeInfo jokeList = gson.fromJson(response, KnowledgeInfo.class);
//                mKnowledgeAdapter = new KnowledgeAdapter(jokeList, getContext());
//                mRecyclerview.setAdapter(mKnowledgeAdapter);
//            }
//        });
    }

    private void setListener() {
        mSwipeItemLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeItemLayout.setRefreshing(false);
            }
        });
    }


    @OnClick({R2.id.iv_search, R2.id.iv_me})
    public void onClick(View view) {
        int id = view.getId();
        if(id==R.id.iv_search){
            Bundle bundle=new Bundle();
            bundle.putString("key","");
            startActivity(getContext(),SearchActivity,bundle);
        }else if(id==R.id.iv_me){
            ToastUtils.showToast("之后完善。。。");
        }
    }
}
