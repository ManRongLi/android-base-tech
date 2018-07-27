package com.myuidemo.componentLayout;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;

import com.myuidemo.base.R;
/**在Android系统中，广播体现在方方面面.
 * 例如当开机完成后系统会产生一条广播，接收到这条广播就能实现开机启动服务的功能；
 * 当网络状态改变时系统会产生一条广播，接收到这条广播就能及时地做出提示和保存数据等操作；
 * 当电池电量改变时，系统会产生一条广播，接收到这条广播就能在电量低时告知用户及时保存进度，等等。
 * */

public class BroadcastActivity extends Activity  implements View.OnClickListener{
  private static final String TAG="BroadcastActivity";
  //普通广播-动态注册
  private MyReceiver receiver = null;
  private IntentFilter filter = null;
  //本地广播-只能静态注册
  private IntentFilter intentFilter;
  private LocalReceiver localReceiver;
  //本地广播数据类型实例。
  private LocalBroadcastManager localBroadcastManager;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_broadcast);

    Button normal=(Button)findViewById(R.id.normal);
    Button ordered=(Button)findViewById(R.id.ordered);
    Button local=(Button)findViewById(R.id.local);

    normal.setOnClickListener(this);
    ordered.setOnClickListener(this);
    local.setOnClickListener(this);


  }


  @Override
  public void onClick(View v) {

    switch (v.getId()){
      case R.id.normal:
        normalBroadcast();
        break;
      case R.id.ordered:
        orderedBroadcast();
        break;
      case R.id.local:
        localBroadcast();
        break;
        default:
          break;
    }

  }

  public void normalBroadcast(){
    //动态注册广播，即在代码中完成注册。好处是我们可以自由的控制注册与注销。
    // 灵活性方面有很大的优势，需要在onDestroy中解除注册（或者取消注册，不然会报异常），
    receiver = new MyReceiver();
    filter = new IntentFilter();
    filter.addAction("android.intent.action.NORMAL_BROADCAST");
    registerReceiver(receiver, filter);
    //发送广播
    Intent intent = new Intent("android.intent.action.NORMAL_BROADCAST");
    intent.putExtra("msg", "hello receiver.");
    sendBroadcast(intent);
    /**
     普通广播对于多个接收者来说是完全异步的,接收者无法终止广播，即无法阻止其他接收者的接收动作(即：abortBroadcast();)
     */
  }

/**有序广播采用静态注册的方式，在Manifest文件中进行注册的方法叫做静态注册，静态注册的好处就是程序即使未启动， 我们也能接收到广播。
 *注意，使用sendOrderedBroadcast方法发送有序广播时，需要一个权限参数，如果为null则表示不要求接收者声明指定的权限，
 * 如果不为null，则表示接收者若要接收此广播，需声明指定权限。
 * 这样做是从安全角度考虑的，例如系统的短信就是有序广播的形式，一个应用可能是具有拦截垃圾短信的功能，
 * 当短信到来时它可以先接受到短信广播，必要时终止广播传递，这样的软件就必须声明接收短信的权限。
 *  */
   public void orderedBroadcast(){
     Intent intent = new Intent("android.intent.action.MY_BROADCAST");
     intent.putExtra("msg", "hello receiver.");
     sendOrderedBroadcast(intent, "scott.permission.MY_BROADCAST_PERMISSION");
   }

   /**本地广播是无法通过静态注册来实现的。因为静态注册是为了让程序未启动也能接收广播。
    * 本地广播是在本程序内进行传递，肯定是已经启动了，因此也完全不需要静态注册。
    * */
   public void localBroadcast(){
     //新建intentFilter并给其action标签赋值。
       intentFilter=new IntentFilter();
       intentFilter.addAction("com.example.tangyi.receiver5.LOCAL_BROADCAST");
     //创建广播接收器实例，并注册。将其接收器与action标签进行绑定。
       localReceiver=new LocalReceiver();
       localBroadcastManager=LocalBroadcastManager.getInstance(getApplicationContext());
       localBroadcastManager.registerReceiver(localReceiver,intentFilter);
     //发送本地广播。
       Intent intent=new Intent("com.example.tangyi.receiver5.LOCAL_BROADCAST");
       localBroadcastManager.sendBroadcast(intent);

   }

  @Override
  protected  void onDestroy(){
    super.onDestroy();
    if( receiver!=null ){//动态广播需要去注册-取消普通广播注册
      unregisterReceiver(receiver);
    }
    if(localReceiver != null){
      //取消注册调用的是unregisterReceiver()方法，并传入接收器实例。
    localBroadcastManager.unregisterReceiver(localReceiver);

    }

  }

}
