package com.myuidemo.friendUI;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.myuidemo.base.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SubMenuActivity extends Activity {
/**
 * 平时我们希望把同一类型的菜单进行分级来显示，这时候我们会用SubMenu.
 * 下面的Demo,是创建两个菜单选项，当点击Menu3的时候 会弹出menu1和menu2的菜单
 创建一个SubMenu的基本步骤如下
 ①: 先去覆盖Activity中的onCreateOptionsMenu（）
 ②：调用addSubMenu()来添加子菜单项,然后调用add（）来添加子菜单
 ③:覆盖onCreateOptionsMenu（Menu menu）menu.getItemId()来获取选项的Id，去响应单击事件*/
/**ListView,
 android:transcriptMode属性：设置列表的transcriptMode模式，该模式指定列表添加新的选项的时候，是否自动滑动到底部，显示新的选项。
 disabled：取消transcriptMode模式，默认的normal：当接受到数据集合改变的通知，并且仅仅当最后一个选项已经显示在屏幕的时候，自动滑动到底部。
 alwaysScroll：无论当前列表显示什么选项，列表将会自动滑动到底部显示最新的选项。
 android:cacheColorHint属性：当你设置的ListView的背景时，应该设置该属性为“#00000000”（透明），不然在滑动的时候，列表的颜色会变黑或者背景图片小时的情况！
 android:divider属性：列表之间绘制的颜色或者图片。一般开发中用于分隔表项。在实际开发过程中，如果你不想要列表之间的分割线，可以设置属性为@null
 android:fadingEdge属性：设置列表的阴影
 android:fastScrollEnabled属性：启用快速滑动条，它能快速的滑动列表。但在实际的测试中发现，当你的数据比较小的时候，是不会显示快速滚动条。
 android:listSelector属性：用来指明列表当前选中的选项的图片
 android:choiceMode属性：定义了列表的选择行为，默认的情况下，列表没有选择行为。
 none：正常不指定选择的列表
 singleChoice：列表允许一个选择
 multipleChoice：列表允许选择多个
 * */

     private static final int menu3 = 3;

     private static final int menu4 = 4;

     private static final int menu1 = 1;

     private static final int menu2 = 2;

     private ListView listView;

@Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sub_menu);

    listView =(ListView)findViewById(R.id.listView1);
    final List<String> adapterData=new ArrayList<String>();
    for (int i = 0; i < 20; i++) {
            adapterData.add("ListItem" + i);
    }
  //只有使用支持选择的布局才能选择多项
    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
               android.R.layout.simple_list_item_checked, adapterData);
    listView.setAdapter(adapter);
    //listView.setFastScrollEnabled(true);

        //定时给列表添加新的选项，并通知列表更新
        final Handler handler = new Handler();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
          @Override
          public void run() {
                   adapterData.add("New ListItem");
                   handler.post(new Runnable() {
              @Override
                public void run() {
                              adapter.notifyDataSetChanged();
                            }
              });
            }
     }, 3000, 3000);

}

  @Override

    public boolean onCreateOptionsMenu(Menu menu) {

             SubMenu M3 = menu.addSubMenu("Menu3");

             SubMenu M4 = menu.addSubMenu("Menu4");

             M3.add(0, menu1, 0, "Menu31");
             M3.add(0, menu2, 0, "Menu32");
             M4.add(0,menu3,0,"Menu41");
             M4.add(0,menu4,0,"Menu42");

           return true;

        }

  @Override

      public boolean onOptionsItemSelected(MenuItem item) {

            switch (item.getItemId()) {

               case menu1:

                     Toast.makeText(this, "你点击的是Menu31", Toast.LENGTH_LONG).show();

                     break;

               case menu2:

                   Toast.makeText(this, "你点击的是Menu32", Toast.LENGTH_LONG).show();

               break;
              case menu3:

                Toast.makeText(this, "你点击的是Menu41", Toast.LENGTH_LONG).show();

                break;

              case menu4:

                Toast.makeText(this, "你点击的是Menu42", Toast.LENGTH_LONG).show();

                break;


              }

            return true;

         }


}
