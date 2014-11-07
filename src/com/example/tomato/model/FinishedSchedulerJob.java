package com.example.tomato.model;

/**
 * Created by mbruncic on 8.10.2014
 */
public class FinishedSchedulerJob {


    private long id;
    private long timestamp;
    private Type type;

    public FinishedSchedulerJob(long id, long timestamp, Type type) {
        this.id = id;
        this.timestamp = timestamp;
        this.type = type;
    }

    public FinishedSchedulerJob(long timestamp, Type type) {
        this.timestamp = timestamp;
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type{
        START_ALARM, CANCEL_ALARM, type, ALARM_TICK
    }

    @Override
    public String toString() {
        return new CustomDateFormat().format(timestamp) + "\t" + type;
    }

}
