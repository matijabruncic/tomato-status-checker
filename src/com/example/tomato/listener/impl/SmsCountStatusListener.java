package com.example.tomato.listener.impl;

import android.content.Context;
import com.example.tomato.model.ReceivedSMS;

/**
 * Created by mbruncic on 10.10.2014
 */
public class SmsCountStatusListener extends AbstractStatusListener {

    @Override
    public String onStatusChecked(Context context, ReceivedSMS receivedSMS) {
        if (receivedSMS.getSmsCount() <  settings.getSmsCountAlert()){
            return "SMS count status:" + receivedSMS.getSmsCount();
        }
        return EMPTY_STRING;
    }
}
