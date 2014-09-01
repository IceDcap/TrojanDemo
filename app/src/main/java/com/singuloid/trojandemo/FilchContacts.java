package com.singuloid.trojandemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by administrator on 14-8-31.
 */
public class FilchContacts extends Activity {
    private static final String TAG = "FilchContacts";


    private ArrayList<HashMap<String, Object>> mContactsMsg;

    private ListView lv = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listcontainer);
        Log.e(TAG, ContactsContract.CommonDataKinds.Email.CONTACT_ID);
        lv = (ListView) findViewById(R.id.list_container);
        lv.setAdapter(new MyAdapter(this));

    }


    private ArrayList<HashMap<String, Object>> getPhoneContacts2() {
        if (mContactsMsg == null) {
            mContactsMsg = new ArrayList<HashMap<String, Object>>();

            Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                    null, null, null, null);
            int contactIdIndex = 0;
            int nameIndex = 0;
            HashMap<String, Object> contactMap = null;
            if (cursor.getCount() > 0) {

                contactIdIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);
                nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            }
            while (cursor.moveToNext()) {
                String contactId = cursor.getString(contactIdIndex);
                String name = cursor.getString(nameIndex);
                contactMap = new HashMap<String, Object>();
                contactMap.put("name", name);

            /*
             * 查找该联系人的phone信息
             */
                Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId,
                        null, null);
                int phoneIndex = 0;

                if (phones.getCount() > 0) {
                    phoneIndex = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                }
                while (phones.moveToNext()) {
                    String phoneNumber = phones.getString(phoneIndex);
                    contactMap.put("number", phoneNumber);
                }
                phones.close();

            /*
             * 查找该联系人的email信息
             */
                Cursor emails = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=" + contactId,
                        null, null);
                int emailIndex = 0;
                if (emails.getCount() > 0) {
                    emailIndex = emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA);
                }
                while (emails.moveToNext()) {
                    String email = emails.getString(emailIndex);
                    contactMap.put("email", email);
                }
                emails.close();

                mContactsMsg.add(contactMap);
            }
            cursor.close();
        }
        return mContactsMsg;
    }

    class MyAdapter extends BaseAdapter {

        private Context mContext;
        int j = 0;
//        String[] emailsAddress = null;

        public MyAdapter(Context context) {
            this.mContext = context;
        }


        @Override
        public int getCount() {

            return getPhoneContacts2().size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.contactitems, null);
                viewHolder.name = (TextView) convertView.findViewById(R.id.contact_name);
                viewHolder.number = (TextView) convertView.findViewById(R.id.contact_number);
                viewHolder.mail = (TextView) convertView.findViewById(R.id.contact_email);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            Object name = mContactsMsg.get(position).get("name");
            Object number = mContactsMsg.get(position).get("number");
            Object email = mContactsMsg.get(position).get("email");
            if (name != null)
                viewHolder.name.setText(name.toString());
            if (number != null)
                viewHolder.number.setText(number.toString());
            if (email != null)
                viewHolder.mail.setText(email.toString());

            return convertView;
        }

    }

    public final class ViewHolder {
        public TextView name;
        public TextView number;
        public TextView mail;
    }
}
