package app.khushbu.trackerbot;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ALL_CONTEST_Activity extends AppCompatActivity {

    public static RecyclerView mMRecyclerView;
    public static ArrayList<ContestData>allContestList=new ArrayList<>();
    public static RecyclerViewAdapter aAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__contest_);

        mMRecyclerView=(RecyclerView)findViewById(R.id.all_contest_RecyclerView);
        aAdapter=new RecyclerViewAdapter(ALL_CONTEST_Activity.this,4);
        mMRecyclerView.setAdapter(aAdapter);


        LinearLayoutManager llm=new LinearLayoutManager(ALL_CONTEST_Activity.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mMRecyclerView.setLayoutManager(llm);

        new Time().setCountdown(10000);
        //Collections.sort(allContestList, (o1, o2) -> (o1.getEvent_end_time().compareTo(o2.getEvent_end_time())));
        Collections.sort(allContestList, new Comparator<ContestData>(){
            public int compare(ContestData o1, ContestData o2){
                return o1.getEvent_end_time().compareTo(o2.getEvent_end_time());
            }
        });
        aAdapter.notifyDataSetChanged();

    }


}