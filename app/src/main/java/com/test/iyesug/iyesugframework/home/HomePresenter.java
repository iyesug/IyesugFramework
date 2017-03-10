package com.test.iyesug.iyesugframework.home;

import com.iyesug.util.LogUtil;
import com.test.iyesug.iyesugframework.data.bean.GirlsBean;
import com.test.iyesug.iyesugframework.data.source.MyDataSource;
import com.test.iyesug.iyesugframework.data.source.Respository;

/**
 * Created by Administrator on 2017/3/1.
 */
public class HomePresenter implements HomeContract.Presenter{

    private HomeContract.View view;
    private Respository respository;
    public HomePresenter(HomeContract.View view) {
        LogUtil.d("HomePresenter.HomePresenter");

        this.view=view;
        respository=new Respository();
    }

    @Override
    public void start() {
        LogUtil.d("HomePresenter.start");

        getGirls(1,20,true);
    }

    @Override
    public void getGirls(int page, int size, final boolean isRefresh) {
        LogUtil.d("HomePresenter.getGirls");

        respository.getDatas(page, size, new MyDataSource.LoadCallBack() {
            @Override
            public void onDatasLoaded(GirlsBean girlsBean) {
                LogUtil.d("HomePresenter.onDatasLoaded");



                if(isRefresh){
                    view.refresh(girlsBean.getResults());
                }else{
                    view.load(girlsBean.getResults());
                }
                view.showNormal();

            }

            @Override
            public void onDataNoAvaiable() {
                LogUtil.d("HomePresenter.onDataNoAvaiable");

                view.showErorr();
            }
        });

    }
}
