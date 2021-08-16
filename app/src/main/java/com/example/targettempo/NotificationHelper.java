package com.example.targettempo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper{
    public static final String NotificationName = "Activity Ready To Start";
    public static final String Notifyer = "Start Activity";
    private NotificationManager managing;
    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotify();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createNotify(){
        NotificationChannel helper = new NotificationChannel(NotificationName, Notifyer,NotificationManager.IMPORTANCE_DEFAULT);
        helper.enableLights(true);
        helper.enableVibration(true);
        helper.setLightColor(R.color.design_default_color_primary);
        helper.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        getManager().createNotificationChannel(helper);
    }
    public NotificationManager getManager(){
        if(managing == null){
            managing = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        }
        return managing;
    }
    public NotificationCompat.Builder getHelperNotification(String title, String message){
      return new NotificationCompat.Builder(getApplicationContext(),NotificationName)
              .setContentTitle(title)
              .setContentText(message)
              .setSmallIcon(R.drawable.ic_baseline_input_24);
    }
}
