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
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Map;
import java.util.TreeMap;


public class CF_Options extends android.app.DialogFragment implements View.OnClickListener{
    Button cfContest,cfCalender;
    RelativeLayout dialogue_box1_lay;
    public static int tag;
    ImageView img_logo;
    ContestData siteK=new ContestData();
    static Map url;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.cf_options_frag,null);
        cfContest=(Button)v.findViewById(R.id.contests);
        cfCalender=(Button)v.findViewById(R.id.calender);
        img_logo=(ImageView)v.findViewById(R.id.logo_view);
        dialogue_box1_lay=(RelativeLayout)v.findViewById(R.id.Dialogue_box1_root);
        if(tag==1)
        {
            img_logo.setImageResource(R.drawable.codeforces);
        }
        else if(tag==2)
        {
            img_logo.setImageResource(R.drawable.codechef);
        }
        else if(tag==12)
        {
            img_logo.setImageResource(R.drawable.topcoder);
        }
        else if(tag==73)
        {
            img_logo.setImageResource(R.drawable.hackerearth);
        }
        else if(tag==63)
        {
            img_logo.setImageResource(R.drawable.hackerrank);
        }
        cfContest.setOnClickListener(this);
        cfCalender.setOnClickListener(this);
        url=new TreeMap();
        url.put(1,"https://calendar.google.com/calendar/embed?src=br1o1n70iqgrrbc875vcehacjg@group.calendar.google.com");
        url.put(2,"https://calendar.google.com/calendar/embed?src=ogc7qt4hlg454ggkj9o6ttqnq8@group.calendar.google.com");
        url.put(12,"https://calendar.google.com/calendar/embed?src=qshn4jb7pq3f8l46nvvilj5c6o@group.calendar.google.com");
        url.put(73,"https://calendar.google.com/calendar/embed?src=k9s51sdqsecvq4mv61ssgjfr3g@group.calendar.google.com");
        url.put(63,"https://calendar.google.com/calendar/embed?src=qfbv488f0ckkdlq0kosp1n5e6s@group.calendar.google.com");
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
            Intent intent = new Intent(getActivity(), WebActivity.class);
            intent.putExtra("url",url.get(tag).toString());
            startActivity(intent);
            dismiss();

        }
    }
}
