package com.test.iyesug.iyesugframework.home;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.iyesug.util.LogUtil;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.test.iyesug.iyesugframework.data.bean.GirlsBean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/1.
 */
public class HomeAdapter extends RecyclerArrayAdapter<GirlsBean.ResultsEntity> {
    private  OnMyItemClickListener onMyItemClickListener;
    public HomeAdapter(Context context) {

        super(context);
        LogUtil.d("HomeAdapter.HomeAdapter ");

    }
    public interface OnMyItemClickListener{
        void onItemClick(int position , BaseViewHolder holder);
    }

    public  void setOnMyItemClickListener(OnMyItemClickListener onMyItemClickListener){
        LogUtil.d("HomeAdapter.setOnMyItemClickListener ");

        this.onMyItemClickListener=onMyItemClickListener;
    }
    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        LogUtil.d("HomeAdapter.OnCreateViewHolder ");

        return new HomeViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, final int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        LogUtil.d("HomeAdapter.onBindViewHolder ");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.d("HomeAdapter.setOnClickListener .onClick");
                if(onMyItemClickListener!=null){
                    onMyItemClickListener.onItemClick(position,holder);
                }
            }
        });

    }
}
