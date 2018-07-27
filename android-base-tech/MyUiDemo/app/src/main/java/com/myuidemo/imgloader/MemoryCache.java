package com.myuidemo.imgloader;

import android.graphics.Bitmap;
import android.util.LruCache;

public class MemoryCache implements ImageCache {
  private LruCache<String,Bitmap> mMemoryCache;

  public MemoryCache(){
    final int maxMemory=(int)(Runtime.getRuntime().maxMemory()/1024);
    final int cacheSize=maxMemory/4;
    mMemoryCache=new LruCache<String,Bitmap>(cacheSize){
      @Override
      protected int sizeOf(String key,Bitmap bitmap){
        return bitmap.getRowBytes()*bitmap.getHeight()/1024;
      }
    };
  }

  @Override
  public Bitmap get(String url){
    return mMemoryCache.get(url);
  }

  @Override
  public void put(String url,Bitmap bmp){
    mMemoryCache.put(url,bmp);
  }

}
