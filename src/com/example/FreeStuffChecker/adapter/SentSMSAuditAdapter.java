package com.example.FreeStuffChecker.adapter;

import android.content.Context;
import com.example.FreeStuffChecker.model.SentSMS;

import java.util.Collection;
import java.util.List;

/**
 * Created by mbruncic on 8.10.2014
 */
public interface SentSMSAuditAdapter {

    public long insert(Context context, SentSMS sentSMS);

    List<SentSMS> findAll(Context context);

    void clearAll(Context context);
}
