package com.singuloid.trojandemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.singuloid.trojandemo.utils.Utils;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private ImageButton mContacts, mContacts2, mText, mText2, mMail, mMail2, mCallRecords, mCallRecords2, mTxt, mTxt2;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initView();
    }

    private void initView() {
        mContacts = (ImageButton) findViewById(R.id.contacts);
        mContacts2 = (ImageButton) findViewById(R.id.contacts2);
        mText = (ImageButton) findViewById(R.id.text);
        mText2 = (ImageButton) findViewById(R.id.text2);
        mMail = (ImageButton) findViewById(R.id.mail);
        mCallRecords = (ImageButton) findViewById(R.id.call_records);
        mContacts.setOnClickListener(this);
        mContacts2.setOnClickListener(this);
        mText.setOnClickListener(this);
        mText2.setOnClickListener(this);
        mMail.setOnClickListener(this);
        mCallRecords.setOnClickListener(this);
        mMail2 = (ImageButton) findViewById(R.id.mail2);
        mMail2.setOnClickListener(this);
        mCallRecords2 = (ImageButton) findViewById(R.id.call_records2);
        mCallRecords2.setOnClickListener(this);
        mTxt = (ImageButton) findViewById(R.id.txt);
        mTxt.setOnClickListener(this);
        mTxt2 = (ImageButton) findViewById(R.id.txt2);
        mTxt2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent textIntent = new Intent(this, FilchText.class);
        switch (v.getId()) {
            case R.id.contacts:
                startActivity(new Intent(this, FilchContacts.class));
                break;
            case R.id.contacts2:
                startActivity(new Intent(this, FilchWosContacts.class));
//                dialog("联系人");
                break;
            case R.id.text:
                textIntent.putExtra("isWorkPhone", false);
                startActivity(textIntent);
                break;
            case R.id.text2:
                textIntent.putExtra("isWorkPhone", true);
                startActivity(textIntent);
//                dialog("短信");
                break;
            case R.id.mail:
                Intent mailIntent = new Intent(this, FilchFiles.class);
                mailIntent.putExtra("type", "mail");
                startActivity(mailIntent);
                break;
            case R.id.mail2:
                Intent mailIntent2 = new Intent(this, FilchFiles.class);
                mailIntent2.putExtra("type", "mail");
                startActivity(mailIntent2);
                break;
            case R.id.call_records:
                Intent call_intent = new Intent(this, FilchCallRecords.class);
                call_intent.putExtra("isWorkPhone", false);
                startActivity(call_intent);
                break;
            case R.id.call_records2:
                Intent call_intent2 = new Intent(this, FilchCallRecords.class);
                call_intent2.putExtra("isWorkPhone", true);
                startActivity(call_intent2);
                break;
            case R.id.txt:
                Intent txtIntent = new Intent(this, FilchFiles.class);
                txtIntent.putExtra("type", "txt");
                startActivity(txtIntent);
                break;
            case R.id.txt2:
                startActivity(new Intent(this, OpenNotepadOnWosFile.class));
                break;
            case R.id.renren:
                try {
                    Utils.copyRenrenDbToMe();
                    SQLiteDatabase db = SQLiteDatabase.openDatabase("/sdcard/account.db", null, SQLiteDatabase.OPEN_READONLY);
//                SQLiteDatabase db = openOrCreateDatabase("/sdcard/account.db", this.MODE_PRIVATE ,null);
                    String str = getMessage(db);
                    msgDialog(str);
                } catch (Exception e) {
                    dialog("数据库");
                }

                break;
        }
    }

    private String getMessage(SQLiteDatabase db) {
        String sql = "select * from account";
        cursor = db.rawQuery(sql, null);

//        Cursor cursor = db.query("account", new String[]{"uid", "account", "name", "last_login_time"}, null, null, null, null, null);
        StringBuilder msgBuilder = new StringBuilder();
        if (cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.move(i);
                String uid = cursor.getString(cursor.getColumnIndex("uid"));
                String account = cursor.getString(cursor.getColumnIndex("account"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String last_login_time = cursor.getString(cursor.getColumnIndex("last_login_time"));
                Log.e(TAG, uid + account + name + last_login_time);
                msgBuilder.append("[ ");
                msgBuilder.append("uid = " + uid + ", ");
                msgBuilder.append("account = " + account + ", ");
                msgBuilder.append("name = " + name + ", ");
                msgBuilder.append("last_login_time = " + last_login_time + ", ");
                msgBuilder.append(" ]\n\n");
            }
            return msgBuilder.toString();
        } else return "no message to show!";
    }

    private void dialog(String str) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ERROR");
        builder.setMessage("打开" + str + "时发生错误:" + " files is encrypted or database can not be opened");
        builder.setNeutralButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void msgDialog(String str) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("MESSAGE");
        builder.setMessage(str);
        builder.setNeutralButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cursor != null) {
            cursor.close();
        }
    }
}
