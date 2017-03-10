package com.iyesug.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

/**
 *
 */
public class AppManager {


    private static Stack<Activity> activityStack;
    private static AppManager instance;


    /**
     * 单例
     * @return AppManager
     */
    public static AppManager getInstance(){
        if(instance==null){
            instance=new AppManager();
        }
        return instance;
    }

    /**
     * 添加指定activity
     * @param activity
     */
    public void addActivity(Activity activity){
        if(activityStack==null){
            activityStack=new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前activity
     * @return Activity
     */
    public Activity getCurrentActivity(){

        return activityStack.lastElement();
    }

    /**
     * 结束指定activity
     * @param activity
     */
    public void finishActivity(Activity activity){
        if(activity!=null){
           activityStack.remove(activity);
           activity.finish();
           activity=null;
        }
    }

    /**
     * 结束当前activity
     */
    public void finishCurrentActivity(){
        finishActivity(activityStack.lastElement());
    }

    /**
     * 结束指定类名的activity
     * @param cls
     */
    public void finishActivity(Class<?> cls){
        for(Activity activity:activityStack){
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
            }
        }

    /**
     * 结束所有activity
     */
    public void finishAllActivity(){

        for(int i = 0,size=activityStack.size();i<size;i++){
                if(null!=activityStack.get(i)){
                    activityStack.get(i).finish();

                }
        }
        activityStack.clear();
    }


    /**关闭app
     * @param context
     */
    public void exitApp(Context context){

        finishAllActivity();
        ActivityManager activityManager= (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        activityManager.restartPackage(context.getPackageName());
        System.exit(0);

    }
}

