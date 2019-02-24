package com.example.babyapp_v2;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.babyapp_v2.Database.BabyAppDB;
import com.example.babyapp_v2.Model.Exercises;
import com.example.babyapp_v2.Utils.Common;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StartActivity extends AppCompatActivity {

    Button btnStart,btnNext;
    ImageView ex_image;
    TextView txtGetReady, txtCountDown, txtTimer, ex_name;
    ProgressBar progressBar;
    LinearLayout layoutGetReady;
    int ex_id = 0;
    List<Exercises> List = new ArrayList<>();
    BabyAppDB babyAppDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        initData();
        babyAppDB = new BabyAppDB(this);


        btnStart = (Button)findViewById(R.id.btnStart2);
        ex_image = (ImageView)findViewById(R.id.detail_imageS);
        txtCountDown = (TextView)findViewById(R.id.txtCountdown);
        txtGetReady = (TextView)findViewById(R.id.txtGetReady);
        txtTimer = (TextView)findViewById(R.id.txtTimer);
        ex_name = (TextView)findViewById(R.id.title2);
        layoutGetReady = (LinearLayout) findViewById(R.id.layout_getReady);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        progressBar.setMax(List.size());

        setExercisesInfo(ex_id);

        btnNext = (Button)findViewById(R.id.btnNext) ;
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(babyAppDB.getSettingMode()==0)
                        exercisesEasyModeCountdown.cancel();
                    else if(babyAppDB.getSettingMode()==1)
                        exercisesMediumModeCountdown.cancel();
                    else if(babyAppDB.getSettingMode()==2)
                        exercisesHardModeCountdown.cancel();

                    resetTimeCountdown.cancel();

                    if(ex_id<List.size())
                    {
//                        showRestTime();
                        ex_id++;
                        progressBar.setProgress(ex_id);
                        txtTimer.setText("");
                    }
                    else
                        showFinished();
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExercises();

//                if(btnStart.getText().toString().toLowerCase().equals("start"))
//                {
//                    showGetReady();
//                    btnStart.setText("Done");
//
//                }
//                else if(btnStart.getText().toString().toLowerCase().equals("done"))
//                {
//                    if(babyAppDB.getSettingMode()==0)
//                        exercisesEasyModeCountdown.cancel();
//                    else if(babyAppDB.getSettingMode()==1)
//                        exercisesMediumModeCountdown.cancel();
//                    else if(babyAppDB.getSettingMode()==2)
//                        exercisesHardModeCountdown.cancel();
//
//                    resetTimeCountdown.cancel();
//
//                    if(ex_id<List.size())
//                    {
//                        showRestTime();
//                        ex_id++;
//                        progressBar.setProgress(ex_id);
//                        txtTimer.setText("");
//                    }
//                    else
//                        showFinished();
//                }
//                else
//                {
//                    if(babyAppDB.getSettingMode()==0)
//                        exercisesEasyModeCountdown.cancel();
//                    else if(babyAppDB.getSettingMode()==1)
//                        exercisesMediumModeCountdown.cancel();
//                    else if(babyAppDB.getSettingMode()==2)
//                        exercisesHardModeCountdown.cancel();
//
//                    resetTimeCountdown.cancel();
//
//                    if (ex_id<List.size())
//                        setExercisesInfo(ex_id);
//                    else
//                        showFinished();
//                }
//

            }
        });
        
    }

    private void showRestTime() {
        ex_image.setVisibility(View.INVISIBLE);
        txtTimer.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.VISIBLE);
        txtCountDown.setVisibility(View.VISIBLE);
        btnStart.setText("Skip");
        layoutGetReady.setVisibility(View.VISIBLE);
        txtGetReady.setText("Rest");
        resetTimeCountdown.start();
    }

    private void showGetReady() {
        ex_image.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.INVISIBLE);
        btnNext.setVisibility(View.INVISIBLE);
        txtTimer.setVisibility(View.VISIBLE);

        layoutGetReady.setVisibility(View.VISIBLE);
        txtGetReady.setText("Get Ready!");
        new CountDownTimer(3000,1000)
        {

            @Override
            public void onTick(long l) {
                txtTimer.setText(""+(l/1000));

            }

            @Override
            public void onFinish() {
                showExercises();

            }
        }.start();
    }

    private void showExercises() {
        if(ex_id<List.size())
        {
            ex_image.setVisibility(View.VISIBLE);
            btnStart.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
            layoutGetReady.setVisibility(View.INVISIBLE);

            if(babyAppDB.getSettingMode()==0)
                exercisesEasyModeCountdown.start();
            else if(babyAppDB.getSettingMode()==1)
                exercisesMediumModeCountdown.start();
            else if(babyAppDB.getSettingMode()==2)
                exercisesHardModeCountdown.start();



            ex_image.setImageResource(List.get(ex_id).getImage_id());
            ex_name.setText(List.get(ex_id).getName());

        }
        else
            showFinished();
    }

    private void showFinished() {
        ex_image.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.INVISIBLE);
        txtTimer.setVisibility(View.INVISIBLE);
        layoutGetReady.setVisibility(View.VISIBLE);
        txtGetReady.setText("Finished!");
        txtCountDown.setText("All exercises done");

//        babyAppDB.saveDay(""+ Calendar.getInstance().getTimeInMillis());
    }

    CountDownTimer exercisesMediumModeCountdown = new CountDownTimer(Common.TIME_LIMIT_MEDIUM,1000) {
        @Override
        public void onTick(long l) {
            txtTimer.setText(""+(l/1000));

        }

        @Override
        public void onFinish() {
            if(ex_id<List.size()-1)
            {
                ex_id++;
                progressBar.setProgress(ex_id);
                txtTimer.setText("");

                setExercisesInfo(ex_id);
//                btnStart.setText("Start");

            }
            else
            {
                showFinished();
            }

        }
    };
    CountDownTimer exercisesEasyModeCountdown = new CountDownTimer(Common.TIME_LIMIT_EASY,1000) {
        @Override
        public void onTick(long l) {
            txtTimer.setText(""+(l/1000));

        }

        @Override
        public void onFinish() {
            if(ex_id<List.size()-1)
            {
                ex_id++;
                progressBar.setProgress(ex_id);
                txtTimer.setText("");

                setExercisesInfo(ex_id);
//                btnStart.setText("Start");

            }
            else
            {
                showFinished();
            }

        }
    };
    CountDownTimer exercisesHardModeCountdown = new CountDownTimer(Common.TIME_LIMIT_HARD,1000) {
        @Override
        public void onTick(long l) {//        babyAppDB.saveDay(""+ Calendar.getInstance().getTimeInMillis());

            txtTimer.setText(""+(l/1000));

        }

        @Override
        public void onFinish() {
            if(ex_id<List.size()-1)
            {
                ex_id++;
                progressBar.setProgress(ex_id);
                txtTimer.setText("");

                setExercisesInfo(ex_id);
//                btnStart.setText("Start");

            }
            else
            {
                showFinished();
            }

        }
    };
    CountDownTimer resetTimeCountdown = new CountDownTimer(5000,1000) {
        @Override
        public void onTick(long l) {
            txtCountDown.setText(""+(l/1000));

        }

        @Override
        public void onFinish() {
            setExercisesInfo(ex_id);
            showExercises();

        }
    };





    private void setExercisesInfo(int id) {
        ex_image.setImageResource(List.get(id).getImage_id());
        ex_name.setText(List.get(id).getName());

        ex_image.setVisibility(View.VISIBLE);
        btnStart.setVisibility(View.VISIBLE);
        txtTimer.setVisibility(View.VISIBLE);

        layoutGetReady.setVisibility(View.INVISIBLE);
    }

    private void initData() {
        List.add(new Exercises(R.drawable.a2, "first"));
        List.add(new Exercises(R.drawable.a3, "second"));
        List.add(new Exercises(R.drawable.a3, "third"));
        List.add(new Exercises(R.drawable.a3, "fourth"));
        List.add(new Exercises(R.drawable.a3, "fifth"));
    }


}
