package app.khushbu.trackerbot;

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