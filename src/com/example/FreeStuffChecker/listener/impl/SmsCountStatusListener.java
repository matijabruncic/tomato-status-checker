package com.example.FreeStuffChecker.listener.impl;

import android.content.Context;
import com.example.FreeStuffChecker.listener.StatusListener;
import com.example.FreeStuffChecker.model.ReceivedSMS;

/**
 * Created by mbruncic on 10.10.2014
 */
public class SmsCountStatusListener extends AbstractStatusListener {

    @Override
    public void onStatusChecked(Context context, ReceivedSMS receivedSMS) {
        if (receivedSMS.getSmsCount() <  settings.getSmsCountAlert()){
            alert(context, "SMS count status:" + receivedSMS.getSmsCount());
        }
    }
}
