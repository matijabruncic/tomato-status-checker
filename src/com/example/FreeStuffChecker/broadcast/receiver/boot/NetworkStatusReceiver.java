package com.example.FreeStuffChecker.broadcast.receiver.boot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.example.FreeStuffChecker.adapter.NetworkStatusAdapter;
import com.example.FreeStuffChecker.adapter.impl.NetworkStatusAdapterImpl;

/**
 * Created by mbruncic on 10.10.2014
 */
public class NetworkStatusReceiver extends BroadcastReceiver {

    private NetworkStatusAdapter networkStatusAdapter = NetworkStatusAdapterImpl.getInstance();
    private static NetworkStatusReceiver instance;

    public NetworkStatusReceiver(){}

    public static NetworkStatusReceiver getInstance(){
        if (instance==null){
            instance = new NetworkStatusReceiver();
        }
        return instance;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        networkStatusAdapter.checkAndFixNetworkStatus(context);
    }
}
