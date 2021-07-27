package com.example.targettempo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationBuilderWithBuilderAccessor;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ScheduleInput extends AppCompatActivity {

    /**This is where we will put all of the activities
     * And we are also instantiating all of the variables...
     */
    ArrayList<UserActivity> activities;

    TextInputEditText ActivityNameInput;
    Button continueInput, endInput;
    TimePicker StartTime, EndTime;
    Timer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_input2);
        timer = new Timer();
        activities = new ArrayList<>();
        ActivityNameInput = findViewById(R.id.ActivityNameInput);
        continueInput = findViewById(R.id.continueInput);
        StartTime = findViewById(R.id.StartTime);
        EndTime = findViewById(R.id.FinishTime);
        Button endInput = findViewById(R.id.endInput);
        endInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ScheduleInput.this,TimeWasted.class));

            }
        });
        /**
         * 12 pm
         * 1: 05 pm
         * 1h 05m -> seconds -> milliseconds ->
         * current approach: check every second to see if it's time for an activity
         * new approach:
         */
        /** This is where we are continuing our inputs. We are adding more and more activities
         * here so when we click the button we stay on the same page.
         */
        continueInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = ActivityNameInput.getText().toString();

                if(name == null || name == "")
                {
                    name = "Un-named activity";
                }

                int startHour = StartTime.getCurrentHour();
                int startMinute = StartTime.getCurrentMinute();

                //waste time = current time - start time

                int endHour = EndTime.getCurrentHour();
                int endMinute = EndTime.getCurrentMinute();

                Date currentDateTime = Calendar.getInstance().getTime();

                int hourDiff = startHour - currentDateTime.getHours();
                int minuteDiff = startMinute - currentDateTime.getMinutes();
                //ct = 1:15 am, ast = 2:20 am
                if(hourDiff < 0)
                {
                    hourDiff = 24 + hourDiff;
                }
                if(minuteDiff < 0) {
                    minuteDiff = 60 + minuteDiff;
                    hourDiff--;
                }
                String finalName = name;
                int delay = (hourDiff*60*60*1000)+(minuteDiff*60*1000);
                delay -= (currentDateTime.getSeconds()*1000);
                System.out.println("Delay: "+delay);
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        NotificationCompat.Builder notify = new NotificationCompat.Builder(ScheduleInput.this);
                        notify.setContentText("Random title").setContentText("Random text");
                        NotificationManager nmanager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        nmanager.notify(1, notify.build());
                        //Toast.makeText(ScheduleInput.this, finalName +" Time", Toast.LENGTH_SHORT).show();
                        //System.out.println("Activity hit");
                    }
                }, delay);
                activities.add(new UserActivity(name, startHour, startMinute, endHour, endMinute));
                Toast.makeText(ScheduleInput.this, "Activity '"+name+"' added", Toast.LENGTH_SHORT).show();
            }
        });

    }
}