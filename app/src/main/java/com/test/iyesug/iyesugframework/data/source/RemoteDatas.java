package com.test.iyesug.iyesugframework.data.source;

import com.iyesug.util.LogUtil;
import com.test.iyesug.iyesugframework.data.bean.GirlsBean;
import com.test.iyesug.iyesugframework.http.Api;
import com.test.iyesug.iyesugframework.http.MyRetrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
/**
 * Created by Administrator on 2017/2/24.
 */

public class RemoteDatas implements  MyDataSource {
    @Override
    public void getDatas(int page, int size, final LoadCallBack loadCallBack) {
        LogUtil.d("RemoteDatas.getDatas ");

        MyRetrofit .getRetrofit()
                .create(Api.class)
                .getgirls("福利",size,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GirlsBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        loadCallBack.onDataNoAvaiable();

                    }

                    @Override
                    public void onNext(GirlsBean girlsBean) {
                        loadCallBack.onDatasLoaded(girlsBean);

                    }
                });



    }

    @Override
    public void getData(LoadCallBack loadCallBack) {
        LogUtil.d("RemoteDatas.getData ");

        getDatas(1,1,loadCallBack);

    }
}
