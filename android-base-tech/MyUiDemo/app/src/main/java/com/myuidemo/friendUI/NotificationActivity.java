package com.myuidemo.friendUI;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.myuidemo.base.R;
import com.myuidemo.base.SplashActivity;

public class NotificationActivity extends Activity implements View.OnClickListener{

  private int count=0;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_notification);

    findViewById(R.id.moreForOne).setOnClickListener(this);
    findViewById(R.id.moreForEvery).setOnClickListener(this);
    findViewById(R.id.moreForLast).setOnClickListener(this);
    findViewById(R.id.myCustomNoti).setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {

    switch (view.getId()){

      case R.id.moreForOne://由于nm.notify(1,n)的requestCode全相同导致的,
        // 并且注意所有PendingIntent的flag都应设置为PendingIntent.FLAG_CANCEL_CURRENT，若为0则都可以跳转
 /*       count++;
        //从系统服务中获得通知管理器
        NotificationManager nm = (NotificationManager) NotificationActivity.this.
            getSystemService(Context.NOTIFICATION_SERVICE);
        //定义notification
        Notification n = new Notification(R.drawable.i2, "我是通知提示", System.currentTimeMillis());
        n.flags = Notification.FLAG_AUTO_CANCEL;
        n.number = count;
        //通知消息与Intent关联
        Intent it = new Intent(NotificationActivity.this, SplashActivity.class);
        it.putExtra("name", "name:" + count);
        PendingIntent pi = PendingIntent.getActivity(NotificationActivity.this, 100, it, PendingIntent.FLAG_CANCEL_CURRENT);
        //具体的通知内容
        n.setLatestEventInfo(NotificationActivity.this, "标题", "启动多次通知只显示一条", pi);
        //执行通知
        nm.notify(1, n);*/
 /**setLatestEventInfo该方法找不到，经过查证官方在APILevel11中，该函数已经被替代，不推荐使用了。
  * 建议使用Notification.Builder来创建 notification 实例*/
        //从系统服务中获得通知管理器
        count++;
        NotificationManager nm = (NotificationManager) NotificationActivity.this.
            getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder1 = new Notification.Builder(NotificationActivity.this);
        builder1.setSmallIcon(R.drawable.i1); //设置图标
        builder1.setTicker("显示通知:"+count);
        builder1.setContentTitle("通知"); //设置标题
        builder1.setContentText("点击查看详细内容"); //消息内容
        builder1.setWhen(System.currentTimeMillis()); //发送时间
        builder1.setDefaults(Notification.DEFAULT_ALL); //设置默认的提示音，振动方式，灯光
        builder1.setAutoCancel(true);//打开程序后图标消失
        builder1.setNumber(count);//如果该通知只是起到 “通知”的作用，不希望有相应的跳转，intent,pendingIntent几行可以不写
        Intent intent =new Intent (NotificationActivity.this,SplashActivity.class);
        intent.putExtra("name", "name:" + count);
        PendingIntent pendingIntent =PendingIntent.getActivity(NotificationActivity.this, 100, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder1.setContentIntent(pendingIntent);
        Notification notification1 = builder1.build();
        nm.notify(1, notification1); // 通过通知管理器发送通知,//发送多条显示一条通知
        break;
      case R.id.moreForLast://这是由于PendingIntent的RequestCode相同而导致的。
        count++;
        NotificationManager m = (NotificationManager) NotificationActivity.this.
            getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder2 = new Notification.Builder(NotificationActivity.this);
        builder2.setSmallIcon(R.drawable.i2); //设置图标
        builder2.setTicker("显示通知:"+count);
        builder2.setContentTitle("通知"); //设置标题
        builder2.setContentText("点击查看详细内容"); //消息内容
        builder2.setWhen(System.currentTimeMillis()); //发送时间
        builder2.setDefaults(Notification.DEFAULT_ALL); //设置默认的提示音，振动方式，灯光
        builder2.setAutoCancel(true);//打开程序后图标消失
        builder2.setNumber(count);//如果该通知只是起到 “通知”的作用，不希望有相应的跳转，intent,pendingIntent几行可以不写
        Intent i =new Intent (NotificationActivity.this,SplashActivity.class);
        i.putExtra("name", "name:" + count);
        PendingIntent p =PendingIntent.getActivity(NotificationActivity.this, 100, i, PendingIntent.FLAG_CANCEL_CURRENT);
        builder2.setContentIntent(p);
        Notification notification2 = builder2.build();
        m.notify(count, notification2); // 通过通知管理器发送通知,发送多条显示多条且都可以跳转
        break;
      case R.id.moreForEvery://nm.notify(count,n)的requestcode每次都不一样，相当于为每一个通知关联了一个属于自己的intent
        count++;
        NotificationManager mt = (NotificationManager) NotificationActivity.this.
            getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder3 = new Notification.Builder(NotificationActivity.this);
        builder3.setSmallIcon(R.drawable.i2); //设置图标
        builder3.setTicker("显示通知:"+count);
        builder3.setContentTitle("通知"); //设置标题
        builder3.setContentText("点击查看详细内容"); //消息内容
        builder3.setWhen(System.currentTimeMillis()); //发送时间
        builder3.setDefaults(Notification.DEFAULT_ALL); //设置默认的提示音，振动方式，灯光
        builder3.setAutoCancel(true);//打开程序后图标消失
        builder3.setNumber(count);//如果该通知只是起到 “通知”的作用，不希望有相应的跳转，intent,pendingIntent几行可以不写
        Intent e =new Intent (NotificationActivity.this,SplashActivity.class);
        e.putExtra("name", "name:" + count);
        PendingIntent t =PendingIntent.getActivity(NotificationActivity.this, count, e, PendingIntent.FLAG_CANCEL_CURRENT);
        builder3.setContentIntent(t);
        Notification notification3 = builder3.build();
        mt.notify(count, notification3); // 通过通知管理器发送通知,发送多条显示多条且都可以跳转
        break;
      case R.id.myCustomNoti:
        ResidentNotificationHelper.sendResidentNoticeType0(NotificationActivity.this, "Test", "myCustomNotificationContent", R.drawable.logo);
        //ResidentNotificationHelper.sendDefaultNotice(NotificationActivity.this, "Test", "TestContent", R.drawable.logo);
        break;

    }

  }
}
