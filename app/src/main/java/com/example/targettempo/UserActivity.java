package com.example.targettempo;

import java.io.Serializable;

public class UserActivity implements Serializable
{
    public int startHour;
    public int startMinute;
    public int endHour;
    public int endMinute;
    public String name;

    public UserActivity(String name, int sh, int sm, int eh, int em)
    {

        this.name = name;
        startHour = sh;
        startMinute = sm;
        endHour = eh;
        endMinute = em;
    }
}
