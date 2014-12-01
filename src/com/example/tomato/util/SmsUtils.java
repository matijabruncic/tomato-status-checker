package com.example.tomato.util;

import com.example.tomato.model.ReceivedSMS;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by mbruncic on 30.11.2014
 */
public class SmsUtils {

    public static final Set<SmsPlaceholder> ORDER = new LinkedHashSet<SmsPlaceholder>(4){{
        //default order, TODO change in runtime
        add(SmsPlaceholder.MINUTE);
        add(SmsPlaceholder.SECOND);
        add(SmsPlaceholder.SMS_COUNT);
        add(SmsPlaceholder.INTERNET);
    }};

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
