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

public class ServiceActivity extends Activity implements View.OnClickListener {

  private static String TAG="ServiceActivity";
  private boolean binded;
  /**在MainActivity销毁之前没有进行解除绑定也会导致后台出现异常信息，此时我们需要确保解除绑定的服务再销毁activity*/

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_service);
    Button start=(Button)findViewById(R.id.start);
    Button stop=(Button)findViewById(R.id.stop);
    Button bind=(Button)findViewById(R.id.bind);
    Button unbind=(Button)findViewById(R.id.unbind);
    Button proCallSer=(Button)findViewById(R.id.pcallService);
    start.setOnClickListener(this);
    stop.setOnClickListener(this);
    bind.setOnClickListener(this);
    unbind.setOnClickListener(this);
    proCallSer.setOnClickListener(this);

  }

  @Override
  public void onClick(View view) {

    switch (view.getId()){
      case R.id.start:
        start(view);
        break;
      case R.id.stop:
        stop(view);
        break;
      case R.id.bind:
        bind(view);
        break;
      case R.id.unbind:
        unbind(view);
        break;
      case R.id.pcallService:
        Intent i=new Intent(this,Service2Activity.class);
        startActivity(i);
        break;
      default:
          break;

    }

  }
/**
 * 启动服务
* @param view
 */
public void start(View view) {
    Intent intent = new Intent(this, MyService.class);
       startService(intent);
   }
/**
* 停止服务
 * @param view
 */
public void stop(View view) {
  Intent intent = new Intent(this, MyService.class);
     stopService(intent);
}

  private ServiceConnection conn = new ServiceConnection() {
   @Override
  public void onServiceConnected(ComponentName name, IBinder service) {
           //connected
     binded = true;
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
     Intent intent = new Intent(this, MyService.class);
     bindService(intent, conn, Context.BIND_AUTO_CREATE);
 }
/**
  * 解除绑定
  * @param view
  */
 public void unbind(View view) {
   //unbindService(conn);
   unbindService();
 }

/**
 * 解除服务绑定
 */
private void unbindService() {
        if (binded) {
         unbindService(conn);
          binded = false;
        }
}

@Override
protected void onDestroy(){
     super.onDestroy();
     unbindService();

}

}
