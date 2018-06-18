package app.khushbu.trackerbot;

import android.os.CountDownTimer;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class Time {

    Time(){

    }
    public void setCountdown(final TextView textView, String start_time, String end_time) {
        Calendar start_calendar = Calendar.getInstance();
        Calendar end_calendar = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        try {
            start_calendar.setTime(sdf.parse(start_time));
            end_calendar.setTime(sdf.parse(end_time));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long start_millis = start_calendar.getTimeInMillis(); //get the start time in milliseconds
        long end_millis = end_calendar.getTimeInMillis(); //get the end time in milliseconds
        long total_millis = (end_millis - start_millis); //total time in milliseconds
        end_calendar.set(2015, 10, 6); // 10 = November, month start at 0 = January

        //1000 = 1 second interval
        CountDownTimer cdt = new CountDownTimer(total_millis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished);
                millisUntilFinished -= TimeUnit.DAYS.toMillis(days);

                long hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished);
                millisUntilFinished -= TimeUnit.HOURS.toMillis(hours);

                long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                millisUntilFinished -= TimeUnit.MINUTES.toMillis(minutes);

                long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);

                textView.setText(days + ":" + hours + ":" + minutes + ":" + seconds); //You can compute the millisUntilFinished on hours/minutes/seconds
            }

            @Override
            public void onFinish() {
                textView.setText("Finish!");
            }
        };
        cdt.start();

    }

    public static String getCurrentTimeStamp() {
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
