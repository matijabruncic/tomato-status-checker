package com.example.FreeStuffChecker.listener.impl;

import android.content.Context;
import com.example.FreeStuffChecker.model.ReceivedSMS;

/**
 * Created by mbruncic on 10.10.2014
 */
public class MinuteStatusListener extends AbstractStatusListener {

    @Override
    public void onStatusChecked(Context context, ReceivedSMS receivedSMS) {
        if (receivedSMS.getMinute() < settings.getMinuteAlert()){
            alert(context, "Minute status: " + receivedSMS.getMinute());
        }
    }
}
