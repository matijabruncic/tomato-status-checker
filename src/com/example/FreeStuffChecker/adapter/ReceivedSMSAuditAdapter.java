package com.example.FreeStuffChecker.adapter;

import android.content.Context;
import com.example.FreeStuffChecker.model.ReceivedSMS;

import java.util.Collection;
import java.util.List;

/**
 * Created by mbruncic on 8.10.2014
 */
public interface ReceivedSMSAuditAdapter {
    long insert(Context context, ReceivedSMS receivedSMS);

    List<ReceivedSMS> findAll(Context context);

    void clearAll(Context context);
}
