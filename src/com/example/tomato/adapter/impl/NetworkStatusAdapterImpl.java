package com.example.tomato.adapter.impl;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.example.tomato.adapter.NetworkStatusAdapter;
import com.example.tomato.config.InternalSettings;

/**
 * Created by mbruncic on 10.10.2014
 */
public class NetworkStatusAdapterImpl implements NetworkStatusAdapter{
    private InternalSettings internalSettings = InternalSettings.getInstance();
    private static NetworkStatusAdapterImpl instance;

    public static NetworkStatusAdapterImpl getInstance() {
        if (instance==null){
            instance=new NetworkStatusAdapterImpl();
        }
        return instance;
    }

    @Override
    public void checkAndFixNetworkStatus(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobNetInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
            internalSettings.setNetworkConnected(true);
        } else {
            internalSettings.setNetworkConnected(true);
        }

    }
}
