package com.myuidemo.seniorUI;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.myuidemo.base.R;

public class ScaleActivity extends Activity {

  private ImageView scale_image;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_scale);
    scale_image=(ImageView)findViewById(R.id.scale_image);
    Animation anim= AnimationUtils.loadAnimation(this,R.animator.scale);
    anim.setFillAfter(true);
    scale_image.startAnimation(anim);
  }

  public void scale(View view){
    Animation anim=new ScaleAnimation(2.0f,1.0f,2.0f,1.0f,Animation.RELATIVE_TO_SELF,0.5f,
        Animation.RELATIVE_TO_SELF,0.5f);
    anim.setDuration(2000);
    anim.setFillAfter(true);
    BounceInterpolator bounce=new BounceInterpolator();
    anim.setInterpolator(bounce);
    scale_image.startAnimation(anim);
  }
}
