package com.test.iyesug.iyesugframework.home;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;
    private  long exitTime=0;
    private  Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d("HomeActivity.onCreate ");
        unbinder= ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
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

        return R.id.girls_fragment;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        LogUtil.d("HomeActivity.onKeyDown ");

        if(keyCode==KeyEvent.KEYCODE_BACK&&event.getAction()==KeyEvent.ACTION_DOWN){
            if(System.currentTimeMillis()-exitTime>2000){
                Snackbar.make(floatingActionButton,"再按一次退出程序",Snackbar.LENGTH_LONG);
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
}
