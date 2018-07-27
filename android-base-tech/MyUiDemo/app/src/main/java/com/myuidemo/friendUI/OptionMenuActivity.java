package com.myuidemo.friendUI;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.myuidemo.base.R;

public class OptionMenuActivity extends Activity implements View.OnClickListener{
  /**注意：如果菜单没有显示，在Manifest.xml文件中添加android:theme="@android:style/Theme.Holo.Light.DarkActionBar"
   * ①：直接去覆盖public boolean onCreateOptionsMenu(Menu menu) { code......}，
   * 这个方法，需要注意的是，这个方法如果覆盖的了，只会被创建一次，也就是说， 选项菜单只会去被实例化一次，，，之后就不会被去调用了
   * ②：调用Menu中的add（）方法，来添加每一个菜单选项, add(groupId, itemId, order, titleRes) group:
   * 选项组号，一般都设置成0就OK啦 itenId: 选项的Id 很重要 order:顺序，一般来说都设置0就行了 titelRes: 选项的标题名字
   * ③：当我们去点击某项的选项的时候，覆盖重写onOptionsItemSelected(MenuItem item)这个方法去实现点击事件
   */
  // 点击菜单选项的常量Id

  private static final int menu1 = 1;

  private static final int menu2 = 2;

  private static final int menu3 = 3;

  private static final int menu4 = 4;

  private static final int menu5 = 5;

  private static final int menu6 = 6;

  private static final int menu7 = 7;

  private  static String TAG="OptionMenuActivity";

  private  PopupWindow pop=null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_option_menu);

    Button subM=(Button)findViewById(R.id.sub_menu);
    Button conM=(Button)findViewById(R.id.con_menu);
    Button toast=(Button)findViewById(R.id.toast);
    Button alertDlg=(Button)findViewById(R.id.alert_dialog);
    Button notification=(Button)findViewById(R.id.notification);
    Button popupwin=(Button)findViewById(R.id.popupWindow);

    subM.setOnClickListener(this);
    conM.setOnClickListener(this);
    toast.setOnClickListener(this);
    alertDlg.setOnClickListener(this);
    notification.setOnClickListener(this);

          popupwin.setOnClickListener(this);
          //弹出窗口
          LayoutInflater inflater = LayoutInflater.from(this);
          // 引入窗口配置文件
          View view = inflater.inflate(R.layout.popup_window, null);
          // 创建PopupWindow对象
          pop = new PopupWindow(view, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, false);
          // 需要设置一下此参数，点击外边可消失
          pop.setBackgroundDrawable(new BitmapDrawable());
         //设置点击窗口外边窗口消失
          pop.setOutsideTouchable(true);
         // 设置此参数获得焦点，否则无法点击
          pop.setFocusable(true);


    Log.d(TAG,"onCreate");
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {

    super.onCreateOptionsMenu(menu);

    menu.add(0, menu1, 0, "Menu1");

    menu.add(0, menu2, 0, "Menu2");

    menu.add(0, menu3, 0, "Menu3");

    menu.add(0, menu4, 0, "Menu4");

    menu.add(0, menu5, 0, "Menu5");

    menu.add(0, menu6, 0, "Menu6");

    menu.add(0, menu7, 0, "Menu7");

    Log.d(TAG,"onCreateOptionsMenu");
    return true;

  }

  /** item.getItemId() 获取被点击的Id  */

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    Log.d(TAG,"onOptionsItemSelected");

    switch (item.getItemId()) {
      case menu1:

        Toast.makeText(this, "你点击Menu1", Toast.LENGTH_LONG).show();
        break;
      case menu2:
        Toast.makeText(this, "你点击Menu2", Toast.LENGTH_LONG).show();
        break;

      case menu3:

        Toast.makeText(this, "你点击Menu3", Toast.LENGTH_LONG).show();
        break;
      case menu4:
        Toast.makeText(this, "你点击Menu4", Toast.LENGTH_LONG).show();
        break;

      case menu5:

        Toast.makeText(this, "你点击Menu5", Toast.LENGTH_LONG).show();

        break;
      case menu6:

        Toast.makeText(this, "你点击Menu6", Toast.LENGTH_LONG).show();

        break;

      case menu7:

        Toast.makeText(this, "你点击Menu7", Toast.LENGTH_LONG).show();

        break;

    }

    return true;

  }


  @Override
  public void onClick(View view) {
    Log.d(TAG,"onClick");
     switch (view.getId()){
       case  R.id.sub_menu:
         Intent i=new Intent(this,SubMenuActivity.class);
         startActivity(i);
         break;
       case R.id.con_menu:
         Intent n=new Intent(this,ContextMenuActivity.class);
         startActivity(n);
         break;
       case R.id.toast:
         Intent t=new Intent(this,ToastActivity.class);
         startActivity(t);
         break;
       case R.id.alert_dialog:
         Intent q=new Intent(this,DialogActivity.class);
         startActivity(q);
         break;
       case R.id.notification:
         Intent v=new Intent(this,NotificationActivity.class);
         startActivity(v);
         break;
       case R.id.popupWindow:
         if (pop.isShowing()) {
           // 隐藏窗口，如果设置了点击窗口外小时即不需要此方式隐藏
           pop.dismiss();
         } else {
           // 显示窗口
           pop.showAsDropDown(view);
         }
         break;
     }
  }


}
