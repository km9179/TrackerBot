package app.khushbu.trackerbot;

import android.content.Intent;
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
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class Ongoing extends Fragment implements RecyclerViewAdapter.ItemClickListener{


    public static ArrayList<ContestData>ongoingContestData =new ArrayList<>();
    public static RecyclerViewAdapter adapter;
    //public static RequestQueue requestQueue;
    //public static TextView error_message;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new InternetCheck(getActivity()).isInternetConnectionAvailable(new InternetCheck.InternetCheckListener() {

            @Override
            public void onComplete(boolean connected) {
                //proceed!
                MainActivity.isConnected=connected;
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView=inflater.inflate(R.layout.ongoing,container,false);

        RecyclerView recyclerView =(RecyclerView)rootView.findViewById(R.id.ongoingRecyclerView);
        //error_message = (TextView)rootView.findViewById(R.id.error_message);
        adapter=new RecyclerViewAdapter(getActivity(),1);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter.setClickListener(this);
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
                downloadClass.downloadTask(requestQueue,1,ContestListActivity.siteKey);
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        return rootView;
    }

    @Override
    public void onLongClick(View view, int position) {

    }

    @Override
    public void onClick(View view, int position) {
        //Log.i("url",ongoingContestData.get(position).getEvent_url());
        new InternetCheck(getActivity()).isInternetConnectionAvailable(new InternetCheck.InternetCheckListener() {

            @Override
            public void onComplete(boolean connected) {
                //proceed!
                MainActivity.isConnected=connected;
            }
        });
        Intent intent=new Intent(getContext(),WebActivity.class);
        intent.putExtra("url",ongoingContestData.get(position).getEvent_url());
        startActivity(intent);
    }
}
