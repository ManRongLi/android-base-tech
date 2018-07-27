package com.myuidemo.seniorUI;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.myuidemo.base.R;

public class TranslateActivity extends Activity {

  private ImageView trans_image;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_translate);
    trans_image=(ImageView)findViewById(R.id.trans_image);
    Animation anim= AnimationUtils.loadAnimation(this,R.animator.translate);
    anim.setFillAfter(true);
    trans_image.startAnimation(anim);
  }

  public void translate(View v){

    Animation anim=new TranslateAnimation(200,0,300,0);
    anim.setDuration(2000);
    anim.setFillAfter(true);
    OvershootInterpolator overshot=new OvershootInterpolator();
    anim.setInterpolator(overshot);
    trans_image.startAnimation(anim);

  }
}
