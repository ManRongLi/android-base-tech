package com.myuidemo.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myuidemo.imgloader.ImageLoader;
import com.myuidemo.imgloader.MemoryCache;


public class ImageAdapter extends BaseAdapter {

  private Context mContext;
  private ImageLoader imageLoader;
  private int[] mThumbIds={R.drawable.i1, R.drawable.i2, R.drawable.i3,
      R.drawable.i4, R.drawable.i5, R.drawable.i6, R.drawable.i7,
      R.drawable.i8, R.drawable.i9, R.drawable.i10,R.drawable.i11,
      R.drawable.i12, R.drawable.i3,R.drawable.i4, R.drawable.i5,
      R.drawable.i6, R.drawable.i17, R.drawable.i18, R.drawable.i19,
      R.drawable.i20, R.drawable.i21};
  private String name[] = {"时钟", "信号", "宝箱", "秒钟", "大象", "FF", "记事本", "书签", "印象", "商店", "主题", "迅雷",
      "ofo","wifi","陌陌","钉钉","头条","飞猪","拉钩","淘宝","QQ"};

  String url[]={"http://www.baidu.com/img/bdlogo.gif",
                "http://www.baidu.com/img/bdlogo.gif",
                "http://www.baidu.com/img/bdlogo.gif"
  };
  public ImageAdapter(Context context) {
    this.mContext = context;
    imageLoader=new ImageLoader();
    imageLoader.setmImageCache(new MemoryCache());
  }

  @Override
  public int getCount() {
    return url.length;
  }

  @Override
  public Object getItem(int position) {
    return null;
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder viewHolder = null;
    if (convertView == null) {
      convertView = LayoutInflater.from(mContext).inflate(R.layout.gridview_item, parent, false);
      viewHolder = new ViewHolder();
      viewHolder.itemImg = (ImageView) convertView.findViewById(R.id.img);
      viewHolder.itemTxt = (TextView)convertView.findViewById(R.id.text);
      convertView.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) convertView.getTag();
    } // 这里只是模拟，实际开发可能需要加载网络图片，可以使用ImageLoader这样的图片加载框架来异步加载图片
     imageLoader.displayImage(url[position], viewHolder.itemImg);
   //  viewHolder.itemImg.setImageResource(mThumbIds[position]);
     viewHolder.itemTxt.setText(name[position]);

    return convertView;
  }
  class ViewHolder {
    ImageView itemImg;
    TextView itemTxt;
  }


  }
