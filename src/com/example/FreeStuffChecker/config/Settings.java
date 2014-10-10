package com.example.FreeStuffChecker.config;

import android.app.AlarmManager;

/**
 * Created by mbruncic on 10.10.2014
 */
public class Settings {

    private static Settings instance;

    private Boolean networkConnected;
    private long interval = AlarmManager.INTERVAL_HOUR;
    private long megaByteAlert = 100;
    private long smsCountAlert = 50;
    private long minuteAlert=999; //TODO change this

    public static Settings getInstance() {
        if (instance==null){
            instance=new Settings();
        }
        return instance;
    }

    public Boolean getNetworkConnected() {
        return networkConnected;
    }

    public void setNetworkConnected(Boolean networkConnected) {
        this.networkConnected = networkConnected;
    }

    //TODO should return object with value and unit
    public long getInterval(){
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public long getMegaByteAlert() {
        return megaByteAlert;
    }

    public void setMegaByteAlert(long megaByteAlert) {
        this.megaByteAlert = megaByteAlert;
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
