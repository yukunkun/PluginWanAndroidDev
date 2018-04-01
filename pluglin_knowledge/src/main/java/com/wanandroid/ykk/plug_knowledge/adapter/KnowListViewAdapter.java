package com.wanandroid.ykk.plug_knowledge.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.wanandroid.ykk.plug_knowledge.R;
import com.wanandroid.ykk.pluglin_lib.enerty.KnowledgeInfo;

import java.util.List;

/**
 * Created by yukun on 18-1-5.
 */

public class KnowListViewAdapter extends BaseAdapter {
    Context context;
    List<KnowledgeInfo.ChildrenBean> data;
    int pos=0;
    public KnowListViewAdapter(List<KnowledgeInfo.ChildrenBean> data, Context context) {
        this.context=context;
        this.data=data;
    }
    public void setSelector(int pos){
        this.pos=pos;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(R.layout.common_item,null);
        TextView viewById = (TextView) convertView.findViewById(R.id.tv_msg);
        viewById.setText(data.get(position).getName());
        if(pos==position){
            viewById.setTextColor(context.getResources().getColor(R.color.color_ff2323));
        }else {
            viewById.setTextColor(context.getResources().getColor(R.color.color_585858));
        }
        return convertView;
    }

}
