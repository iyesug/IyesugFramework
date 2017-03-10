package com.test.iyesug.iyesugframework.data.source;

import com.test.iyesug.iyesugframework.data.bean.GirlsBean;

/**
 * Created by Administrator on 2017/2/24.
 */
public interface MyDataSource {

    interface LoadCallBack{
        void onDataNoAvaiable();
        void onDatasLoaded(GirlsBean girlsBean);

    }
    void getDatas(int page,int size,LoadCallBack loadCallBack);
    void getData(LoadCallBack loadCallBack);
}
