package com.kumailn.alarmclock;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {
    public MyService() {
    }

    Ringtone ringtone;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        boolean Alarm_State = intent.getBooleanExtra("ON", false);
        String myURI = intent.getStringExtra("URI");

        Log.e("Alarm State:", String.valueOf(Alarm_State));

        Toast.makeText(getBaseContext(), "In service", Toast.LENGTH_SHORT).show();
        Log.e("In service","");
        //NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent_main = new Intent(this.getApplicationContext(), MainActivity.class);
        Uri alarmUri;
        //alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if(!(myURI.equals("NONE"))){
            alarmUri = Uri.parse(myURI);
        }
        else{
            alarmUri = Uri.parse("android.resource://" + "com.kumailn.alarmclock/" + "raw/fellow");
        }

        ringtone = RingtoneManager.getRingtone(getApplicationContext(), alarmUri);
        ringtone.setAudioAttributes(new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).build());

        ringtone.play();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        PendingIntent pendingIntentMain = PendingIntent.getActivity(this, 0, intent_main, PendingIntent.FLAG_CANCEL_CURRENT);
        Notification notificationPopup = new Notification.Builder(this).setContentTitle("Alarm is ON!").setContentText("Click here")
                .setContentIntent(pendingIntentMain).setAutoCancel(true).setSmallIcon(R.drawable.acd).setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_ALL).build();
        notificationManager.notify(0, notificationPopup);


        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(ringtone != null){
            ringtone.stop();
            Log.e("Ringtone"," success fully stopped");
        }

        Log.e("Service","Now Shutting Down");
        this.stopSelf();
    }
}
