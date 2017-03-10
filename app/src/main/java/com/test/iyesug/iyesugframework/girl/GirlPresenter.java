package com.test.iyesug.iyesugframework.girl;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.view.View;
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

import static com.test.iyesug.iyesugframework.util.Constants.dir;

/**
 * Created by Administrator on 2017/3/10.
 */

public class GirlPresenter implements GirlContract.Presenter {
    private ArrayList<GirlsBean.ResultsEntity> datas;
    private GirlContract.View view;
    private GirlContract.View.OnGirlChange onGirlChange;
    public GirlPresenter(GirlContract.View view,ArrayList<GirlsBean.ResultsEntity> datas) {
        this.view=view;
        this.datas=datas;
        onGirlChange= (GirlContract.View.OnGirlChange) ((GirlFragment)view).getActivity();
    }

    @Override
    public void  share(GirlAdapter girlAdapter, final View linearLayout){
        Snackbar.make(linearLayout, "准备分享图片~", Snackbar.LENGTH_SHORT).show();

        PinchImageView image=getCurrentView(girlAdapter);
        if(image!=null){
            Bitmap bitmap= BitmapUtil.drawableToBitmap(image.getDrawable());
            Observable.just(BitmapUtil.saveImageToGallery(bitmap, dir, "share.jpg", false,((GirlFragment)view).getActivity().getApplication()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean isSuccess) {
                            if (isSuccess) {
                                //由文件得到uri
                                Uri imageUri = Uri.fromFile(new File(dir + "/share.jpg"));
                                ShareUtil.shareImg("分享图片到","分享图片","",imageUri,((GirlFragment)view).getActivity());
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

    @Override
    public void getColor(GirlAdapter girlAdapter) {
        PinchImageView imageView=getCurrentView(girlAdapter);
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
    @Override
    public  void save(GirlAdapter girlAdapter, final View linearLayout, ViewPager viewPager){
        Snackbar.make(linearLayout, "开始下载图片~", Snackbar.LENGTH_INDEFINITE).show();

        String imgUrl = datas.get(viewPager.getCurrentItem()).getUrl();
        PinchImageView imageView = getCurrentView(girlAdapter);
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
                        ((GirlFragment)view).getActivity().getApplication()
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

    public PinchImageView getCurrentView(GirlAdapter girlAdapter){
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
    public void start() {

    }
}
