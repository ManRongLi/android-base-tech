package com.myuidemo.imgloader;

import android.graphics.Bitmap;

public class DoubleCache implements ImageCache{
  ImageCache mMemoryCache=new MemoryCache();
  ImageCache mDiskCache=new DiskCache();

  @Override
  public Bitmap get(String url){
    Bitmap bitmap=mMemoryCache.get(url);
    if(bitmap==null){
      bitmap=mDiskCache.get(url);
    }
    return bitmap;
  }

  @Override
  public void put(String url,Bitmap bmp){
    mMemoryCache.put(url,bmp);
    mDiskCache.put(url,bmp);

  }

}
