package app.khushbu.trackerbot;


import android.os.Build;
import android.os.CountDownTimer;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Time {

    Time(){

    }
    public void setCountdown(long time) {

        CountDownTimer cdt = new CountDownTimer(time, 1000) {

            public void onTick(long millisUntilFinished) {
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
            }

        }.start();


    }

    public String getCurrentTimeStamp(String timeZone) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
        Date now = new Date();
        sdfDate.setTimeZone(TimeZone.getTimeZone(timeZone));
        sdfTime.setTimeZone(TimeZone.getTimeZone(timeZone));
        String strDate = sdfDate.format(now)+"T"+sdfTime.format(now);
        //Log.i("xyz",strDate);
        return strDate;
    }

    public String convertTime(String time){
        time = time.substring(0,10)+" "+time.substring(11,time.length());
        SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.ENGLISH);
        sdfTime.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().getDisplayName()));
        DateFormat format = sdfTime;
        //format.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date date = (Date)format.parse(time);
            Log.i("date",time);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.ENGLISH);
            //sdf.setTimeZone(TimeZone.getTimeZone("IST"));
            time = sdf.format(date);
            time = time.substring(0,10)+"T"+time.substring(11,time.length());
            Log.i("date1",time);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return time;
    }
}
