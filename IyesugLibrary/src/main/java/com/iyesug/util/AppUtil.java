package com.iyesug.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Administrator on 2017/2/22.
 */

public class AppUtil {
    private AppUtil(){
        throw  new UnsupportedOperationException("无法实例化");
    }


    /**
     * 获取app名称
     * @param context
     * @return
     */
    public String getAppName(Context context){

        PackageManager packageManager=context.getPackageManager();
        try {
            PackageInfo packageInfo=packageManager.getPackageInfo(context.getPackageName(),0);
            int labelRes=packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取版本名
     * @param context
     * @return
     */
    public String getVersionName(Context context){
        PackageManager packageManager=context.getPackageManager();
        try {
            PackageInfo packageInfo=packageManager.getPackageInfo(context.getPackageName(),0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取版本号
     * @param context
     * @return
     */
    public int getVersionCode(Context context){
        PackageManager packageManager=context.getPackageManager();
        try {
            PackageInfo packageInfo=packageManager.getPackageInfo(context.getPackageName(),0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
