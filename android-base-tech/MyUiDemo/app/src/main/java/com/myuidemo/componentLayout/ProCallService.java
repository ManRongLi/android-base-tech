package com.myuidemo.componentLayout;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class ProCallService extends Service {
  private static final String TAG = "ProCallService";

  @Override
  public boolean onUnbind(Intent intent) {
    Log.i(TAG, "onUnbind called.");
    return super.onUnbind(intent);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    Log.i(TAG, "onDestroy called.");
  }

  @Override
  public void onCreate() {
    super.onCreate();
    Log.i(TAG, "onCreate called.");
  }

  @Override
  public IBinder onBind(Intent intent) {
    // TODO: Return the communication channel to the service.
    Log.i(TAG, "onBind called.");
    //throw new UnsupportedOperationException("Not yet implemented");
    return new MyBinder() {
    };
  }

  /**
   * 绑定对象
   *
   * @author user
   */
  public class MyBinder extends Binder {

    private String name=null;
    private String pwd=null;
    /**
     * 问候
     *
     * @param name
     */
    public void greet(String name) {
      this.name=name;
      Log.i(TAG, "hello, " + name);
    }

    public void setPwd(String password){
      this.pwd=password;
      Log.i(TAG, "password: " + password);
    }

    public String getName(){
      Log.i(TAG, "getName, " + name);
      return name;
    }

  }

}
