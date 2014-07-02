package com.example.NWatch.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

/**
 * Created by AZGARD on 02.07.2014.
 */
public class ServiceReceiver extends BroadcastReceiver {
    private TelephonyManager mTelephonyManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        TelephonyStateListener mTelStateListener = new TelephonyStateListener();
        mTelephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        mTelephonyManager.listen(mTelStateListener, PhoneStateListener.LISTEN_SERVICE_STATE);
        mTelephonyManager.listen(mTelStateListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    public void onDestroy() {
        mTelephonyManager.listen(null, PhoneStateListener.LISTEN_NONE);
    }
}
