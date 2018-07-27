package com.myuidemo.fiveLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.myuidemo.base.R;

public class LayoutActivity extends Activity implements View.OnClickListener{

  /**
   * 在TableLayout中，单元格可以为空，但是不能跨列，意思是只能不能有相邻的单元格为空。
   在TableLayout布局中，一列的宽度由该列中最宽的那个单元格指定，而该表格的宽度由父容器指定。
   可以为每一列设置以下属性：
   Shrinkable  表示该列的宽度可以进行收缩，以使表格能够适应父容器的大小
   Stretchable 表示该列的宽度可以进行拉伸，以使能够填满表格中的空闲空间
   Collapsed  表示该列会被隐藏
   */
  /*绝对布局中将所有的子元素通过设置android:layout_x 和 android:layout_y属性，
  将子元素的坐标位置固定下来，即坐标(android:layout_x, android:layout_y) ，
  layout_x用来表示横坐标，layout_y用来表示纵坐标。屏幕左上角为坐标(0,0)，横向往右为正方，纵向往下为正方。
  实际应用中，这种布局用的比较少，因为Android终端一般机型比较多，各自的屏幕大小。
  分辨率等可能都不一样，如果用绝对布局，可能导致在有的终端上显示不全等。*/
  private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
  private final int FP = ViewGroup.LayoutParams.FILL_PARENT;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_layout_five);

    //新建TableLayout01的实例
    TableLayout tableLayout = (TableLayout) findViewById(R.id.TableLayout01);
    //全部列自动填充空白处
    tableLayout.setStretchAllColumns(true);

    //生成10行，8列的表格
    for (int row = 0; row < 10; row++) {
      TableRow tableRow = new TableRow(this);
      for (int col = 0; col < 8; col++) {
        //tv用于显示
        TextView tv = new TextView(this);
        tv.setText("(" + col + "," + row + ")");
        tableRow.addView(tv);
      }
      //新建的TableRow添加到TableLayout
      tableLayout.addView(tableRow, new TableLayout.LayoutParams(FP, WC));
    }
    Button layout=(Button)findViewById(R.id.layout);
    layout.setOnClickListener(this);


  }

  @Override
  public void onClick(View view) {
    Intent i=new Intent(this,subLayoutActivity.class);
    startActivity(i);
  }

}
