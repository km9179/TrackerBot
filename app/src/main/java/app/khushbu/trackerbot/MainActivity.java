package app.khushbu.trackerbot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static app.khushbu.trackerbot.R.layout.activity1;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity1);
        button1=(Button)findViewById(R.id.codeforces);
        button1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(this,Activity2.class);
        startActivity(intent);

    }
}
