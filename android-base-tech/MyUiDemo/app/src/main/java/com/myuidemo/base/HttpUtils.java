package com.myuidemo.base;

import android.os.Handler;
import android.os.Message;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class HttpUtils {

  /*1.HttpURLConnection实现步骤
(1).得到HttpURLConnection对象，通过调用URL.openConnection()方法得到该对象
(2).设置请求头属性，比如数据类型，数据长度等等
(3).可选的操作  setDoOutput(true),默认为false无法向外写入数据！setDoInput(true),一般不用设置默认为true
 (4).浏览器向服务器发送的数据，比如post提交form表单或者像服务器发送一个文件
(5).浏览器读取服务器发来的相应，包括servlet写进response的头数据(content-type及content-length等等)，body数据
(6).调用HttpURLConnection的disconnect()方法， 即设置 http.keepAlive = false;释放资源
*/
  /*GZIP压缩
对于文本数据，特别是json数据或者html网页数据，最好使用gzip进行压缩，
理论上文本数据可以压缩为原来的1/3，效果很明显，压缩之后应该使用gzip流进行解压缩！
conn.setRequestProperty("Accept-Encoding", "gzip"); //设置头参数
 InputStream is = new BufferedInputStream(conn.getInputStream());
 String encoding = conn.getContentEncoding();
 if(encoding!=null && encoding.contains("gzip")){//首先判断服务器返回的数据是否支持gzip压缩，
    is = new GZIPInputStream(is);   //如果支持则应该使用GZIPInputStream解压，否则会出现乱码无效数据
 }

*/
  public static void get() {
    HttpURLConnection conn = null;
    try {
      URL url = new URL("http://127.0.0.1:8080/Day18/servlet/UploadTest");
      //1.得到HttpURLConnection实例化对象
      conn = (HttpURLConnection) url.openConnection();
      //2.设置请求信息（请求方式... ...）
      //设置请求方式和响应时间
      conn.setRequestMethod("GET");
      //conn.setRequestProperty("encoding","UTF-8"); //可以指定编码
      conn.setConnectTimeout(5000);
      //不使用缓存
      conn.setUseCaches(false);
      //3.读取相应
      if (conn.getResponseCode() == 200) {
        //先将服务器得到的流对象 包装 存入缓冲区，忽略了正在缓冲时间
        InputStream in = new BufferedInputStream(conn.getInputStream());
        // 得到servlet写入的头信息，response.setHeader("year", "2013");
        String year = conn.getHeaderField("year");
        System.out.println("year=" + year);
        byte[] bytes = StreamUtils.InputStreamToByte(in);   //封装的一个方法，通过指定的输入流得到其字节数据
        System.out.println(new String(bytes, "utf-8"));
        System.out.println("[浏览器]成功！");
      } else {
        System.out.println("请求失败！");
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      //4.释放资源
      if (conn != null) {
        //关闭连接 即设置 http.keepAlive = false;
        conn.disconnect();
      }
    }
  }


  /**
   * 2.  * post请求方式，完成form表单的提交
   * 3.
   */
  public static void post() {
    HttpURLConnection conn = null;
    try {
      URL url = new URL("http://127.0.0.1:8080/Day18/servlet/Logining");
      String para = new String("username=admin&password=admin");
      //1.得到HttpURLConnection实例化对象
      conn = (HttpURLConnection) url.openConnection();
      //2.设置请求方式
      conn.setRequestMethod("POST");
      //3.设置post提交内容的类型和长度
      /*
       * 只有设置contentType为application/x-www-form-urlencoded，
       * servlet就可以直接使用request.getParameter("username");直接得到所需要信息
       */
      conn.setRequestProperty("contentType", "application/x-www-form-urlencoded");
      conn.setRequestProperty("Content-Length", String.valueOf(para.getBytes().length));
      //默认为false
      conn.setDoOutput(true);
      //4.向服务器写入数据
      conn.getOutputStream().write(para.getBytes());
      //5.得到服务器相应
      if (conn.getResponseCode() == 200) {
        System.out.println("服务器已经收到表单数据！");
      } else {
        System.out.println("请求失败！");
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      //6.释放资源
      if (conn != null) {
        //关闭连接 即设置 http.keepAlive = false;
        conn.disconnect();
      }
    }
  }

  /**
   * post方式，完成文件的上传
   */
  private static void uploadFile() {
    HttpURLConnection conn = null;
    OutputStream out = null;
    InputStream in = null;
    FileInputStream fin = null;
    String filePath = "c:\\android帮助文档.rar";
    try {
      fin = new FileInputStream(filePath);
      //1.得到HttpURLConnection实例化对象
      conn = (HttpURLConnection) new URL("http://127.0.0.1:8080/Day18/servlet/UploadTest").openConnection();
      //2.设置请求方式
      conn.setRequestMethod("POST");
      conn.setDoOutput(true);
      //不使用缓存
      conn.setUseCaches(false);
      //conn.setRequestProperty("Range", "bytes="+start+"-"+end);多线程请求部分数据
      //3.设置请求头属性
      //上传文件的类型 rard Mime-type为application/x-rar-compressed
      conn.setRequestProperty("content-type", "application/x-rar-compressed");
      /*
       * (1).在已知文件大小，需要上传大文件时，应该设置下面的属性，即文件长度
       *  当文件较小时，可以设置头信息即conn.setRequestProperty("content-length", "文件字节长度大小");
       * (2).在文件大小不可知时，使用setChunkedStreamingMode();
       */
      conn.setFixedLengthStreamingMode(fin.available());
      String fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);
      //可以将文件名称信息已头文件方式发送，在servlet中可以使用request.getHeader("filename")读取
      conn.setRequestProperty("filename", fileName);

      //4.向服务器中发送数据
      out = new BufferedOutputStream(conn.getOutputStream());
      long totalSize = fin.available();
      long currentSize = 0;
      int len = -1;
      byte[] bytes = new byte[1024 * 5];
      while ((len = fin.read(bytes)) != -1) {
        out.write(bytes);
        currentSize += len;
        System.out.println("已经长传:" + (int) (currentSize * 100 / (float) totalSize) + "%");
      }

      System.out.println("上传成功！");
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      //5.释放相应的资源
      if (conn != null) {
        conn.disconnect();
      }
    }
  }


  //get方式登录
  public static void requestNetForGetLogin(final Handler handler, final String username, final String password) {

    //在子线程中操作网络请求
    new Thread(new Runnable() {
      @Override
      public void run() {
        //urlConnection请求服务器，验证
        try {
          //1：url对象
          URL url = new URL("http://192.168.1.100:8081//servlet/LoginServlet?username=" + URLEncoder.encode(username)+ "&pwd=" +  URLEncoder.encode(password) + "");
          //2;url.openconnection
          HttpURLConnection conn = (HttpURLConnection) url.openConnection();
          //3
          conn.setRequestMethod("GET");
          conn.setConnectTimeout(10 * 1000);
          //4
          int code = conn.getResponseCode();
          if (code == 200) {
            InputStream inputStream = conn.getInputStream();
            String result = StreamUtils.inputStream2String(inputStream);
            System.out.println("=====================服务器返回的信息：：" + result);
            boolean isLoginsuccess=false;
            if (result.contains("success")) {
              isLoginsuccess=true;
            }
            Message msg = Message.obtain();
            msg.obj=isLoginsuccess;
            handler.sendMessage(msg);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }).start();
  }

  //post方式登录
  public static void requestNetForPOSTLogin(final Handler handler,final String username,final String password) {
    new Thread(new Runnable() {
      @Override
      public void run() {
        //urlConnection请求服务器，验证
        try {
          //1：url对象
          URL url = new URL("http://192.168.1.100:8081//servlet/LoginServlet");

          //2;url.openconnection
          HttpURLConnection conn = (HttpURLConnection) url.openConnection();

          //3设置请求参数
          conn.setRequestMethod("POST");
          conn.setConnectTimeout(10 * 1000);
          //请求头的信息
          String body = "username=" + URLEncoder.encode(username) + "&pwd=" + URLEncoder.encode(password);
          conn.setRequestProperty("Content-Length", String.valueOf(body.length()));
          conn.setRequestProperty("Cache-Control", "max-age=0");
          conn.setRequestProperty("Origin", "http://192.168.1.100:8081");

          //设置conn可以写请求的内容
          conn.setDoOutput(true);
          conn.getOutputStream().write(body.getBytes());

          //4响应码
          int code = conn.getResponseCode();
          if (code == 200) {
            InputStream inputStream = conn.getInputStream();
            String result = StreamUtils.inputStream2String(inputStream);
            System.out.println("=====================服务器返回的信息：：" + result);
            boolean isLoginsuccess=false;
            if (result.contains("success")) {
              isLoginsuccess=true;
            }
            Message msg = Message.obtain();
            msg.obj=isLoginsuccess;
            handler.sendMessage(msg);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }).start();
  }
}