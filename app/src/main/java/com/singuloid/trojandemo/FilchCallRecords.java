package com.singuloid.trojandemo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

import com.singuloid.trojandemo.utils.Utils;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by administrator on 14-9-10.
 */
public class FilchCallRecords extends Activity {
    private static final String TAG = "FilchCallRecords";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        Intent intent = getIntent();
        boolean isWorkPhone = intent.getBooleanExtra("isWorkPhone", false);
        String str = "";
        if (isWorkPhone) {
            try {
                String s = getRecords(isWorkPhone);
                byte[] temp = s.getBytes("utf-8");
                str = new String(temp, "gbk");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else str = getRecords(isWorkPhone);
        tv.setText(str);
        ScrollView sv = new ScrollView(this);
        sv.addView(tv);
        setContentView(sv);
    }

    private String getRecords(boolean isWos) {
        StringBuilder callRecordsBuilder = new StringBuilder();
        Log.e(TAG, "CallLog.Calls.CONTENT_URI = " + CallLog.Calls.CONTENT_URI);
        Uri uri;
        if (isWos){
            final String wosUri = "content://wos_call_log/calls";
            uri = Uri.parse(wosUri);
        }else uri = Uri.parse("content://call_log/calls");
        Cursor cursor = this.getContentResolver().query(uri,
                null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
//                CallsLog calls =new CallsLog();
                //号码
                String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                if (isWos) number = Utils.getFakeNumber(11);
                //呼叫类型
                String type;
                switch (Integer.parseInt(cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE)))) {
                    case CallLog.Calls.INCOMING_TYPE:
                        type = "呼入";
                        break;
                    case CallLog.Calls.OUTGOING_TYPE:
                        type = "呼出";
                        break;
                    case CallLog.Calls.MISSED_TYPE:
                        type = "未接";
                        break;
                    default:
                        type = "挂断";//应该是挂断.根据我手机类型判断出的
                        break;
                }
                SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.DATE))));
                //呼叫时间
                String time = sfd.format(date);
                if (isWos) time = Utils.getFakeNumber(19);
                //联系人
                String name = cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.CACHED_NAME));
                if (name == null || name.equals("")) name = "无名氏";

                //通话时间,单位:s
                String duration = cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.DURATION));
                callRecordsBuilder.append("[ ");
                callRecordsBuilder.append(type + ", ");
                callRecordsBuilder.append(number + ", ");
                callRecordsBuilder.append(time + ", ");
                callRecordsBuilder.append(name + ", ");
                callRecordsBuilder.append(duration + "s");
                callRecordsBuilder.append(" ]\n\n");

            } while (cursor.moveToNext());
            if (!cursor.isClosed()) {
                cursor.close();
                cursor = null;
            }
        } else callRecordsBuilder.append("no result!");

        return callRecordsBuilder.toString();
    }
}
