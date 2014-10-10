package com.example.FreeStuffChecker.broadcast.receiver.boot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.example.FreeStuffChecker.config.InternalSettings;
import com.example.FreeStuffChecker.service.SMSBackgroundService;

/**
 * Created by mbruncic on 7.10.2014
 */
public class BootUpReceiver extends BroadcastReceiver {

    private SMSBackgroundService smsBackgroundService = SMSBackgroundService.getInstance();
    private InternalSettings internalSettings = InternalSettings.getInstance();

    @Override
    public void onReceive(Context context, Intent intent) {
        internalSettings.setNetworkConnected(false);
        smsBackgroundService.onStartCommand(intent, intent.getFlags(), 0);
    }
}
