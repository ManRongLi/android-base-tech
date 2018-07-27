package com.myuidemo.componentLayout;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.myuidemo.base.R;

public class Service2Activity extends Activity implements View.OnClickListener {

  private static String TAG="Service2Activity";
  /**
    * 绑定对象实例
    */
  private ProCallService.MyBinder binder;

  private boolean binded;
  /**在MainActivity销毁之前没有进行解除绑定也会导致后台出现异常信息，此时我们需要确保解除绑定的服务再销毁activity*/

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_service2);

    Button bind=(Button)findViewById(R.id.bind);
    Button unbind=(Button)findViewById(R.id.unbind);

    bind.setOnClickListener(this);
    unbind.setOnClickListener(this);


  }

  @Override
  public void onClick(View view) {

    switch (view.getId()){

      case R.id.bind:
        bind(view);
        break;
      case R.id.unbind:
        unbind(view);
        break;
      default:
          break;

    }

  }

  private ServiceConnection conn = new ServiceConnection() {
   @Override
  public void onServiceConnected(ComponentName name, IBinder service) {
           //connected
     binded = true;
     binder = (ProCallService.MyBinder) service;  //获取其实例
     binder.greet("scott");                  //调用其方法
     binder.setPwd("***111");
     binder.getName();
     Log.i(TAG, "onServiceConnected called.");
         }
   /**
     *  Called when a connection to the Service has been lost.
     *  This typically happens when the process hosting the service has crashed or been killed.
     *  This does not remove the ServiceConnection itself.
      *  this binding to the service will remain active,
    *  and you will receive a call to onServiceConnected when the Service is next running.
    */
    @Override
   public void onServiceDisconnected(ComponentName name) {
       }
};

/**
 * 绑定服务
  * @param view
 */
public void bind(View view) {
     Intent intent = new Intent(this,ProCallService.class);
     bindService(intent, conn, Context.BIND_AUTO_CREATE);
 }
/**
  * 解除绑定
  * @param view
  */
 public void unbind(View view) {
    unbindService(conn);
 }

}
