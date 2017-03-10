package com.test.iyesug.iyesugframework.girl;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.iyesug.base.BaseFragment;
import com.iyesug.util.BitmapUtil;
import com.iyesug.util.ShareUtil;
import com.iyesug.widget.PinchImageView;
import com.test.iyesug.iyesugframework.R;
import com.test.iyesug.iyesugframework.data.bean.GirlsBean;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/1.
 */
public class GirlFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    @BindView(R.id.rootView)
    RelativeLayout linearLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.text)
    TextView text;
    private Unbinder unbinder;
    private OnGirlChange onGirlChange;
    private ArrayList<GirlsBean.ResultsEntity> datas;
    private int current;
    private GirlAdapter girlAdapter;
    private  String dir = Environment.getExternalStorageDirectory() + "/save";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         View rootView=super.onCreateView(inflater, container, savedInstanceState);
        unbinder= ButterKnife.bind(getContext(),rootView);
        return rootView;
    }


    interface OnGirlChange{
        void change(int color);
    }

    public static GirlFragment getInstance(ArrayList<Parcelable> datas, int current){
        Bundle bundle=new Bundle();
        GirlFragment girlFragment=new GirlFragment();
        bundle.putParcelableArrayList("girls",datas);
        bundle.putInt("current",current);
        girlFragment.setArguments(bundle);
        return girlFragment;
    }
    @Override
    protected void initView(View view, Bundle saveInstanceState) {
        unbinder= ButterKnife.bind(this,view);
        Bundle bundle=getArguments();
        if(bundle!=null){
            datas=bundle.getParcelableArrayList("girls");
            current=bundle.getInt("current");
        }
        girlAdapter=new GirlAdapter(getContext(),datas);
        viewPager.setAdapter(girlAdapter);
        viewPager.setCurrentItem(current);
        viewPager.setOnPageChangeListener(this);
        setText(current);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_girl;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onGirlChange= (OnGirlChange) activity;

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setText(position);
        getColor();
    }

    private void setText(int position) {
        text.setText(position+1+"/"+datas.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void getColor() {
        PinchImageView imageView=getCurrentView();
        Bitmap bitmap= BitmapUtil.drawableToBitmap(imageView.getDrawable());
        if(bitmap!=null){
        final Palette.Builder builder= Palette.from(bitmap);
        builder.generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch psw=palette.getDarkMutedSwatch();
                if(psw==null){
                    return;
                }
                onGirlChange.change(psw.getRgb());
            }
        });
        }
    }
    public  void save(){
        Snackbar.make(linearLayout, "开始下载图片~", Snackbar.LENGTH_INDEFINITE).show();

        String imgUrl = datas.get(viewPager.getCurrentItem()).getUrl();
        PinchImageView imageView = getCurrentView();
        Bitmap bitmap = BitmapUtil.drawableToBitmap(imageView.getDrawable());

        Observable.just(
                BitmapUtil.saveImageToGallery(
                        bitmap,
                        dir,
                        imgUrl.substring(
                                imgUrl.lastIndexOf("/") + 1,
                                imgUrl.length()
                        ),
                        true,
                        getActivity().getApplication()
                )
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean isSuccess) {
                        if (isSuccess) {
                            Snackbar.make(linearLayout, "下载好了呢~", Snackbar.LENGTH_LONG).show();
                        } else {
                            Snackbar.make(linearLayout, "下载出错了哦~", Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
    }
    public void  share(){
        Snackbar.make(linearLayout, "准备分享图片~", Snackbar.LENGTH_SHORT).show();

        PinchImageView image=getCurrentView();
        if(image!=null){
            Bitmap bitmap=BitmapUtil.drawableToBitmap(image.getDrawable());
            Observable.just(BitmapUtil.saveImageToGallery(bitmap, dir, "share.jpg", false,getActivity().getApplication()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean isSuccess) {
                            if (isSuccess) {
                                //由文件得到uri
                                Uri imageUri = Uri.fromFile(new File(dir + "/share.jpg"));
                                ShareUtil.shareImg("分享图片到","分享图片","",imageUri,getContext());
                             /*   Intent shareIntent = new Intent();
                                shareIntent.setAction(Intent.ACTION_SEND);
                                shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                                shareIntent.setType("image*//*");
                                startActivity(Intent.createChooser(shareIntent, "分享图片到"));*/
                            } else {
                                Snackbar.make(linearLayout, "分享出错了~", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
    public PinchImageView getCurrentView(){
        View rootView=girlAdapter.getPrimaryItem();
        if(rootView==null){
            return null;
        }
        PinchImageView image= (PinchImageView) rootView.findViewById(R.id.img);
        if(image!=null){
            return image;
        }
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
