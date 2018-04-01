package com.wanandroid.ykk.plug_knowledge.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.wanandroid.ykk.plug_knowledge.R;
import com.wanandroid.ykk.plug_knowledge.R2;
import com.wanandroid.ykk.plug_knowledge.activity.KnowListActivity;
import com.wanandroid.ykk.pluglin_lib.enerty.KnowledgeInfo;
import com.wanandroid.ykk.pluglin_lib.utils.ActivityUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yukun on 18-1-4.
 */

public class KnowledgeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<KnowledgeInfo> mKnowledgeInfos;
    Context mContext;



    public KnowledgeAdapter(List<KnowledgeInfo> mKnowledgeInfos, Context context) {
        this.mKnowledgeInfos = mKnowledgeInfos;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.knowledge_item, null);
        return new MyHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyHolder) {
            final KnowledgeInfo dataBean = mKnowledgeInfos.get(position);
            ((MyHolder) holder).mTvTitle.setText(dataBean.getName());
            List<KnowledgeInfo.ChildrenBean> children = dataBean.getChildren();
            String child="";
            for (int i = 0; i < children.size(); i++) {
                child=child+children.get(i).getName()+" / ";
            }
            ((MyHolder) holder).mTvCatgroy.setText(child);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startKnowListActivity(mContext,dataBean);
                }
            });
        }
    }
    public static void startKnowListActivity(Context context, KnowledgeInfo dataBean){
        Intent intent=new Intent(context, KnowListActivity.class);
        intent.putExtra("knowlist",dataBean);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mKnowledgeInfos.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R2.id.tv_title)
        TextView mTvTitle;
        @BindView(R2.id.tv_catgroy)
        TextView mTvCatgroy;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
