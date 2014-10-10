package com.example.FreeStuffChecker.broadcast.receiver.boot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.example.FreeStuffChecker.config.Settings;
import com.example.FreeStuffChecker.service.SMSBackgroundService;

/**
 * Created by mbruncic on 7.10.2014
 */
public class BootUpReceiver extends BroadcastReceiver {

    private SMSBackgroundService smsBackgroundService = SMSBackgroundService.getInstance();
    private Settings settings = Settings.getInstance();

    @Override
    public void onReceive(Context context, Intent intent) {
        settings.setNetworkConnected(false);
        smsBackgroundService.onStartCommand(intent, intent.getFlags(), 0);
    }
}
