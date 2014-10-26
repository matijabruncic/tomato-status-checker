package com.example.FreeStuffChecker.listener.impl;

import android.content.Context;
import com.example.FreeStuffChecker.model.ReceivedSMS;
import org.apache.log4j.lf5.util.StreamUtils;

/**
 * Created by mbruncic on 10.10.2014
 */
public class InternetTrafficStatusListener extends com.example.FreeStuffChecker.listener.impl.AbstractStatusListener {

    @Override
    public String onStatusChecked(Context context, ReceivedSMS receivedSMS){
        if (receivedSMS.getInternetTraffic() < settings.getInternetTrafficAlert()){
            return "MB status: " + receivedSMS.getInternetTraffic();
        }
        return EMPTY_STRING;
    }
}
