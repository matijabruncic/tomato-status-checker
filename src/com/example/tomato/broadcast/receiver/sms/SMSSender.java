package com.example.tomato.broadcast.receiver.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.example.tomato.model.Type;
import com.example.tomato.service.SMSBackgroundService;

/**
 * Created by mbruncic on 8.10.2014
 */
public class SMSSender extends BroadcastReceiver {

    private SMSBackgroundService service = SMSBackgroundService.getInstance();
    private boolean started = false;
    private static SMSSender instance;

    private SMSSender(){}

    public static SMSSender getInstance(){
        if (instance == null){
            instance = new SMSSender();
        }
        return instance;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Type type = started ? Type.ALARM_TICK : Type.START_ALARM;
        Toast.makeText(context, "SMS sent", Toast.LENGTH_SHORT).show();
        String destinationAddress = "13411";
        String text = "?";
        service.sendSMS(context, destinationAddress, text, type);
        started = true;
    }
}
