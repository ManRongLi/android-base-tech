package com.myuidemo.componentLayout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {

  private static final String TAG = "MyReceiver";

  @Override
  public void onReceive(Context context, Intent intent) {
    // TODO: This method is called when the BroadcastReceiver is receiving
    // an Intent broadcast.
    //throw new UnsupportedOperationException("Not yet implemented");
    String action=intent.getAction();
    if(action.equals("android.intent.action.NORMAL_BROADCAST")) {
      String msg = intent.getStringExtra("msg");
      Log.i(TAG, msg);
    }

  }
}
