package app.khushbu.trackerbot;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


public class Database {

    Database(){

    }

    //public static ArrayList<ContestData> retrieved_data=new ArrayList<>();
    ContentValues values=new ContentValues();
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

    public long insert(String contest_name,int key_val,String start_time,String end_time,int duration,String url)
    {


        if(contest_name.contains("'")){
            contest_name=contest_name.replace("'","");
        }

        if(CheckIsDataAlreadyInDBorNot(Helper.TABLE_NAME, Helper.CON_NAME,contest_name)){
            Log.i("ROW1-msg","exists");
            return -1;


        }
        else {
            values.put(Helper.KEY, key_val);
            values.put(Helper.CON_NAME, contest_name);
            values.put(Helper.EVENT_START_TIME, start_time);
            values.put(Helper.EVENT_END_TIME,end_time);
            values.put(Helper.EVENT_DURATION,duration);
            values.put(Helper.EVENT_URL,url);
            long idd = MainActivity.db.insert(Helper.TABLE_NAME, null, values);

            return idd;
        }

    }

    public void deleteRow(String currentTime){
        String statement = "DELETE FROM "+Helper.TABLE_NAME+" WHERE "+Helper.EVENT_START_TIME+" <= '"+currentTime+"'";
        //String statement = "DELETE FROM "+Helper.TABLE_NAME;
        Cursor c=MainActivity.db.rawQuery(statement,null);



    }

    public boolean deleteTitle(String name)
    {
        return MainActivity.db.delete(Helper.TABLE_NAME, Helper.CON_NAME + "= '" + name+"'", null) > 0;
    }
    public void getData()
    {
        String[] columns={Helper.KEY,Helper.CON_NAME,Helper.EVENT_START_TIME};
        Cursor cursor=MainActivity.db.query(Helper.TABLE_NAME,null,null,null,null,null,Helper.EVENT_START_TIME);
        int i=0;
        Fav.favContestData.clear();
        while(cursor.moveToNext())
        {
            String contestName=cursor.getString(cursor.getColumnIndex(Helper.CON_NAME));
            contestName=contestName.replaceAll("'","");
            //Fav.favContestData.add(new ContestData(cursor.getInt(cursor.getColumnIndex(KEY)),cursor.getString(cursor.getColumnIndex(CON_NAME)),cursor.getString(cursor.getColumnIndex(EVENT_START_TIME))));
            Fav.favContestData.add(new ContestData(
                    contestName,
                    cursor.getString(cursor.getColumnIndex(Helper.EVENT_URL)),
                    cursor.getString(cursor.getColumnIndex(Helper.EVENT_START_TIME)),
                    cursor.getString(cursor.getColumnIndex(Helper.EVENT_END_TIME)),
                    cursor.getInt(cursor.getColumnIndex(Helper.EVENT_DURATION)),
                    cursor.getInt(cursor.getColumnIndex(Helper.KEY))
            ));
            Log.i("msg1",Fav.favContestData.get(0).getEvent_names());
            //Log.i("ROW1:",cursor.getInt(cursor.getColumnIndex(Helper.KEY))+"\n"+cursor.getString(cursor.getColumnIndex(Helper.CON_NAME))+"\n"+cursor.getString(cursor.getColumnIndex(Helper.EVENT_START_TIME)));


        }
        cursor.close();
    }
    public void show_data()
    {
        String[] columns={Helper.KEY,Helper.CON_NAME,Helper.EVENT_START_TIME,Helper.EVENT_END_TIME,Helper.EVENT_DURATION,Helper.EVENT_URL};
        Cursor cursor=MainActivity.db.query(Helper.TABLE_NAME,null,null,null,null,null,null);
        int i=0;
        while(cursor.moveToNext())
        {
            //retrieved_data.add(new ContestData(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
            Log.i("ROW1:",cursor.getInt(cursor.getColumnIndex(Helper.KEY))+"\n"+cursor.getString(cursor.getColumnIndex(Helper.CON_NAME))+"\n"+cursor.getString(cursor.getColumnIndex(Helper.EVENT_START_TIME)));

        }
    }





    static class Helper extends SQLiteOpenHelper {

        public static final String DATABASE_NAME = "tbdatabase";
        public static final String TABLE_NAME = "TBTABLE";
        public static final int DATABASE_VERSION = 5;
        public static final String KEY = "key";     //KEY is storing IMG_ID for contest logo.
        public static final String CON_NAME = "contest";  //CONTEST NAME.
        public static final String EVENT_START_TIME="Event_Start_Time";
        public static final String EVENT_END_TIME="Event_End_Time";
        public static final String EVENT_URL="Event_url";
        public static final String EVENT_DURATION="Event_Duration";


        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY + " INTEGER, " + CON_NAME + " VARCHAR(255)," +EVENT_START_TIME+" VARCHAR(255)," +EVENT_END_TIME+" VARCHAR(255)," +EVENT_DURATION+" INTEGER," +EVENT_URL+" VARCHAR(255));";
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

}
