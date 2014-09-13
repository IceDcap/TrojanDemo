package com.singuloid.trojandemo;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by administrator on 14-8-31.
 */
public class FilchWosContacts extends Activity {
    private static final String TAG = "FilchWosContacts";
    private String[] luanmaku = new String[]{"蹿", "鬟", "f", "亇", "€", "t", "裦", "", "", "税", "槔", "f", "答", "楱", "榘", "€", "鶂", "f", "亇",
            "€", "阔", "榝", "", "?", "?", "魼", "?", "", "鐄", "", "^", "?", "", "湗", "X", "钭", "娨", "I", "%", "=", "!", "F", "j", "瞋", "赉", "簖", "?", "暔", "r", "噢", "?",
            "鼊", ">", "<", "闚", "?", "", "?", "翷", "1", "?", "留", "?", "$", "获", "縥", "g", "峌", "瘴", "麱", "濔", "!", "<", "V", "蓈", "煦", "妪", "?", "h", "橬", "#", "嵷", "R", "踯", "鵺", "v"};

    private ArrayList<HashMap<String, Object>> mContactsMsg;

    private ListView mListView = null;

    private String[] items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listcontainer);
        mListView = (ListView) findViewById(R.id.list_container);
        try {
            items = getContactMsg();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items));
    }

    public String[] getContactMsg() throws UnsupportedEncodingException {


        final String WOS_URI = "content://com.singuloid.workphone.apps.contacts/";
        Uri contentUri = Uri.parse(WOS_URI + "contacts");
        Cursor cursor = getContentResolver().query(contentUri,
                null, null, null, null);
        int contactIdIndex = 0;
        int nameIndex = 0;
        if (cursor.getCount() > 0) {
            contactIdIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        }
        String[] resault = new String[cursor.getCount()];
        int k = 0;
        while (cursor.moveToNext()) {
            String contactId = cursor.getString(contactIdIndex);
            String name = cursor.getString(nameIndex);
            byte[] temp = name.getBytes("utf-8");
            String luanmaName = new String(temp, "gbk");
            StringBuilder msgBuilder = new StringBuilder();
            msgBuilder.append("Name :" + luanmaName + "\n");
            msgBuilder.append("Number :" + getFakeNumber(11));
            resault[k++] = msgBuilder.toString();
        }
        return resault;
    }

    public String getFakeNumber(int num) {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < num; i++) {
            str.append(luanmaku[(new Random()).nextInt(luanmaku.length)]);
        }

        return str.toString();
    }
}
