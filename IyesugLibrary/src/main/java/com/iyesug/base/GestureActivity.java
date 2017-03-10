package com.iyesug.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2017/2/22.
 */

public abstract class GestureActivity extends AppActivity implements View.OnTouchListener,GestureDetector.OnGestureListener {

    private GestureDetector mGestureDetector;
    private int varticalMinDestance=300;
    private int minVelocity=0;

    protected abstract void doFInish();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initGrsture();
    }

    private void initGrsture(){
        mGestureDetector=new GestureDetector(this);
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
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if(e1.getX()-e2.getX()>varticalMinDestance&&Math.abs(velocityX)>velocityX){

        }else if(e2.getX()-e1.getX()>varticalMinDestance&& Math.abs(velocityX)>velocityX){
            finish();
        }
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mGestureDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }
}
