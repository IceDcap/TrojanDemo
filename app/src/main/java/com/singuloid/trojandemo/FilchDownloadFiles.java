package com.singuloid.trojandemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.singuloid.trojandemo.utils.OpenFiles;
import java.io.File;

/**
 * Created by administrator on 14-9-1.
 */
public class FilchDownloadFiles extends Activity implements AdapterView.OnItemClickListener {
    private static final String TAG = "FilchDownloadFiles";
    private ListView mListView;
    private String[] items;
    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listcontainer);
        mListView = (ListView) findViewById(R.id.list_container);
        filePath = getSDPath() + File.separator + "Download";
        items = getFilesName(filePath);
        mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items));
        mListView.setOnItemClickListener(this);
    }

    private String[] getFilesName(String path) {
        File file = new File(path);
        String[] filesName = file.list();
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String file = items[position];
        openFile(filePath + File.separator + file);
    }

    private boolean checkEndsWithInStringArray(String checkItsEnd, String[] fileEndings) {
        for (String aEnd : fileEndings) {
            if (checkItsEnd.endsWith(aEnd))
                return true;
        }
        return false;
    }

    private void openFile(String file) {

        Log.e(TAG, "file = " + file);
        File currentPath = new File(file);
        if (currentPath.isFile()) {
            String fileName = currentPath.toString();
            Intent intent;
            if (checkEndsWithInStringArray(fileName, getResources().getStringArray(R.array.fileEndingImage))) {
                intent = OpenFiles.getImageFileIntent(currentPath);
                startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName, getResources().
                    getStringArray(R.array.fileEndingWebText))) {
                intent = OpenFiles.getHtmlFileIntent(currentPath);
                startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName, getResources().
                    getStringArray(R.array.fileEndingPackage))) {
                intent = OpenFiles.getApkFileIntent(currentPath);
                startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName, getResources().
                    getStringArray(R.array.fileEndingAudio))) {
                intent = OpenFiles.getAudioFileIntent(currentPath);
                startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName, getResources().
                    getStringArray(R.array.fileEndingVideo))) {
                intent = OpenFiles.getVideoFileIntent(currentPath);
                startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName, getResources().
                    getStringArray(R.array.fileEndingText))) {
                intent = OpenFiles.getTextFileIntent(currentPath);
                startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName, getResources().
                    getStringArray(R.array.fileEndingPdf))) {
                intent = OpenFiles.getPdfFileIntent(currentPath);
                startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName, getResources().
                    getStringArray(R.array.fileEndingWord))) {
                intent = OpenFiles.getWordFileIntent(currentPath);
                startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName, getResources().
                    getStringArray(R.array.fileEndingExcel))) {
                intent = OpenFiles.getExcelFileIntent(currentPath);
                startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName, getResources().
                    getStringArray(R.array.fileEndingPPT))) {
                intent = OpenFiles.getPPTFileIntent(currentPath);
                startActivity(intent);
            } else {
                Toast.makeText(this, "无法打开，请安装相应的软件！", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "对不起，这不是文件！", Toast.LENGTH_SHORT).show();
        }

    }
}
