package app.khushbu.trackerbot;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Ongoing extends Fragment{


    public static ArrayList<String> event_names=new ArrayList<>();
    public static ArrayList<String>event_url=new ArrayList<>();
    public static ArrayList<String>event_start_time=new ArrayList<>();
    public static ArrayList<String>event_end_time=new ArrayList<>();
    public static ArrayList<Integer>event_duration=new ArrayList<>();
    public static ArrayAdapter adapter;
    //public static RequestQueue requestQueue;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.ongoing,container,false);

        ListView listView=(ListView)rootView.findViewById(R.id.listView);
        adapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,event_names);
        listView.setAdapter(adapter);
        return rootView;
    }
}
