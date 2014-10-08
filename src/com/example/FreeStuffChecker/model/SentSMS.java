package com.example.FreeStuffChecker.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by mbruncic on 8.10.2014
 */
public class SentSMS implements Comparable<SentSMS>{

    private long id;
    private long timestamp;
    private String to;
    private String message;
    private Type type;

    public SentSMS(long id, long timestamp, String to, String message, Type type) {
        this.id = id;
        this.timestamp = timestamp;
        this.to = to;
        this.message = message;
        this.type = type;
    }

    public SentSMS(long timestamp, String to, String message, Type type) {
        this.timestamp = timestamp;
        this.to = to;
        this.message = message;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return new CustomDateFormat().format(timestamp) + "\t" + type;
    }

    @Override
    public int compareTo(SentSMS sentSMS) {
        return (int)(sentSMS.timestamp-this.timestamp);
    }
}