package com.iyesug.util;

import android.util.Log;

/**
 * Created by Administrator on 2017/2/23.
 */

public class LogUtil {

    private LogUtil(){
        throw  new UnsupportedOperationException("无法实例化");
    }

    public static boolean isDebug=true;
    public static final String TAG="LogUtil";

    static int count=0;

    public static void d(String msg) {
        if (isDebug) {
            Log.e(TAG, msg);
            count++;
        }
    }
}
