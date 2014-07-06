package com.example.NWatch.service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import com.example.NWatch.connection.BluetoothManager;
import com.example.NWatch.utils.Constants;
import com.example.NWatch.utils.Logs;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by AZGARD on 02.07.2014.
 */
public class NotificationService extends Service {
    private static final String TAG = "NotificationService";
    private ServiceReceiver mServiceReceiver = null;

    private static Handler mHandler = new Handler();
    final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private static BluetoothManager mBtManager = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mBtManager = new BluetoothManager(this, mHandler);
        Set s = mBluetoothAdapter.getBondedDevices();
        BluetoothDevice bDevice = null;
        Iterator<BluetoothDevice> i = s.iterator();
        while (i.hasNext()) {
            BluetoothDevice bd = i.next();
            if (bd.getName().equals("HC-06")) {
                //MAC-address "20:14:03:25:62:51"
                bDevice = bd;
                break;
            }
        }
        if (bDevice != null) {
            mBtManager.connect(bDevice);
        }

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

    public static void ledOn() {
        if (mBtManager != null) {
            String lon = "1";
            byte[] buffer = lon.getBytes();
            mBtManager.write(buffer);
        }
    }

    public static void ledOff() {
        if (mBtManager != null) {
            String lon = "0";
            byte[] buffer = lon.getBytes();
            mBtManager.write(buffer);
        }
    }

    public static void ledBlink() {
        if (mBtManager != null) {
            String lon = "2";
            byte[] buffer = lon.getBytes();
            mBtManager.write(buffer);
        }
    }
}
