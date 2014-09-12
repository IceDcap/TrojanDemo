package com.singuloid.trojandemo.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import java.io.File;

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
}
