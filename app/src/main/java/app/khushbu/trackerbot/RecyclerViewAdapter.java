package app.khushbu.trackerbot;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    Context context;
    Upcoming upcoming;
    ContentValues values=new ContentValues();
    Helper helper;
    SQLiteDatabase db = helper.getWritableDatabase();

    int id;   //id for identifying fragment------->1 = ongoing fragment and 2 = upcoming fragment

    // data is passed into the constructor
    RecyclerViewAdapter(Context context, int id) {
        this.context = context;
        this.id = id;
        upcoming = new Upcoming();
        //Helper helper = new Helper(context);
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (id == 1)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ongoing_row, parent, false);
        else if(id==2)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_row, parent, false);
        else
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_row, parent, false);

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
            String startTime = time.getCurrentTimeStamp();
            startTime = startTime.substring(0, 10) + " " + startTime.substring(11, startTime.length());
            String endTime = Ongoing.ongoingContestData.get(position).getEvent_end_time();
            endTime = endTime.substring(0, 10) + " " + endTime.substring(11, endTime.length());

            time.setCountdown(holder.textView2, startTime, endTime);


        } else if(id==2) {

            //upcoming fragment
            if(!Upcoming.is_in_actionMode){
                ContestListActivity.textToolbar.setVisibility(View.GONE);
                Upcoming.upcomingContestData.get(position).setSelected(false);
                holder.checkBox.setChecked(false);
                holder.checkBox.setVisibility(View.GONE);

            }
            else{
                ContestListActivity.textToolbar.setVisibility(View.VISIBLE);
                if(Upcoming.upcomingContestData.get(position).isSelected()){
                    holder.checkBox.setChecked(true);
                    holder.checkBox.setVisibility(View.VISIBLE);
                }
                else{
                    holder.checkBox.setChecked(false);
                    holder.checkBox.setVisibility(View.GONE);
                }
            }
            holder.imageView.setImageResource(Upcoming.upcomingContestData.get(position).getImgId());
            holder.textView1.setText(Upcoming.upcomingContestData.get(position).getEvent_names());
            String z = Upcoming.upcomingContestData.get(position).getEvent_start_time();
            holder.textView2.setText(z);


            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    upcoming.prepareSelection(v,position);
                }
            });

        }
        else
        {


        }

        //View itv;
        // helper=new Helper(context);

        //holder.textView.setText(Upcoming.event_names.get(position));
        /*holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ColorDrawable cd = (ColorDrawable) v.getBackground();
                int colorCode = cd.getColor();
                if (colorCode == Color.parseColor("#9be9f0")) {
                    v.setBackgroundColor(Color.parseColor("#ffffff"));
                    String selection = CON_NAME + " LIKE ?";
                    String[] selectionArgs = {};

                }
            }
        });
        //itvv=holder.itv;

        holder.v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                view.setBackgroundColor(Color.parseColor("#9be9f0"));
                //values.put(CON_NAME, Upcoming.event_names.get(position));
               // db.insert(TABLE_NAME, null, values);
                return true;// returning true instead of false, works for me
            }
        });*/
    }

    // total number of rows
    @Override
    public int getItemCount() {
        if (this.id == 1)
            return Ongoing.ongoingContestData.size();
        else
            return Upcoming.upcomingContestData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener , View.OnClickListener{
        TextView textView1, textView2;
        ImageView imageView;
        View v;
        CardView cardView;
        CheckBox checkBox;

        ViewHolder(View itemView) {
            super(itemView);
            v = itemView;
            textView1 = (TextView) itemView.findViewById(R.id.name);
            textView2 = (TextView) itemView.findViewById(R.id.time);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            cardView.setOnLongClickListener(this);
            cardView.setOnClickListener(this);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);

        }


        @Override
        public boolean onLongClick(View v) {
            if(mClickListener!=null) {
                mClickListener.onLongClick(v, getAdapterPosition());
                return true;
            }
            return false;
        }

        @Override
        public void onClick(View v) {
            if(mClickListener!=null){
                mClickListener.onClick(v,getAdapterPosition());
            }
        }
    }

    // convenience method for getting data at click position
    ContestData getItem(int id) {
        if (this.id == 1)
            return Ongoing.ongoingContestData.get(id);
        else if(id==2)
            return Upcoming.upcomingContestData.get(id);
        else
            return Fav.favContestData.get(id);

    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onLongClick(View view, int position);
        void onClick(View view ,int position);
    }

    public long insert(String contest_name,String key_val,String date_time)
    {
        values.put(Helper.KEY,key_val);
        values.put(Helper.CON_NAME,contest_name);
        values.put(Helper.DATE_TIME,date_time);
        long idd=db.insert(Helper.TABLE_NAME,null,values);
        return idd;

    }
    public boolean deleteTitle(String name)
    {
        return db.delete(Helper.TABLE_NAME, Helper.CON_NAME + "=" + name, null) > 0;
    }


    static class Helper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "tbdatabase";
        private static final String TABLE_NAME = "TBTABLE";
        private static final int DATABASE_VERSION = 2;
        private static final String KEY = "key";
        private static final String CON_NAME = "contest";
        private static final String DATE_TIME="DateTime";
        // private static final String PLAT_NAME="codeforces";

        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY + " VARCHAR(255), " + CON_NAME + " VARCHAR(255)," +DATE_TIME+" VARCHAR(255));";
        private Context context;
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public Helper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
            //Message.message(context,"CONSTRUCTOR WAS CALLED");
            Log.i("Constructor", "Called");
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE);
                // Message.message(context,"DB CREATED");
                Log.i("CREATE", "create was called");
            } catch (SQLException e) {
                //Message.message(context,""+e);
            }


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            try {
                db.execSQL(DROP_TABLE);
                // Message.message(context,"TABLE DROPPED");
                onCreate(db);
                //Message.message(context,"TABLE RECREATED");
                Log.i("UPGRADE", "On upgrade called");
            } catch (SQLException e) {
                //Message.message(context,""+e);
            }


        }

    }
}

