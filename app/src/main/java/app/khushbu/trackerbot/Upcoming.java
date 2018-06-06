package app.khushbu.trackerbot;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

public class Upcoming extends Fragment {

    public static ArrayList<String> event_names=new ArrayList<>();
    public static ArrayList<String>event_url=new ArrayList<>();
    public static ArrayList<String>event_start_time=new ArrayList<>();
    public static ArrayList<String>event_end_time=new ArrayList<>();
    public static ArrayList<Integer>event_duration=new ArrayList<>();
    //public static ArrayAdapter adapter;
    public static RecyclerViewAdapter adapter;
    //public static RequestQueue requestQueue;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.upcoming,container,false);

        /*ListView listView=(ListView)rootView.findViewById(R.id.listView);
        adapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,event_names);
        listView.setAdapter(adapter);*/

        String [] event_name = event_names.toArray(new String[event_names.size()]);

        RecyclerView recyclerView=(RecyclerView)rootView.findViewById(R.id.recyclerView);
        adapter=new RecyclerViewAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        LinearLayoutManager llm=new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        //requestQueue=Volley.newRequestQueue(getActivity());
        //DownloadClass downloadClass=new DownloadClass();
        //downloadClass.downloadTask(requestQueue);
        //downloadTask();

        return rootView;
    }

    /*public void downloadTask(){

        String url="https://clist.by/api/v1/json/contest/?username=abhi&api_key=8f62bf40d07bb9af09535a22f21653ace0da43a4&resource__id=1&order_by=-start";


        JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray=response.getJSONArray("objects");

                    for(int i=0;i<jsonArray.length();i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String eventName=jsonObject.getString("event");
                        String eventUrl=jsonObject.getString("href");
                        String eventStartTime=jsonObject.getString("start");
                        String eventEndTime=jsonObject.getString("end");
                        int eventDuration=jsonObject.getInt("duration");


                        //Log.i("names",eventName);
                        event_names.add(eventName);
                        event_url.add(eventUrl);
                        event_start_time.add(eventStartTime);
                        event_end_time.add(eventEndTime);
                        event_duration.add(eventDuration);
                        adapter.notifyDataSetChanged();
                    }

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(objectRequest);
        //adapter.notifyDataSetChanged();

    }*/

}
