package app.khushbu.trackerbot;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;

import static android.content.Context.CONNECTIVITY_SERVICE;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    private DrawerLayout mDrawerLayout;
    ContentValues values=new ContentValues();
    static Database.Helper helper;
    static SQLiteDatabase db;

    static boolean isConnected;

    static Map imgId;
    //Button buttonCF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new InternetCheck(this).isInternetConnectionAvailable(new InternetCheck.InternetCheckListener() {

            @Override
            public void onComplete(boolean connected) {
                //proceed!
                isConnected = connected;
            }
        });

        /*setContentView(activity1);
        buttonCF=(Button)findViewById(R.id.codeforces);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });

        */

        setContentView(R.layout.home_screen_layout);

        imgId = new TreeMap();
        imgId.put(1, R.drawable.codeforces_logo);
        imgId.put(2, R.drawable.codechef_logo);
        imgId.put(12, R.drawable.topcoder);
        imgId.put(73, R.drawable.hackerearth_logo);
        imgId.put(63, R.drawable.hackerrank);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(db == null) {
            helper = new Database.Helper((MainActivity) this);
            db = helper.getWritableDatabase();


        }


        if(ALL_CONTEST_Activity.allContestList.size()==0) {
            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            DownloadClass downloadClass = new DownloadClass();
            downloadClass.setUrl("https://clist.by/api/v1/json/contest/?username=abhi&api_key=8f62bf40d07bb9af09535a22f21653ace0da43a4&order_by=-end&limit=100&offset=100");
            downloadClass.downloadContestTask(requestQueue);
            downloadClass.setFlag(3);
            downloadClass.setUrl("https://clist.by/api/v1/json/contest/?username=abhi&api_key=8f62bf40d07bb9af09535a22f21653ace0da43a4&order_by=-end&limit=100&offset=0");
            downloadClass.downloadContestTask(requestQueue);
        }

    }

    public void onClick(final View view) {

        int siteKey = Integer.parseInt(view.getTag().toString());
        new InternetCheck(this).isInternetConnectionAvailable(new InternetCheck.InternetCheckListener() {

            @Override
            public void onComplete(boolean connected) {
                //proceed!
                isConnected=connected;
            }
        });
        if(isConnected){
            RequestQueue requestQueue= Volley.newRequestQueue(this);

            DownloadClass downloadClass=new DownloadClass();
            downloadClass.downloadTask(requestQueue,1,siteKey);
            downloadClass.downloadTask(requestQueue,2,siteKey);

            FragmentManager man = this.getFragmentManager();
            CF_Options contest = new CF_Options();
            CF_Options.tag = (int) Integer.parseInt(view.getTag().toString());
            contest.show(man, "Dialogue");

        }
        else{
            try {
                final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();

                alertDialog.setTitle("NO INTERNET CONNECTION");
                alertDialog.setMessage("Please! Check Your Internet Connection");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", (DialogInterface.OnClickListener) null);

                alertDialog.show();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        //Log.i("xy",view.getTag().toString());

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.x, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        else if(id== R.id.nav_fav)
        {
            Intent in=new Intent(this,Fav.class);
            startActivity(in);
        }
        else if(id==R.id.nav_all_ongoing_contest)
        {


            Intent in=new Intent(this,ALL_CONTEST_Activity.class);
            startActivity(in);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}

class InternetCheck extends AsyncTask<Void, Void, Void> {


    private Activity activity;
    private InternetCheckListener listener;
    boolean b ;
    public InternetCheck(Activity x){

        activity= x;

    }

    @Override
    protected Void doInBackground(Void... params) {



        if(isNetworkAvailable()) {
            try {
                Socket sock = new Socket();
                sock.connect(new InetSocketAddress("8.8.8.8", 53), 1500);
                sock.close();
                b = true;

            } catch (IOException e) {
                b = false;
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        listener.onComplete(b);
    }

    public void isInternetConnectionAvailable(InternetCheckListener x){
        listener=x;
        execute();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }


    public interface InternetCheckListener{
        void onComplete(boolean connected);
    }

}
