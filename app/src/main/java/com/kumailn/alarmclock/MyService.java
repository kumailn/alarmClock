package com.kumailn.alarmclock;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
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

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getBaseContext(), "In service", Toast.LENGTH_SHORT).show();
        Log.e("In service","");
        //NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent_main = new Intent(this.getApplicationContext(), MainActivity.class);
        Uri alarmUri;
        alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), alarmUri);
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
}
