package com.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class JDateTime {

    public static String getCurrentDateTime(){
        Date date = Calendar.getInstance().getTime();
        TimeZone gmt8TimeZone = TimeZone.getTimeZone("GMT+8");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
        dateFormat.setTimeZone(gmt8TimeZone);
        String strDate = dateFormat.format(date);
        return strDate;
    }
    public static Date getDate(String date_string){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
        try{
            Date date = dateFormat.parse(date_string);
            return date;
        }catch(Exception e){
            return null;
        }
    }
}
