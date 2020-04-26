package com.example.walletkeeper.packages;

import android.os.Bundle;

import java.util.Calendar;

public class Time {
    public int year,month,day,hour,minute,second;
    public String ID;
    private Calendar calendar = Calendar.getInstance();
    public Time()
    {
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH)+1;
        day=calendar.get(Calendar.DAY_OF_MONTH);
        hour=calendar.get(Calendar.HOUR);
        minute=calendar.get(Calendar.MINUTE);
        second=calendar.get(Calendar.SECOND);
        ID=""+year+month+day+hour+minute+second;
    }

}
