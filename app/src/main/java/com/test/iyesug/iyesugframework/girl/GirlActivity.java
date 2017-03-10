package com.test.iyesug.iyesugframework.girl;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.*;
import butterknife.ButterKnife;
import com.iyesug.base.AppActivity;
import com.iyesug.base.BaseFragment;
import com.iyesug.util.ColorUtil;
import com.test.iyesug.iyesugframework.R;

/**
 * Created by Administrator on 2017/3/1.
 */

public class GirlActivity extends AppActivity implements GirlFragment.OnGirlChange {
    private GirlFragment girlFragment;


    Toolbar toolbar;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_girl;
    }

    @Override
    protected BaseFragment getFistFragment() {
        girlFragment=GirlFragment.getInstance(
                getIntent().getParcelableArrayListExtra("girls"),
                getIntent().getIntExtra("current",0)
        );
        return girlFragment;
    }

    @Override
    protected int getFragmentID() {
        return R.id.girl_fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar= (Toolbar) findViewById(R.id.toolbarv7);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        toolbar.setTitle(R.string.meizhi);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_girl, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_share) {
            girlFragment.share();
            return true;
        } else if (id == R.id.action_save) {
            girlFragment.save();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void change(int color) {
        toolbar.setBackgroundColor(color);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.setStatusBarColor(ColorUtil.colorBurn(color));
            window.setNavigationBarColor(ColorUtil.colorBurn(color));
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finishActivity();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    private void finishActivity() {
        finish();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }
}
