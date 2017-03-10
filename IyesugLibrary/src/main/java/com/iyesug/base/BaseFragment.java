package com.iyesug.base;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by Administrator on 2017/2/21.
 */
public abstract class BaseFragment extends Fragment {
    protected BaseActivity mActivity;
    protected abstract void initView(View view, Bundle saveInstanceState);
    protected abstract int getLayoutID();
    protected BaseActivity getHolderActivity(){
        return mActivity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity= (BaseActivity) context;
    }

    /**
     * 添加fragment
     * @param fragment
     */
    protected void addFragment(BaseFragment fragment){
        getHolderActivity().addFragment(fragment);
    }

    /**
     * 移除fragment
     */
    protected void removeFragment(){
        getHolderActivity().removeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(getLayoutID(),container,false);
        initView(view,savedInstanceState);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
