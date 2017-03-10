package com.test.iyesug.iyesugframework.splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.iyesug.base.AppManager;
import com.iyesug.base.BaseFragment;
import com.iyesug.util.LogUtil;
import com.iyesug.util.SPUtil;
import com.test.iyesug.iyesugframework.R;
import com.test.iyesug.iyesugframework.home.HomeActivity;

/**
 * Created by Administrator on 2017/2/24.
 */

public class SplashFragment extends BaseFragment implements SplashContract.View{

    @BindView(R.id.splash)
    ImageView imageView;

    private ScaleAnimation scaleAnimation;
    private Unbinder unbinder;
    private SplashContract.Presenter splashPresenter;

    public static SplashFragment getInstance(){
        LogUtil.d("SplashFragment.getInstance");

        SplashFragment splashFragment=new SplashFragment();
        return  splashFragment;
    }

    @Override
    protected void initView(View view, Bundle saveInstanceState) {
        LogUtil.d("SplashFragment.initView");
        unbinder= ButterKnife.bind(this,view);
        splashPresenter=new SplashPresenter(SplashFragment.this);


    }

    private void initAnim() {
        scaleAnimation=new ScaleAnimation(1.3f,1.0f,1.3f,1.0f,
                Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(2000);
        scaleAnimation.setRepeatMode(3);

        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                LogUtil.d("SplashFragment.onAnimationStart");
            }

            /**
             * @param animation
             */
            @Override
            public void onAnimationEnd(Animation animation) {
                LogUtil.d("SplashFragment.onAnimationEnd");
                startActivity(new Intent(getActivity(),HomeActivity.class));
                AppManager.getInstance().finishCurrentActivity();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected int getLayoutID() {
        LogUtil.d("SplashFragment.getLayoutID");

        return R.layout.fragment_splash;
    }

    @Override
    public void show() {
        LogUtil.d("SplashFragment.show");
        initAnim();
        Object url=SPUtil.get(getActivity().getApplication(),"splash","");
        if("".equals(url)){
            url=R.drawable.welcome;
        }
        LogUtil.d("url:::::::"+url);
        Glide.with(getActivity())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .animate(scaleAnimation)
                .into(imageView);

    }

    @Override
    public void show(String url) {
        LogUtil.d("SplashFragment.show(String url) ");
        initAnim();
        Glide.with(getContext().getApplicationContext())
                .load(url)
                .animate(scaleAnimation)
                .into(imageView);
    }

    @Override
    public void onResume() {
        LogUtil.d("SplashFragment.onResume ");

        super.onResume();
        splashPresenter.start();
    }

    @Override
    public void onDestroyView() {
        LogUtil.d("SplashFragment.onDestroyView ");

        super.onDestroyView();
        unbinder.unbind();
    }
}
