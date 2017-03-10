package com.iyesug.util;

import android.content.Context;
import android.os.Build;
import android.os.Environment;

import java.io.*;

/**
 * Created by Administrator on 2017/2/24.
 */

public class FileUtil {


    /**
     * 创建文件夹
     * @param dirPath 文件夹路径
     * @return
     */
    public static boolean makeDir(String dirPath){
        return new File(dirPath).mkdirs();
    }

    /**
     * 创建文件
     * @param filePath 文件路径
     * @throws IOException
     */
    public static void makeFile(String filePath) throws IOException {
        File file=new File(filePath);
        makeDir(file.getParent());
        file.createNewFile();
        file=null;

    }

    /**
     * 删除文件
     * @param filePath 文件路径
     * @return
     */
    public boolean deleFile(String filePath){
        return new File(filePath).delete();
    }


    /**
     * 删除文件夹
     * @param dirPath 文件夹路径
     * @param delFile 是否包含子文件
     * @return
     */
    public  boolean delDir(String dirPath,boolean delFile){
        if(delFile){
            File file=new File(dirPath);
            if(file.isFile()){
                return file.delete();
            }else  if(file.isDirectory()){
                int fileSize=file.listFiles().length;
                if(fileSize==0){
                    return file.delete();
                }else{
                    File[] fileList=file.listFiles();
                    for(int i=0;i<fileSize;i++){
                        if(fileList[i].isFile()){
                            fileList[i].delete();
                        }else{
                            delDir(fileList[i].getAbsolutePath(),true);
                        }
                    }
                }
            }
            else{
                return false;
            }
            return false;
        }else{
            return new File(dirPath).delete();
        }
    }

    /**
     * 复制文件夹
     * @param source 源文件夹
     * @param target 目标文件夹
     * @param isDir 是否文件夹
     * @throws IOException
     */
    public void  copy(String source ,String target,boolean isDir) throws IOException {
        if(isDir){
            (new File(target)).mkdirs();
            File fileSource=new File(source);
            String[] files=fileSource.list();
            File temp=null;
            for(int i=0;i<files.length;i++){
                if(source.endsWith(File.separator)){
                    temp=new File(source+files[i]);
                }else{
                    temp=new File(source+File.separator+files[i]);
                }
                if(temp.isFile()){
                    FileInputStream fileInputStream=new FileInputStream(temp);
                    FileOutputStream fileOutputStream=new FileOutputStream(target+File.separator+files[i]);
                    byte[] b=new byte[1024];
                    int len = 0;
                    while ((len=fileInputStream.read(b))!=-1){
                        fileOutputStream.write(b,0,len);
                    }
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    fileInputStream.close();
                }else  if(temp.isDirectory()){
                    copy(source+File.separator+files[i],target+File.separator+files[i],true);
                }
            }
        }else{
            int len=0;
            File fileSource=new File(source);
            if(fileSource.exists()){
                File targetFile=new File(target);
                targetFile.getParentFile().mkdirs();
                targetFile.createNewFile();
                FileOutputStream fileOutputStream=new FileOutputStream(targetFile);
                InputStream inputStream=new FileInputStream(source);
                byte[] b=new byte[1024];
                while ((len=inputStream.read(b))!=-1){
                    fileOutputStream.write(b,0,len);
                }
                fileOutputStream.flush();
                fileOutputStream.close();
                inputStream.close();
            }
        }
    }

    /**移动文件
     * @param source 源文件
     * @param target 目标文件
     * @param isDir 是否文件夹
     * @return
     * @throws IOException
     */
    public boolean move(String source,String target,boolean isDir) throws IOException {
        copy(source,target,isDir);
        if(isDir){
            return delDir(source,true);
        }else{
            return deleFile(source);
        }
    }

    /**获取缓存路径
     * @param context
     * @return
     */
    public static String getCacheDir(Context context){
        String path = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                    ||!Environment.isExternalStorageEmulated()){
                path=context.getExternalCacheDir().getAbsolutePath();
            }else{
                path=context.getCacheDir().getAbsolutePath();
            }
        }

        return path;
    }


}
