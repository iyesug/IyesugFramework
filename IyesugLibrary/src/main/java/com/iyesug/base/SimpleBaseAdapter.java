package com.iyesug.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/2/22.
 */

public abstract class SimpleBaseAdapter<T> extends BaseAdapter {
    protected Context context;
    protected LayoutInflater layoutInflater;
    protected List<T> datas=new ArrayList<T>();

    public SimpleBaseAdapter(Context context, List<T> datas) {
        this.context = context;
        this.datas = datas;
        layoutInflater = LayoutInflater.from(context);
    }

    public void refreshDatas(List<T> datas){
        this.datas=datas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas!=null?datas.size():0;
    }

    @Override
    public Object getItem(int position) {
        return datas!=null?datas.get(position):null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);
}
