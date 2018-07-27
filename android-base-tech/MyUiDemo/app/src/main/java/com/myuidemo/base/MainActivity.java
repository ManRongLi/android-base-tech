package com.myuidemo.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements SeekBar.OnSeekBarChangeListener {

  private static final String tag = "MainActivity";
  private static boolean swit = false;
  private static boolean save = false;
  private static boolean imgbtn = false;
  private TextView title;
  private Spinner spin;
  private ScrollView scrollview;
  private ImageButton imgBtn;
  private ImageView imgStar;
  private SeekBar skbDegree;
  private TextView skbValue;
  public RadioGroup radioGroup;
  private RadioButton rbone;
  private RadioButton rbsecond;
  private RadioButton rbthird;
  private CheckBox cbSave;
  private ProgressBar proBar;
  private Button btnSure;
  private LinearLayout Linear;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initView();
  }

  private void initView() {

    title = (TextView) findViewById(R.id.textv_title);
    spin = (Spinner) findViewById(R.id.spinner_theme);
    final List<String> datas = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      datas.add("项目" + i);
    }

    MyAdapter adapter = new MyAdapter(this);
    spin.setAdapter(adapter);

    adapter.setDatas(datas);

    /**选项选择监听*/
    spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(MainActivity.this, "点击了" + datas.get(position), Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });

    scrollview = (ScrollView) findViewById(R.id.scrollView_category);
    imgBtn = (ImageButton) findViewById(R.id.imgButton);
    imgBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (imgbtn) {
          imgBtn.setImageAlpha(255);
          imgbtn = false;
        } else {
          imgBtn.setImageAlpha(10);
          imgbtn = true;
        }

      }
    });

    skbDegree = (SeekBar) findViewById(R.id.seekBar_degree);
    skbDegree.setMax(255);
    skbDegree.setProgress(10);
    skbDegree.setOnSeekBarChangeListener(this);
    skbValue = (TextView) findViewById(R.id.skb_value);
    imgStar = (ImageView) findViewById(R.id.imageView_star);
    imgStar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (swit) {
          imgStar.setImageAlpha(255);
          swit = false;
        } else {
          imgStar.setImageAlpha(10);
          swit = true;
        }
      }
    });

    cbSave = (CheckBox) findViewById(R.id.checkBox);
    cbSave.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (!save) {
          Toast.makeText(getApplicationContext(), "保存", Toast.LENGTH_SHORT).show();
          save = true;
        } else {
          Toast.makeText(getApplicationContext(), "不保存", Toast.LENGTH_SHORT).show();
          save = false;
        }
        cbSave.setChecked(save);
      }
    });

    proBar = (ProgressBar) findViewById(R.id.progressBar);
    final TextView textView = (TextView) findViewById(R.id.tv_proBar);
    new Thread() {
      @Override
      public void run() {
        int i = 0;
        while (i < 100) {
          i++;
          try {
            Thread.sleep(80);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          final int j = i;
          proBar.setProgress(i);
          runOnUiThread(new Runnable() {
            @Override
            public void run() {
              textView.setText(j + "%");
            }
          });
        }
      }
    }.start();

    Linear = (LinearLayout) findViewById(R.id.Linear);

    btnSure = (Button) findViewById(R.id.button);
    btnSure.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        StringBuffer sb = new StringBuffer();
        //拿到所有的子类长度
        int cNum = Linear.getChildCount();
        for (int i = 0; i < cNum; i++) {
          //根据i 拿到每一个CheckBox
          CheckBox cb = (CheckBox) Linear.getChildAt(i);
          //判断CheckBox是否被选中
          if (cb.isChecked()) {
            //把被选中的文字添加到StringBuffer中
            sb.append(cb.getText().toString());
          }
        }
        Toast.makeText(MainActivity.this, sb.toString()+" 提交成功", Toast.LENGTH_LONG).show();
        Intent i = new Intent(getApplicationContext(), GridViewActivity.class);
        startActivity(i);
      }
    });


  }

  /**
   * 当进度条发生变化时调用该方法
   *
   * @param seekBar
   * @param progress
   * @param fromUser
   */

  @Override
  public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
    //设置文本框的值
    skbValue.setText("图片虚幻度为：" + progress);
    //滑动滑动条时图片虚幻度跟着变幻
    imgStar.setImageAlpha(progress);

  }

  /**
   * 开始滑动时调用该方法
   *
   * @param seekBar
   */
  @Override
  public void onStartTrackingTouch(SeekBar seekBar) {

  }

  /**
   * 结束滑动时调用该方法
   *
   * @param seekBar
   */
  @Override
  public void onStopTrackingTouch(SeekBar seekBar) {

  }


}
