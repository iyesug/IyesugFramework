package com.test.iyesug.iyesugframework;

import android.app.Application;

/**
 * Created by Administrator on 2017/2/23.
 */

public class MyApplication extends Application {

    private static MyApplication myApplication;
    public static String currentGirl = "http://ww2.sinaimg.cn/large/610dc034jw1f5k1k4azguj20u00u0421.jpg";

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication=this;
    }
    public static MyApplication getInstance(){


        return myApplication;
    }


}
