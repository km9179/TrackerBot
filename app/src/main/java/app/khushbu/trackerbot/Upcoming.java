package app.khushbu.trackerbot;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class Upcoming extends Fragment implements RecyclerViewAdapter.ItemClickListener {


    public static ArrayList<ContestData> upcomingContestData=new ArrayList<>();
    public static ArrayList<ContestData> selectedContest = new ArrayList<>();
    static int counter;
    public static RecyclerViewAdapter adapter;
    static SwipeRefreshLayout swipeRefreshLayout;
    static boolean is_in_actionMode;

    ContestListActivity activity;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*new InternetCheck(getActivity()).isInternetConnectionAvailable(new InternetCheck.InternetCheckListener() {

            @Override
            public void onComplete(boolean connected) {
                //proceed!
                MainActivity.isConnected=connected;
            }
        });*/
        counter=0;
        is_in_actionMode=false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView=inflater.inflate(R.layout.upcoming,container,false);

        /*ListView listView=(ListView)rootView.findViewById(R.id.listView);
        adapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,event_names);
        listView.setAdapter(adapter);*/

        //toolbar=(Toolbar)((AppCompatActivity)getActivity()).getSupportActionBar().getCustomView().findViewById(R.id.toolbar);
        //textToolbar=(TextView)toolbar.findViewById(R.id.toolbarText);
        RecyclerView recyclerView=(RecyclerView)rootView.findViewById(R.id.upcomingRecyclerView);
        adapter=new RecyclerViewAdapter(getActivity(),2);
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);
        activity=(ContestListActivity)getActivity();

        LinearLayoutManager llm=new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        //for refreshing the whole contest list activity on pull

        swipeRefreshLayout=(SwipeRefreshLayout)rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(is_in_actionMode)
                    swipeRefreshLayout.setRefreshing(false);
                else {

                    RequestQueue requestQueue = Volley.newRequestQueue(rootView.getContext());
                    DownloadClass downloadClass = new DownloadClass();
                    downloadClass.formUrl(ContestListActivity.siteKey);
                    downloadClass.downloadTask(requestQueue, 2);
                    adapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onLongClick(View view, int position) {

        if(!is_in_actionMode) {
            swipeRefreshLayout.setEnabled(false);
            activity.changeMenu(1);
            ContestListActivity.textToolbar.setVisibility(View.VISIBLE);
            is_in_actionMode = true;
            //adapter.notifyDataSetChanged();
            activity.setLayoutScrollFlags(1);
            CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);
            checkBox.setChecked(true);
            counter += 1;
            checkBox.setVisibility(View.VISIBLE);
            selectedContest.add(upcomingContestData.get(position));
            upcomingContestData.get(position).setSelected(true);
            updateCounter(counter);
        }
    }

    @Override
    public void onClick(View view, int position) {
        /*new InternetCheck(getActivity()).isInternetConnectionAvailable(new InternetCheck.InternetCheckListener() {

            @Override
            public void onComplete(boolean connected) {
                //proceed!
                MainActivity.isConnected=connected;
            }
        });*/
        if(is_in_actionMode){
            CheckBox checkBox=(CheckBox) view.findViewById(R.id.checkBox);
            if(checkBox.isChecked()) {

                checkBox.setChecked(false);
                counter -= 1;
                checkBox.setVisibility(View.GONE);
                selectedContest.remove(upcomingContestData.get(position));
                upcomingContestData.get(position).setSelected(false);
                Log.i("t","t");
            }
            else{
                checkBox.setChecked(true);
                counter += 1;
                checkBox.setVisibility(View.VISIBLE);
                selectedContest.add(upcomingContestData.get(position));
                upcomingContestData.get(position).setSelected(true);
                Log.i("u","u");
            }
            updateCounter(counter);
        }
        else{
            //Log.i("url",upcomingContestData.get(position).getEvent_url());

            Intent intent=new Intent(getContext(),WebActivity.class);
            intent.putExtra("url",upcomingContestData.get(position).getEvent_url());
            startActivity(intent);

        }
    }

    public  void prepareSelection(View view, int position){
        if(((CheckBox)view).isChecked()){
            selectedContest.add(upcomingContestData.get(position));
            counter += 1;
            upcomingContestData.get(position).setSelected(true);

        }
        else{
            selectedContest.remove(upcomingContestData.get(position));
            counter -= 1;
            upcomingContestData.get(position).setSelected(false);
            ((CheckBox)view).setChecked(false);
            ((CheckBox)view).setVisibility(View.GONE);
        }
        updateCounter(counter);

    }

    public void updateCounter(int counter){
        if(counter==0) {
            ContestListActivity.textToolbar.setText("0");
        }
        else
            ContestListActivity.textToolbar.setText(Integer.toString(counter));
    }



    /*private class MyActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_contest_list_action_mode,menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            is_in_actionMode=false;
            adapter.notifyDataSetChanged();
            myActionMode = null;

        }


    }*/

}
