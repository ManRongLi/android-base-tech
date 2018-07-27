package com.myuidemo.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GridViewActivity extends Activity {

  private GridView gv;
  private String[] url={
      "https://www.baidu.com","https://www.qq.com","https://translate.google.cn/?hl=en"
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_gridview);
    gv = (GridView) findViewById(R.id.gridview);

   // initdata_simpleAdapter();
    ImageAdapter adapter=new ImageAdapter(this);
    gv.setAdapter(adapter);
    gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        AlertDialog.Builder builder = new AlertDialog.Builder(GridViewActivity.this);
        builder.setTitle("提示").setMessage("i: "+i+"l: "+l).create().show();
        Uri uri = Uri.parse(url[i]);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
      }
    });

  }

  public void initdata_simpleAdapter(){
    //初始化数据
    int icon[] = {R.drawable.i1, R.drawable.i2, R.drawable.i3,
        R.drawable.i4, R.drawable.i5, R.drawable.i6, R.drawable.i7,
        R.drawable.i8, R.drawable.i9, R.drawable.i10,R.drawable.i11,
        R.drawable.i12, R.drawable.i3,R.drawable.i4, R.drawable.i5,
        R.drawable.i6, R.drawable.i17, R.drawable.i18, R.drawable.i19,
        R.drawable.i20, R.drawable.i21};
    //图标下的文字
    String name[] = {"时钟", "信号", "宝箱", "秒钟", "大象", "FF", "记事本", "书签", "印象", "商店", "主题", "迅雷",
        "ofo","wifi","陌陌","钉钉","头条","飞猪","拉钩","淘宝","QQ"};
    final ArrayList<Map<String, Object>> datalist = new ArrayList<Map<String, Object>>();
    for (int i = 0; i < icon.length; i++) {
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("img", icon[i]);
      map.put("text", name[i]);
      datalist.add(map);
    }
    String[] from = {"img", "text"};

    int[] to = {R.id.img, R.id.text};

    SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), datalist, R.layout.gridview_item, from, to);
    gv.setAdapter(adapter);

    gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                              long arg3) {
        AlertDialog.Builder builder = new AlertDialog.Builder(GridViewActivity.this);
        builder.setTitle("提示").setMessage(datalist.get(arg2).get("text").toString()).create().show();
      }
    });
  }

}
