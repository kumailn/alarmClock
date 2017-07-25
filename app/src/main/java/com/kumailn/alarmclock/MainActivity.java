package com.kumailn.alarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;
import java.io.File;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    PendingIntent pendingIntent;
    AlarmManager alarm_manager;
    String myURI = "NONE";
    Uri aa;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TimePicker pickTime = (TimePicker)findViewById(R.id.timePicker);
        Button checkTime = (Button)findViewById(R.id.checkTimeButton);
        Button stopTime = (Button)findViewById(R.id.stopTime);
        //Alarm manager object initialization
        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        final Calendar calendar = Calendar.getInstance();

        if(!(loadRingtone().equals("NONE"))){
            myURI = loadRingtone();
        }


        checkTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String hour = String.valueOf(pickTime.getHour());
                final String minute = String.valueOf(pickTime.getMinute());

                calendar.set(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                        pickTime.getHour(), pickTime.getMinute(), 0);

                Log.e(String.valueOf(calendar.getTimeInMillis()), " time in milis");

                Intent i = new Intent(MainActivity.this, MyReceiver.class);
                i.putExtra("ON", true);
                i.putExtra("URI", myURI);
                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, i, 0);

                alarm_manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),  pendingIntent);



                Toast.makeText(MainActivity.this, hour + " " + minute, Toast.LENGTH_LONG).show();
            }
        });

        stopTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(new Intent(getApplicationContext(), MyService.class));

            }
        });


    checkTime.setOnLongClickListener(new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            Intent intent = new Intent()
                    .setType("*/*")
                    .setAction(Intent.ACTION_GET_CONTENT);

            startActivityForResult(Intent.createChooser(intent, "Select a file"), 123);
            return true;
        }
    });

    stopTime.setOnLongClickListener(new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            File file= new File(aa.getPath());
            Toast.makeText(getApplicationContext(), String.valueOf(file.getName()), Toast.LENGTH_SHORT).show();
            return true;
        }
    });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==123 && resultCode==RESULT_OK) {
            Uri selectedfile = data.getData(); //The uri with the location of the file
            aa = selectedfile;

            Toast.makeText(getApplicationContext(), String.valueOf("Ringtone Set Successfully"), Toast.LENGTH_SHORT).show();

            myURI = String.valueOf(selectedfile);

            saveRingtone(myURI);
        }
    }

    public void saveRingtone(String ringtoneURI){
        SharedPreferences sharedPreferences = getSharedPreferences("myData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("RINGTONE", ringtoneURI);
        editor.commit();
    }

    public String loadRingtone(){
        SharedPreferences sharedPreferences = getSharedPreferences("myData", MODE_PRIVATE);
        String str = sharedPreferences.getString("RINGTONE", "NONE");
        return (str);
    }

}
