package com.example.targettempo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


class MyAdapter extends ArrayAdapter<UserActivity>
{
    Context context;
    ArrayList<UserActivity> user_activities;

    public MyAdapter(@NonNull Context context, ArrayList<UserActivity> activities) {
        super(context, R.layout.user_activity_row, activities);
        this.context = context;
        this.user_activities = activities;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inf = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        UserActivity current_activity = user_activities.get(position);

        View row = inf.inflate(R.layout.user_activity_row, parent, false);

        TextView header = row.findViewById(R.id.activity_header);
        TextView startTime = row.findViewById(R.id.start_time);
        TextView endTime = row.findViewById(R.id.end_time);

        Calendar current = Calendar.getInstance();

        int hour = current.get(Calendar.HOUR_OF_DAY);
        int minute = current.get(Calendar.MINUTE);
        int currentTimeMs = UserActivity.toDaySeconds(hour, minute);
        int timeDiff = current_activity.startTime - currentTimeMs;
        Button startButton = row.findViewById(R.id.startActivityButton);

        if(timeDiff <= 0 && !current_activity.started)
        {
            startButton.setVisibility(View.VISIBLE);
        }

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_activities.get(position).wastedMinutes = (-1*timeDiff)/60;
                user_activities.get(position).started = true;
                notifyDataSetChanged();
            }
        });
        //Add the rest of the parameters
        header.setText(current_activity.name);
        startTime.setText(UserActivity.getDayHours(current_activity.startTime)+":"+ UserActivity.getDayRemainderMins(current_activity.startTime));
        endTime.setText(UserActivity.getDayHours(current_activity.endTime)+":"+ UserActivity.getDayRemainderMins(current_activity.endTime));

        return row;
    }
}


public class ListViewActivity extends AppCompatActivity {

    ListView activity_list_view;
    ArrayList<UserActivity> userActivities;
    Button addActivityButton;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);

        //Replace this with fetch activitis code from DB or file storage
        userActivities = new ArrayList<>();

        System.out.println(userActivities.size());

        activity_list_view = findViewById(R.id.activity_list_view);
        adapter = new MyAdapter(this, userActivities);

        activity_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserActivity clickedActivity = userActivities.get(position);
                Intent showActivityTime = new Intent(ListViewActivity.this, TimeWasted.class);
                showActivityTime.putExtra("activity", clickedActivity);
                startActivity(showActivityTime);
            }
        });



        activity_list_view.setAdapter(adapter);

        addActivityButton = findViewById(R.id.addActivityButton);

        addActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addActivityIntent = new Intent(ListViewActivity.this, ScheduleInput.class);
                startActivityForResult(addActivityIntent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK) {
            UserActivity addedActivity = (UserActivity) data.getSerializableExtra("activity");
            userActivities.add(addedActivity);
            adapter.notifyDataSetChanged();
            Intent serviceIntent = new Intent(ListViewActivity.this, MyService.class);
            serviceIntent.putExtra("Activities", userActivities);
            startService(serviceIntent);
        }
    }


}