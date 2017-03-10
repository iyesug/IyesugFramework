package com.test.iyesug.iyesugframework.splash;

import com.iyesug.util.LogUtil;
import com.test.iyesug.iyesugframework.MyApplication;
import com.test.iyesug.iyesugframework.data.bean.GirlsBean;
import com.test.iyesug.iyesugframework.data.source.MyDataSource;
import com.test.iyesug.iyesugframework.data.source.Respository;

import java.util.Random;

/**
 * Created by Administrator on 2017/2/24.
 */
public class SplashPresenter  implements SplashContract.Presenter {

    private SplashContract.View view;
    private Respository respository;
    int count=0;
    public SplashPresenter(SplashContract.View view) {
        LogUtil.d("SplashPresenter.SplashPresenter ");

        this.view = view;
        respository=new Respository();
    }

    @Override
    public void start() {
        LogUtil.d("SplashPresenter.start ");
        callRemote();
    }

    private void callRemote() {
        Random r=new Random();
        int i=r.nextInt(400);
        respository.getDatas(i,1,new MyDataSource.LoadCallBack() {

            @Override
            public void onDataNoAvaiable() {
                LogUtil.d("SplashPresenter.onDataNoAvaiable ");
                view.show();
            }

            @Override
            public void onDatasLoaded(GirlsBean girlsBean) {
                LogUtil.d("SplashPresenter.onDatasLoaded ");
                MyApplication.currentGirl=girlsBean.getResults().get(0).getUrl();
                view.show(MyApplication.currentGirl);
            }


        });
    }
}
