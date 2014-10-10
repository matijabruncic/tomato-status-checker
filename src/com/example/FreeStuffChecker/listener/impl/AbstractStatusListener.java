package com.example.FreeStuffChecker.listener.impl;

import android.content.Context;
import android.location.GpsStatus;
import android.widget.Toast;
import com.example.FreeStuffChecker.config.Settings;
import com.example.FreeStuffChecker.listener.StatusListener;

/**
 * Created by mbruncic on 10.10.2014
 */
public abstract class AbstractStatusListener implements StatusListener{

    protected Settings settings = Settings.getInstance();

    protected void alert(Context context, String text) {
        Toast.makeText(context, "**ALERT**\n" + text, Toast.LENGTH_LONG).show();
    }
}
