package com.myuidemo.seniorUI;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.myuidemo.base.R;

public class RotateActivity extends Activity {

  private int currAngle;
  private View imgChart;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_rotate);

    imgChart=findViewById(R.id.imageview);
    Animation animation= AnimationUtils.loadAnimation(this,R.animator.rotate);
    imgChart.startAnimation(animation);

  }

  public void positive(View v){

    Animation anim=new RotateAnimation(currAngle,currAngle+180,Animation.RELATIVE_TO_SELF,
        0.5f,Animation.RELATIVE_TO_SELF,0.5f);
    /**均速插值器**/
    LinearInterpolator lir=new LinearInterpolator();
    anim.setInterpolator(lir);
    anim.setDuration(1000);
    /**动画完成后不恢复原状**/
    anim.setFillAfter(true);
    currAngle+=180;
    if(currAngle>360){
      currAngle=currAngle-360;
    }
    imgChart.startAnimation(anim);
  }

  public void negative(View v){

    Animation anim=new RotateAnimation(currAngle,currAngle-180,Animation.RELATIVE_TO_SELF,
        0.5f,Animation.RELATIVE_TO_SELF,0.5f);
    /**均速插值器**/
    LinearInterpolator lir=new LinearInterpolator();
    anim.setInterpolator(lir);
    anim.setDuration(1000);
    /**动画完成后不恢复原状**/
    anim.setFillAfter(true);
    currAngle-=180;
    if(currAngle<-360){
      currAngle=currAngle+360;
    }
    imgChart.startAnimation(anim);
  }

}
