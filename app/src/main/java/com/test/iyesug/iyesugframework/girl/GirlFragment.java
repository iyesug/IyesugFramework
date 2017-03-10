package com.test.iyesug.iyesugframework.girl;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.iyesug.base.BaseFragment;
import com.test.iyesug.iyesugframework.R;
import com.test.iyesug.iyesugframework.data.bean.GirlsBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/1.
 */
public class GirlFragment extends BaseFragment implements GirlContract.View,ViewPager.OnPageChangeListener {

    @BindView(R.id.rootView)
    RelativeLayout linearLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.text)
    TextView text;
    private Unbinder unbinder;

    private ArrayList<GirlsBean.ResultsEntity> datas;
    private int current;
    private GirlAdapter girlAdapter;
    private  String dir = Environment.getExternalStorageDirectory() + "/save";
    public GirlContract.Presenter girlPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         View rootView=super.onCreateView(inflater, container, savedInstanceState);
        unbinder= ButterKnife.bind(getContext(),rootView);
        return rootView;
    }




    public static GirlFragment getInstance(ArrayList<Parcelable> datas, int current){
        Bundle bundle=new Bundle();
        GirlFragment girlFragment=new GirlFragment();
        bundle.putParcelableArrayList("girls",datas);
        bundle.putInt("current",current);
        girlFragment.setArguments(bundle);
        return girlFragment;
    }
    @Override
    protected void initView(View view, Bundle saveInstanceState) {
        unbinder= ButterKnife.bind(this,view);
        Bundle bundle=getArguments();
        if(bundle!=null){
            datas=bundle.getParcelableArrayList("girls");
            current=bundle.getInt("current");
        }
        girlPresenter=new GirlPresenter(this,datas);

        girlPresenter.start();
        girlAdapter=new GirlAdapter(getContext(),datas);
        viewPager.setAdapter(girlAdapter);
        viewPager.setCurrentItem(current);
        viewPager.setOnPageChangeListener(this);
        setText(current);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_girl;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setText(position);
        girlPresenter.getColor(girlAdapter);
    }
    @Override
    public void setText(int position) {
        text.setText(position+1+"/"+datas.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public  void share(){
        girlPresenter.share(girlAdapter,linearLayout);
    }
    public  void save(){
        girlPresenter.save(girlAdapter,linearLayout,viewPager);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
