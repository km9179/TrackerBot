package app.khushbu.trackerbot;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;



public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    Context context;
    Upcoming upcoming;
    Fav fav;



    int id;   //id for identifying fragment------->1 = ongoing fragment and 2 = upcoming fragment

    // data is passed into the constructor
    RecyclerViewAdapter(Context context, int id) {
        this.context = context;
        this.id = id;
        upcoming = new Upcoming();
        fav = new Fav();
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=null;
        if (id == 1)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ongoing_row, parent, false);
        else if (id == 2)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_row, parent, false);
        else if(id==3)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_row, parent, false);
        else if(id==4) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_ongoing_row, parent, false);

        }
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        //siteKey = tag of buttons on homePage

        if (this.id == 1) {

            //ongoing fragment

            Time time = new Time();

            holder.imageView.setImageResource(Ongoing.ongoingContestData.get(position).getImgId());
            holder.textView1.setText(Ongoing.ongoingContestData.get(position).getEvent_names());
            String startTime = time.getCurrentTimeStamp("UTC");
            startTime = startTime.substring(0, 10) + " " + startTime.substring(11, startTime.length());
            String endTime = Ongoing.ongoingContestData.get(position).getEvent_end_time();
            endTime = endTime.substring(0, 10) + " " + endTime.substring(11, endTime.length());

            if(holder.cdt != null)
                holder.cdt.cancel();
            holder.update(holder.textView2, startTime, endTime);


        } else if (id == 2) {

            //upcoming fragment
            if (!Upcoming.is_in_actionMode) {
                ContestListActivity.textToolbar.setVisibility(View.GONE);
                Upcoming.upcomingContestData.get(position).setSelected(false);
                ContestListActivity.toolbarCheckbox.setChecked(false);
                ContestListActivity.toolbarCheckbox.setVisibility(View.GONE);
                holder.checkBox.setChecked(false);
                holder.checkBox.setVisibility(View.GONE);

            } else {
                ContestListActivity.textToolbar.setVisibility(View.VISIBLE);
                if (Upcoming.upcomingContestData.get(position).isSelected()) {
                    holder.checkBox.setChecked(true);
                    holder.checkBox.setVisibility(View.VISIBLE);
                } else {
                    holder.checkBox.setChecked(false);
                    holder.checkBox.setVisibility(View.GONE);
                }
            }
            holder.imageView.setImageResource(Upcoming.upcomingContestData.get(position).getImgId());
            holder.textView1.setText(Upcoming.upcomingContestData.get(position).getEvent_names());
            String z = Upcoming.upcomingContestData.get(position).getEvent_start_time();
            z = new Time().convertTime(z);
            holder.textView2.setText(z);


            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    upcoming.prepareSelection(v, position);
                }
            });

        }
        else if(this.id==3) {
            // Favourites

            if (!Fav.is_in_actionMode) {
                Fav.textToolbar.setVisibility(View.GONE);
                Fav.favContestData.get(position).setSelected(false);
                Fav.toolbarCheckbox.setChecked(false);
                Fav.toolbarCheckbox.setVisibility(View.GONE);
                holder.checkBox.setChecked(false);
                holder.checkBox.setVisibility(View.GONE);

            } else {
                Fav.textToolbar.setVisibility(View.VISIBLE);
                if (Fav.favContestData.get(position).isSelected()) {
                    holder.checkBox.setChecked(true);
                    holder.checkBox.setVisibility(View.VISIBLE);
                } else {
                    holder.checkBox.setChecked(false);
                    holder.checkBox.setVisibility(View.GONE);
                }
            }
            holder.imageView.setImageResource(Fav.favContestData.get(position).getImgId());
            holder.textView1.setText(Fav.favContestData.get(position).getEvent_names());
            String z = Fav.favContestData.get(position).getEvent_start_time();
            holder.textView2.setText(z);


            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fav.prepareSelection(v, position);
                }
            });

        }
        else if(id==4){

            //All ongoing contest

            //holder.imageView.setImageResource(ALL_CONTEST_Activity.allContestList.get(position).getImgId());
            holder.textView1.setText(ALL_CONTEST_Activity.allContestList.get(position).getEvent_names());
            holder.textView2.setText(ALL_CONTEST_Activity.allContestList.get(position).getEvent_end_time());
            holder.siteName.setText(ALL_CONTEST_Activity.allContestList.get(position).getSite_name());
            if(holder.cdt != null){
                holder.cdt.cancel();
            }
            String startTime = new Time().getCurrentTimeStamp("UTC");
            startTime = startTime.substring(0, 10) + " " + startTime.substring(11, startTime.length());
            String endTime = ALL_CONTEST_Activity.allContestList.get(position).getEvent_end_time();
            endTime = endTime.substring(0, 10) + " " + endTime.substring(11, endTime.length());

            holder.update(holder.textView2, startTime, endTime);
        }


    }

    // total number of rows
    @Override
    public int getItemCount() {
        if (this.id == 1)
            return Ongoing.ongoingContestData.size();
        else if(this.id==2)
            return Upcoming.upcomingContestData.size();
        else if(this.id==3)
            return Fav.favContestData.size();
        else if(this.id==4)
            return ALL_CONTEST_Activity.allContestList.size();
        return 0;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
        TextView textView1, textView2,siteName;
        ImageView imageView;
        View v;
        CardView cardView;
        CheckBox checkBox;
        CountDownTimer cdt;
        ViewHolder(View itemView) {
            super(itemView);
            v = itemView;
            textView1 = (TextView) itemView.findViewById(R.id.name);
            textView2 = (TextView) itemView.findViewById(R.id.time);
            siteName = (TextView) itemView.findViewById(R.id.siteName);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            cardView.setOnLongClickListener(this);
            cardView.setOnClickListener(this);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);

        }


        @Override
        public boolean onLongClick(View v) {
            if (mClickListener != null) {
                mClickListener.onLongClick(v, getAdapterPosition());
                return true;
            }
            return false;
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) {
                mClickListener.onClick(v, getAdapterPosition());
            }
        }

        public void update(final TextView textView, String start_time, String end_time){
            Calendar start_calendar = Calendar.getInstance();
            Calendar end_calendar = Calendar.getInstance();
            /*String start_day=start_time.substring(0,10);
            String end_day =end_time.substring(0,10);
            SimpleDateFormat sdf;
            if(!start_day.equals(end_day)){
                start_time = start_day;
                end_time = end_day;
                sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);

            }*/
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
            cdt = new CountDownTimer(total_millis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    long days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished);
                    millisUntilFinished -= TimeUnit.DAYS.toMillis(days);

                    long hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished);
                    millisUntilFinished -= TimeUnit.HOURS.toMillis(hours);

                    long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                    millisUntilFinished -= TimeUnit.MINUTES.toMillis(minutes);

                    long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);

                    if (days == 0) {
                        textView.setText(String.format("%02d",hours) + ":" + String.format("%02d",minutes) + ":" + String.format("%02d",seconds) + "  left"); //You can compute the millisUntilFinished on hours/minutes/seconds

                    }
                    else if(days == 1){
                        textView.setText(String.format("%02d",days) + " day left");
                    }
                    else{
                        textView.setText(String.format("%02d",days) + " days left");
                    }
                }

                @Override
                public void onFinish() {
                    textView.setText("Finished!");
                }
            };
            cdt.start();
        }
    }

    // convenience method for getting data at click position
    ContestData getItem(int id) {
        if (this.id == 1)
            return Ongoing.ongoingContestData.get(id);
        else if (this.id == 2)
            return Upcoming.upcomingContestData.get(id);
        else if(this.id==3)
            return Fav.favContestData.get(id);
        else if(this.id==4)
            return ALL_CONTEST_Activity.allContestList.get(id);
        return null;

    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onLongClick(View view, int position);

        void onClick(View view, int position);
    }
}
