package com.example.FreeStuffChecker.config;

/**
 * Created by mbruncic on 10.10.2014
 */
public class InternalSettings extends Settings {

    private static InternalSettings instance = new InternalSettings();
    private Boolean networkConnected;

    private InternalSettings() {
    }

    public static InternalSettings getInstance() {
        if (instance == null) {
            instance = new InternalSettings();
        }
        return instance;
    }

    public Boolean getNetworkConnected() {
        return networkConnected;
    }

    public void setNetworkConnected(Boolean networkConnected) {
        this.networkConnected = networkConnected;
    }
}
