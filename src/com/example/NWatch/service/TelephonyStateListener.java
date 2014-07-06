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
    private static boolean wasOffHook = false;

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
                Logs.d(TAG, "IDLE");
                if (phoneRinging == true) {
                    if (wasOffHook == false) {
                        NotificationService.ledBlink();
                    }
                }
                phoneRinging = false;
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Logs.d(TAG, "OFFHOOK");
                NotificationService.ledOff();
                wasOffHook = true;
                phoneRinging = false;
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                System.out.println(incomingNumber + " IS RINGING");
                NotificationService.ledOn();
                phoneRinging = true;
                break;
        }
    }

    public boolean isPhoneRinging() {
        return phoneRinging;
    }
}
