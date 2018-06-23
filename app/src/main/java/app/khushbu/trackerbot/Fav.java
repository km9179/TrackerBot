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
import android.widget.LinearLayout;
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
    static CheckBox toolbarCheckbox;

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
        toolbarCheckbox =(CheckBox)toolbar.findViewById(R.id.toolbarCheckBox);
        toolbarCheckbox.setChecked(false);
        toolbarCheckbox.setVisibility(View.GONE);
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
            toolbarCheckbox.setVisibility(View.VISIBLE);
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

            database.show_data();
            for(int i=0;i<favSelectedContestData.size();i++){
                favContestData.remove(favSelectedContestData.get(i));
                database.deleteTitle(favSelectedContestData.get(i).getEvent_names());


            }
            Log.i("ROW1-msg","deleted");
            database.show_data();


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
        favSelectedContestData.clear();
        textToolbar.setVisibility(View.GONE);
        toolbarCheckbox.setChecked(false);
        toolbarCheckbox.setVisibility(View.GONE);
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

            new InternetCheck(this).isInternetConnectionAvailable(new InternetCheck.InternetCheckListener() {

                @Override
                public void onComplete(boolean connected) {

                    MainActivity.isConnected=connected;
                }
            });

            Intent intent=new Intent(Fav.this,WebActivity.class);
            intent.putExtra("url",favContestData.get(position).getEvent_url());
            startActivity(intent);

        }
    }

    @Override
    public void onBackPressed() {
        if(is_in_actionMode){
            clearActionMode();

        }
        else
            super.onBackPressed();
    }

    public void onToolbarCheckBoxClick(View view){
        toolbarCheckbox =(CheckBox)view;
        if(toolbarCheckbox.isChecked()){
            for(int i=0;i<mAdapter.getItemCount();i++){
                if(!mAdapter.getItem(i).isSelected()) {
                    mAdapter.getItem(i).setSelected(true);
                    favSelectedContestData.add(mAdapter.getItem(i));
                    counter+=1;
                }
                mAdapter.notifyDataSetChanged();
                updateCounter(counter);
            }
        }
        else{
            for(int i=0;i<mAdapter.getItemCount();i++){
                if(mAdapter.getItem(i).isSelected()) {
                    mAdapter.getItem(i).setSelected(false);
                    favSelectedContestData.remove(mAdapter.getItem(i));
                    counter -= 1;
                }
                mAdapter.notifyDataSetChanged();

                updateCounter(counter);
            }
        }
    }
}
