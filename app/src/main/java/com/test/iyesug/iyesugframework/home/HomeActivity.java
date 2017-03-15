package com.test.iyesug.iyesugframework.home;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.view.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.iyesug.base.AppActivity;
import com.iyesug.base.BaseFragment;
import com.iyesug.util.LogUtil;
import com.test.iyesug.iyesugframework.R;

/**
 * Created by Administrator on 2017/2/28.
 */
public class HomeActivity extends AppActivity {
/*    @BindView(R.id.toolbar)
    Toolbar toolbar;*/
    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;

    private  long exitTime=0;
    private  Unbinder unbinder;


    BottomNavigationView bottomNavigationView;
    ViewPager viewPager;
    MenuItem prevMenuItem;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d("HomeActivity.onCreate ");
        unbinder= ButterKnife.bind(this);
        initView();
    }

    private void initView() {
/*        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);*/

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        setColor(item.getItemId());
                        switch (item.getItemId()) {
                            case R.id.item_call:
                                viewPager.setCurrentItem(0,false);


                                break;
                            case R.id.item_mail:
                                viewPager.setCurrentItem(1,false);


                                break;
                            case R.id.item_person:
                                viewPager.setCurrentItem(2,false);

                                break;
                        }


                        return false;
                    }
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                setColor(position);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // 如果想禁止滑动，可以把下面的代码取消注释
//        viewPager.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });

        setupViewPager(viewPager);


    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(HomeFragment.getInstance());
        adapter.addFragment(HomeFragment.getInstance());
        adapter.addFragment(HomeFragment.getInstance());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
    }
    @Override
    public void onClick(View v) {
        super.onClick(v);
        LogUtil.d("HomeActivity.onClick ");
        switch (v.getId()){
            case R.id.fab:
                //发邮件
                Uri uri=Uri.parse("mailto:2305545847@qq.com");
                Intent intent=new Intent(Intent.ACTION_SENDTO,uri);
                startActivity(Intent.createChooser(intent,"请选择邮件应用"));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        LogUtil.d("HomeActivity.onCreateOptionsMenu ");

        getMenuInflater().inflate(R.menu.menu_home,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        LogUtil.d("HomeActivity.onOptionsItemSelected ");

        switch (item.getItemId()){
            case R.id.action_about:
                //TODO
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getContentLayout() {
        LogUtil.d("HomeActivity.getContentLayout ");

        return R.layout.activity_home;
    }

    @Override
    protected int getFragmentID() {
        LogUtil.d("HomeActivity.getFragmentID ");

        return R.id.viewpager;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        LogUtil.d("HomeActivity.onKeyDown ");

        if(keyCode==KeyEvent.KEYCODE_BACK&&event.getAction()==KeyEvent.ACTION_DOWN){
            if(System.currentTimeMillis()-exitTime>2000){

                Snackbar.make(floatingActionButton,"再按一次退出程序",Snackbar.LENGTH_SHORT).show();
                exitTime= System.currentTimeMillis();
            }else{
                finish();
                System.exit(0);
            }
            return  true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected BaseFragment getFistFragment() {
        LogUtil.d("HomeActivity.getFistFragment ");

        return HomeFragment.getInstance();
    }

    //切换夜间模式的过渡动画
    private void showAnimation() {
 /*       final View decorView = getWindow().getDecorView();*/
        Bitmap cacheBitmap = getCacheBitmapFromView(bottomNavigationView);
        if (bottomNavigationView instanceof ViewGroup && cacheBitmap != null) {
           /* final View view = new View(this);*/
            bottomNavigationView.setBackgroundDrawable(new BitmapDrawable(getResources(), cacheBitmap));


            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(bottomNavigationView, "alpha", 0.8f, 1f);
            objectAnimator.setDuration(500);
            objectAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                   /* ((ViewGroup) decorView).removeView(bottomNavigationView);*/
                }
            });
            objectAnimator.start();
        }
    }

    //获取一个 View的缓存视图，用于夜间模式切换时的过渡动画
    private Bitmap getCacheBitmapFromView(View view) {
        final boolean drawingCacheEnabled = true;
        view.setDrawingCacheEnabled(drawingCacheEnabled);
        view.buildDrawingCache(drawingCacheEnabled);
        final Bitmap drawingCache = view.getDrawingCache();
        Bitmap bitmap;
        if (drawingCache != null) {
            bitmap = Bitmap.createBitmap(drawingCache);
            view.setDrawingCacheEnabled(false);
        } else {
            bitmap = null;
        }
        return bitmap;
    }

    private void setColor(int position){
        showAnimation();
        if(position==0){
            bottomNavigationView.setItemBackgroundResource(R.color.black);
        }else if(position==1){
            bottomNavigationView.setItemBackgroundResource(R.color.green);
        }else if(position==2){
            bottomNavigationView.setItemBackgroundResource(R.color.blue);}

    }
}
