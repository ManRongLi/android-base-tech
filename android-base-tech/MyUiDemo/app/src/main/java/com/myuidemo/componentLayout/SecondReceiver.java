package com.myuidemo.componentLayout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SecondReceiver extends BroadcastReceiver {

  private static final String TAG = "OrderedBroadcastSecond";

  @Override
  public void onReceive(Context context, Intent intent) {
    // TODO: This method is called when the BroadcastReceiver is receiving
    // 优先级低的接收者可以用getResultExtras获取到最新的经过处理的信息集合
    String msg = getResultExtras(true).getString("msg");
            Log.i(TAG, "SecondReceiver: " + msg);
            Bundle bundle = new Bundle();
            bundle.putString("msg", msg + "@SecondReceiver");
            setResultExtras(bundle);
  }
}
