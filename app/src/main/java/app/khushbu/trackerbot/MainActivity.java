package app.khushbu.trackerbot;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import static app.khushbu.trackerbot.R.layout.activity1;

public class MainActivity extends AppCompatActivity {

    Button buttonCF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity1);
        buttonCF=(Button)findViewById(R.id.codeforces);
    }

    public void onClick(View view) {
        /*Intent intent=new Intent(this,Activity2.class);
        String z= "fhb";
        z=view.getTag().toString();
        intent.putExtra("button",z);
        startActivity(intent);

        Log.i("button",z);*/
        FragmentManager man=this.getFragmentManager();
        CF_Options contest=new CF_Options();
        contest.show(man,"Dialogue");


    }
}
