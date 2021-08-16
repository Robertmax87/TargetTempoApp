package com.example.targettempo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationBuilderWithBuilderAccessor;
import androidx.core.app.NotificationCompat;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ScheduleInput extends AppCompatActivity {
    private NotificationHelper mNotificationHelper;

    /**
     * This is where we will put all of the activities
     * And we are also instantiating all of the variables
     * from our schedule activity class.
     */
    ArrayList<UserActivity> activities;
    TextInputEditText ActivityNameInput;
    Button continueInput, endInput;
    TimePicker StartTime, EndTime;
    Timer timer;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        /** the onCreate method with a savedInstanceState takes all of the input information
         * and stores it together.
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_input2);
        ActivityNameInput = findViewById(R.id.ActivityNameInput);
        StartTime = findViewById(R.id.StartTime);
        EndTime = findViewById(R.id.FinishTime);
        Button endInput = findViewById(R.id.endInput);
        mNotificationHelper = new NotificationHelper(this);
        endInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(ScheduleInput.this,TimeWasted.class));

                String name = ActivityNameInput.getText().toString();

                sendOnChannel1("Title", "Message");

                if (name == null || name == "") {
                    name = "Un-named activity";
                }

                int startHour = StartTime.getCurrentHour();
                int startMinute = StartTime.getCurrentMinute();

                //waste time = current time - start time

                int endHour = EndTime.getCurrentHour();
                int endMinute = EndTime.getCurrentMinute();

                UserActivity newActivity = new UserActivity(name, startHour, startMinute, endHour, endMinute);
                Intent response = new Intent();
                response.putExtra("activity", newActivity);
                setResult(Activity.RESULT_OK, response);
                finish();
            }

        });

    }

    public void onTimeSet(TimePicker startTime, int hourOfDay, int minute) {
        int startHour = StartTime.getCurrentHour();
        int startMinute = StartTime.getCurrentMinute();

        //waste time = current time - start time

        int endHour = EndTime.getCurrentHour();
        int endMinute = EndTime.getCurrentMinute();
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, startHour);
        c.set(Calendar.MINUTE, startMinute);
        c.set(Calendar.SECOND, 0);

    }

    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

    }

    public void sendOnChannel1(String title, String message) {
    NotificationCompat.Builder nb = mNotificationHelper.getHelperNotification(title, message);
    mNotificationHelper.getManager().notify(1, nb.build());
    }

}