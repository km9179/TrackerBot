package app.khushbu.trackerbot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import android.os.CountDownTimer;
import android.widget.Toast;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>  {

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    Context context;
    int id;   //id for identifying fragment------->1 = ongoing fragment and 2 = upcoming fragment

    // data is passed into the constructor
    RecyclerViewAdapter(Context context,int id) {
        this.context=context;
        this.id=id;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row,parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        //siteKey = tag of buttons on homePage

        int siteKey=ContestListActivity.siteKey;
        if(siteKey==1)
            holder.imageView.setImageResource(R.drawable.codeforces_logo);
        else if(siteKey==2)
            holder.imageView.setImageResource(R.drawable.codechef_logo);
        else if(siteKey==12)
            holder.imageView.setImageResource(R.drawable.topcoder);
        else if(siteKey==73)
            holder.imageView.setImageResource(R.drawable.hackerearth_logo);
        else if(siteKey==63)
            holder.imageView.setImageResource(R.drawable.hackerrank);


        if (this.id == 1) {

            //ongoing fragment

            holder.textView1.setText(Ongoing.event_names.get(position));
            String startTime= DownloadClass.getCurrentTimeStamp();
            startTime=startTime.substring(0,10)+" "+startTime.substring(11,startTime.length());
            String endTime=Ongoing.event_end_time.get(position);
            endTime=endTime.substring(0,10)+" "+endTime.substring(11,endTime.length());
            //Log.i("Time",startTime+" "+endTime);
            setCountdown(holder.textView2,startTime,endTime);

            /*holder.v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(v.getContext(),WebActivity.class);
                    intent.putExtra("url",Ongoing.event_url.get(position));
                    v.getContext().startActivity(intent);
                }
            });*/
        }
        else {

            //upcoming fragment

            holder.textView1.setText(Upcoming.event_names.get(position));
            String z=Upcoming.event_start_time.get(position);
            holder.textView2.setText(z);

            /*holder.v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(v.getContext(),WebActivity.class);
                    intent.putExtra("url",Upcoming.event_url.get(position));
                    v.getContext().startActivity(intent);
                }
            });*/

        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        if(this.id==1)
            return Ongoing.event_names.size();
        else
            return Upcoming.event_names.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView1,textView2;
        ImageView imageView;
        View v;


        ViewHolder(View itemView) {
            super(itemView);
            v=itemView;
            textView1 = (TextView) itemView.findViewById(R.id.name);
            textView2 = (TextView) itemView.findViewById(R.id.time);
            imageView = (ImageView)itemView.findViewById(R.id.imageView);
            //itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        if(this.id==1)
            return Ongoing.event_names.get(id);
        else
            return Upcoming.event_names.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }


    //set countdown timer

    public void setCountdown(final TextView textView,String start_time, String end_time){
        Calendar start_calendar = Calendar.getInstance();
        Calendar end_calendar = Calendar.getInstance();

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
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
}
