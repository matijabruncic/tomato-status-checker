package com.example.FreeStuffChecker.adapter.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.FreeStuffChecker.adapter.ReceivedSMSAuditAdapter;
import com.example.FreeStuffChecker.db.provider.ReceivedSMSesProvider;
import com.example.FreeStuffChecker.model.ReceivedSMS;

import java.util.*;

/**
 * Created by mbruncic on 8.10.2014
 */
public class ReceivedSMSAuditAdapterImpl implements ReceivedSMSAuditAdapter {


    private Map<Context, ReceivedSMSesProvider> providerMap = new HashMap<Context, ReceivedSMSesProvider>();
    private static ReceivedSMSAuditAdapterImpl instance;

    private ReceivedSMSAuditAdapterImpl(){}

    public static ReceivedSMSAuditAdapterImpl getInstance(){
        if (instance == null){
            instance = new ReceivedSMSAuditAdapterImpl();
        }
        return instance;
    }


    @Override
    public long insert(Context context, ReceivedSMS receivedSMS) {
        SQLiteDatabase writableDatabase = getWritableDatabase(context);
        ContentValues values = new ContentValues();
        values.put(ReceivedSMSesProvider.ReceivedSMSesEntity.COLUMN_NAME_TIMESTAMP, receivedSMS.getTimestamp());
        values.put(ReceivedSMSesProvider.ReceivedSMSesEntity.COLUMN_NAME_MINUTE, receivedSMS.getMinute());
        values.put(ReceivedSMSesProvider.ReceivedSMSesEntity.COLUMN_NAME_SECOND, receivedSMS.getSecond());
        values.put(ReceivedSMSesProvider.ReceivedSMSesEntity.COLUMN_NAME_INTERNET_TRAFFIC, receivedSMS.getInternetTraffic());
        values.put(ReceivedSMSesProvider.ReceivedSMSesEntity.COLUMN_NAME_SMS_COUNT, receivedSMS.getSmsCount());
        return writableDatabase.insert(ReceivedSMSesProvider.ReceivedSMSesEntity.TABLE_NAME, null, values);
    }

    @Override
    public List<ReceivedSMS> findAll(Context context) {
        SQLiteDatabase readableDatabase = getReadableDatabase(context);
        Cursor cursor = readableDatabase.rawQuery("select * from " + ReceivedSMSesProvider.ReceivedSMSesEntity.TABLE_NAME, null);
        List<ReceivedSMS> result = new ArrayList<ReceivedSMS>();
        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                long id = cursor.getLong(cursor.getColumnIndex(ReceivedSMSesProvider.ReceivedSMSesEntity._ID));
                long timestamp = cursor.getLong(cursor.getColumnIndex(ReceivedSMSesProvider.ReceivedSMSesEntity.COLUMN_NAME_TIMESTAMP));
                int minute = cursor.getInt(cursor.getColumnIndex(ReceivedSMSesProvider.ReceivedSMSesEntity.COLUMN_NAME_MINUTE));
                int second = cursor.getInt(cursor.getColumnIndex(ReceivedSMSesProvider.ReceivedSMSesEntity.COLUMN_NAME_SECOND));
                int internetTraffic = cursor.getInt(cursor.getColumnIndex(ReceivedSMSesProvider.ReceivedSMSesEntity.COLUMN_NAME_INTERNET_TRAFFIC));
                int smsCount= cursor.getInt(cursor.getColumnIndex(ReceivedSMSesProvider.ReceivedSMSesEntity.COLUMN_NAME_SMS_COUNT));
                result.add(new ReceivedSMS(id, minute, second, internetTraffic, smsCount, timestamp));
                cursor.moveToNext();
            }
        }
        return result;

    }

    @Override
    public void clearAll(Context context) {
        SQLiteDatabase writableDatabase = getWritableDatabase(context);
        writableDatabase.execSQL(String.format("DELETE FROM %s", ReceivedSMSesProvider.ReceivedSMSesEntity.TABLE_NAME));
    }


    private SQLiteDatabase getWritableDatabase(Context context) {
        ReceivedSMSesProvider provider = getProvider(context);
        return provider.getWritableDatabase();
    }

    private SQLiteDatabase getReadableDatabase(Context context) {
        ReceivedSMSesProvider provider = getProvider(context);
        return provider.getReadableDatabase();
    }

    private ReceivedSMSesProvider getProvider(Context context) {
        if (providerMap.containsKey(context)){
            return providerMap.get(context);
        }
        return new ReceivedSMSesProvider(context);
    }
}
