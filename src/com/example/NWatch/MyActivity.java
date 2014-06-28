package com.example.NWatch;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MyActivity extends Activity {
    private static final int REQUEST_ENABLE_BT = 1;
    Button ledOn;
    Button ledOff;
    Button connect;

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

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast toast = Toast.makeText(getApplicationContext(), "Bluetooth недоступен на вашем устройстве", Toast.LENGTH_SHORT);
            toast.show();
        }
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        OnClickListener connectL = new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };

       OnClickListener ledOnL = new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "Led on", Toast.LENGTH_SHORT);
                toast.show();
            }
        };

        OnClickListener ledOffL = new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "Led off", Toast.LENGTH_SHORT);
                toast.show();
            }
        };

        connect.setOnClickListener(connectL);
        ledOn.setOnClickListener(ledOnL);
        ledOff.setOnClickListener(ledOffL);

    }
}
