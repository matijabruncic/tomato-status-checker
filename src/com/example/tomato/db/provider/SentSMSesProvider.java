package com.example.tomato.db.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by mbruncic on 8.10.2014
 */
public class SentSMSesProvider extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "SentSMSes.db";

    public SentSMSesProvider(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TIMESTAMP, %s TEXT, %s TEXT, %s TEXT)",
                SentSmsesEntity.TABLE_NAME,
                SentSmsesEntity._ID,
                SentSmsesEntity.COLUMN_NAME_TIMESTAMP,
                SentSmsesEntity.COLUMN_NAME_MESSAGE,
                SentSmsesEntity.COLUMN_NAME_DESTINATION_ADDRESS,
                SentSmsesEntity.COLUMN_NAME_TYPE);
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(String.format("DROP TABLE %s", SentSmsesEntity.TABLE_NAME));
        onCreate(sqLiteDatabase);
    }

    public static abstract class SentSmsesEntity implements BaseColumns {
        public static final String TABLE_NAME = SentSMSesProvider.DATABASE_NAME.replace(".db", "");
        public static final String COLUMN_NAME_TIMESTAMP = "timestamp";
        public static final String COLUMN_NAME_MESSAGE = "message";
        public static final String COLUMN_NAME_DESTINATION_ADDRESS = "destinationAddress";
        public static final String COLUMN_NAME_TYPE = "type";
    }
}
