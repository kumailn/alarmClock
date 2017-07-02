package com.kumailn.alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "In Receiver", Toast.LENGTH_SHORT).show();
        Log.e("In the receiver", "");
        Intent ii = new Intent(context, MyService.class);
        context.startService(ii);
    }
}