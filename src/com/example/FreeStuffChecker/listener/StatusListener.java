package com.example.FreeStuffChecker.listener;

import android.content.Context;
import com.example.FreeStuffChecker.model.ReceivedSMS;

/**
 * Created by mbruncic on 10.10.2014
 */
public interface StatusListener {
    void onStatusChecked(Context context, ReceivedSMS receivedSMS);
}
