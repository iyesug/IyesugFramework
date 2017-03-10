package com.test.iyesug.iyesugframework.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewStub;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.iyesug.base.BaseFragment;
import com.iyesug.util.ColorsUtil;
import com.iyesug.util.LogUtil;
import com.iyesug.util.SPUtil;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.test.iyesug.iyesugframework.R;
import com.test.iyesug.iyesugframework.data.bean.GirlsBean;
import com.test.iyesug.iyesugframework.girl.GirlActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/1.
 */
public class HomeFragment extends BaseFragment implements HomeContract.View,SwipeRefreshLayout.OnRefreshListener,RecyclerArrayAdapter.OnLoadMoreListener{

    @BindView(R.id.girls_recycler_view)
    EasyRecyclerView recyclerView;

    @BindView(R.id.network_error_layout)
    ViewStub viewStub;

    private Unbinder unbinder;
    private HomeContract.Presenter homePresenter;
    private ArrayList<GirlsBean.ResultsEntity> datas;
    private HomeAdapter homeAdapter;
    private int size=20;
    private int page=1;
    private  View networkError;


    public static BaseFragment getInstance() {
        LogUtil.d("HomeFragment.getInstance");

        HomeFragment homeFragment=new HomeFragment();
        return homeFragment;
    }

    @Override
    protected void initView(View view, Bundle saveInstanceState) {
        LogUtil.d("HomeFragment.initView");

        unbinder= ButterKnife.bind(this,view);
        homePresenter=new HomePresenter(this);
        initRecyclerView();
        homePresenter.start();


    }

    private void initRecyclerView() {
        LogUtil.d("HomeFragment.initRecyclerView");

        datas=new ArrayList<>();
        StaggeredGridLayoutManager staggered
                =new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggered);
        homeAdapter=new HomeAdapter(this.getContext());
        recyclerView.setAdapterWithProgress(homeAdapter);
        homeAdapter.setMore(R.layout.load_more_layout,this);
        homeAdapter.setNoMore(R.layout.no_more_layout);
        homeAdapter.setError(R.layout.load_error_layout);
        homeAdapter.setOnMyItemClickListener(new HomeAdapter.OnMyItemClickListener() {
            @Override
            public void onItemClick(int position, BaseViewHolder holder) {
                LogUtil.d("HomeFragment.onItemClick");
                Intent intent=new Intent(mActivity,GirlActivity.class);
                intent.putParcelableArrayListExtra("girls",datas);
                intent.putExtra("current",position);
                ActivityOptionsCompat options=ActivityOptionsCompat.makeScaleUpAnimation(
                        holder.itemView,
                        holder.itemView.getWidth(),
                        holder.itemView.getHeight(),
                        0,0
                );
                startActivity(intent,options.toBundle());
            }
        });
        recyclerView.setRefreshListener(this);
        recyclerView.setRefreshingColor(
                ColorsUtil.CYAN,
                ColorsUtil.LOWLIGHT,
                ColorsUtil.BLUE_LIGHT,
                ColorsUtil.ORANGE,
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );
    }

    @Override
    protected int getLayoutID() {
        LogUtil.d("HomeFragment.getLayoutID");

        return R.layout.fragment_home;
    }

    @Override
    public void load(List<GirlsBean.ResultsEntity> datas) {
        LogUtil.d("HomeFragment.load");

        this.datas.addAll(datas);
        homeAdapter.addAll(datas);

    }

    @Override
    public void refresh(List<GirlsBean.ResultsEntity> datas) {
        LogUtil.d("HomeFragment.refresh");

        this.datas.clear();
        this.datas.addAll(datas);
        homeAdapter.clear();
        homeAdapter.addAll(datas);
    }

    @Override
    public void showErorr() {
        LogUtil.d("HomeFragment.showErorr");

       /* recyclerView.showError();*/
        if(networkError!=null){
            networkError.setVisibility(View.VISIBLE);
            return;
        }
        networkError=viewStub.inflate();
    }

    @Override
    public void showNormal() {
        LogUtil.d("HomeFragment.showNormal");
        SPUtil.put(getContext(),"splash",datas.get(datas.size()-1).getUrl());
        if(networkError!=null){
            networkError.setVisibility(View.GONE);

        }
    }

    @Override
    public void onRefresh() {
        LogUtil.d("HomeFragment.onRefresh");

        page=1;
        homePresenter.getGirls(page,size,true);
    }

    @Override
    public void onLoadMore() {
        LogUtil.d("HomeFragment.onLoadMore");

        if(datas.size()%20==0){
            page++;
            homePresenter.getGirls(page,size,false);
        }

    }

    @Override
    public void onDestroyView() {
        LogUtil.d("HomeFragment.onDestroyView");

        super.onDestroyView();
        unbinder.unbind();
    }
}
