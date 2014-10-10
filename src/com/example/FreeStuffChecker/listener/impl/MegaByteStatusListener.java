package com.example.FreeStuffChecker.listener.impl;

import android.content.Context;
import com.example.FreeStuffChecker.listener.StatusListener;
import com.example.FreeStuffChecker.model.ReceivedSMS;

/**
 * Created by mbruncic on 10.10.2014
 */
public class MegaByteStatusListener extends AbstractStatusListener {

    @Override
    public void onStatusChecked(Context context, ReceivedSMS receivedSMS){
        if (receivedSMS.getMegabyte() < settings.getMegaByteAlert()){
            alert(context, "MB status: " + receivedSMS.getMegabyte());
        }
    }
}
