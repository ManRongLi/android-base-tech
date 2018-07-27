package com.myuidemo.base;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtils {
  public static String inputStream2String(InputStream in) throws IOException {

    StringBuffer out = new StringBuffer();
    byte[] b = new byte[4096];
    int n;
    while ((n = in.read(b)) != -1) {
      out.append(new String(b, 0, n));
    }
    Log.i("String的长度", new Integer(out.length()).toString());
    return out.toString();
  }

  public static byte[] InputStreamToByte(InputStream is) throws IOException {
    ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
    int ch;
    while ((ch = is.read()) != -1) {
      bytestream.write(ch);
    }
    byte imgdata[] = bytestream.toByteArray();
    bytestream.close();
    return imgdata;
  }


}
