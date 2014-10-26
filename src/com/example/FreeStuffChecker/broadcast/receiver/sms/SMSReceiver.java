package com.example.FreeStuffChecker.broadcast.receiver.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;
import com.example.FreeStuffChecker.MainActivity;
import com.example.FreeStuffChecker.adapter.ReceivedSMSAuditAdapter;
import com.example.FreeStuffChecker.adapter.impl.ReceivedSMSAuditAdapterImpl;
import com.example.FreeStuffChecker.listener.StatusListener;
import com.example.FreeStuffChecker.listener.impl.InternetTrafficStatusListener;
import com.example.FreeStuffChecker.listener.impl.MinuteStatusListener;
import com.example.FreeStuffChecker.listener.impl.SmsCountStatusListener;
import com.example.FreeStuffChecker.model.ReceivedSMS;
import com.example.FreeStuffChecker.service.SMSBackgroundService;

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
    public static final String MINUTE_PLACEHOLDER = "{minute}";
    public static final String SECOND_PLACEHOLDER = "{second}";
    public static final String SMS_COUNT_PLACEHOLDER = "{smsCount}";
    public static final String INTERNET_PLACEHOLDER = "{internet}";
    private ReceivedSMSAuditAdapter receivedSMSAuditAdapter = ReceivedSMSAuditAdapterImpl.getInstance();
    private List<? extends StatusListener> listeners = Arrays.asList(new InternetTrafficStatusListener(), new SmsCountStatusListener(), new MinuteStatusListener());
    private static String pattern;


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
            if (message.getOriginatingAddress().equals("13411") && (message.getMessageBody().startsWith("Preostalo ti je ") || message.getMessageBody().startsWith("Stanje preostalih besplatnih minuta"))){
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
                }
                abortBroadcast();
            }
        }
    }

    public static ReceivedSMS extractStuff(String text) {
        if (pattern==null)  return null;
        String[] split = pattern.split("\\{.*?\\}");

        for (String s : split) {
            text = text.replace(s, " ");
        }
        return null;
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

    public static void setPattern(String pattern) throws IllegalArgumentException{
        validatePattern(pattern);
        SMSReceiver.pattern = pattern;
    }

    private static void validatePattern(String pattern) throws IllegalArgumentException{
        if (!pattern.contains(MINUTE_PLACEHOLDER))      throw new IllegalArgumentException("Missing placeholder:"+MINUTE_PLACEHOLDER);
        if (!pattern.contains(SECOND_PLACEHOLDER))      throw new IllegalArgumentException("Missing placeholder:"+SECOND_PLACEHOLDER);
        if (!pattern.contains(SMS_COUNT_PLACEHOLDER))      throw new IllegalArgumentException("Missing placeholder:"+ SMS_COUNT_PLACEHOLDER);
        if (!pattern.contains(INTERNET_PLACEHOLDER))      throw new IllegalArgumentException("Missing placeholder:"+INTERNET_PLACEHOLDER);
    }

    public static void main(String[] args) {
        SMSReceiver.setPattern("Stanje preostalih besplatnih minuta u Brutalnoj tarifi je "+MINUTE_PLACEHOLDER+
                " min i "+SECOND_PLACEHOLDER+
                " sec, besplatnih SMS poruka "+SMS_COUNT_PLACEHOLDER+
                " i besplatnih MB "+INTERNET_PLACEHOLDER+". Tarifa vrijedi do 16.11.2014.");
        ReceivedSMS receivedSMS = SMSReceiver.extractStuff("Stanje preostalih besplatnih minuta u Brutalnoj tarifi je 991 min i 0 sec, besplatnih SMS poruka 986 i besplatnih MB 1474. Tarifa vrijedi do 16.11.2014.");
        assert 991==receivedSMS.getMinute();
    }
}
