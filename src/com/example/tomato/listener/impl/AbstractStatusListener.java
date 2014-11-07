package com.example.tomato.listener.impl;

import com.example.tomato.config.Settings;
import com.example.tomato.listener.StatusListener;

/**
 * Created by mbruncic on 10.10.2014
 */
public abstract class AbstractStatusListener implements StatusListener {

    public static final String EMPTY_STRING="";
    protected Settings settings = Settings.getInstance();

}
