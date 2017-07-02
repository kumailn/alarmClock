package com.kumailn.alarmclock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TimePicker pickTime = (TimePicker)findViewById(R.id.timePicker);
        Button checkTime = (Button)findViewById(R.id.checkTimeButton);


        checkTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String hour = String.valueOf(pickTime.getHour());
                final String minute = String.valueOf(pickTime.getMinute());

                Toast.makeText(MainActivity.this, hour + " " + minute, Toast.LENGTH_LONG).show();
            }
        });



    }
}
