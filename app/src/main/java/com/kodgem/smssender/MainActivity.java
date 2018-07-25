package com.kodgem.smssender;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kodgem.smssender.Models.ModelContact;
import com.kodgem.smssender.Models.StaticClass;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btnTumListe,btnFlitreli;
    List<ModelContact> listContact;
    EditText edMessage;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;
        listContact = new ArrayList<>();
        btnTumListe = findViewById(R.id.btn_tumliste);
        btnFlitreli = findViewById(R.id.btn_filitreli);
        edMessage = findViewById(R.id.ed_message);



        getContacts();
    }

   // Bu method rehberdeki tüm kişileri getiriyor
    private void getContacts()
    {
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String phoneNumber = "-";
                Log.i("Names", name);
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
                {
                    // Query phone here. Covered next
                    Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,null, null);
                    while (phones.moveToNext()) {
                        phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.i("Number", phoneNumber);
                    }
                    phones.close();
                }
                ModelContact modelContact = new ModelContact();
                modelContact.setName(name);
                modelContact.setNumber(phoneNumber);
                modelContact.setNumberEdit(editNumber(phoneNumber));
                listContact.add(modelContact);
                Log.d("**Phone:",name+" -> "+phoneNumber+" : "+modelContact.getNumberEdit());
            }
        }
        StaticClass.listContact = listContact;
    }

    // bu method içerisine gelen numarayı ( veya boşluklarda ayırarak düzenliyor
    private String editNumber(String number)
    {
        String numberResult = "";
        for (int i = 0; i < number.length(); i++) {
            String nu = ""+number.charAt(i);
            if(isNumeric(nu))
            {
                numberResult += nu;
            }
        }
        return numberResult;
    }

    public static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
}
