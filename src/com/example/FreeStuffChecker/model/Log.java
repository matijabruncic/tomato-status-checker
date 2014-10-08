package com.example.FreeStuffChecker.model;

/**
 * Created by mbruncic on 8.10.2014
 */
public class Log {
    private SentSMS sentSMS;
    private ReceivedSMS receivedSMS;

    public Log() {
    }

    public Log(SentSMS sentSMS, ReceivedSMS receivedSMS) {
        this.sentSMS = sentSMS;
        this.receivedSMS = receivedSMS;
    }

    public SentSMS getSentSMS() {
        return sentSMS;
    }

    public void setSentSMS(SentSMS sentSMS) {
        this.sentSMS = sentSMS;
    }

    public ReceivedSMS getReceivedSMS() {
        return receivedSMS;
    }

    public void setReceivedSMS(ReceivedSMS receivedSMS) {
        this.receivedSMS = receivedSMS;
    }

    @Override
    public String toString() {
        return "sent:" + sentSMS + ", received:" + receivedSMS;
    }
}
