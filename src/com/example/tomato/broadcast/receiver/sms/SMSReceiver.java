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

import java.util.*;

/**
 * Created by mbruncic on 6.10.2014
 */
public class SMSReceiver extends BroadcastReceiver {

    public static final Set<SmsPlaceholder> ORDER = new LinkedHashSet<SmsPlaceholder>(4){{
        //default order, TODO change in runtime
        add(SmsPlaceholder.MINUTE);
        add(SmsPlaceholder.SECOND);
        add(SmsPlaceholder.SMS_COUNT);
        add(SmsPlaceholder.INTERNET);
    }};
    private ReceivedSMSAuditAdapter receivedSMSAuditAdapter = ReceivedSMSAuditAdapterImpl.getInstance();
    private List<? extends StatusListener> listeners = Arrays.asList(new InternetTrafficStatusListener(), new SmsCountStatusListener(), new MinuteStatusListener());


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
                    ReceivedSMS receivedSMS = extractStuff(text);
                    Toast.makeText(context, receivedSMS.toString(), Toast.LENGTH_LONG).show();
                    receivedSMSAuditAdapter.insert(context, receivedSMS);
                    StringBuilder alertText = new StringBuilder();
                    for (StatusListener listener : listeners) {
                        alertText.append(listener.onStatusChecked(context, receivedSMS));
                    }
                    if (!alertText.toString().trim().equals("")){
                        MainActivity.setAlertText(alertText.toString());
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

    public static ReceivedSMS extractStuff(String text) {
        text = removeDate(text);
        text = removeNonDigits(text);
        String[] split = text.split("#");
        if (split.length != ORDER.size()){
            throw new IllegalStateException(String.format("Found %d numbers in SMS, configuration has %d number", split.length, ORDER.size()));
        }
        Integer minute = null, second = null, smsCount = null, internet = null;
        Iterator<SmsPlaceholder> iterator = ORDER.iterator();
        for (String aSplit : split) {
            SmsPlaceholder key = iterator.next();
            if (key.equals(SmsPlaceholder.MINUTE)) {
                minute = Integer.parseInt(aSplit);
            } else if (key.equals(SmsPlaceholder.SECOND)) {
                second = Integer.parseInt(aSplit);
            } else if (key.equals(SmsPlaceholder.SMS_COUNT)) {
                smsCount = Integer.parseInt(aSplit);
            } else if (key.equals(SmsPlaceholder.INTERNET)) {
                internet = Integer.parseInt(aSplit);
            } else {
                throw new IllegalStateException("Unknown key: " + key);
            }
        }
        return new ReceivedSMS(minute, second, internet, smsCount, System.currentTimeMillis());
    }

    private static String removeNonDigits(String text) {
        String s = text.replaceAll("\\D+", "#");
        if (s.indexOf("#")==0){
            s=s.substring(1, s.length());
        }
        if (s.lastIndexOf("#")==s.length()-1){
            s=s.substring(0, s.length()-1);
        }
        return s;
    }

    private static String removeDate(String text) {
        return text.replaceAll("\\d{2}\\.\\d{2}\\.\\d{4}\\.", "");
    }

    public static enum SmsPlaceholder{
        MINUTE, SECOND, SMS_COUNT, INTERNET
    }
}
