package com.example.tomato.adapter.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.tomato.adapter.SentSMSAuditAdapter;
import com.example.tomato.db.provider.SentSMSesProvider;
import com.example.tomato.model.SentSMS;
import com.example.tomato.model.Type;

import java.util.*;

/**
 * Created by mbruncic on 8.10.2014
 */
public class SentSMSAuditAdapterImpl implements SentSMSAuditAdapter {

    private Map<Context, SentSMSesProvider> providerMap = new HashMap<Context, SentSMSesProvider>();
    private static SentSMSAuditAdapterImpl instance;

    private SentSMSAuditAdapterImpl(){}

    public static SentSMSAuditAdapterImpl getInstance(){
        if (instance == null){
            instance = new SentSMSAuditAdapterImpl();
        }
        return instance;
    }

    @Override
    public long insert(Context context, SentSMS sentSMS) {
        SQLiteDatabase writableDatabase = getWritableDatabase(context);
        ContentValues values = new ContentValues();
        values.put(SentSMSesProvider.SentSmsesEntity.COLUMN_NAME_DESTINATION_ADDRESS, sentSMS.getTo());
        values.put(SentSMSesProvider.SentSmsesEntity.COLUMN_NAME_MESSAGE, sentSMS.getMessage());
        values.put(SentSMSesProvider.SentSmsesEntity.COLUMN_NAME_TIMESTAMP, sentSMS.getTimestamp());
        values.put(SentSMSesProvider.SentSmsesEntity.COLUMN_NAME_TYPE, sentSMS.getType().toString());
        return writableDatabase.insert(SentSMSesProvider.SentSmsesEntity.TABLE_NAME, null, values);
    }

    @Override
    public List<SentSMS> findAll(Context context) {
        SQLiteDatabase readableDatabase = getReadableDatabase(context);
        Cursor cursor = readableDatabase.rawQuery("select * from " + SentSMSesProvider.SentSmsesEntity.TABLE_NAME + " limit 10", null);
        List<SentSMS> result = new ArrayList<SentSMS>();
        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                long id = cursor.getLong(cursor.getColumnIndex(SentSMSesProvider.SentSmsesEntity._ID));
                long timestamp = cursor.getLong(cursor.getColumnIndex(SentSMSesProvider.SentSmsesEntity.COLUMN_NAME_TIMESTAMP));
                String type = cursor.getString(cursor.getColumnIndex(SentSMSesProvider.SentSmsesEntity.COLUMN_NAME_TYPE));
                String to = cursor.getString(cursor.getColumnIndex(SentSMSesProvider.SentSmsesEntity.COLUMN_NAME_DESTINATION_ADDRESS));
                String message = cursor.getString(cursor.getColumnIndex(SentSMSesProvider.SentSmsesEntity.COLUMN_NAME_MESSAGE));
                result.add(new SentSMS(id, timestamp, to, message, Type.valueOf(type)));
                cursor.moveToNext();
            }
        }
        return result;
    }

    @Override
    public void clearAll(Context context) {
        SQLiteDatabase writableDatabase = getWritableDatabase(context);
        writableDatabase.execSQL(String.format("DELETE FROM %s", SentSMSesProvider.SentSmsesEntity.TABLE_NAME));
    }
    private SQLiteDatabase getWritableDatabase(Context context) {
        SentSMSesProvider provider = getProvider(context);
        return provider.getWritableDatabase();
    }

    private SQLiteDatabase getReadableDatabase(Context context) {
        SentSMSesProvider provider = getProvider(context);
        return provider.getReadableDatabase();
    }

    private SentSMSesProvider getProvider(Context context) {
        if (providerMap.containsKey(context)){
            return providerMap.get(context);
        }
        return new SentSMSesProvider(context);
    }
}
