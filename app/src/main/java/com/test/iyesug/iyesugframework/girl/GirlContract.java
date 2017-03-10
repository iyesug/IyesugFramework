package com.test.iyesug.iyesugframework.girl;

import android.support.v4.view.ViewPager;
import com.iyesug.base.BasePresenter;
import com.iyesug.base.BaseView;

/**
 * Created by Administrator on 2017/3/1.
 */
public interface GirlContract {
    interface View extends BaseView{
        void setText(int position);

        interface OnGirlChange{
            void change(int color);
        }
    }
    interface Presenter extends BasePresenter{
        void getColor(GirlAdapter girlAdapter);
        void save(GirlAdapter girlAdapter, android.view.View linearLayout, ViewPager viewPager);
        void share(GirlAdapter girlAdapter, final android.view.View linearLayout);

    }
}
