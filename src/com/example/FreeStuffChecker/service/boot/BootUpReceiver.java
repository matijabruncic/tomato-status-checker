package com.example.FreeStuffChecker.service.boot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.example.FreeStuffChecker.service.SMSBackgroundService;

/**
 * Created by mbruncic on 7.10.2014
 */
public class BootUpReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        SMSBackgroundService instance = SMSBackgroundService.getInstance();
        instance.onStartCommand(intent, intent.getFlags(), 0);
    }
}
