package com.myuidemo.friendUI;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.myuidemo.base.R;

public class DialogActivity extends Activity implements View.OnClickListener {

    static final int PROGRESS_DIALOG = 0;
    Button button;
    ProgressThread progressThread;
    ProgressDialog progressDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dialog);

    findViewById(R.id.alertWithButton).setOnClickListener(this);
    findViewById(R.id.alertWithList).setOnClickListener(this);
    findViewById(R.id.alertWithChoicebox).setOnClickListener(this);
    button = (Button) findViewById(R.id.progressDlg);
    button.setOnClickListener(this);
    findViewById(R.id.myCustomDlg).setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.alertWithButton:
        AlertDialog.Builder builder = new AlertDialog.Builder(DialogActivity.this);
        builder.setMessage("Are you sure you want to exit?")
            .setCancelable(false)
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                DialogActivity.this.finish();
              }
            })
            .setNegativeButton("No", new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
              }
            });
        AlertDialog alert = builder.create();
        alert.show();
        break;
      case R.id.alertWithList:
        final CharSequence[] items={"Red","Green","Blue"};
        AlertDialog.Builder builder1=new AlertDialog.Builder(DialogActivity.this);
        builder1.setTitle("Pick a color");
        builder1.setItems(items, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            Toast.makeText(getApplicationContext(),items[i],Toast.LENGTH_LONG).show();
          }
        });
        AlertDialog alertDialog=builder1.create();
        alertDialog.show();

        break;
      case R.id.alertWithChoicebox:
        final CharSequence[] items2={"Red","Green","Blue"};
        AlertDialog.Builder builder2=new AlertDialog.Builder(DialogActivity.this);
        builder2.setTitle("Pick a color");
        //多选-setMultiChoiceItems()
        builder2.setSingleChoiceItems(items2,-1, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            Toast.makeText(getApplicationContext(),items2[i],Toast.LENGTH_LONG).show();
          }
        });
        AlertDialog alertDialog2=builder2.create();
        alertDialog2.show();

        break;
      case R.id.progressDlg:
        showDialog(PROGRESS_DIALOG);
        break;
      case R.id.myCustomDlg:
        AlertDialog.Builder builder3;
        AlertDialog alertDialog3;
        Context mContext = DialogActivity.this;//不能为getApplicationContext，报错：android.view.WindowManager$BadTokenException: Unable to add window
        LayoutInflater inflater = getLayoutInflater();//或者mContext.getSystemService(LAYOUT_INFLATER_SERVICE);取得LayoutInflater
        View layout = inflater.inflate(R.layout.toast_custom,
            (ViewGroup) findViewById(R.id.llToast));
        ImageView image = (ImageView) layout
            .findViewById(R.id.tvImageToast);
        image.setImageResource(R.drawable.i2);
        TextView title = (TextView) layout.findViewById(R.id.tvTitleToast);
        title.setText("Attention");
        TextView text = (TextView) layout.findViewById(R.id.tvTextToast);
        text.setText("Hello, this is a custom dialog !");
        builder3=new AlertDialog.Builder(mContext);
        builder3.setView(layout);
        builder3.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
                         DialogActivity.this.finish();
                      }
        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
       });

        alertDialog3=builder3.create();
        alertDialog3.show();

        break;
    }
  }

  protected Dialog onCreateDialog(int id){
    switch (id){
      case PROGRESS_DIALOG:
        progressDialog=new ProgressDialog(DialogActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage("Loading...");
        progressThread=new ProgressThread(handler);
        progressThread.start();
        return progressDialog;
      default:
          return null;
    }

  }

  final Handler handler=new Handler(){
    public void handleMessage(Message msg){
      int total = msg.getData().getInt("total");
      progressDialog.setProgress(total);
      if(total>=100){
        dismissDialog(PROGRESS_DIALOG);
        progressThread.setState(ProgressThread.STATE_DONE);
      }
    }

  };

  private class ProgressThread extends Thread{
    Handler mHandler;
    final static int STATE_DONE =0;
    final static int STATE_RUNNING=1;
    int mState;
    int total;
    ProgressThread(Handler h){
      mHandler = h;

    }

    public void run(){
      mState=STATE_RUNNING;
      total=0;
      while(mState==STATE_RUNNING){
        try{
          Thread.sleep(100);
        }catch (InterruptedException e){
          Log.e("ERROR","Thread Interrupted");
        }
        Message msg=mHandler.obtainMessage();
        Bundle b=new Bundle();
        b.putInt("total",total);
        msg.setData(b);
        mHandler.sendMessage(msg);
        total++;
      }
    }
   /* sets the current state for the thread,
    * used to stop the thread */
    public void setState(int state){
      mState = state;
    }
  }
}
