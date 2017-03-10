package com.test.iyesug.iyesugframework.girl;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.iyesug.widget.PinchImageView;
import com.test.iyesug.iyesugframework.R;
import com.test.iyesug.iyesugframework.data.bean.GirlsBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/2.
 */
public class GirlAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<GirlsBean.ResultsEntity> datas;
    private LayoutInflater layoutInflater;
    private View currentView;
    public GirlAdapter(Context context,ArrayList<GirlsBean.ResultsEntity> datas){
        this.datas=datas;
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        if(datas==null){
            return 0;
        }
        return datas.size();
    }

    @Override
    public void setPrimaryItem(View container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        currentView= (View) object;
    }

    public View getPrimaryItem(){
        return currentView;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        String url=datas.get(position).getUrl();
        View view=layoutInflater.inflate(R.layout.item_girl_detail,container,false);
        PinchImageView pinchImageView= (PinchImageView) view.findViewById(R.id.img);
/*        TextView textView= (TextView) view.findViewById(R.id.text);
        textView.setText(position+1+"/"+datas.size());*/
        Glide.with(context)
                .load(url)
                .thumbnail(0.2f)
                .crossFade()
                .into(pinchImageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Glide.clear((View)object);
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}
