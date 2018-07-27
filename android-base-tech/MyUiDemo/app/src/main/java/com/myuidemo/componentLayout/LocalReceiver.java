package com.myuidemo.componentLayout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class LocalReceiver extends BroadcastReceiver {

  private static final String TAG = "LocalReceiver";

  @Override
  public void onReceive(Context context, Intent intent) {
    // TODO: This method is called when the BroadcastReceiver is receiving
    // an Intent broadcast.
    //throw new UnsupportedOperationException("Not yet implemented");
    String action = intent.getAction();
    Log.i(TAG,"action:"+action);
    Toast.makeText(context,"这是本地广播接收器",Toast.LENGTH_SHORT).show();

  }
}
