package com.myuidemo.seniorUI;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.myuidemo.base.R;

public class AlphaActivity extends Activity implements Animation.AnimationListener{

  private ImageView imageChart;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_alpha);

    imageChart=findViewById(R.id.imageview);
    Animation animation= AnimationUtils.loadAnimation(this,R.animator.alpha);
    animation.setAnimationListener(this);
    imageChart.startAnimation(animation);
  }

  public void alpha(View v){

    Animation anim=new AlphaAnimation(1.0f,0.0f);
    anim.setDuration(3000);
    anim.setFillAfter(true);
    imageChart.startAnimation(anim);
  }

  @Override
  public void onAnimationStart(Animation animation) {
    Log.i("alpha", "onAnimationStart called.");
  }

  @Override
  public void onAnimationEnd(Animation animation) {
    Log.i("alpha", "onAnimationEnd called");
  }

  @Override
  public void onAnimationRepeat(Animation animation) {
    Log.i("alpha", "onAnimationRepeat called");
  }
}
