package com.example.FreeStuffChecker.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telephony.SmsManager;
import com.example.FreeStuffChecker.adapter.SentSMSAuditAdapter;
import com.example.FreeStuffChecker.adapter.impl.NetworkStatusAdapterImpl;
import com.example.FreeStuffChecker.adapter.impl.SentSMSAuditAdapterImpl;
import com.example.FreeStuffChecker.broadcast.receiver.sms.SMSSender;
import com.example.FreeStuffChecker.config.InternalSettings;
import com.example.FreeStuffChecker.config.Settings;
import com.example.FreeStuffChecker.model.SentSMS;
import com.example.FreeStuffChecker.model.Type;

/**
 * Created by mbruncic on 7.10.2014
 */
public class SMSBackgroundService extends Service{

//    public static final Logger LOGGER = LoggerFactory.getLogger(SMSBackgroundService.class);
    private static SMSBackgroundService instance;
    private AlarmManager alarmManager;
    private PendingIntent activity;
    private SentSMSAuditAdapter sentSMSAuditAdapter = SentSMSAuditAdapterImpl.getInstance();
    private InternalSettings internalSettings = InternalSettings.getInstance();

    /**
     * Should use only getInstance, please do not use constructor!
     */
    public static SMSBackgroundService getInstance(){
        if (instance==null){
            instance = new SMSBackgroundService();
        }
        return instance;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startAlarmManager();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        alarmManager.cancel(activity);
        sentSMSAuditAdapter.insert(this, new SentSMS(System.currentTimeMillis(), null, null, Type.CANCEL_ALARM));
        super.onDestroy();
    }

    private void startAlarmManager() {
        alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        activity = PendingIntent.getBroadcast(this, 0, new Intent("SMS sender"), 0);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, 0 , internalSettings.getInterval(), activity);
        registerReceiver(SMSSender.getInstance(), new IntentFilter("SMS sender"));
    }

    public void sendSMSOnButtonClick(Context context){
        sendSMS(context, "13411", "?", Type.MANUAL);
    }

    public void sendSMS(Context context, String destinationAddress, String text, Type type) {
        try {
            if (internalSettings.getNetworkConnected()==null){
                NetworkStatusAdapterImpl.getInstance();
            }
            if (isNetworkConnected()) {
                PendingIntent activity = PendingIntent.getActivity(context, 0, new Intent(), 0);
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(destinationAddress, null, text, activity, null);
                sentSMSAuditAdapter.insert(context, new SentSMS(System.currentTimeMillis(), destinationAddress, text, type));
            } else {
                sentSMSAuditAdapter.insert(context, new SentSMS(System.currentTimeMillis(), destinationAddress, text, Type.FAILED_NETWORK_NOT_CONNECTED));
            }
        } catch (Exception e){
//            LOGGER.error(e.getMessage(), e);
            //TODO fix logger
        }
    }

    private boolean isNetworkConnected() {
        Boolean networkConnected = internalSettings.getNetworkConnected();
        if (networkConnected == null){
            return false;
        }
        return networkConnected;
    }
}
