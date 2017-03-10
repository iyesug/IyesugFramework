package com.iyesug.util;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/2/22.
 */

public class BitmapUtil {

    /**
     * drawable转化bitmap
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable){
        if(drawable!=null){
        Bitmap bitmap=Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity()!= PixelFormat.OPAQUE?Bitmap.Config.ARGB_8888:Bitmap.Config.RGB_565
        );

        Canvas canvas=new Canvas(bitmap);

        drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }return null;
    }

    /**
     * 保存图片
     * @param bitmap
     * @param dir
     * @param name
     * @param isShowBitmap
     * @param application
     * @return
     */

    public  static boolean saveBitma(Bitmap bitmap, String dir, String name, boolean isShowBitmap, Application application){
        File path = new File(dir);
        if (!path.exists()) {
            path.mkdirs();
        }
        File file = new File(path + "/" + name);
        if (file.exists()) {
            file.delete();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return true;
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                    fileOutputStream);
            fileOutputStream.flush();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 其次把文件插入到系统图库
        if (isShowBitmap) {
            try {
                MediaStore.Images.Media.insertImage(application.getContentResolver(),
                        file.getAbsolutePath(), name, null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // 最后通知图库更新
            application .sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file)));
        }




        return true;
    }


    /**
     * 保存图片至相册
     */
    public static boolean saveImageToGallery( Bitmap bmp, String dir, String name, boolean isShowBitmap,Application context) {
        // 首先保存图片
        File appDir = new File(dir);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = name;
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        if(isShowBitmap){


        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsoluteFile())));

        }
        return true;
    }

}
