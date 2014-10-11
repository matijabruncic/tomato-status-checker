package com.example.FreeStuffChecker.config;

import android.app.AlarmManager;

/**
 * Created by mbruncic on 10.10.2014
 */
public class Settings {

    private static Settings instance;


    private long interval = 1;
    private long internetTrafficAlert = 100;
    private long smsCountAlert = 50;
    private long minuteAlert = 100;

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public long getInternetTrafficAlert() {
        return internetTrafficAlert;
    }

    public void setInternetTrafficAlert(long internetTrafficAlert) {
        this.internetTrafficAlert = internetTrafficAlert;
    }

    public long getSmsCountAlert() {
        return smsCountAlert;
    }

    public void setSmsCountAlert(long smsCountAlert) {
        this.smsCountAlert = smsCountAlert;
    }

    public long getMinuteAlert() {
        return minuteAlert;
    }

    public void setMinuteAlert(long minuteAlert) {
        this.minuteAlert = minuteAlert;
    }
}
