package com.kumailn.alarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    PendingIntent pendingIntent;
    AlarmManager alarm_manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TimePicker pickTime = (TimePicker)findViewById(R.id.timePicker);
        Button checkTime = (Button)findViewById(R.id.checkTimeButton);
        //Alarm manager object initialization
        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        final Calendar calendar = Calendar.getInstance();




        checkTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String hour = String.valueOf(pickTime.getHour());
                final String minute = String.valueOf(pickTime.getMinute());

                calendar.set(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                        pickTime.getHour(), pickTime.getMinute(), 0);

                Log.e(String.valueOf(calendar.getTimeInMillis()), " time in milis");

                Intent i = new Intent(MainActivity.this, MyReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, i, 0);
                alarm_manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),  pendingIntent);

                Toast.makeText(MainActivity.this, hour + " " + minute, Toast.LENGTH_LONG).show();
            }
        });



    }
}
