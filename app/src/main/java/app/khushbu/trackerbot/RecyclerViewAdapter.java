package app.khushbu.trackerbot;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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

import java.util.ArrayList;

//import static app.khushbu.trackerbot.RecyclerViewAdapter.Helper.CON_NAME;
//import static app.khushbu.trackerbot.RecyclerViewAdapter.Helper.KEY;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    Context context;
    Upcoming upcoming;
    Fav fav;
    //ContentValues values = new ContentValues();
    //Helper helper;
    //SQLiteDatabase db;
    public static ArrayList<ContestData> retrieved_data = new ArrayList<>();


    int id;   //id for identifying fragment------->1 = ongoing fragment and 2 = upcoming fragment

    // data is passed into the constructor
    RecyclerViewAdapter(Context context, int id) {
        this.context = context;
        this.id = id;
        upcoming = new Upcoming();
        fav = new Fav();
        //helper=new Helper(context);
        //db = helper.getWritableDatabase();
        //Helper helper = new Helper(context);
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
            holder.textView2.setText(z);


            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    upcoming.prepareSelection(v, position);
                }
            });

        }
        else if(this.id==3) {

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
        else if(this.id==2)
            return Upcoming.upcomingContestData.size();
        else if(this.id==3)
            return Fav.favContestData.size();
        return 0;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
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
    }

    // convenience method for getting data at click position
    ContestData getItem(int id) {
        if (this.id == 1)
            return Ongoing.ongoingContestData.get(id);
        else if (this.id == 2)
            return Upcoming.upcomingContestData.get(id);
        else if(this.id==3)
            return Fav.favContestData.get(id);
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















/*
    public boolean CheckIsDataAlreadyInDBorNot(String TableName,
                                                      String col_name_val, String con_name) {

        String Query = "Select * from " + TableName + " where " + col_name_val + " = '" + con_name+"'";
        Cursor cursor = MainActivity.db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public long insert(String contest_name,int key_val,String date_time)
    {
        //int query=("SELECT EXISTS(SELECT "+helper.CON_NAME+" FROM "+helper.TABLE_NAME+" WHERE "+helper.CON_NAME+" = "+contest_name+" ;")
        //Cursor cur=db.rawQuery(query,null);
       // if(query==0) {
        //if(CheckIsDataAlreadyInDBorNot(Helper.TABLE_NAME,Helper.CON_NAME,contest_name)) {
        //    return -1;

        //}
        //}
        //else


        if(CheckIsDataAlreadyInDBorNot(Helper.TABLE_NAME,Helper.CON_NAME,contest_name)){
            Log.i("ROW1-msg","exists");
            return -1;


        }
        else {
            values.put(Helper.KEY, key_val);
            values.put(Helper.CON_NAME, contest_name);
            values.put(Helper.DATE_TIME, date_time);
            long idd = MainActivity.db.insert(Helper.TABLE_NAME, null, values);

            return idd;
        }

    }

    public void deleteRow(String currentTime){
        String statement = "DELETE FROM "+Helper.TABLE_NAME+" WHERE "+Helper.DATE_TIME+" <= '"+new Time().getCurrentTimeStamp()+"'";
        Cursor c=MainActivity.db.rawQuery(statement,null);
        while(c.moveToNext()){
            Log.i("deletable",c.getString(1));
        }


    }

    public boolean deleteTitle(String name)
    {
        return MainActivity.db.delete(Helper.TABLE_NAME, Helper.CON_NAME + "=" + name, null) > 0;
    }
    public void getData()
    {
        String[] columns={KEY,CON_NAME,Helper.DATE_TIME};
        Cursor cursor=MainActivity.db.query(Helper.TABLE_NAME,columns,null,null,null,null,null);
        int i=0;
        while(cursor.moveToNext())
        {
            retrieved_data.add(new ContestData(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));

        }
    }
    public void show_data()
    {
        String[] columns={KEY,CON_NAME,Helper.DATE_TIME};
        Cursor cursor=MainActivity.db.query(Helper.TABLE_NAME,columns,null,null,null,null,null);
        int i=0;
        while(cursor.moveToNext())
        {
            //retrieved_data.add(new ContestData(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
            Log.i("ROW1:",cursor.getInt(0)+"\n"+cursor.getString(1)+"\n"+cursor.getString(2));

        }
    }





    static class Helper extends SQLiteOpenHelper {

        public static final String DATABASE_NAME = "tbdatabase";
        public static final String TABLE_NAME = "TBTABLE";
        public static final int DATABASE_VERSION = 4;
        public static final String KEY = "key";
        public static final String CON_NAME = "contest";
        public static final String DATE_TIME="DateTime";
        // private static final String PLAT_NAME="codeforces";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY + " INTEGER, " + CON_NAME + " VARCHAR(255)," +DATE_TIME+" VARCHAR(255));";
        public Context context;
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public Helper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
            Message.message(context,"CONSTRUCTOR WAS CALLED");
            Log.i("Constructor", "Called");
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE);
                Message.message(context,"DB CREATED");
                Log.i("CREATE", "create was called");
            } catch (SQLException e) {
                Message.message(context,""+e);
            }


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            try {
                db.execSQL(DROP_TABLE);
                 Message.message(context,"TABLE DROPPED");
                onCreate(db);
                Message.message(context,"TABLE RECREATED");
                Log.i("UPGRADE", "On upgrade called");
            } catch (SQLException e) {
                Message.message(context,""+e);
            }


        }

    }
    /*

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Helper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="tbdatabase";
    public static final String TABLE_NAME="TBTABLE";
    public static final int DATABASE_VERSION=2;
    public static final String KEY="key";
    public static final String CON_NAME="contest";
    // private static final String PLAT_NAME="codeforces";

    private static final  String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+"("+KEY+" VARCHAR(255), "+CON_NAME+" VARCHAR(255));";
    private Context context;
    private static final String DROP_TABLE="DROP TABLE IF EXISTS "+TABLE_NAME;

    public Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
        //Message.message(context,"CONSTRUCTOR WAS CALLED");
        Log.i("Constructor","Called");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE);
           // Message.message(context,"DB CREATED");
            Log.i("CREATE","create was called");
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
            Log.i("UPGRADE","On upgrade called");
        } catch (SQLException e) {
            //Message.message(context,""+e);
        }


    }
}
     */
//}

