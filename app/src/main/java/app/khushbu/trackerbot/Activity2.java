package app.khushbu.trackerbot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        Intent intent=getIntent();
        String z=intent.getStringExtra("button");

        TextView textView=(TextView)findViewById(R.id.textView);
        textView.setText(z);
    }
}
