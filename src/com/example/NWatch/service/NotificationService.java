package com.example.NWatch.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import com.example.NWatch.utils.Constants;
import com.example.NWatch.utils.Logs;

/**
 * Created by AZGARD on 02.07.2014.
 */
public class NotificationService extends Service {
    private static final String TAG = "NotificationService";
    private ServiceReceiver mServiceReceiver = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logs.d(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logs.d(TAG, "onStartCommand");
        initialize();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logs.d(TAG, "onDestroy");
    }

    private void initialize() {
        mServiceReceiver = new ServiceReceiver();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(Constants.NOTIFICATION_LISTENER);
//        registerReceiver(mServiceReceiver, filter);
        Intent intent = new Intent(Constants.NOTIFICATION_LISTENER);
        sendBroadcast(intent);
        Logs.d(TAG, "Calls_listening");
    }
}
