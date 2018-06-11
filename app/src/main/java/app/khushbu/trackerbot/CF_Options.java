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


public class CF_Options extends android.app.DialogFragment implements View.OnClickListener{
    Button cfContest;
    public static int tag;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.cf_options_frag,null);
        cfContest=(Button)v.findViewById(R.id.contests);
        cfContest.setOnClickListener(this);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(getActivity(),ContestListActivity.class);
        intent.putExtra("id",tag);
        startActivity(intent);
    }
}
