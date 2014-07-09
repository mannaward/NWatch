package com.example.NWatch;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.ListPreference;
import android.preference.PreferenceManager;
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
    Button connect;

    String customPref;

    private boolean isDefNotification;
    private boolean isIncCall;
    private boolean isSMS;
    private boolean isSMSFilter;
    private boolean isEmail;
    private boolean isMissCalls;
    private boolean isPhoneTime;

    private String macAddress;
    private String eLogin;
    private String ePass;
    private String wType;

    private BluetoothManager mBtManager;
    private Handler mHandler = new Handler();
    private ServiceReceiver mServiceReceiver;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

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

        ServiceReceiver mSReceiver = new ServiceReceiver();
        IntentFilter filter = new IntentFilter(Constants.NOTIFICATION_LISTENER);
        registerReceiver(mSReceiver, filter);

        OnClickListener connectL = new OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(context, NotificationService.class));
            }
        };

        Button prefBtn = (Button) findViewById(R.id.prefBtn);
        prefBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent settingsActivity = new Intent(getBaseContext(),
                        Preferences.class);
                startActivity(settingsActivity);
            }
        });

        connect.setOnClickListener(connectL);
    }

    @Override
    public void onStart() {
        super.onStart();
        getPrefs();
    }

    private void getPrefs() {
        // Get the xml/preferences.xml preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        isDefNotification = prefs.getBoolean("defNotification", true);
        isIncCall = prefs.getBoolean("incomingCallNotification", false);
        isSMS = prefs.getBoolean("smsNotification", false);
        isSMSFilter = prefs.getBoolean("smsFilter", false);
        isEmail = prefs.getBoolean("emailNotification", false);
        isMissCalls = prefs.getBoolean("missingCallNotification", false);
        isPhoneTime = prefs.getBoolean("timeCheckbox", true);

        macAddress = prefs.getString("macAddress", "00:00:00:00:00:00");
        eLogin = prefs.getString("login", "Login");
        ePass = prefs.getString("pass", "Password");
        wType = prefs.getString("watchType", "type1");

        // Get the custom preference
        SharedPreferences mSettings = getSharedPreferences("myCustomSharedPrefs", Activity.MODE_PRIVATE);
        customPref = mSettings.getString("myCusomPref", "");
    }

    public boolean isSMSFilter() {
        return isSMSFilter;
    }

    public boolean isDefNotification() {
        return isDefNotification;
    }

    public boolean isIncCall() {
        return isIncCall;
    }

    public boolean isSMS() {
        return isSMS;
    }

    public boolean isEmail() {
        return isEmail;
    }

    public boolean isMissCalls() {
        return isMissCalls;
    }

    public boolean isPhoneTime() {
        return isPhoneTime;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public String getELogin() {
        return eLogin;
    }

    public String getEPass() {
        return ePass;
    }

    public String getWType() {
        return wType;
    }
}
