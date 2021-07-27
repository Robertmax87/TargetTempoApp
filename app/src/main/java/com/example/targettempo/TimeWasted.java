package com.example.targettempo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimeWasted extends AppCompatActivity {

    TextView Chronos;
    TextView timeWasted;
    Timer timer;

    protected void updateChronos(int hourDiff, int minuteDiff)
    {
        String timeLeftMessage = "Time Left";
        String lateMessage = "You are late by";
        if(minuteDiff < 0)
        {
            // late
            //timeWasted.setText(lateMessage);
            int factor = -1;
            if(hourDiff < 0)
            {
                factor = 1;
            }
            hourDiff += factor;
            minuteDiff = 60+minuteDiff;
        }
        if(hourDiff < 0)
        {
            //late
            timeWasted.setText(lateMessage);
            hourDiff *= -1;
        }
        else
        {
            //we have time
            timeWasted.setText(timeLeftMessage);
        }
        Chronos.setText(hourDiff+":"+minuteDiff);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_wasted);
        //TODO: retrieve activity from the intent
        UserActivity currentActivity = new UserActivity("Test", 10, 00, 13, 00);

        this.Chronos = findViewById(R.id.Chronos);
        this.timeWasted = findViewById(R.id.timeWasted);
        Button Return = (Button)findViewById(R.id.Return);

        timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                Date currentDateTime = Calendar.getInstance().getTime();
                int hourDiff = currentActivity.startHour - currentDateTime.getHours();
                int minuteDiff = currentActivity.startMinute - currentDateTime.getMinutes();

                updateChronos(hourDiff, minuteDiff);

            }
        }, 0, 60*1000);

        Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TimeWasted.this, ScheduleInput.class));
            }
        });

    }

}