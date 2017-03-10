package com.iyesug.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by Administrator on 2017/2/22.
 */

public abstract class AppActivity extends  BaseActivity {
    protected abstract BaseFragment getFistFragment();

    protected void HandleIntent(Intent intent) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentLayout());
        if(null!=getIntent()){
            HandleIntent(getIntent());
        }
        if(null==getSupportFragmentManager().getFragments()){
            BaseFragment fistFragment=getFistFragment();
            if(null!=fistFragment){
                addFragment(fistFragment);
            }
        }
        AppManager.getInstance().addActivity(this);
    }

    @Override
    protected int getContentLayout() {
        return 0;
    }

    @Override
    protected int getFragmentID() {
        return 0;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getInstance().finishActivity(this);
    }
}
