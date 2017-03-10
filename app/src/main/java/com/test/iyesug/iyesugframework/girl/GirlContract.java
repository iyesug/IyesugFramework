package com.test.iyesug.iyesugframework.girl;

import com.iyesug.base.BasePresenter;
import com.iyesug.base.BaseView;
import com.test.iyesug.iyesugframework.data.bean.GirlsBean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/1.
 */
public interface GirlContract {
    interface View extends BaseView{
        void load(List<GirlsBean.ResultsEntity> datas);
        void refresh(List<GirlsBean.ResultsEntity> datas);
        void showErorr();
        void showNormal();

    }
    interface Presenter extends BasePresenter{
        void getGirls(int page, int size, boolean isRefresh);

    }
}
