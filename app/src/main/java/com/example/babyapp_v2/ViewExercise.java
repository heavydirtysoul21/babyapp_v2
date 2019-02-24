package com.example.babyapp_v2;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.babyapp_v2.Database.BabyAppDB;
import com.example.babyapp_v2.Utils.Common;

import java.util.Locale;

public class ViewExercise extends AppCompatActivity {

    private static final long START_TIME_IN_MILLIS = 5000;
    int image_id;
    String name;
    TextView timer, title;
    ImageView detail_image;
    Button btnStart;
    private CountDownTimer mCountDownTimer;
//    private boolean mTimeRunning;
    boolean isRunning = false;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    BabyAppDB babyAppDB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exercise);
        timer = (TextView)findViewById(R.id.timer);
        title = (TextView)findViewById(R.id.title);
        detail_image = (ImageView)findViewById(R.id.detail_image);
        btnStart = (Button)findViewById(R.id.btnStart);
        babyAppDB = new BabyAppDB(this);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isRunning)
                {
                    btnStart.setText("Done");
                    int timeLimit = 0;
                    if(babyAppDB.getSettingMode()==0)
                        timeLimit = Common.TIME_LIMIT_EASY;
                    if(babyAppDB.getSettingMode()==1)
                        timeLimit = Common.TIME_LIMIT_MEDIUM;
                    if(babyAppDB.getSettingMode()==2)
                        timeLimit = Common.TIME_LIMIT_HARD;
                    new CountDownTimer(timeLimit,1000){

                        @Override
                        public void onTick(long l) {
                            timer.setText(""+l/1000);


                        }

                        @Override
                        public void onFinish() {
                            finish();

                        }
                    }.start();
                }
                else
                {
                    finish();
                }
                isRunning = !isRunning;


            }
        });

        timer.setText("");
        if (getIntent()!=null)
        {
            image_id = getIntent().getIntExtra("image_id", -1);
            name = getIntent().getStringExtra("name");

            detail_image.setImageResource(image_id);
            title.setText(name);
        }




    }


}
