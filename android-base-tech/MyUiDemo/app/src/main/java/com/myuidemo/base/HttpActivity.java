package com.myuidemo.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpActivity extends Activity implements View.OnClickListener {

  private static final String TAG = "HttpActivity";
  public static final int SHOW_RESPONSE = 0;
  private EditText text;
  private HttpParams httpParams;
  private HttpClient httpClient;
  private WebView wv;

  //新建Handler的对象，在这里接收Message，然后更新TextView控件的内容
  private final Handler handler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      switch (msg.what) {
        case SHOW_RESPONSE:
          String response = (String) msg.obj;
          text.setText(response);
          break;
        default:
          break;
      }
    }

  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_http);

    findViewById(R.id.btn_post).setOnClickListener(this);
    findViewById(R.id.btn_get).setOnClickListener(this);
    text = (EditText) findViewById(R.id.edt_content);
    findViewById(R.id.btn_load).setOnClickListener(this);
    //步骤1. 定义Webview组件
    //方式1：直接在在Activity中生成
    //WebView webView = new WebView(this);
    //方法2：在Activity的layout文件里添加webview控件：
    wv = (WebView) findViewById(R.id.wv);
    webSettings();
    webLoadurl();
  }

  private void webSettings(){
    //声明WebSettings子类
    WebSettings webSettings = wv.getSettings();
    //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
    webSettings.setJavaScriptEnabled(true);
    // 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
    // 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可
    // 支持插件 webSettings.setPluginsEnabled(true);
    // 设置自适应屏幕，两者合用
    webSettings.setUseWideViewPort(true);
    //将图片调整到适合webview的大小
    webSettings.setLoadWithOverviewMode(true);
    // 缩放至屏幕的大小,缩放操作
    webSettings.setSupportZoom(true);
    //支持缩放，默认为true。
    webSettings.setBuiltInZoomControls(true);
    //设置内置的缩放控件。若为false，则该WebView不可缩放
    webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
    webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
    webSettings.setAllowFileAccess(true); //设置可以访问文件
    webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
    webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
    webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

    wv.requestFocus();//触摸焦点起作用
    wv.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);//取消滚动条
  }

  public void webLoadurl(){

       //一个Java对象绑定到一个Javascript对象中，Javascript对象名就是interfaceName，作用域是Global，
       // 这样便可以扩展Javascript的API，获取Android的数据。
       wv.addJavascriptInterface(new AndroidtoJs(), "demo");

       //步骤2. 选择加载方式
       // 方式1. 加载一个网页： webView.loadUrl("http://www.google.com/");
       // 方式2：加载apk包中的html页面，注意要手动界面创建assets文件夹
       wv.loadUrl("file:///android_asset/test.html");
       //方式3：加载手机本地的html页面 webView.loadUrl("content://com.android.htmlfileprovider/sdcard/test.html");
       //方式4：加载js方法，webView.loadUrl("javascript:callJS()");

       // 步骤3. 复写shouldOverrideUrlLoading()方法，使得打开网页时不调用系统浏览器， 而是在本WebView中显示
       wv.setWebViewClient(new WebViewClient() {
         @Override
         public boolean shouldOverrideUrlLoading(WebView view, String url) {
           view.loadUrl(url);
           return true;
         }

         @Override
         public void onPageStarted(WebView view, String url, Bitmap favicon) {
           //设定加载开始的操作,我们可以设定一个loading的页面，告诉用户程序在等待网络响应。
           Log.d(TAG, "onPageStarted,url:" + url);
         }

         @Override
         public void onPageFinished(WebView view, String url) {
           //设定加载结束的操作,我们可以关闭loading 条，切换程序动作.
           Log.d(TAG, "onPageFinished,url:" + url);
         }

         @Override
         public void onLoadResource(WebView view, String url) {
           //设定加载资源的操作,每一个资源（比如图片）的加载都会调用一次.
           Log.d(TAG, "onLoadResource,url:" + url);
         }

         @Override
         public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
           switch (errorCode) {//该方法传回了错误码，根据错误类型可以进行不同的错误分类处理
             case HttpStatus.SC_NOT_FOUND:
               view.loadUrl("file:///android_assets/error_handle.html");
               break;
           }
           Log.d(TAG, "onReceivedError,errorCode:" + errorCode);
         }

         @Override//webView默认是不处理https请求的，页面显示空白，需要进行如下设置
         public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
           handler.proceed(); //表示等待证书响应
           // handler.cancel(); //表示挂起连接，为默认方式
           // handler.handleMessage(null); //可做其他处理
           Log.d(TAG, "onReceivedSslError,SslError:" + error);
         }
       });

       //设置WebChromeClient类
       wv.setWebChromeClient(new WebChromeClient() {
         //获取网站标题
         @Override
         public void onReceivedTitle(WebView view, String title) {
           Log.d(TAG, "标题在这里:" + title);
           //mtitle.setText(title);
         }

         //获取加载进度
         @Override
         public void onProgressChanged(WebView view, int newProgress) {
           if (newProgress < 100) {
             String progress = newProgress + "%";
             Log.d(TAG, "progress:" + progress);
             // loading.setText(progress);
           } else if (newProgress == 100) {
             String progress = newProgress + "%";
             Log.d(TAG, "progress:" + progress);
             //loading.setText(progress);
           }
         }

         @Override
         public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
           //message就是wave函数里alert的字符串，这样你就可以在android客户端里对这个数据进行处理

           AlertDialog.Builder b = new AlertDialog.Builder(HttpActivity.this);
           b.setTitle("Alert");
           b.setMessage(message);
           b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
               result.confirm();
             }
           });
           b.setCancelable(false); b.create().show();
           return true;

         }

       });

       // 特别注意：5.1以上默认禁止了https和http混用，以下方式是开启
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
         wv.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
       }

  }
  public void androidCallJS(){

    wv.post(new Runnable() {
      @Override
      public void run() {
        // 注意调用的JS方法名要对应上
        // 调用javascript的wave()方法
        wv.loadUrl("javascript:wave()");
      }
    });
  }



  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.btn_post:
        //用HttpClient发送请求，分为五步
        //第一步：创建HttpClient对象
        getHttpClient();
        //post方式传递键值对
        postRequestWithHttpClient();
        break;
      case R.id.btn_get:
        //用HttpClient发送请求，分为五步
        //第一步：创建HttpClient对象
        getHttpClient();
        //get方式传递键值对
        getRequestWithHttpClient();
        break;
      case R.id.btn_load:
        androidCallJS();
        break;

    }

  }

  public void getHttpClient() {
    // 创建 HttpParams 以用来设置 HTTP 参数（这一部分不是必需的）
    this.httpParams = new BasicHttpParams();
    // 设置连接超时和 Socket 超时，以及 Socket 缓存大小
    HttpConnectionParams.setConnectionTimeout(httpParams, 20 * 1000);
    HttpConnectionParams.setSoTimeout(httpParams, 20 * 1000);
    HttpConnectionParams.setSocketBufferSize(httpParams, 8192);
    // 设置重定向，缺省为 true
    HttpClientParams.setRedirecting(httpParams, true);
    // 设置 user agent
    String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2) Gecko/20100115 Firefox/3.6";
    HttpProtocolParams.setUserAgent(httpParams, userAgent);
    // 创建一个 HttpClient 实例
    // 注意 HttpClient httpClient = new HttpClient(); 是Commons HttpClient
    // 中的用法，在 Android 1.5 中我们需要使用 Apache 的缺省实现 DefaultHttpClient
    httpClient = new DefaultHttpClient(httpParams);

  }

  public String doPost(String url, List<NameValuePair> params) {
    //第二步：创建代表请求的对象,参数是访问的服务器地址
    /* 建立HTTPPost对象 */
    HttpPost httpRequest = new HttpPost(url);
    String strResult = "doPostError";
    try {
      /* 添加请求参数到请求对象-post方式-调用setEntity()方法 */
      httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
      //第三步：执行请求，获取服务器发还的相应对象
      /* 发送请求并等待响应 */
      HttpResponse httpResponse = httpClient.execute(httpRequest);
      //第四步：检查相应的状态是否正常：检查状态码的值是200表示正常
      /* 若状态码为200 ok */
      if (httpResponse.getStatusLine().getStatusCode() == 200) {
        //第五步：从相应对象当中取出数据，放到entity当中，并转换为字符串
        /* 读返回数据 */
        strResult = EntityUtils.toString(httpResponse.getEntity());//指定编码方式EntityUtils.toString(entitiy,"utf-8")
        Log.d(TAG, "srtResult -ok:" + strResult);
      } else {
        strResult = "Error Response: "
            + httpResponse.getStatusLine().toString();
        Log.d(TAG, "srtResult:" + strResult);
      }
    } catch (ClientProtocolException e) {
      strResult = e.toString();
      e.printStackTrace();
    } catch (IOException e) {
      strResult = e.toString();//e.getMessage().toString();
      e.printStackTrace();
    } catch (Exception e) {
      strResult = e.toString();
      e.printStackTrace();
    }
    Log.v("strResult", strResult);
    return strResult;
  }

  public String doGet(String url, Map params) {

    /* 添加请求参数到请求对象-get方式-字符串拼接到url */
    String paramStr = "";
    Iterator iter = params.entrySet().iterator();
    while (iter.hasNext()) {
      Map.Entry entry = (Map.Entry) iter.next();
      Object key = entry.getKey();
      Object val = entry.getValue();
      paramStr += paramStr = "&" + key + "=" + val;
    }
    if (!paramStr.equals("")) {
      paramStr = paramStr.replaceFirst("&", "?");
      url += paramStr;
    }
    /* 建立HTTPGet对象 */
    HttpGet httpRequest = new HttpGet(url);
    String strResult = "doGetError";
    try {
      /* 发送请求并等待响应 */
      HttpResponse httpResponse = httpClient.execute(httpRequest);
      /* 若状态码为200 ok */
      if (httpResponse.getStatusLine().getStatusCode() == 200) {
        /* 读返回数据 */
        strResult = EntityUtils.toString(httpResponse.getEntity());
      } else {
        strResult = "Error Response: "
            + httpResponse.getStatusLine().toString();
      }
    } catch (ClientProtocolException e) {
      strResult = e.toString();
      e.printStackTrace();
    } catch (IOException e) {
      strResult = e.toString();
      e.printStackTrace();
    } catch (Exception e) {
      strResult = e.toString();
      e.printStackTrace();
    }
    Log.v("strResult", strResult);
    return strResult;
  }

  private void postRequestWithHttpClient() {
    new Thread(new Runnable() {
      @Override
      public void run() {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("email", "firewings.r@gmail.com"));
        params.add(new BasicNameValuePair("password", "954619"));
        params.add(new BasicNameValuePair("remember", "1"));
        params.add(new BasicNameValuePair("from", "kx"));
        params.add(new BasicNameValuePair("login", "登 录"));
        params.add(new BasicNameValuePair("refcode", ""));
        params.add(new BasicNameValuePair("refuid", "0"));
        String url = "http://www.baidu.com/";
        String response = doPost(url, params);

        //在子线程中将Message对象发出去
        Message message = new Message();
        message.what = SHOW_RESPONSE;
        message.obj = response.toString();
        handler.sendMessage(message);
      }
    }).start();
  }

  private void getRequestWithHttpClient() {
    new Thread(new Runnable() {
      @Override
      public void run() {
        Map params2 = new HashMap();
        params2.put("hl", "zh-CN");
        params2.put("source", "hp");
        params2.put("q", "haha");
        params2.put("aq", "f");
        params2.put("aqi", "g10");
        params2.put("aql", "");
        params2.put("oq", "");
        String url2 = "http://www.google.cn/search";
        String response = doGet(url2, params2);
        //在子线程中将Message对象发出去
        Message message = new Message();
        message.what = SHOW_RESPONSE;
        message.obj = response.toString();
        handler.sendMessage(message);

      }
    }).start();
  }

  //点击返回上一页面而不是退出浏览器
  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK && wv.canGoBack()) {
      wv.goBack();
      return true;
    }
    return super.onKeyDown(keyCode, event);
  }

  //销毁Webview
  @Override
  protected void onDestroy() {
    if (wv != null) {
      wv.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
      wv.clearHistory();
      ((ViewGroup) wv.getParent()).removeView(wv);
      wv.destroy();
      wv = null;
    }
    super.onDestroy();
  }


}


