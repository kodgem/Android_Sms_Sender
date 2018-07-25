package com.kodgem.smssender;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kodgem.smssender.Models.ModelContact;
import com.kodgem.smssender.Models.StaticClass;

import java.util.ArrayList;
import java.util.List;

public class SmsSendActivity extends AppCompatActivity {

    TextView tvTotalContact;
    LinearLayout layoutFilter;
    EditText edFilter;
    Button btnFiltre,btnSend;
    List<ModelContact> listContact;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_send);

        context = SmsSendActivity.this;
        listContact = new ArrayList<>();
        layoutFilter = findViewById(R.id.layout_filtre);
        tvTotalContact = findViewById(R.id.tv_totalconnector);
        edFilter = findViewById(R.id.ed_filtre);
        btnFiltre = findViewById(R.id.btn_filter);
        btnSend = findViewById(R.id.btn_Send);


    }

    // bu method listeyi filtreye göre ayırıyor.
    private void filtrele(String filtre)
    {
        listContact.clear();
        for (int i = 0; i < StaticClass.listContact.size(); i++) {
            if(StaticClass.listContact.get(i).getName().toLowerCase().trim().contains(filtre.toLowerCase().trim()))
            {
                listContact.add(StaticClass.listContact.get(i));
            }
        }
        tvTotalContact.setText("Toplam Gönderilecek Sayısı : "+listContact.size());
        if(listContact.size() > 0)
        {
            btnSend.setVisibility(View.VISIBLE);
        }
        else
        {
            btnSend.setVisibility(View.GONE);
        }
    }


    // Bu method listedeki tüm kullanıcılara smss gönderiyor
    private void topluGonder(List<ModelContact> list)
    {
        for (int i = 0; i < list.size(); i++) {
            sendSMS(list.get(i).getNumberEdit(),StaticClass.message);
        }
        Toast.makeText(context,"Sms gönderimi tammalandı",Toast.LENGTH_SHORT).show();
    }

    // Bu method Sms gönderme işlemini yapıyor
    private void sendSMS(String phoneNumber, String message) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }
}
