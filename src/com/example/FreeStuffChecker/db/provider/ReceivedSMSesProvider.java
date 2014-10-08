package com.example.FreeStuffChecker.db.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by mbruncic on 8.10.2014
 */
public class ReceivedSMSesProvider extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ReceivedSMSes.db";
    public static final int DATABASE_VERSION = 1;

    public ReceivedSMSesProvider(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TIMESTAMP, %s INTEGER, %s INTEGER, %s INTEGER, %s INTEGER)",
                ReceivedSMSesEntity.TABLE_NAME,
                ReceivedSMSesEntity._ID,
                ReceivedSMSesEntity.COLUMN_NAME_TIMESTAMP,
                ReceivedSMSesEntity.COLUMN_NAME_MINUTE,
                ReceivedSMSesEntity.COLUMN_NAME_SECOND,
                ReceivedSMSesEntity.COLUMN_NAME_MEGA_BYTE,
                ReceivedSMSesEntity.COLUMN_NAME_SMS_COUNT
        );
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(String.format("DROP TABLE %s", ReceivedSMSesEntity.TABLE_NAME));
        onCreate(sqLiteDatabase);
    }

    public static abstract class ReceivedSMSesEntity implements BaseColumns {
        public static final String TABLE_NAME = ReceivedSMSesProvider.DATABASE_NAME.replace(".db", "");
        public static final String COLUMN_NAME_TIMESTAMP = "timestamp";
        public static final String COLUMN_NAME_MINUTE = "minute";
        public static final String COLUMN_NAME_SECOND = "second";
        public static final String COLUMN_NAME_MEGA_BYTE = "megabyte";
        public static final String COLUMN_NAME_SMS_COUNT = "SMSCount";
    }
}
