package com.example.targettempo;

import java.io.Serializable;

public class UserActivity implements Serializable
{
    public int startTime;
    public int endTime;
    public String name;
    public int wastedMinutes;
    public boolean started;


    public static int toDaySeconds(int hour, int mins)
    {
        return (hour*3600)+(mins*60);
    }

    public static int getDayHours(int seconds)
    {
        return seconds/3600;
    }

    public static int getDayRemainderMins(int seconds)
    {
        int mins = seconds/60;
        int rmins = mins%60;
        return rmins;
    }


    public UserActivity(String name, int sh, int sm, int eh, int em)
    {
        this.name = name;
        this.startTime = toDaySeconds(sh, sm);
        this.endTime = toDaySeconds(eh, em);
        this.wastedMinutes = 0;
        started = false;
    }
}
