package app.khushbu.trackerbot;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class ALL_CONTEST_Activity extends AppCompatActivity {

    public static RecyclerView mMRecyclerView;
    public static ArrayList<ContestData>all_ongoing_contest_list=new ArrayList<>();
    public static RecyclerViewAdapter aAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__contest_);

        /*mMRecyclerView=(RecyclerView)findViewById(R.id.all_contest_RecyclerView);
        aAdapter=new RecyclerViewAdapter(getActivity(),4);
        mMRecyclerView.setAdapter(aAdapter);

        LinearLayoutManager llm=new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mMRecyclerView.setLayoutManager(llm);
*/
    }

    //public Context getActivity() {
      //  return this;
    //}
}
