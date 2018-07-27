package com.myuidemo.friendUI;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.myuidemo.base.R;

public class ContextMenuActivity extends Activity {
/**当我们长时间按住屏幕的时候，会弹出菜单，那个就是ContextMenu（）;
 * 显示上下文菜单我们必须要去调用  registerForContextMenu(View) ，
 * onCreateContextMenu(ContextMenu, View, ContextMenu.ContextMenuInfo).
 * 还可以通过res/menu/xml文件的方式来添加菜单布局
 * */
  private static final int menu3 = 3;
  private static final int menu1 = 1;
  private static final int menu2 = 2;

  private TextView context_demo;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_context_menu);
    context_demo = (TextView) this.findViewById(R.id.context_demo);//需要长按该控件才会弹出菜单
    registerForContextMenu(context_demo);

  }

  @Override
  public void onCreateContextMenu(ContextMenu menu, View v,
                                  ContextMenu.ContextMenuInfo menuInfo){
    super.onCreateContextMenu(menu,v,menuInfo);
    //MenuInflater inflater = getMenuInflater();
    // inflater.inflate(R.menu.menu_context, menu);
    menu.add(0,menu1,0,"Menu1");
    menu.add(0,menu2,0,"Menu2");
    menu.add(0,menu3,0,"Menu3");

  }

  @Override
  public boolean onContextItemSelected(MenuItem item){
    switch (item.getItemId()){
      case menu1://case R.id.Hello
        Toast.makeText(this,"你 点击的是Menu1",Toast.LENGTH_LONG);
        context_demo.setText("你 点击的是Menu1");
        break;
      case menu2:
        Toast.makeText(this,"你 点击的是Menu2",Toast.LENGTH_LONG);
        context_demo.setText("你 点击的是Menu2");
        break;
      case menu3:
        Toast.makeText(this,"你 点击的是Menu3",Toast.LENGTH_LONG);
        context_demo.setText("你 点击的是Menu3");
        break;
    }
    return true;

  }

}
