package com.iyesug.base;

import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

/**
 * Created by Administrator on 2017/2/21.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{


    protected abstract int getContentLayout();
    protected abstract int getFragmentID();

    protected <T extends View> T $(int id){
        return (T)findViewById(id);
    }

    /**
     * 添加fragment
     * @param fragment
     */
    protected void addFragment(BaseFragment fragment){

        if(fragment!=null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(getFragmentID(),fragment,fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }
    }

    /**
     * 移除fragment
     */
    protected void removeFragment(){

        if(getSupportFragmentManager().getBackStackEntryCount()>1){
            getSupportFragmentManager().popBackStack();
        }else{

            finish();
        }
    }

    /**返回键
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(KeyEvent.KEYCODE_BACK==keyCode){
            if(getSupportFragmentManager().getBackStackEntryCount()==1){
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
