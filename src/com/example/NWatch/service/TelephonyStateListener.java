package com.example.NWatch.service;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;
import com.example.NWatch.utils.Logs;

/**
 * Created by AZGARD on 02.07.2014.
 */
public class TelephonyStateListener extends PhoneStateListener {
    private static final String TAG = "TelStateListener";
    private static boolean phoneRinging = false;

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
                Logs.d(TAG, "IDLE");
                phoneRinging = false;
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Logs.d(TAG, "OFFHOOK");
                phoneRinging = false;
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                Logs.d(TAG, "RINGING");
                phoneRinging = true;
                break;
        }
    }

    public boolean isPhoneRinging() {
        return phoneRinging;
    }
}
