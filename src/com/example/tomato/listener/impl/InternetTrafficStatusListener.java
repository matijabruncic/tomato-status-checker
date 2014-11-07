package com.example.tomato.listener.impl;

import android.content.Context;
import com.example.tomato.model.ReceivedSMS;

/**
 * Created by mbruncic on 10.10.2014
 */
public class InternetTrafficStatusListener extends com.example.tomato.listener.impl.AbstractStatusListener {

    @Override
    public String onStatusChecked(Context context, ReceivedSMS receivedSMS){
        if (receivedSMS.getInternetTraffic() < settings.getInternetTrafficAlert()){
            return "MB status: " + receivedSMS.getInternetTraffic();
        }
        return EMPTY_STRING;
    }
}
