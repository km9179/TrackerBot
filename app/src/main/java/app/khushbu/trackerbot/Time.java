package app.khushbu.trackerbot;


import android.os.CountDownTimer;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    public String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
        Date now = new Date();
        sdfDate.setTimeZone(TimeZone.getTimeZone("UTC"));
        sdfTime.setTimeZone(TimeZone.getTimeZone("UTC"));
        String strDate = sdfDate.format(now)+"T"+sdfTime.format(now);
        //Log.i("xyz",strDate);
        return strDate;
    }
}
