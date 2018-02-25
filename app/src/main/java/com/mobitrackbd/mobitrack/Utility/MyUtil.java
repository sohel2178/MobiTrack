package com.mobitrackbd.mobitrack.Utility;



import com.mobitrackbd.mobitrack.Model.Span;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Sohel on 1/27/2018.
 */

public class MyUtil {
    private static final String DATE_FORMAT="dd-MMM-yyyy";
    private static final String DATE_FORMAT_TWO="yyyy-MM-dd";
    private static final String TIME_FORMAT="yyyy-MM-dd HH:mm:ss";


    public static String getStringDate(Date date){
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        return df.format(date);
    }
    public static String getStringDateTwo(Date date){
        DateFormat df = new SimpleDateFormat(DATE_FORMAT_TWO);
        return df.format(date);
    }

    public static long getTimeInMilis(String dateStr){

        DateFormat df = new SimpleDateFormat(TIME_FORMAT);
        Date date = null;
        try {
            date = df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static String getStringDate2(String time){

        DateFormat df = new SimpleDateFormat(TIME_FORMAT);
        Date date = new Date(Long.parseLong(time));
        return df.format(date);
    }

    public static int getDuration(long fDate,long sDate){
        long diff = fDate-sDate;
        return (int) (TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS)+1);
    }

    public static long getBeginingTime(long time){

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);

        int year = calendar.get(Calendar.YEAR);
        int dayofmonth = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);

        calendar.set(year,month,dayofmonth,0,0,0);
        return calendar.getTimeInMillis();
    }

    public static long getBeginingTime(Date date){

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());

        int year = calendar.get(Calendar.YEAR);
        int dayofmonth = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);

        calendar.set(year,month,dayofmonth,0,0,0);
        return calendar.getTimeInMillis();
    }

    public static List<Span> getSpanList(Date date){

        List<Span> spanList = new ArrayList<>();

        long startTime = getBeginingTime(date);

        for(int i=0;i<24;i++){
            Span span = new Span(i);
            span.setStartTime(startTime);
            span.setEndTime(oneHourInterval(startTime)-1000);

            startTime = oneHourInterval(startTime);

            spanList.add(span);

        }

        return spanList;


    }

    public static long getEndingTime(long time){

        long beginingTime = getBeginingTime(time);

        return beginingTime+(23*60*60+59*60+59)*1000;

    }

    public static long getEndingTime(Date time){

        long beginingTime = getBeginingTime(time);

        return beginingTime+(23*60*60+59*60+59)*1000;

    }

    public static long oneHourInterval(long time){


        return time+(60*60)*1000;

    }

    public static String getTwoDecimalFormat(double value){

        return String.format("%.2f", value);

    }






}
