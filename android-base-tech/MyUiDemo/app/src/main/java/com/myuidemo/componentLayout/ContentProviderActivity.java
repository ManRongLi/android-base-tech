package com.myuidemo.componentLayout;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.myuidemo.base.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContentProviderActivity extends Activity implements OnClickListener {

  private static final String TAG = "ContentProviderActivity";
  //读取联系人
  //[content://com.android.contacts/contacts]
  private static final Uri CONTACTS_URI = ContactsContract.Contacts.CONTENT_URI;
  //[content://com.android.contacts/data/phones]
  private static final Uri PHONES_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
  //[content://com.android.contacts/data/emails]
  private static final Uri EMAIL_URI = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
  private static final String _ID = ContactsContract.Contacts._ID;
  private static final String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
  private static final String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
  private static final String CONTACT_ID = ContactsContract.Data.CONTACT_ID;
  private static final String PHONE_NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
  private static final String PHONE_TYPE = ContactsContract.CommonDataKinds.Phone.TYPE;
  private static final String EMAIL_DATA = ContactsContract.CommonDataKinds.Email.DATA;
  private static final String EMAIL_TYPE = ContactsContract.CommonDataKinds.Email.TYPE;
  //写入联系人
  //[content://com.android.contacts/raw_contacts]
  private static final Uri RAW_CONTACTS_URI = ContactsContract.RawContacts.CONTENT_URI;
  //[content://com.android.contacts/data]
  private static final Uri DATA_URI = ContactsContract.Data.CONTENT_URI;
  private static final String ACCOUNT_TYPE = ContactsContract.RawContacts.ACCOUNT_TYPE;
  private static final String ACCOUNT_NAME = ContactsContract.RawContacts.ACCOUNT_NAME;
  private static final String RAW_CONTACT_ID = ContactsContract.Data.RAW_CONTACT_ID;
  private static final String MIMETYPE = ContactsContract.Data.MIMETYPE;
  private static final String NAME_ITEM_TYPE = ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE;
  private static final String EMAIL_ITEM_TYPE = ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE;
  private static final int EMAIL_TYPE_HOME = ContactsContract.CommonDataKinds.Email.TYPE_HOME;
  private static final int EMAIL_TYPE_WORK = ContactsContract.CommonDataKinds.Email.TYPE_WORK;
  private static final String PHONE_ITEM_TYPE = ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE;
  private static final int PHONE_TYPE_HOME = ContactsContract.CommonDataKinds.Phone.TYPE_HOME;
  private static final int PHONE_TYPE_MOBILE = ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE;
  private static final String AUTHORITY = ContactsContract.AUTHORITY;

  private SendReceiver sendReceiver = new SendReceiver();
  private DeliverReceiver deliverReceiver = new DeliverReceiver();


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_content_provider);

    Button readConts = (Button) findViewById(R.id.readContacts);
    Button writeConts = (Button) findViewById(R.id.writeContacts);
    Button readSms = (Button) findViewById(R.id.readSMS);
    Button sendSms = (Button) findViewById(R.id.sendSMS);
    Button myProvider = (Button) findViewById(R.id.myProvider);

    readConts.setOnClickListener(this);
    writeConts.setOnClickListener(this);
    readSms.setOnClickListener(this);
    sendSms.setOnClickListener(this);
    myProvider.setOnClickListener(this);

    //注册发送成功的广播
    registerReceiver(sendReceiver, new IntentFilter("SENT_SMS_ACTION"));
    //注册接收成功的广播
    registerReceiver(deliverReceiver, new IntentFilter("DELIVERED_SMS_ACTION"));


  }

  @Override
  public void onClick(View v) {

    switch (v.getId()) {
      case R.id.readContacts:
        testReadContacts();
        break;
      case R.id.writeContacts:
        testWriteContacts();
        break;
      case R.id.readSMS:
        testReadConversation();
        testReadSMS();
        break;
      case R.id.sendSMS:
        sendSMS("15112668312","I am LMR,and this is my test.you may check your WeChat now, please !");
        break;
      case R.id.myProvider:
        Intent i=new Intent(getApplicationContext(),TestMyProviderActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        break;
      default:
        break;
    }

  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    unregisterReceiver(sendReceiver);
    unregisterReceiver(deliverReceiver);

  }
  public void testReadContacts() {
    /**不仅可以使用ContentResolver直接进行读取操作（即查询），
     * 还可以使用Activity提供的managedQuery方法方便的实现同样的效果*/
    ContentResolver resolver = getApplicationContext().getContentResolver();
    Cursor c = resolver.query(CONTACTS_URI, null, null, null, null);
    while (c.moveToNext()) {
      int _id = c.getInt(c.getColumnIndex(_ID));
      String displayName = c.getString(c.getColumnIndex(DISPLAY_NAME));

      Log.i(TAG, displayName);

      ArrayList<String> phones = new ArrayList<String>();
      ArrayList<String> emails = new ArrayList<String>();

      String selection = CONTACT_ID + "=" + _id;  //the 'where' clause

      //获取手机号
      int hasPhoneNumber = c.getInt(c.getColumnIndex(HAS_PHONE_NUMBER));
      if (hasPhoneNumber > 0) {
        Cursor phc = resolver.query(PHONES_URI, null, selection, null, null);
        while (phc.moveToNext()) {
          String phoneNumber = phc.getString(phc.getColumnIndex(PHONE_NUMBER));
          int phoneType = phc.getInt(phc.getColumnIndex(PHONE_TYPE));
          phones.add(getPhoneTypeNameById(phoneType) + " : " + phoneNumber);
        }
        phc.close();
      }

      Log.i(TAG, "phones: " + phones);

      //获取邮箱
      Cursor emc = resolver.query(EMAIL_URI, null, selection, null, null);
      while (emc.moveToNext()) {
        String emailData = emc.getString(emc.getColumnIndex(EMAIL_DATA));
        int emailType = emc.getInt(emc.getColumnIndex(EMAIL_TYPE));
        emails.add(getEmailTypeNameById(emailType) + " : " + emailData);
      }
      emc.close();

      Log.i(TAG, "emails: " + emails);
    }
    c.close();
  }

  private String getPhoneTypeNameById(int typeId) {
    switch (typeId) {
      case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
        return "home";
      case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
        return "mobile";
      case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
        return "work";
      default:
        return "none";
    }
  }

  private String getEmailTypeNameById(int typeId) {
    switch (typeId) {
      case ContactsContract.CommonDataKinds.Email.TYPE_HOME:
        return "home";
      case ContactsContract.CommonDataKinds.Email.TYPE_WORK:
        return "work";
      case ContactsContract.CommonDataKinds.Email.TYPE_OTHER:
        return "other";
      default:
        return "none";
    }
  }

  public void testWriteContacts(){
    ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();

    ContentProviderOperation operation = ContentProviderOperation.newInsert(RAW_CONTACTS_URI)
        .withValue(ACCOUNT_TYPE, null)
        .withValue(ACCOUNT_NAME, null)
        .build();
    operations.add(operation);

    //添加联系人名称操作
    operation = ContentProviderOperation.newInsert(DATA_URI)
        .withValueBackReference(RAW_CONTACT_ID, 0)
        .withValue(MIMETYPE, NAME_ITEM_TYPE)
        .withValue(DISPLAY_NAME, "Mandy Li")
        .build();
    operations.add(operation);

    //添加家庭座机号码
    operation = ContentProviderOperation.newInsert(DATA_URI)
        .withValueBackReference(RAW_CONTACT_ID, 0)
        .withValue(MIMETYPE, PHONE_ITEM_TYPE)
        .withValue(PHONE_TYPE, PHONE_TYPE_HOME)
        .withValue(PHONE_NUMBER, "01034567890")
        .build();
    operations.add(operation);

    //添加移动手机号码
    operation = ContentProviderOperation.newInsert(DATA_URI)
        .withValueBackReference(RAW_CONTACT_ID, 0)
        .withValue(MIMETYPE, PHONE_ITEM_TYPE)
        .withValue(PHONE_TYPE, PHONE_TYPE_MOBILE)
        .withValue(PHONE_NUMBER, "13034567890")
        .build();
    operations.add(operation);

    //添加家庭邮箱
    operation = ContentProviderOperation.newInsert(DATA_URI)
        .withValueBackReference(RAW_CONTACT_ID, 0)
        .withValue(MIMETYPE, EMAIL_ITEM_TYPE)
        .withValue(EMAIL_TYPE, EMAIL_TYPE_HOME)
        .withValue(EMAIL_DATA, "scott@android.com")
        .build();
    operations.add(operation);

    //添加工作邮箱
    operation = ContentProviderOperation.newInsert(DATA_URI)
        .withValueBackReference(RAW_CONTACT_ID, 0)
        .withValue(MIMETYPE, EMAIL_ITEM_TYPE)
        .withValue(EMAIL_TYPE, EMAIL_TYPE_WORK)
        .withValue(EMAIL_DATA, "scott@msapple.com")
        .build();
    operations.add(operation);

    ContentResolver resolver = getApplicationContext().getContentResolver();
    //批量执行,返回执行结果集
   try {
     ContentProviderResult[] results = resolver.applyBatch(AUTHORITY, operations);
     for (ContentProviderResult result : results) {
       Log.i(TAG, result.uri.toString());
     }
   }catch (Exception e){
     e.printStackTrace();
   }

  }

  /**读写短消息主要涉及两张表：两张表分别是threads表和sms表，前者代表所有会话信息，每个会话代表与一个联系人之间短信的群组；
   * 后者代表短信的具体信息。在sms表中的thread_id指向了threads表中的_id，指定每条短信的会话id，以便对短信进行分组。
   * 下面介绍一下表中的每个字段的意义：
   * threads表：_id字段表示该会话id；date表示该会话最后一条短信的日期，一般用来对多个会话排序显示；
   * message_count表示该会话所包含的短信数量；snippet表示该会话中最后一条短信的内容；
   * read表示该会话是否已读（0：未读，1：已读），一般来说该会话中有了新短信但没查看时，该会话read变为未读状态，当查看过新短信后read就变为已读状态。
   * sms表：_id表示该短信的id；thread_id表示该短信所属的会话的id；date表示该短信的日期；
   * read表示该短信是否已读；type表示该短信的类型，例如1表示接收类型，2表示发送类型，3表示草稿类型；body表示短信的内容
   * */
  //会话
  private static final String CONVERSATIONS = "content://sms/conversations/";
  //查询联系人
  private static final String CONTACTS_LOOKUP = "content://com.android.contacts/phone_lookup/";
  //全部短信
  private static final String SMS_ALL = "content://sms/";
  //收件箱
  //  private static final String SMS_INBOX = "content://sms/inbox";
  //已发送
  //  private static final String SMS_SENT  = "content://sms/sent";
  //草稿箱
  //  private static final String SMS_DRAFT = "content://sms/draft";
  //发送失败箱
  //private static final String SMS_FAILED = ""content://sms/failed"";

  private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  /**
   * 读取会话信息
   */
  public void testReadConversation() {
    ContentResolver resolver =getApplicationContext().getContentResolver();
    Uri uri = Uri.parse(CONVERSATIONS);
    String[] projection = new String[]{"groups.group_thread_id AS group_id", "groups.msg_count AS msg_count",
        "groups.group_date AS last_date", "sms.body AS last_msg", "sms.address AS contact"};
    Cursor thinc = resolver.query(uri, projection, null, null, "groups.group_date DESC");   //查询并按日期倒序
    Cursor richc = new CursorWrapper(thinc) {   //对Cursor进行处理,遇到号码后获取对应的联系人名称
      @Override
      public String getString(int columnIndex) {
        if (super.getColumnIndex("contact") == columnIndex) {
          String contact = super.getString(columnIndex);
          //读取联系人,查询对应的名称
          Uri uri = Uri.parse(CONTACTS_LOOKUP + contact);
          Cursor cursor = getApplicationContext().getContentResolver().query(uri, null, null, null, null);
          if (cursor.moveToFirst()) {
            String contactName = cursor.getString(cursor.getColumnIndex("display_name"));
            return contactName;
          }
          return contact;
        }
        return super.getString(columnIndex);
      }
    };
    while (richc.moveToNext()) {
      String groupId = "groupId: " + richc.getInt(richc.getColumnIndex("group_id"));
      String msgCount = "msgCount: " + richc.getLong(richc.getColumnIndex("msg_count"));
      String lastMsg = "lastMsg: " + richc.getString(richc.getColumnIndex("last_msg"));
      String contact = "contact: " + richc.getString(richc.getColumnIndex("contact"));
      String lastDate = "lastDate: " + dateFormat.format(richc.getLong(richc.getColumnIndex("last_date")));

      printLog(groupId, contact, msgCount, lastMsg, lastDate, "---------------END---------------");
    }
    richc.close();
  }

  /**
   * 读取短信
   */
  public void testReadSMS() {
    ContentResolver resolver = getApplicationContext().getContentResolver();
    Uri uri = Uri.parse(SMS_ALL);
    String[] projection = {"thread_id AS group_id", "address AS contact", "body AS msg_content", "date", "type"};
    Cursor c = resolver.query(uri, projection, null, null, "date DESC");    //查询并按日期倒序
    while (c.moveToNext()) {
      String groupId = "groupId: " + c.getInt(c.getColumnIndex("group_id"));
      String contact = "contact: " + c.getString(c.getColumnIndex("contact"));
      String msgContent = "msgContent: " + c.getString(c.getColumnIndex("msg_content"));
      String date = "date: " + dateFormat.format(c.getLong(c.getColumnIndex("date")));
      String type = "type: " + getTypeById(c.getInt(c.getColumnIndex("type")));

      printLog(groupId, contact, msgContent, date, type, "---------------END---------------");
    }
    c.close();
  }

  private String getTypeById(int typeId) {
    switch (typeId) {
      case 1:
        return "receive";
      case 2:
        return "send";
      case 3:
        return "draft";
      default:
        return "none";
    }
  }

  private void printLog(String... strings) {
    for (String s : strings) {
      Log.i(TAG, s == null ? "NULL" : s);
    }
  }

  public void sendSMS(String address,String body) {

    //android.telephony.SmsManager, not [android.telephony.gsm.SmsManager]
    SmsManager smsManager = SmsManager.getDefault();
    //短信发送成功或失败后会产生一条SENT_SMS_ACTION的广播
    PendingIntent sendIntent = PendingIntent.getBroadcast(this, 0, new Intent("SENT_SMS_ACTION"), 0);
    //接收方成功收到短信后,发送方会产生一条DELIVERED_SMS_ACTION广播
    PendingIntent deliveryIntent = PendingIntent.getBroadcast(this, 0, new Intent("DELIVERED_SMS_ACTION"), 0);
    if (body.length() > 70) {    //如果字数超过70,需拆分成多条短信发送
      List<String> msgs = smsManager.divideMessage(body);
      for (String msg : msgs) {
        smsManager.sendTextMessage(address, null, msg, sendIntent, deliveryIntent);
      }
    } else {
      smsManager.sendTextMessage(address, null, body, sendIntent, deliveryIntent);
    }

    //写入到短信数据源
    ContentValues values = new ContentValues();
    values.put("address", address);  //发送地址
    values.put("body", body);   //消息内容
    values.put("date", System.currentTimeMillis()); //创建时间
    values.put("read", 0);  //0:未读;1:已读
    values.put("type", 2);  //1:接收;2:发送
    getContentResolver().insert(Uri.parse("content://sms/sent"), values);   //插入数据
  }

  private class SendReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
      switch (getResultCode()) {
        case Activity.RESULT_OK:
          Toast.makeText(context, "Sent Successfully.", Toast.LENGTH_SHORT).show();
          break;
        default:
          Toast.makeText(context, "Failed to Send.", Toast.LENGTH_SHORT).show();
      }
    }
  }

  /**
   * 发送方的短信发送到对方手机上之后,对方手机会返回给运营商一个信号,
   * 运营商再把这个信号发给发送方,发送方此时可确认对方接收成功
   * 模拟器不支持,真机上需等待片刻
   *
   * @author user
   */
  private class DeliverReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
      Toast.makeText(context, "Delivered Successfully.", Toast.LENGTH_SHORT).show();
    }
  }

  /**
   * 删除收件箱里特定内容的短信
   * */
  public  void deleteSMSInbox(Context context, String num,
                                    String content) {
    Cursor cursor = null;
    try {
      ContentResolver CR = context.getContentResolver();
      Uri uri = Uri.parse("content://sms/inbox");// 收信箱
      String[] projection = new String[] { "_id", "address", "person",
          "body", "date", "type", "thread_id" };
      String where = " address = ? and body like ?";
      String args[] = new String[] { num, "%" + content + "%" };
      // 查询收信箱里所有的短信
      cursor = CR.query(uri, projection, where, args, "date desc");
      if (cursor != null && cursor.moveToFirst()) {
        do {
          long id = cursor.getInt(cursor.getColumnIndex("_id")); // 获取的是行号，即thread_id(1233)
          CR.delete(Uri.parse("content://sms/" + id), null, null);
          Log.e(TAG, "delete sms/inbox,id: " + id);

        } while (cursor.moveToNext());
      }
      cursor.close();

    } catch (Exception e) {
      Log.e(TAG, "deleteSMSinbox-Exception:: " + e);
    } finally {
      if (cursor != null) {
        try {
          cursor.close();
        } catch (Exception e) {
        }
      }
    }
    Log.d(TAG, "deleteSMSInbox!");
  }

  /**
   * 删除收件箱里过期的所有短信
   * */
  public void deleteSMSInboxOverTime(Context context, int delay) {//delay为天数

    if (delay < 0) {
      Log.e(TAG, "the second param is not a postive number !");
      return;
    }
    Cursor cursor = null;
    try {
      ContentResolver CR = context.getContentResolver();
      Uri uri = Uri.parse("content://sms/inbox");// 收信箱
      String[] projection = new String[] { "_id", "address", "person",
          "body", "date", "type", "thread_id" };
      Date d = new Date();
      long time = d.getTime() - delay * 24 * 3600;
      // String where = " address = '+86"+num+"'";
      // 查询收信箱里所有的短信
      cursor = CR.query(uri, projection, null, null, "date desc");
      if (cursor != null && cursor.moveToFirst()) {
        do {
          long id = cursor.getInt(cursor.getColumnIndex("_id")); // 获取的是行号，即thread_id(1233)
          long date = cursor.getLong(cursor
              .getColumnIndexOrThrow("date"));
          if (time > date) {// 超过期限则删除

            CR.delete(Uri.parse("content://sms/" + id), null, null);
            Log.e(TAG, "delete inbox over delay, _id: " + id);
          }

        } while (cursor.moveToNext());

      }
      cursor.close();
    } catch (Exception e) {
      Log.e(TAG, "deleteSMSinbox-Exception:" + e);
    } finally {
      if (cursor != null) {
        try {
          cursor.close();
        } catch (Exception e) {
        }
      }
    }
    Log.e(TAG, "deleteSMSInboxOverTime");
  }
  /**
   * 删除已发送特定号码发送人的所有短信
   * */
  public  void deleteSMSSent(Context context, String num) {
    Cursor cursor = null;
    try {
      ContentResolver CR = context.getContentResolver();
      Uri uri = Uri.parse("content://sms/sent");// 已发送（成功的）
      String[] projection = new String[] { "_id", "address", "person",
          "body", "date", "type", "thread_id" };
      String where = " address like '%" + num + "'";
      // 查询收信箱里所有的短信
      cursor = CR.query(uri, projection, where, null, "date desc");
      if (cursor != null && cursor.moveToFirst()) {
        do {
          int a = cursor.getCount();
          int b = cursor.getColumnCount();
          long id = cursor.getLong(0);// 获取的是address的值（即8615891768372）
          id = cursor.getInt(cursor.getColumnIndex("_id")); // 获取的是行号，即thread_id(1233)
          Log.e(TAG, "a:" + a + "b:" + b + "_id:" + id);
          CR.delete(Uri.parse("content://sms/" + id), null, null);

        } while (cursor.moveToNext());
      }
      cursor.close();

    } catch (Exception e) {Log.e(TAG, "deleteSMSsent-Exception:: " + e);
    }finally {
      if (cursor != null) {
        try {
          cursor.close();
        } catch (Exception e) {
        }
      }
    }
    Log.e(TAG, "deleteSMSSent-num");
  }

}
