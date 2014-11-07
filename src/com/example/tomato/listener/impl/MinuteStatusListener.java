package com.example.tomato.listener.impl;

import android.content.Context;
import com.example.tomato.model.ReceivedSMS;

/**
 * Created by mbruncic on 10.10.2014
 */
public class MinuteStatusListener extends AbstractStatusListener {

    @Override
    public String onStatusChecked(Context context, ReceivedSMS receivedSMS) {
        if (receivedSMS.getMinute() < settings.getMinuteAlert()){
            return "Minute status: " + receivedSMS.getMinute();
        }
        return EMPTY_STRING;
    }
}
