package com.myuidemo.seniorUI;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.myuidemo.base.R;

public class PropertyAnimActivity extends Activity {

  private ImageView mBlueBall;
  private int mScreentHeight;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_property_anim);
    mBlueBall=(ImageView)findViewById(R.id.id_ball);
    DisplayMetrics dm = getResources().getDisplayMetrics();
    mScreentHeight=dm.heightPixels;
  }

  public void rotateAnimRun(View v){

    ObjectAnimator
        .ofFloat(v,"rotationX",0.0F,360.0F)
        .setDuration(500)
        .start();
  }
  public void rotateyAnimRun(final View v){

    ObjectAnimator anim=ObjectAnimator.ofFloat(v,"rotation",1.0F,0.0F)
                        .setDuration(500);
   //ObjectAnimator不能指定旋转目标，默认为调用该方法的控件-按钮
    anim.start();
    anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animator) {
        float cVal = (Float) animator.getAnimatedValue();
        v.setAlpha(cVal);
        v.setScaleX(cVal);
        v.setScaleY(cVal);

      }
    });
  }
  public void propertyValuesHolder(View view){
    PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f,
        0f, 1f);
    PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f,
        0, 1f);
    PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f,
        0, 1f);
    ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ).setDuration(1000).start();
  }
  public void verticalRun(View v){
    ValueAnimator animator=ValueAnimator.ofFloat(0,mScreentHeight-mBlueBall.getHeight());
    animator.setTarget(mBlueBall);
    animator.setDuration(1000).start();
    //animator.setInterpolator(value);
    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animator) {
        mBlueBall.setTranslationY((Float)animator.getAnimatedValue());
      }
    });
  }
  public void paraBola(View v){
    ValueAnimator valueAnimator=new ValueAnimator();
    valueAnimator.setDuration(3000);
    valueAnimator.setObjectValues(new PointF(0,0));
    valueAnimator.setInterpolator(new LinearInterpolator());
    valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
      @Override
      public PointF evaluate(float fraction, PointF startValue, PointF endValue) {

        Log.e("paraBola",fraction*3+"");
        PointF point=new PointF();
        point.x=200*fraction*3;
        point.y=0.5f*200*(fraction*3)*(fraction*3);
        return point;

      }
    });
     valueAnimator.start();
     valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
       @Override
       public void onAnimationUpdate(ValueAnimator animation) {
         PointF point=(PointF) animation.getAnimatedValue();
         mBlueBall.setX(point.x);
         mBlueBall.setY(point.y);
       }
     });
  }
  public void fadeOut(View v){
    ObjectAnimator anim = ObjectAnimator.ofFloat(mBlueBall, "alpha", 0.5f);
    anim.addListener(new Animator.AnimatorListener() {
      String TAG="fadeOut-AnimatorListener";
      @Override
      public void onAnimationStart(Animator animator) {
        Log.e(TAG, "onAnimationStart");
      }

      @Override
      public void onAnimationEnd(Animator animator) {
        Log.e(TAG, "onAnimationEnd");
        ViewGroup parent = (ViewGroup) mBlueBall.getParent();
        if (parent != null)
          parent.removeView(mBlueBall);
      }

      @Override
      public void onAnimationCancel(Animator animator) {
        Log.e(TAG, "onAnimationCancel");
      }

      @Override
      public void onAnimationRepeat(Animator animator) {
        Log.e(TAG, "onAnimationRepeat");
      }
    });
 //长的代码我不能接收，那你可以使用AnimatorListenerAdapter,继承了AnimatorListener接口，然后空实现了所有的方法
/*    anim.addListener(new AnimatorListenerAdapter()
    {
         @Override
         public void onAnimationEnd(Animator animation)
          {
                Log.e("AnimatorListenerAdapter", "onAnimationEnd");
                ViewGroup parent = (ViewGroup) mBlueBall.getParent();
                if (parent != null)
                      parent.removeView(mBlueBall);
            }
      });*/

    anim.start();
  }

  public void togetherRun(View v){
    ObjectAnimator anim1=ObjectAnimator.ofFloat(mBlueBall,"scaleX",1.0f,2f);
    ObjectAnimator anim2=ObjectAnimator.ofFloat(mBlueBall,"scaleY",1.0f,2f);
    AnimatorSet animSet =new AnimatorSet();
    animSet.setDuration(2000);
    animSet.setInterpolator(new LinearInterpolator());
    //两个动画同时执行
    animSet.playTogether(anim1,anim2);
    animSet.start();
  }

  public void playWithAfter(View v){

    float cx=mBlueBall.getX();
    ObjectAnimator anim1=ObjectAnimator.ofFloat(mBlueBall,"scaleX",1.0f,2f);
    ObjectAnimator anim2=ObjectAnimator.ofFloat(mBlueBall,"scaleY",1.0f,2f);
    ObjectAnimator anim3=ObjectAnimator.ofFloat(mBlueBall,"X",cx,0f);
    ObjectAnimator anim4=ObjectAnimator.ofFloat(mBlueBall,"X",cx);
    /**
     * anim1，anim2,anim3同时执行
     * anim4接着执行
     */
    AnimatorSet animSet=new AnimatorSet();
    animSet.play(anim1).with(anim2);
    animSet.play(anim2).with(anim3);
    animSet.play(anim4).after(anim3);
    animSet.setDuration(1000);
    animSet.start();

  }
}
