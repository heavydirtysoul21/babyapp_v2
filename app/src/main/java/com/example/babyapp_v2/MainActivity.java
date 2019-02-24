package com.example.babyapp_v2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.babyapp_v2.Adapter.RecyclerViewAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSchedule, btnSettings;
    ImageView btnTrainig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSchedule = (Button) findViewById(R.id.btnSchedule);
        btnSchedule.setOnClickListener(this);
        btnSettings = (Button) findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(this);
        btnTrainig = (ImageView)findViewById(R.id.imgStart);


        btnTrainig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSchedule:
                Intent intent = new Intent(MainActivity.this, ScheduleActivity.class);
                startActivity(intent);
                break;
            case R.id.btnSettings:
                Intent intent2 = new Intent(MainActivity.this, Settings.class);
                startActivity(intent2);
                break;



        }
    }


}
