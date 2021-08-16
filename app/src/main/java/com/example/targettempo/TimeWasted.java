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

    protected void updateChronos(int timeDiffMs)
    {
        String timeLeftMessage = "Time Left";
        String lateMessage = "You are late by";
        String onTimeMEssage = "It's time for your activity";

        if(timeDiffMs == 0)
        {
            timeWasted.setText(onTimeMEssage);
        }
        else if(timeDiffMs > 0)
        {
            timeWasted.setText(timeLeftMessage);
        }
        else
        {
            timeDiffMs *= -1;
            timeWasted.setText(lateMessage);
        }

        int hourDiff = UserActivity.getDayHours(timeDiffMs);
        int minuteDiff = UserActivity.getDayRemainderMins(timeDiffMs);

        Chronos.setText(hourDiff+":"+minuteDiff);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_wasted);
        //TODO: retrieve activity from the intent
        //UserActivity currentActivity = new UserActivity("Test", 12, 00, 18,48 );
        UserActivity currentActivity = (UserActivity) getIntent().getSerializableExtra("activity");

        this.Chronos = findViewById(R.id.Chronos);
        this.timeWasted = findViewById(R.id.timeWasted);
        Button Return = (Button)findViewById(R.id.Return);

        timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                /**if the start hour has passed then do the end time minus the current time and
                 * display that to show how many minutes you have left...
                 */

                Calendar current = Calendar.getInstance();
                //int hourBlock = currentActivity.endHour - currentActivity.startHour;
                //int minBlock = currentActivity.endHour - currentActivity.startMinute;
                int currentTimeMs = UserActivity.toDaySeconds(current.get(Calendar.HOUR_OF_DAY), current.get(Calendar.MINUTE));
                int timeDiffMs = currentActivity.startTime - currentTimeMs;
                /**
                int minuteDiff =  currentDateTime.getMinutes()- currentActivity.startMinute;
                int endHour = currentActivity.endHour - currentDateTime.getHours();
                int endMinute = currentActivity.endMinute - currentDateTime.getMinutes();
                 **/
                updateChronos(timeDiffMs);

            }
        }, 0, 60*1000);

        Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(TimeWasted.this, ScheduleInput.class));
                finish();
            }
        });

    }

}