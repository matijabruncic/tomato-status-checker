package com.example.FreeStuffChecker;

import android.app.Activity;
import android.content.*;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.example.FreeStuffChecker.adapter.ReceivedSMSAuditAdapter;
import com.example.FreeStuffChecker.adapter.SentSMSAuditAdapter;
import com.example.FreeStuffChecker.adapter.impl.ReceivedSMSAuditAdapterImpl;
import com.example.FreeStuffChecker.adapter.impl.SentSMSAuditAdapterImpl;
import com.example.FreeStuffChecker.db.provider.ReceivedSMSesProvider;
import com.example.FreeStuffChecker.db.provider.SentSMSesProvider;
import com.example.FreeStuffChecker.model.ReceivedSMS;
import com.example.FreeStuffChecker.model.SentSMS;
import com.example.FreeStuffChecker.service.SMSBackgroundService;

import java.util.*;

public class MainActivity extends Activity {

    private final ReceivedSMSAuditAdapter receivedSMSAuditAdapter = ReceivedSMSAuditAdapterImpl.getInstance();
    private final SentSMSAuditAdapter sentSMSAuditAdapter = SentSMSAuditAdapterImpl.getInstance();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createUI();

//        dropDB(this);
        startService(new Intent(this, SMSBackgroundService.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, SMSBackgroundService.class));
    }

    private void dropDB(Context context) {
        SentSMSesProvider sentSMSesProvider = new SentSMSesProvider(context);
        sentSMSesProvider.onUpgrade(sentSMSesProvider.getWritableDatabase(), 1, 1);
        ReceivedSMSesProvider receivedSMSesProvider = new ReceivedSMSesProvider(context);
        receivedSMSesProvider.onUpgrade(receivedSMSesProvider.getWritableDatabase(), 1, 1);
    }

    private void createUI() {

        setContentView(R.layout.main);

        findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SMSBackgroundService.getInstance().sendSMSOnButtonClick(view.getContext());
            }
        });

        findViewById(R.id.printSentSMSesButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printSentSMSes();
            }
        });

        findViewById(R.id.printReceivedSMSes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printReceivedSMSes();
            }
        });

        findViewById(R.id.resetLogsButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetAllLogs();
            }
        });

    }

    private void printSentSMSes() {
        List<SentSMS> sentSMSes = sentSMSAuditAdapter.findAll(this);
        print(sentSMSes);
    }

    private void printReceivedSMSes() {
        List<ReceivedSMS> receivedSMSes = receivedSMSAuditAdapter.findAll(this);
        print(receivedSMSes);
    }

    private void resetAllLogs() {
        receivedSMSAuditAdapter.clearAll(this);
        sentSMSAuditAdapter.clearAll(this);
    }


    private void print(List<? extends Comparable> list) {
        Collections.sort(list);
        TableLayout tableLayout = (TableLayout) findViewById(R.id.table);
        tableLayout.removeAllViews();
        for (Object sentSMS : list) {
            TextView textView = new TextView(this);
            textView.setText(sentSMS.toString());
            textView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            tableRow.addView(textView);
            tableLayout.addView(tableRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }
    }
}
