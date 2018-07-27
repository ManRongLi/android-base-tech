package com.myuidemo.componentLayout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class FirstReceiver extends BroadcastReceiver {

  private static final String TAG = "OrderedBroadcastFirst";

  @Override
  public void onReceive(Context context, Intent intent) {
    // TODO: This method is called when the BroadcastReceiver is receiving
    // an Intent broadcast.
         String msg = intent.getStringExtra("msg");//首先接收到广播消息
            Log.i(TAG, "FirstReceiver: " + msg);
            Bundle bundle = new Bundle();//将一个Bundle对象设置为结果集对象，传递到下一个接收者那里，这样以来，
            bundle.putString("msg", msg + "@FirstReceiver");//优先级低的接收者可以用getResultExtras获取到最新的经过处理的信息集合
            setResultExtras(bundle);
  }
}
