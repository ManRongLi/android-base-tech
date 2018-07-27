package com.myuidemo.componentLayout;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
  private static final String TAG = "MyService";

  public MyService() {
  }

  @Override
  public void onCreate() {
    super.onCreate();
    Log.i(TAG, "onCreate called.");
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    Log.i(TAG, "onStartCommand called.");
    return super.onStartCommand(intent, flags, startId);
  }

  @Override
  public void onStart(Intent intent, int startId) {
    super.onStart(intent, startId);
    Log.i(TAG, "onStart called.");
  }

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
  public IBinder onBind(Intent intent) {
    // TODO: Return the communication channel to the service.
    Log.i(TAG, "onBind called.");
    //throw new UnsupportedOperationException("Not yet implemented");
    return new Binder() {};
  }

}
