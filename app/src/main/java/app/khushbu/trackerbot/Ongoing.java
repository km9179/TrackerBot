package app.khushbu.trackerbot;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class Ongoing extends Fragment{


    public static ArrayList<String> event_names=new ArrayList<>();
    public static ArrayList<String>event_url=new ArrayList<>();
    public static ArrayList<String>event_start_time=new ArrayList<>();
    public static ArrayList<String>event_end_time=new ArrayList<>();
    public static ArrayList<Integer>event_duration=new ArrayList<>();
    public static RecyclerViewAdapter adapter;
    //public static RequestQueue requestQueue;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView=inflater.inflate(R.layout.ongoing,container,false);

        RecyclerView recyclerView=(RecyclerView)rootView.findViewById(R.id.ongoingRecyclerView);
        adapter=new RecyclerViewAdapter(getActivity(),1);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager llm=new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        //for refreshing the whole contest list activity on pull

        final SwipeRefreshLayout swipeRefreshLayout=(SwipeRefreshLayout)rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RequestQueue requestQueue= Volley.newRequestQueue(rootView.getContext());
                DownloadClass downloadClass=new DownloadClass();
                downloadClass.downloadTask(requestQueue);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return rootView;
    }
}
