package com.myuidemo.seniorUI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.myuidemo.base.R;

public class AnimationActivity extends Activity implements View.OnClickListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_animation);
    findViewById(R.id.btn_rotate).setOnClickListener(this);
    findViewById(R.id.btn_scale).setOnClickListener(this);
    findViewById(R.id.btn_translate).setOnClickListener(this);
    findViewById(R.id.btn_alpha).setOnClickListener(this);
    findViewById(R.id.btn_property).setOnClickListener(this);
    findViewById(R.id.btn_custom).setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()){
      case R.id.btn_rotate:
        Intent i=new Intent(this,RotateActivity.class);
        startActivity(i);
        break;
      case R.id.btn_translate:
        Intent t=new Intent(this,TranslateActivity.class);
        startActivity(t);
        break;
      case R.id.btn_alpha:
        Intent y=new Intent(this, AlphaActivity.class);
        startActivity(y);
        break;
      case R.id.btn_scale:
        Intent u=new Intent(this,ScaleActivity.class);
        startActivity(u);
        break;
      case R.id.btn_property:
        Intent o=new Intent(this,PropertyAnimActivity.class);
        startActivity(o);
        break;
      case R.id.btn_custom:
        Intent p=new Intent(this,LayoutAnimActivity.class);
        startActivity(p);
        break;

    }

  }
}
