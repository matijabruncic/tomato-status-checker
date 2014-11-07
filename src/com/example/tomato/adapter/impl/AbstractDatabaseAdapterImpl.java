package com.example.tomato.adapter.impl;

import android.content.Context;
import com.example.tomato.adapter.DatabaseAdapter;
import com.example.tomato.db.provider.ReceivedSMSesProvider;
import com.example.tomato.db.provider.SentSMSesProvider;

/**
 * Created by mbruncic on 10.10.2014
 */
public class AbstractDatabaseAdapterImpl implements DatabaseAdapter{

    @Override
    public void dropAllDatabases(Context context) {
            SentSMSesProvider sentSMSesProvider = new SentSMSesProvider(context);
            sentSMSesProvider.onUpgrade(sentSMSesProvider.getWritableDatabase(), 1, 1);
            ReceivedSMSesProvider receivedSMSesProvider = new ReceivedSMSesProvider(context);
            receivedSMSesProvider.onUpgrade(receivedSMSesProvider.getWritableDatabase(), 1, 1);
    }
}
