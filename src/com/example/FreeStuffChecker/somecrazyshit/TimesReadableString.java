package com.example.FreeStuffChecker.somecrazyshit;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by mbruncic on 17.10.2014
 */
public class TimesReadableString {

    private int times;
    private String text;
    private AtomicInteger counter;

    public TimesReadableString(int times, String text) {
        this.text = text;
        this.times = times;
        counter=new AtomicInteger(times);
    }

    public TimesReadableString(int times) {
        this.times=times;
        counter=new AtomicInteger(times);
    }

    public void setText(String text) {
        this.text = text;
        counter = new AtomicInteger(times);
    }

    public String getText() {
        if (counter.getAndDecrement()>0) {
            return text;
        } else {
            return null;
        }
    }
}
