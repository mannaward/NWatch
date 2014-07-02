package com.example.NWatch;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import com.example.NWatch.connection.BluetoothManager;
import com.example.NWatch.service.NotificationService;
import com.example.NWatch.service.ServiceReceiver;
import com.example.NWatch.utils.Constants;

import java.util.Iterator;
import java.util.Set;

public class MyActivity extends Activity {
    private static final String TAG = "main_activity";

    private static final int REQUEST_ENABLE_BT = 1;
    Button ledOn;
    Button ledOff;
    Button connect;

    private BluetoothManager mBtManager;
    //private ServiceHandler mServiceHandler = new ServiceHandler();
    private Handler mHandler = new Handler();

    private ServiceReceiver mServiceReceiver;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ledOn = (Button) findViewById(R.id.button);
        ledOff = (Button) findViewById(R.id.button2);
        connect = (Button) findViewById(R.id.button3);

        final Context context = this;

        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast toast = Toast.makeText(getApplicationContext(), "Bluetooth недоступен на вашем устройстве", Toast.LENGTH_SHORT);
            toast.show();
        }
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }


        //Intent intent = new Intent(Constants.NOTIFICATION_LISTENER);
        //sendBroadcast(intent);
        ServiceReceiver mSReceiver = new ServiceReceiver();
        IntentFilter filter = new IntentFilter(Constants.NOTIFICATION_LISTENER);
        registerReceiver(mSReceiver, filter);


        OnClickListener connectL = new OnClickListener() {
            @Override
            public void onClick(View v) {
                mBtManager = new BluetoothManager(context, mHandler);
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
                startService(new Intent(context, NotificationService.class));
            }
        };

       OnClickListener ledOnL = new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBtManager != null) {
                    // byte definitions for buffer setting
                    byte TRANSACTION_START_BYTE = (byte) 0xfc;
                    byte TRANSACTION_END_BYTE = (byte) 0xfd;
                    String lon = "1";
                    byte[] buffer = lon.getBytes();


                    mBtManager.write(buffer);
                    Toast toast = Toast.makeText(getApplicationContext(), "Led on", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        };

        OnClickListener ledOffL = new OnClickListener() {
            @Override
            public void onClick(View v) {
                String lon = "0";
                byte[] buffer = lon.getBytes();
                mBtManager.write(buffer);
                Toast toast = Toast.makeText(getApplicationContext(), "Led off", Toast.LENGTH_SHORT);
                toast.show();
            }
        };

        connect.setOnClickListener(connectL);
        ledOn.setOnClickListener(ledOnL);
        ledOff.setOnClickListener(ledOffL);

    }

//    class ServiceHandler extends Handler {
//        @Override
//        public void handleMessage(Message msg) {
//
//            switch(msg.what) {
//                case BluetoothManager.MESSAGE_STATE_CHANGE:
//                    // Bluetooth state Changed
//                    Logs.d(TAG, "Service - MESSAGE_STATE_CHANGE: " + msg.arg1);
//
//                    switch (msg.arg1) {
//                        case BluetoothManager.STATE_NONE:
//                            mActivityHandler.obtainMessage(Constants.MESSAGE_BT_STATE_INITIALIZED).sendToTarget();
//                            if(mRefreshTimer != null) {
//                                mRefreshTimer.cancel();
//                                mRefreshTimer = null;
//                            }
//                            break;
//
//                        case BluetoothManager.STATE_LISTEN:
//                            mActivityHandler.obtainMessage(Constants.MESSAGE_BT_STATE_LISTENING).sendToTarget();
//                            break;
//
//                        case BluetoothManager.STATE_CONNECTING:
//                            mActivityHandler.obtainMessage(Constants.MESSAGE_BT_STATE_CONNECTING).sendToTarget();
//                            break;
//
//                        case BluetoothManager.STATE_CONNECTED:
//                            mActivityHandler.obtainMessage(Constants.MESSAGE_BT_STATE_CONNECTED).sendToTarget();
//
//                            // Fully update remote device every 1 hour
//                            reserveRemoteUpdate(5000);
//                            break;
//                    }
//                    break;
//
//                case BluetoothManager.MESSAGE_WRITE:
//                    Logs.d(TAG, "Service - MESSAGE_WRITE: ");
//                    break;
//
//                case BluetoothManager.MESSAGE_READ:
//                    Logs.d(TAG, "Service - MESSAGE_READ: ");
//
//                    byte[] readBuf = (byte[]) msg.obj;
//                    // construct commands from the valid bytes in the buffer
//                    if(mTransactionReceiver != null)
//                        mTransactionReceiver.setByteArray(readBuf);
//                    break;
//
//                case BluetoothManager.MESSAGE_DEVICE_NAME:
//                    Logs.d(TAG, "Service - MESSAGE_DEVICE_NAME: ");
//
//                    // save connected device's name and notify using toast
//                    String deviceAddress = msg.getData().getString(Constants.SERVICE_HANDLER_MSG_KEY_DEVICE_ADDRESS);
//                    String deviceName = msg.getData().getString(Constants.SERVICE_HANDLER_MSG_KEY_DEVICE_NAME);
//
//                    if(deviceName != null && deviceAddress != null) {
//                        // Remember device's address and name
//                        mConnectionInfo.setDeviceAddress(deviceAddress);
//                        mConnectionInfo.setDeviceName(deviceName);
//
//                        Toast.makeText(getApplicationContext(),
//                                "Connected to " + deviceName, Toast.LENGTH_SHORT).show();
//                    }
//                    break;
//
//                case BluetoothManager.MESSAGE_TOAST:
//                    Logs.d(TAG, "Service - MESSAGE_TOAST: ");
//
////				Toast.makeText(getApplicationContext(),
////						msg.getData().getString(Constants.SERVICE_HANDLER_MSG_KEY_TOAST),
////						Toast.LENGTH_SHORT).show();
//                    break;
//
//            }	// End of switch(msg.what)
//
//            super.handleMessage(msg);
//        }
//    }	// End of class MainHandler


}
