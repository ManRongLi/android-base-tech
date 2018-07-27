package com.myuidemo.imgloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageLoader {

  ImageCache mImageCache=new MemoryCache();

  ExecutorService mExecutorService= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

  public void setmImageCache(ImageCache cache){
    mImageCache=cache;
  }

//加载图片
  public void displayImage(final String url, final ImageView imageView){
    Bitmap bmp=mImageCache.get(url);

    if(bmp!=null){
      imageView.setImageBitmap(bmp);
      return;
    }

    submitLoadRequest(url,imageView);
  }

  private void submitLoadRequest(final String url,final ImageView imageView){

    imageView.setTag(url);
    mExecutorService.submit(new Runnable() {
      @Override
      public void run() {
        Bitmap bitmap=downloadImage(url);
        if(bitmap==null){
          return;
        }
        if(imageView.getTag().equals(url)){
          imageView.setImageBitmap(bitmap);
        }
        mImageCache.put(url,bitmap);
      }
    });
  }

  public Bitmap downloadImage(String imageUrl){
    Bitmap bitmap=null;
    try{
      URL url=new URL(imageUrl);
      final HttpURLConnection conn=(HttpURLConnection)url.openConnection();
      conn.setRequestMethod("GET");
//      conn.setConnectTimeout(10 * 1000);
//      int code = conn.getResponseCode();
//      if (code == 200) {
//        Log.e("ImageLoader","访问成功，服务器返回200");
//      }
      bitmap= BitmapFactory.decodeStream(conn.getInputStream());
      conn.disconnect();
    }catch (Exception e){e.printStackTrace();}

    return bitmap;
  }

}
