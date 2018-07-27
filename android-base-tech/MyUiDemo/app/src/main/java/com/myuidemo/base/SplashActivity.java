package com.myuidemo.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.myuidemo.componentLayout.BroadcastActivity;
import com.myuidemo.componentLayout.ContentProviderActivity;
import com.myuidemo.componentLayout.LifeCycleActivity;
import com.myuidemo.componentLayout.ServiceActivity;
import com.myuidemo.fiveLayout.LayoutActivity;
import com.myuidemo.friendUI.OptionMenuActivity;
import com.myuidemo.seniorUI.AnimationActivity;

public class SplashActivity extends Activity implements View.OnClickListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    Button btn1=(Button)findViewById(R.id.btn_base);
    btn1.setOnClickListener(this);
    Button btn3=(Button)findViewById(R.id.btn_contprovider);
    btn3.setOnClickListener(this);
    Button btn4=(Button)findViewById(R.id.btn_activity);
    btn4.setOnClickListener(this);
    Button btn5=(Button)findViewById(R.id.btn_service);
    btn5.setOnClickListener(this);
    Button btn6=(Button)findViewById(R.id.btn_broadcast);
    btn6.setOnClickListener(this);
    Button btn7=(Button)findViewById(R.id.btn_layout);
    btn7.setOnClickListener(this);
    Button btn8=(Button)findViewById(R.id.btn_menu);
    btn8.setOnClickListener(this);
    Button btn9=(Button)findViewById(R.id.btn_http);
    btn9.setOnClickListener(this);
    Button btn10=(Button)findViewById(R.id.btn_animation);
    btn10.setOnClickListener(this);

  }
  @Override
  public void onClick(View v){
    switch (v.getId()){
      case R.id.btn_base:
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
        break;
      case R.id.btn_contprovider://有待更新
        Intent t=new Intent(this,ContentProviderActivity.class);
        startActivity(t);
        break;
      case R.id.btn_activity:
        Intent e=new Intent(this, LifeCycleActivity.class);
        startActivity(e);
        break;
      case R.id.btn_service://有待更新
        Intent a=new Intent(this, ServiceActivity.class);
        startActivity(a);
        break;
      case R.id.btn_broadcast://有待更新
        Intent b=new Intent(this, BroadcastActivity.class);
        startActivity(b);
        break;
      case R.id.btn_layout:
        Intent c=new Intent(this, LayoutActivity.class);
        startActivity(c);
        break;
      case R.id.btn_menu:
        Intent d=new Intent(this, OptionMenuActivity.class);
        startActivity(d);
        break;
      case R.id.btn_http:
        Intent u=new Intent(this,HttpActivity.class);
        startActivity(u);
        break;
      case R.id.btn_animation:
        Intent o=new Intent(this, AnimationActivity.class);
        startActivity(o);
    }
  }

}
