package com.example.NWatch;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
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

public class NWatchActivity extends Activity {
    private static final String TAG = "main_activity";

    private static final int REQUEST_ENABLE_BT = 1;

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
        final Context context = this;

        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast toast = Toast.makeText(getApplicationContext(), "Bluetooth недоступен на вашем устройстве", Toast.LENGTH_SHORT);
            toast.show();
        }
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, Constants.REQUEST_ENABLE_BT);
        }

        ServiceReceiver mSReceiver = new ServiceReceiver();
        IntentFilter filter = new IntentFilter(Constants.NOTIFICATION_LISTENER);
        registerReceiver(mSReceiver, filter);

        Button connect = (Button) findViewById(R.id.button3);
        connect.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(context, NotificationService.class));
            }
        });

        Button prefBtn = (Button) findViewById(R.id.prefBtn);
        prefBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent settingsActivity = new Intent(getBaseContext(), Preferences.class);
                startActivity(settingsActivity);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

}
