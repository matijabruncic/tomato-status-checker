package com.example.tomato.adapter;

import android.content.Context;
import com.example.tomato.model.ReceivedSMS;

import java.util.List;

/**
 * Created by mbruncic on 8.10.2014
 */
public interface ReceivedSMSAuditAdapter {
    long insert(Context context, ReceivedSMS receivedSMS);

    List<ReceivedSMS> findAll(Context context);

    void clearAll(Context context);
}
