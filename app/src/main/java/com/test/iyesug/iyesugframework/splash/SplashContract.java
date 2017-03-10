package com.test.iyesug.iyesugframework.splash;

import com.iyesug.base.BasePresenter;
import com.iyesug.base.BaseView;

/**
 * Created by Administrator on 2017/2/24.
 */

public interface SplashContract {
    interface Presenter extends BasePresenter{}
    interface View extends BaseView<Presenter>{
        void show();
        void show(String url);

    }
}
