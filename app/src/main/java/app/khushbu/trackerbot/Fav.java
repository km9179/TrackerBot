package app.khushbu.trackerbot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import app.khushbu.trackerbot.R;

public class Fav extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static ArrayList<String> event_names=new ArrayList<>();
    public static ArrayList<String>event_url=new ArrayList<>();
    public static ArrayList<String>event_start_time=new ArrayList<>();
    public static ArrayList<String>event_end_time=new ArrayList<>();
    public static ArrayList<Integer>event_duration=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);
        mRecyclerView=(RecyclerView)findViewById(R.id.recyclerViewfav);
        mLayoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter=new RecyclerViewAdapter(this,2);
        mRecyclerView.setAdapter(mAdapter);
    }
}
