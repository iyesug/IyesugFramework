package com.test.iyesug.iyesugframework.splash;

import android.view.View;
import com.iyesug.base.AppActivity;
import com.iyesug.base.BaseFragment;
import com.iyesug.util.LogUtil;
import com.test.iyesug.iyesugframework.R;

/**
 * Created by Administrator on 2017/2/24.
 */

public class SplashActivity extends AppActivity {

    @Override
    protected BaseFragment getFistFragment() {
        LogUtil.d("SplashActivity.getFistFragment ");

        return SplashFragment.getInstance();
    }

    @Override
    protected int getContentLayout() {
        LogUtil.d("SplashActivity.getContentID ");

        return R.layout.activity_splash;
    }

    @Override
    protected int getFragmentID() {
        LogUtil.d("SplashActivity.getFragmentID ");

        return R.id.splash_fragment;
    }

    @Override
    public void onClick(View v) {

    }
}
