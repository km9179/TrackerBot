package app.khushbu.trackerbot;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Map;
import java.util.TreeMap;


public class CF_Options extends android.app.DialogFragment implements View.OnClickListener{
    Button cfContest,cfCalender;
    public static int tag;
    static Map url;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.cf_options_frag,null);
        cfContest=(Button)v.findViewById(R.id.contests);
        cfCalender=(Button)v.findViewById(R.id.calender);
        cfContest.setOnClickListener(this);
        cfCalender.setOnClickListener(this);
        url=new TreeMap();
        url.put(1,"http://codeforces.com/");
        url.put(2,"http://codechef.com/");
        url.put(12,"http://topcoder.com/");
        url.put(73,"http://hackerearth.com/");
        url.put(63,"http://hackerrank.com/");
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        if(id==R.id.contests) {
            Intent intent = new Intent(getActivity(), ContestListActivity.class);
            intent.putExtra("id", tag);
            startActivity(intent);
            dismiss();
        }
        else if(id==R.id.calender)
        {
            Intent intent = new Intent(getActivity(), ContestListActivity.class);
           // intent.putExtra("url",url.);
            startActivity(intent);
            dismiss();

        }
    }
}
