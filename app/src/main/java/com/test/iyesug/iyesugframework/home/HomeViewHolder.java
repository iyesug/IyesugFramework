package com.test.iyesug.iyesugframework.home;

import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.iyesug.util.LogUtil;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.test.iyesug.iyesugframework.R;
import com.test.iyesug.iyesugframework.data.bean.GirlsBean;

/**
 * Created by Administrator on 2017/3/1.
 */
public class HomeViewHolder extends BaseViewHolder<GirlsBean.ResultsEntity> {
    private ImageView imageView;
    public HomeViewHolder(ViewGroup parent) {

        super(parent, R.layout.item_girl);
        imageView=$(R.id.girl_img);
        LogUtil.d("HomeViewHolder.HomeViewHolder");

    }

    @Override
    public void setData(GirlsBean.ResultsEntity data) {
        super.setData(data);
        LogUtil.d("HomeViewHolder.setData");

        Glide.with(getContext())
                .load(data.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);

    }
}
