package com.example.FreeStuffChecker.model;

/**
 * Created by mbruncic on 6.10.2014
 */
public class ReceivedSMS implements Comparable<ReceivedSMS>{
    long id;
    int minute;
    int second;
    int megabyte;
    int smsCount;
    long timestamp;

    public ReceivedSMS(long id, int minute, int second, int megabyte, int smsCount, long timestamp) {
        this.id = id;
        this.minute = minute;
        this.second = second;
        this.megabyte = megabyte;
        this.smsCount = smsCount;
        this.timestamp = timestamp;
    }

    public ReceivedSMS(int minute, int second, int megabyte, int smsCount, long timestamp) {
        this.minute = minute;
        this.second = second;
        this.megabyte = megabyte;
        this.smsCount = smsCount;
        this.timestamp = timestamp;
    }

    public int getMegabyte() {
        return megabyte;
    }

    public void setMegabyte(int megabyte) {
        this.megabyte = megabyte;
    }

    public int getSmsCount() {
        return smsCount;
    }

    public void setSmsCount(int smsCount) {
        this.smsCount = smsCount;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return new CustomDateFormat().format(timestamp) + "\tMB:" + megabyte + "\tSMS:" + smsCount + "\tMin:" + minute;
    }

    @Override
    public int compareTo(ReceivedSMS sms) {
        return (int)(sms.timestamp-this.timestamp);
    }
}
