package com.wanandroid.ykk.plugin_hot.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wanandroid.ykk.plugin_hot.R;
import com.wanandroid.ykk.plugin_hot.R2;
import com.wanandroid.ykk.plugin_hot.adapter.ListViewAdapter;
import com.wanandroid.ykk.pluglin_lib.activity.BaseFragment;
import com.wanandroid.ykk.pluglin_lib.enerty.CommonUrl;
import com.wanandroid.ykk.pluglin_lib.enerty.SearchHot;
import com.wanandroid.ykk.pluglin_lib.enerty.SearchInfo;
import com.wanandroid.ykk.pluglin_lib.http.CustomCallBack;
import com.wanandroid.ykk.pluglin_lib.http.NetService;
import com.wanandroid.ykk.pluglin_lib.http.RetrifitNetUtils;
import com.wanandroid.ykk.pluglin_lib.impl.ActivityConfig;
import com.wanandroid.ykk.pluglin_lib.utils.ToastUtils;
import com.wanandroid.ykk.pluglin_lib.views.NoScrolledListView;
import com.wanandroid.ykk.pluglin_lib.views.TagLayout;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import okhttp3.Call;

/**
 * Created by yukun on 18-1-4.
 */

public class HotFragment extends BaseFragment implements ActivityConfig{
    @BindView(R2.id.taglayout)
    TagLayout mTaglayout;
    @BindView(R2.id.listview)
    NoScrolledListView mListview;
    @BindView(R2.id.iv_search)
    ImageView mIvSearch;
    @BindView(R2.id.iv_me)
    ImageView mIvMe;
    @BindView(R2.id.scrollview)
    ScrollView mScrollview;

    private List<SearchHot> mJokeList;
    private List<CommonUrl>  mCommonUrl;

    public static HotFragment getInstance() {
        return new HotFragment();
    }

    @Override
    public int getLayout() {
        return R.layout.hot_fragment;
    }

    @Override
    public void initView(View inflate, Bundle savedInstanceState) {
        getInfo();
        setListener();
        OverScrollDecoratorHelper.setUpOverScroll(mScrollview);
        mListview.setFocusable(false);
    }

    private void setListener() {
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle=new Bundle();
                bundle.putString("url",mCommonUrl.get(position).getLink());
                bundle.putString("title",mCommonUrl.get(position).getName());
                startActivity(getContext(),"DetailActivity",bundle);
            }
        });
    }

    private void getInfo() {
        RetrifitNetUtils.getRetrifitUtils().getHotKet().enqueue(new CustomCallBack() {
            @Override
            public void onSuccess(String respond) {

                Gson gson = new Gson();
                mJokeList = gson.fromJson(respond,new TypeToken<List<SearchHot>>() {}.getType());
                initTagLayout();
            }

            @Override
            public void onFail(String errMsg) {
                ToastUtils.showToast(errMsg);
            }

            @Override
            public void onFinish() {

            }
        });

        RetrifitNetUtils.getRetrifitUtils().getCommon().enqueue(new CustomCallBack() {

            @Override
            public void onSuccess(String response) {

                Gson gson = new Gson();
                mCommonUrl = gson.fromJson(response,new TypeToken<List<CommonUrl>>() {}.getType());
                CommonUrl commonUrl=new CommonUrl();
                commonUrl.setName(getString(R.string.jianshu_name));
                commonUrl.setLink(getString(R.string.jianshu_url));
                mCommonUrl.add(commonUrl);
                ListViewAdapter listAdapter = new ListViewAdapter(getContext(), mCommonUrl);
                mListview.setAdapter(listAdapter);
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

    private void initTagLayout() {
        for (int i = 0; i < mJokeList.size(); i++) {
            final TextView tv = new TextView(getContext());
            tv.setText(mJokeList.get(i).getName());
            tv.setTextColor(getResources().getColor(R.color.color_ff2323));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(15, 15, 15, 15);
            tv.setPadding(12, 8, 12, 8);
            tv.setLayoutParams(params);
            tv.setClickable(true);
            tv.setTextSize(14);
            tv.setBackgroundResource(R.drawable.shape_tag_back);
            mTaglayout.addView(tv);
            //监听
            final int finalI = i;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SearchHot searchHot = mJokeList.get(finalI);
                    Bundle bundle=new Bundle();
                    bundle.putString("key",searchHot.getName());
                    startActivity(getContext(),SearchActivity,bundle);
                }
            });
        }
    }

    @OnClick({R2.id.iv_search, R2.id.iv_me})
    public void onClick(View view) {
        if (view.getId() == R.id.iv_search) {
            Bundle bundle=new Bundle();
            bundle.putString("key","");
            startActivity(getContext(),SearchActivity,bundle);
        } else if (view.getId() == R.id.iv_me) {
            ToastUtils.showToast("之后完善。。。");
        }
    }

}
