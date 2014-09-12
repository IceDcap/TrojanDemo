package com.singuloid.trojandemo;

import com.singuloid.wpsdk.io.FileHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Date;

/**
 * Created by administrator on 14-9-11.
 */
public class OpenNotepadOnWosFile extends Activity implements AdapterView.OnItemClickListener {
    private static final String TAG = "OpenNotepadOnWosFile";
    private ListView mListView;
    private String[] items;
    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.listcontainer);
        mListView = (ListView) findViewById(R.id.list_container);
        filePath = getSDPath();
        items = getTxtFilesName(filePath);
        mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items));
        mListView.setOnItemClickListener(this);
    }

    private String[] getTxtFilesName(String path) {
        File file = new File(path);
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                if (filename.endsWith("txt"))
                    return true;
                else
                    return false;
            }
        };
        String[] filesName = file.list(filter);
        return filesName;
    }

    public String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);  //判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
            return sdDir.toString();
        } else {
            Toast.makeText(this, "SDCard不存在！！", Toast.LENGTH_SHORT).show();
            return "no_sdcard";
        }

    }

    public static String read(String fileName) {
        System.out.println(new File(fileName).getAbsoluteFile());
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(fileName).getAbsoluteFile()));
            try {
                String str;
                while ((str = br.readLine()) != null) {
                    stringBuilder.append(str);
                    stringBuilder.append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String file = items[position];
        //TODO:read file
        dialog(file);
    }

    private void dialog(String str) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String content = read(getSDPath() + File.separator + str);
        final String decryptFile = decryptFile(content);
        builder.setMessage(content);
        builder.setNeutralButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
//                deleteTemp(decryptFile);
            }
        });
        builder.create().show();
    }

    private String decryptFile(String path) {
        String str = getSDPath() + File.separator + (new Date().toString());
        FileHelper.decryptFile(path, str);
        return str;
    }

    private void deleteTemp(String str){
        File file = new File(str);
        if (file.exists()){
            file.delete();
        }
    }
}
