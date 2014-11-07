package com.example.tomato.listener;

import android.content.Context;
import com.example.tomato.model.ReceivedSMS;

/**
 * Created by mbruncic on 10.10.2014
 */
public interface StatusListener {
    String onStatusChecked(Context context, ReceivedSMS receivedSMS);
}
