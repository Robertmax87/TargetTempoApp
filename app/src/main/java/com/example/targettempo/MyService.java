package com.example.targettempo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {

    ArrayList<UserActivity> userActivities;
    ArrayList<Timer> activityTimers;
    private NotificationHelper mNotificationHelper;

    public MyService() {

    }

    private void createNotification(String title, String message) {
        NotificationCompat.Builder nb = mNotificationHelper.getHelperNotification(title, message);
        mNotificationHelper.getManager().notify(1, nb.build());
    }

    //use in time wasted class
    public int getTotalTimeWasted()
    {
        int totalWastedMinutes = 0;
        for(int i = 0;i<userActivities.size();i++)
        {
            totalWastedMinutes += userActivities.get(i).wastedMinutes;
        }
        return totalWastedMinutes;
    }

    private void setTimers()
    {
        Calendar current = Calendar.getInstance();

        int hour = current.get(Calendar.HOUR_OF_DAY);
        int minute = current.get(Calendar.MINUTE);
        int seconds = current.get(Calendar.SECOND);

        int currentTimeMs = UserActivity.toDaySeconds(hour, minute) + seconds;
        for (int i = 0;i < userActivities.size();i++)
        {
            UserActivity activity = userActivities.get(i);
            int timeLeft = activity.startTime - currentTimeMs;
            if(timeLeft < 0)
            {
                timeLeft = 1;
            }
            Timer timer = new Timer();
            System.out.println("Starting timer for "+activity.name);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    createNotification("Activity time", "Time for Activity "+activity.name);
                }
            }, timeLeft*1000);
            activityTimers.add(timer);
            /**
             * setting time 11:25:25
             * starting time 11:26:25
             *
             */
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.userActivities = (ArrayList<UserActivity>) intent.getSerializableExtra("Activities");
        activityTimers = new ArrayList<>();
        mNotificationHelper = new NotificationHelper(this);
        setTimers();
        return START_STICKY;
    }
}