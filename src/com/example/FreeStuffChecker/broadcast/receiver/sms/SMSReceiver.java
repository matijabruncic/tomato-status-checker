package com.example.FreeStuffChecker.broadcast.receiver.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;
import com.example.FreeStuffChecker.adapter.ReceivedSMSAuditAdapter;
import com.example.FreeStuffChecker.adapter.impl.ReceivedSMSAuditAdapterImpl;
import com.example.FreeStuffChecker.listener.StatusListener;
import com.example.FreeStuffChecker.listener.impl.InternetTrafficStatusListener;
import com.example.FreeStuffChecker.listener.impl.MinuteStatusListener;
import com.example.FreeStuffChecker.listener.impl.SmsCountStatusListener;
import com.example.FreeStuffChecker.model.ReceivedSMS;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mbruncic on 6.10.2014
 */
public class SMSReceiver extends BroadcastReceiver {

    public static final Pattern MIN_PATTERN = Pattern.compile("\\s[0-9]*\\smin");
    public static final Pattern SEC_PATTERN = Pattern.compile("\\s[0-9]*\\ssec");
    public static final Pattern SMS_PATTERN = Pattern.compile("\\s[0-9]*\\sSMS");
    public static final Pattern MB_PATTERN = Pattern.compile("\\s[0-9]*\\sMB");
    public static final Pattern DIGIT_PATTERN = Pattern.compile("[0-9]+");
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
            if (message.getOriginatingAddress().equals("13411") && message.getMessageBody().startsWith("Preostalo ti je ")){
                try {
                    String text = message.getMessageBody();
                    ReceivedSMS receivedSMS = extractStuff(text);
                    Toast.makeText(context, receivedSMS.toString(), Toast.LENGTH_LONG).show();
                    receivedSMSAuditAdapter.insert(context, receivedSMS);
                    for (StatusListener listener : listeners) {
                        listener.onStatusChecked(context, receivedSMS);
                    }
                } catch (Exception e){
                    //TODO LOGGER
                }
                abortBroadcast();
            }
        }
    }

    private static ReceivedSMS extractStuff(String text) {
        Integer min = Integer.valueOf(getValue(text, MIN_PATTERN));
        Integer sec = Integer.valueOf(getValue(text, SEC_PATTERN));
        Integer smsCount = Integer.valueOf(getValue(text, SMS_PATTERN));
        Integer mb = Integer.valueOf(getValue(text, MB_PATTERN));
        return new ReceivedSMS(min, sec, mb, smsCount, System.currentTimeMillis());
    }

    private static String getValue(String text, Pattern pattern) {
        Matcher matcher = pattern.matcher(text);
        matcher.find();
        String result = matcher.group();
        matcher = DIGIT_PATTERN.matcher(result);
        matcher.find();
        result = matcher.group();
        return result;
    }
}
