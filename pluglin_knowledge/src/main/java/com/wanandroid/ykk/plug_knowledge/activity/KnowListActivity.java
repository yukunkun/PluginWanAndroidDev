package com.wanandroid.ykk.plug_knowledge.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wanandroid.ykk.plug_knowledge.R;
import com.wanandroid.ykk.plug_knowledge.R2;
import com.wanandroid.ykk.plug_knowledge.adapter.KnowListViewAdapter;
import com.wanandroid.ykk.plug_knowledge.adapter.KnowledgeListAdapter;
import com.wanandroid.ykk.pluglin_lib.activity.BaseActivity;
import com.wanandroid.ykk.pluglin_lib.enerty.KnowledgeInfo;
import com.wanandroid.ykk.pluglin_lib.enerty.KnowledgeListInfo;
import com.wanandroid.ykk.pluglin_lib.http.CustomCallBack;
import com.wanandroid.ykk.pluglin_lib.http.RetrifitNetUtils;
import com.wanandroid.ykk.pluglin_lib.impl.ActivityConfig;
import com.wanandroid.ykk.pluglin_lib.utils.ToastUtils;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class KnowListActivity extends BaseActivity implements ActivityConfig {

    @BindView(R2.id.titles)
    TextView mTitle;
    @BindView(R2.id.iv_search)
    ImageView mIvSearch;
    @BindView(R2.id.iv_me)
    ImageView mIvList;
    @BindView(R2.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R2.id.smartrefreshlayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R2.id.listview)
    ListView mListview;
    @BindView(R2.id.drawlayout)
    DrawerLayout mDrawlayout;
    private int page=0;
    private KnowledgeInfo mKnowlist;
    private KnowListViewAdapter mKnowListAdapter;
    private KnowledgeListAdapter mKnowledgeAdapter;
    private KnowledgeListInfo mKnowledgeListInfo;
    private List<KnowledgeListInfo.DatasBean> mKlInfos=new ArrayList<>();
    private int cid;

    @Override
    public int getLayout() {
        mKnowlist = (KnowledgeInfo) getIntent().getSerializableExtra("knowlist");
        return R.layout.activity_know_list;
    }

    @Override
    protected void creatView(Bundle savedInstanceState) {

    }

    @Override
    public void initView() {
        mTitle.setText(mKnowlist.getName()+"");
        List<KnowledgeInfo.ChildrenBean> children = mKnowlist.getChildren();
        mKnowListAdapter = new KnowListViewAdapter(children,this);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(layoutManager);
        mKnowledgeAdapter = new KnowledgeListAdapter(this,mKlInfos);
        mRecyclerview.setAdapter(mKnowledgeAdapter);
        OverScrollDecoratorHelper.setUpOverScroll(mListview);
        mRefreshLayout.setPrimaryColors(Color.GRAY, Color.BLUE);
        mRefreshLayout.setBackgroundResource(R.color.colorPrimary);
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        if(mKnowlist.getChildren().size()==0){
            return;
        }
        mListview.setAdapter(mKnowListAdapter);
        setListener();
        cid=(int)mKnowlist.getChildren().get(0).getId();
        getInfo(cid);
    }

    private void getInfo(int pcid) {
        RetrifitNetUtils.getRetrifitUtils().getKnowledgeList(page,pcid+"").enqueue(new CustomCallBack() {
            @Override
            public void onSuccess(String respond) {
                Gson gson = new Gson();
                KnowledgeListInfo knowledgeListInfo = gson.fromJson(respond, KnowledgeListInfo.class);
                mKlInfos.addAll(knowledgeListInfo.getDatas());
                Log.i("-------",mKlInfos.toString());

                mKnowledgeAdapter.notifyDataSetChanged();
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
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                page=0;
                mKlInfos.clear();
                cid=(int) mKnowlist.getChildren().get(position).getId();
                getInfo(cid);
                mDrawlayout.closeDrawer(Gravity.LEFT);
                mKnowListAdapter.setSelector(position);
                mKnowListAdapter.notifyDataSetChanged();
            }
        });

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page=0;
                mKlInfos.clear();
                getInfo(cid);
            }
        });

        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                getInfo(cid);

            }
        });
        mKnowledgeAdapter.setOnItemClickListener(new KnowledgeListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String title, String url) {
                Bundle bundle=new Bundle();
                bundle.putString("url",url);
                bundle.putString("title",title);
                startActivity(KnowListActivity.this,DetailActivity,bundle);
            }
        });
    }

    @OnClick({R2.id.iv_search, R2.id.iv_me})
    public void onClick(View view) {
        int id = view.getId();
            if(id==R.id.iv_search){
                Bundle bundle=new Bundle();
                bundle.putString("key","");
                startActivity(this,SearchActivity,bundle);
            }else if(id==R.id.iv_me){
                if(mDrawlayout.isDrawerOpen(Gravity.LEFT)){
                    mDrawlayout.closeDrawer(Gravity.LEFT);
                }else {
                    mDrawlayout.openDrawer(Gravity.LEFT);
                }
            }
    }
}
