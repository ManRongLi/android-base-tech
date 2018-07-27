package com.myuidemo.seniorUI;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.LayoutTransition;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.myuidemo.base.R;

public class LayoutAnimActivity extends Activity implements CompoundButton.OnCheckedChangeListener {

  private ImageView mMV;
  private ViewGroup viewGroup;
  private GridLayout mGridLayout;
  private int mVal;
  private LayoutTransition mTransition;
  private CheckBox mAppear, mChangeAppear, mDisAppear, mChangeDisAppear;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_layout_anim);
    mMV=(ImageView)findViewById(R.id.scale_image);

    viewGroup=(ViewGroup)findViewById(R.id.anim_container);
    mAppear=(CheckBox)findViewById(R.id.id_appear);
    mChangeAppear=(CheckBox)findViewById(R.id.id_change_appear);
    mDisAppear=(CheckBox)findViewById(R.id.id_disappear);
    mChangeDisAppear=(CheckBox)findViewById(R.id.id_change_disappear);

    mAppear.setOnCheckedChangeListener(this);
    mChangeAppear.setOnCheckedChangeListener(this);
    mDisAppear.setOnCheckedChangeListener(this);
    mChangeDisAppear.setOnCheckedChangeListener(this);

    // 创建一个GridLayout
    mGridLayout = new GridLayout(this);
    // 设置每列5个按钮
    mGridLayout.setColumnCount(5);
    // 添加到布局中
    viewGroup.addView(mGridLayout);
    //默认动画全部开启
    mTransition = new LayoutTransition();
    mGridLayout.setLayoutTransition(mTransition);

  }

  public void scalex(View v){
    //加载动画
    Animator anim= AnimatorInflater.loadAnimator(this,R.animator.scalex);
    anim.setTarget(mMV);
    anim.start();

  }
  public void scalexy(View v){
    //加载动画
    Animator anim= AnimatorInflater.loadAnimator(this,R.animator.scalexy);
   mMV.setPivotX(0);//横向，纵向缩小以左上角为中心点
   mMV.setPivotY(0);
   //显示的调用invalidate
    mMV.invalidate();
    anim.setTarget(mMV);
    anim.start();

  }
  public void addBtn(View v){
        final Button button=new Button(this);
        button.setText((++mVal)+"");
        mGridLayout.addView(button,Math.min(1,mGridLayout.getChildCount()));
        button.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            mGridLayout.removeView(button);
          }
        });
  }

  @Override
  public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

    mTransition = new LayoutTransition();
    mTransition.setAnimator(LayoutTransition.APPEARING,(mAppear.isChecked() ?
        mTransition.getAnimator(LayoutTransition.APPEARING) : null));
    mTransition.setAnimator(LayoutTransition.CHANGE_APPEARING,(mChangeAppear.isChecked() ?
        mTransition.getAnimator(LayoutTransition.CHANGE_APPEARING) : null));
    mTransition.setAnimator(LayoutTransition.DISAPPEARING,(mDisAppear.isChecked() ?
        mTransition.getAnimator(LayoutTransition.DISAPPEARING) : null));
    mTransition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING,(mChangeDisAppear.isChecked() ?
        mTransition.getAnimator(LayoutTransition.CHANGE_DISAPPEARING) : null));
    mGridLayout.setLayoutTransition(mTransition);
  }
}
