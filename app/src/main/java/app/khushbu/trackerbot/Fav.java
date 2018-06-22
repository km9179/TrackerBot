package app.khushbu.trackerbot;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import app.khushbu.trackerbot.R;

public class Fav extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener{
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static ArrayList<ContestData> favContestData=new ArrayList<>();

    public ArrayList<ContestData> favSelectedContestData=new ArrayList<>();


    Toolbar toolbar;
    static TextView textToolbar;
    static boolean is_in_actionMode;

    Database database;
    //UpdateDatabase updateDatabase;

    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);

        new InternetCheck(this).isInternetConnectionAvailable(new InternetCheck.InternetCheckListener() {

            @Override
            public void onComplete(boolean connected) {

                MainActivity.isConnected=connected;
            }
        });
        is_in_actionMode=false;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        textToolbar=(TextView)toolbar.findViewById(R.id.textToolbar);

        counter=0;

        database =new Database();
        //updateDatabase = new UpdateDatabase();

        mRecyclerView=(RecyclerView)findViewById(R.id.recyclerViewfav);
        mLayoutManager=new LinearLayoutManager(this);
       // mLayoutManager.setOrientation
        favContestData.clear();
        Database database=new Database();
        database.getData();
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter=new RecyclerViewAdapter(this,3);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setClickListener(this);
    }

    @Override
    public void onLongClick(View view, int position) {

        if(!is_in_actionMode){

            changeMenu(1);
            textToolbar.setVisibility(View.VISIBLE);
            is_in_actionMode=true;
            setLayoutScrollFlags(1);

            CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);
            checkBox.setChecked(true);

            counter += 1;

            checkBox.setVisibility(View.VISIBLE);

            favSelectedContestData.add(favContestData.get(position));
            favContestData.get(position).setSelected(true);

            updateCounter(counter);


        }
    }

    public void changeMenu(int id){
        toolbar.getMenu().clear();
        if(id==1) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            toolbar.inflateMenu(R.menu.menu_fav_list_action_mode);
        }
        else{
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            toolbar.inflateMenu((R.menu.menu_contest_list));
        }
    }

    public  void prepareSelection(View view, int position){
        if(((CheckBox)view).isChecked()){
            favSelectedContestData.add(favContestData.get(position));
            counter += 1;
            favContestData.get(position).setSelected(true);

        }
        else{
            favSelectedContestData.remove(favContestData.get(position));
            counter -= 1;
            favContestData.get(position).setSelected(false);
            ((CheckBox)view).setChecked(false);
            ((CheckBox)view).setVisibility(View.GONE);
        }
        updateCounter(counter);

    }

    public void updateCounter(int counter){
        if(counter==0) {
            textToolbar.setText("0");
        }
        else
            textToolbar.setText(Integer.toString(counter));
    }

    public void setLayoutScrollFlags(int id){

        AppBarLayout.LayoutParams toolbarParams=(AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        if(id==1) {
            toolbarParams.setScrollFlags(0);
        }
        else{
            toolbarParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                    | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {

            Database database =new Database();
            for(int i=0;i<favSelectedContestData.size();i++){
                favContestData.remove(favSelectedContestData.get(i));
                database.deleteTitle(favSelectedContestData.get(i).getEvent_names());
                //database.deleteRow("0");
                //updateDatabase.execute();
            }


            clearActionMode();
            return true;
        }
        else if(id == android.R.id.home){
            if(is_in_actionMode) {
                clearActionMode();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void clearActionMode(){

        changeMenu(2);
        is_in_actionMode=false;
        this.mAdapter.notifyDataSetChanged();
        //favSelectedContestData.clear();
        textToolbar.setVisibility(View.GONE);
        counter = 0;
        setLayoutScrollFlags(2);
    }

    @Override
    public void onClick(View view, int position) {
        new InternetCheck(this).isInternetConnectionAvailable(new InternetCheck.InternetCheckListener() {

            @Override
            public void onComplete(boolean connected) {
                //proceed!
                MainActivity.isConnected=connected;
            }
        });
        if(is_in_actionMode){
            CheckBox checkBox=(CheckBox) view.findViewById(R.id.checkBox);
            if(checkBox.isChecked()) {

                checkBox.setChecked(false);
                counter -= 1;
                checkBox.setVisibility(View.GONE);
                favSelectedContestData.remove(favContestData.get(position));
                favContestData.get(position).setSelected(false);
                Log.i("t","t");
            }
            else{
                checkBox.setChecked(true);
                counter += 1;
                checkBox.setVisibility(View.VISIBLE);
                favSelectedContestData.add(favContestData.get(position));
                favContestData.get(position).setSelected(true);
                Log.i("u","u");
            }
            updateCounter(counter);
        }
        else{
            //Log.i("url",upcomingContestData.get(position).getEvent_url());

            Intent intent=new Intent(Fav.this,WebActivity.class);
            intent.putExtra("url",favContestData.get(position).getEvent_url());
            startActivity(intent);

        }
    }

    /*class UpdateDatabase extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            for(int i=0;i<Upcoming.selectedContest.size();i++)
            {
                //Fav.favContestData.add(Upcoming.selectedContest.get(i));
                try {
                    for(int j=0;j<favSelectedContestData.size();i++){
                        database.deleteTitle(favSelectedContestData.get(i).getEvent_names());
                        //database.deleteRow("0");
                    }
                }
                catch (Exception e){
                    String errorString = favSelectedContestData.get(i).getEvent_names();
                    errorString = errorString.replace("'","''");
                    //errorString =errorString.replaceAll("\"","\"");
                    //errorString = StringEscapeUtils.escapeJava(errorString);
                    favSelectedContestData.get(i).setEvent_names(errorString);
                    //long iddd = database.insert(Upcoming.selectedContest.get(i).getEvent_names(), Upcoming.selectedContest.get(i).getImgId(), Upcoming.selectedContest.get(i).getEvent_start_time(), Upcoming.selectedContest.get(i).getEvent_end_time(), Upcoming.selectedContest.get(i).getEvent_duration(), Upcoming.selectedContest.get(i).getEvent_url());
                    database.deleteTitle(favSelectedContestData.get(i).getEvent_names());
                }
                //Message.message(ContestListActivity.this,"Inserted Data");
            }
            favSelectedContestData.clear();
            return null;
        }
    }*/
}
