package com.example.tomato;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.*;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.example.FreeStuffChecker.R;
import com.example.tomato.adapter.ReceivedSMSAuditAdapter;
import com.example.tomato.adapter.SentSMSAuditAdapter;
import com.example.tomato.adapter.impl.NetworkStatusAdapterImpl;
import com.example.tomato.adapter.impl.ReceivedSMSAuditAdapterImpl;
import com.example.tomato.adapter.impl.SentSMSAuditAdapterImpl;
import com.example.tomato.config.InternalSettings;
import com.example.tomato.config.Settings;
import com.example.tomato.listener.CustomSeekBarListener;
import com.example.tomato.model.Layout;
import com.example.tomato.model.ReceivedSMS;
import com.example.tomato.model.SentSMS;
import com.example.tomato.service.SMSBackgroundService;

import java.util.*;

public class MainActivity extends Activity{

    private final ReceivedSMSAuditAdapter receivedSMSAuditAdapter = ReceivedSMSAuditAdapterImpl.getInstance();
    private final SentSMSAuditAdapter sentSMSAuditAdapter = SentSMSAuditAdapterImpl.getInstance();
    private final InternalSettings internalSettings = InternalSettings.getInstance();
    private final Settings settings = Settings.getInstance();
    private List<View> allViews = new ArrayList<View>();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initialize();
        createUI();
    }

    @Override
    public void onBackPressed() {
        Integer id = null;
        for (View view : allViews) {
            if (view.getVisibility() == View.VISIBLE){
                id = view.getId();
                break;
            }
        }
        if (id!=null && id !=R.id.mainScreen){
            setAllLayoutsToGone();
            findViewById(R.id.mainScreen).setVisibility(View.VISIBLE);
            refreshToggleScheduleCheckButton();
        } else {
            super.onBackPressed();
        }
    }

    private void initialize() {
        if (internalSettings.getNetworkConnected() == null) {
            NetworkStatusAdapterImpl.getInstance().checkAndFixNetworkStatus(this);
        }
        if (!isMyServiceRunning(SMSBackgroundService.class)) {
            startService(new Intent(this, SMSBackgroundService.class));
        }
        allViews.add(findViewById(R.id.mainScreen));
        allViews.add(findViewById(R.id.settingsScreen));
    }

    private void createUI() {

        populateSettingsView();
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

        View toggleScheduledCheck = findViewById(R.id.toggleScheduledCheck);
        toggleScheduledCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleScheduledCheck();
            }
        });
        refreshToggleScheduleCheckButton();
        findViewById(R.id.settingsButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                populateSettingsView();
                changeVisibleLayout(Layout.SETTINGS_SCREEN);
            }
        });

        findViewById(R.id.saveSettingsButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO save settings in application, think about persisting
                saveSettings();
                changeVisibleLayout(Layout.MAIN_SCREEN);
            }
        });

        ((SeekBar)findViewById(R.id.checkIntervalInput)).setOnSeekBarChangeListener(new CustomSeekBarListener());
        ((SeekBar)findViewById(R.id.minuteAlertThresholdInput)).setOnSeekBarChangeListener(new CustomSeekBarListener());
        ((SeekBar)findViewById(R.id.internetTrafficAlertThresholdInput)).setOnSeekBarChangeListener(new CustomSeekBarListener());
        ((SeekBar)findViewById(R.id.smsCountThresholdInput)).setOnSeekBarChangeListener(new CustomSeekBarListener());

    }

    private void refreshToggleScheduleCheckButton() {
        View view = findViewById(R.id.toggleScheduledCheck);
        if (SMSBackgroundService.getInstance().isServiceRunning()) {
            view.setBackgroundColor(Color.GREEN);
        } else {
            view.setBackgroundColor(Color.RED);
        }
    }

    private void toggleScheduledCheck() {
        SMSBackgroundService smsBackgroundService = SMSBackgroundService.getInstance();
        if(smsBackgroundService.isServiceRunning()){
            smsBackgroundService.cancelAlarm();
        }else {
            smsBackgroundService.startAlarm();
        }
        refreshToggleScheduleCheckButton();
    }

    private void saveSettings() {
        settings.setInterval((long) getValue(R.id.checkIntervalInput));
        settings.setInternetTrafficAlert((long) getValue(R.id.internetTrafficAlertThresholdInput));
        settings.setMinuteAlert((long) getValue(R.id.minuteAlertThresholdInput));
        settings.setSmsCountAlert((long) getValue(R.id.smsCountThresholdInput));
    }

    private int getValue(int checkIntervalInputId) {
        SeekBar seekBar = (SeekBar) findViewById(checkIntervalInputId);
        return seekBar.getProgress();
    }

    private void changeVisibleLayout(Layout layout) {
        switch (layout) {
            case MAIN_SCREEN: {
                setAllLayoutsToGone();
                findViewById(R.id.mainScreen).setVisibility(View.VISIBLE);
                break;
            }
            case SETTINGS_SCREEN: {
                setAllLayoutsToGone();
                findViewById(R.id.settingsScreen).setVisibility(View.VISIBLE);
                break;
            }
        }
    }

    private void setAllLayoutsToGone() {
        for (View view : allViews) {
            view.setVisibility(View.GONE);
        }
    }

    private void populateSettingsView() {
        ((SeekBar) findViewById(R.id.checkIntervalInput)).setProgress((int) settings.getInterval());
        ((SeekBar) findViewById(R.id.minuteAlertThresholdInput)).setProgress((int) settings.getMinuteAlert());
        ((SeekBar) findViewById(R.id.smsCountThresholdInput)).setProgress((int) settings.getSmsCountAlert());
        ((SeekBar) findViewById(R.id.internetTrafficAlertThresholdInput)).setProgress((int) settings.getInternetTrafficAlert());
    }

    private void printSentSMSes() {
        List<SentSMS> sentSMSes = sentSMSAuditAdapter.findAll(this);
        print(sentSMSes);
    }

    private void printReceivedSMSes() {
        List<ReceivedSMS> receivedSMSes = receivedSMSAuditAdapter.findAll(this);
        print(receivedSMSes);
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

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
