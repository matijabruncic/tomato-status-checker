package com.example.FreeStuffChecker.listener.impl;

import com.example.FreeStuffChecker.config.Settings;
import com.example.FreeStuffChecker.listener.StatusListener;

/**
 * Created by mbruncic on 10.10.2014
 */
public abstract class AbstractStatusListener implements StatusListener {

    public static final String EMPTY_STRING="";
    protected Settings settings = Settings.getInstance();

}
