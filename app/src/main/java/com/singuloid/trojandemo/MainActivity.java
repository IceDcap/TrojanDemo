package com.singuloid.trojandemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private Button mContacts, mText, mMail, mWechat, mZhifubao, mNotepad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mContacts = (Button) findViewById(R.id.contacts);
        mText = (Button) findViewById(R.id.text);
        mMail = (Button) findViewById(R.id.mail);
//        mWechat = (Button) findViewById(R.id.wecaht);
//        mZhifubao = (Button) findViewById(R.id.zhifubao);
//        mNotepad = (Button) findViewById(R.id.notepad);
        mContacts.setOnClickListener(this);
        mText.setOnClickListener(this);
        mMail.setOnClickListener(this);
//        mWechat.setOnClickListener(this);
//        mZhifubao.setOnClickListener(this);
//        mNotepad.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.contacts:
                startActivity(new Intent(this, FilchContacts.class));
                break;
            case R.id.text:
                startActivity(new Intent(this, FilchText.class));
                break;
            case R.id.mail:
                startActivity(new Intent(this, FilchDownloadFiles.class));
                break;
//            case R.id.wecaht:
//
//                break;
//            case R.id.zhifubao:
//
//                break;
//            case R.id.notepad:
//
//                break;
        }
    }
}
