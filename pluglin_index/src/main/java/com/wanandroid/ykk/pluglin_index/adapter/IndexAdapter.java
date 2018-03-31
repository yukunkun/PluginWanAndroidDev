package com.wanandroid.ykk.pluglin_index.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.wanandroid.ykk.pluglin_index.R;
import com.wanandroid.ykk.pluglin_index.R2;
import com.wanandroid.ykk.pluglin_lib.enerty.FeedInfo;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by yukun on 18-1-4.
 */

public class IndexAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<FeedInfo> mFeedInfos ;
    Context mContext;
    Random mRandom=new Random();
    Integer[] mList=new Integer[]{R.color.color_2b2b2b,R.color.color_2e4eef, R.color.colorPrimary,
            R.color.color_ff2323,R.color.color_ff01bb,R.color.color_ff4081,R.color.color_ffe100,
            R.color.color_30f209,R.color.color_30f209};


    public IndexAdapter(List<FeedInfo> feedInfos, Context context) {
        mFeedInfos = feedInfos;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.index_item, null);
        return new MyHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof MyHolder){
            final FeedInfo datasBean = mFeedInfos.get(position);
            ((MyHolder) holder).mTvName.setText(datasBean.getAuthor());
            ((MyHolder) holder).mTvContent.setText(datasBean.getTitle());
            ((MyHolder) holder).mTvTime.setText(datasBean.getNiceDate());
            ((MyHolder) holder).mTvClass.setText(datasBean.getChapterName());
            int pos = mRandom.nextInt(mList.length);
            ((MyHolder) holder).mTvName.setTextColor(mContext.getResources().getColor(mList[pos]));
            if(datasBean.isCollect()){
                ((MyHolder) holder).mIvCollect.setImageResource(R.mipmap.collection_fill);
            }else {
                ((MyHolder) holder).mIvCollect.setImageResource(R.mipmap.collection);
            }
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    ActivityUtils.startDetaikActivity(mContext,datasBean.getLink(),datasBean.getTitle());
//                }
//            });
//            ((MyHolder) holder).mIvCollect.setOnClickListener(new View.OnClickListener() {
//
//
//                @Override
//                public void onClick(View v) {
//                    if(MyApp.getUesrInfo()==null){
//                        ActivityUtils.startLoginActivity(mContext);
//                    }else {
//                        if(!datasBean.isCollect()){
//                            OkHttpUtils.initClient(MyApp.getMyApp().getOkHttpCliet()).post()
//                                    .url(Constanct.COLLECTURL+datasBean.getId()+"/json")
//                                    .build()
//                                    .execute(new StringCallback() {
//                                        @Override
//                                        public void onError(Call call, Exception e, int id) {
//
//                                        }
//
//                                        @Override
//                                        public void onResponse(String response, int id) {
//                                            ToastUtils.showToast("收藏成功");
//                                            mFeedInfos.get(position).setCollect(true);
//                                            ((MyHolder) holder).mIvCollect.setImageResource(R.mipmap.collection_fill);
//                                        }
//                                    });
//                        }else {
//                            OkHttpUtils.initClient(MyApp.getMyApp().getOkHttpCliet()).post().url(Constanct.CANCELCOLURL+datasBean.getId()+"/json")
//                                    .addParams("originId",datasBean.getId()+"")
//                                    .build()
//                                    .execute(new StringCallback() {
//                                        @Override
//                                        public void onError(Call call, Exception e, int id) {
//                                            Log.i("collect",e.toString());
//                                        }
//
//                                        @Override
//                                        public void onResponse(String response, int id) {
//                                            ToastUtils.showToast("取消收藏");
//                                            mFeedInfos.get(position).setCollect(false);
//                                            ((MyHolder) holder).mIvCollect.setImageResource(R.mipmap.collection);
//                                        }
//                                    });
//                        }
//                    }
//                }
//            });
        }
    }

    @Override
    public int getItemCount() {
        return mFeedInfos.size();

    }

    class MyHolder extends RecyclerView.ViewHolder{
        @BindView(R2.id.tv_name)
        TextView mTvName;
        @BindView(R2.id.iv_collect)
        ImageView mIvCollect;
        @BindView(R2.id.tv_class)
        TextView mTvClass;
        @BindView(R2.id.tv_time)
        TextView mTvTime;
        @BindView(R2.id.tv_content)
        TextView mTvContent;
        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
