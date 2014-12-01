package com.example.tomato.broadcast.receiver.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;
import com.example.tomato.MainActivity;
import com.example.tomato.adapter.ReceivedSMSAuditAdapter;
import com.example.tomato.adapter.impl.ReceivedSMSAuditAdapterImpl;
import com.example.tomato.listener.StatusListener;
import com.example.tomato.listener.impl.InternetTrafficStatusListener;
import com.example.tomato.listener.impl.MinuteStatusListener;
import com.example.tomato.listener.impl.SmsCountStatusListener;
import com.example.tomato.model.ReceivedSMS;
import com.example.tomato.service.SMSBackgroundService;
import com.example.tomato.util.SmsUtils;

import java.util.*;

/**
 * Created by mbruncic on 6.10.2014
 */
public class SMSReceiver extends BroadcastReceiver {

    private ReceivedSMSAuditAdapter receivedSMSAuditAdapter = ReceivedSMSAuditAdapterImpl.getInstance();
    private List<? extends StatusListener> listeners = Arrays.asList(new InternetTrafficStatusListener(), new SmsCountStatusListener(), new MinuteStatusListener());
    private SMSBackgroundService service = SMSBackgroundService.getInstance();


    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null)
        {
            Object[] pdus = (Object[]) bundle.get("pdus");
            if (pdus.length == 0){
                return;
            }
            SmsMessage message = SmsMessage.createFromPdu((byte[]) pdus[0]);
            if (isForMe(message)){
                boolean shouldAbort=true;
                try {
                    String text = message.getMessageBody();
                    ReceivedSMS receivedSMS = SmsUtils.extractStuff(text);
                    Toast.makeText(context, receivedSMS.toString(), Toast.LENGTH_LONG).show();
                    receivedSMSAuditAdapter.insert(context, receivedSMS);
                    StringBuilder alertText = new StringBuilder();
                    for (StatusListener listener : listeners) {
                        alertText.append(listener.onStatusChecked(context, receivedSMS));
                    }
                    if (!alertText.toString().trim().equals("")){
                        service.notification(alertText);
                    }
                } catch (Exception e){
                    //TODO LOGGER
                    shouldAbort=false;
                }
                if (shouldAbort) {
                    abortBroadcast();
                }
            }
        }
    }

    private boolean isForMe(SmsMessage message) {
        if(!message.getOriginatingAddress().equals("13411")){
            return false;
        }
        if (!(message.getMessageBody().startsWith("Preostalo ti je ") || message.getMessageBody().startsWith("Stanje preostalih besplatnih minuta"))){
            return false;
        }
        return true;
    }

}
