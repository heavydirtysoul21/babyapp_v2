package com.example.babyapp_v2;

import android.content.Intent;
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
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class StartActivity extends AppCompatActivity {

    private Button startButton, btnNext, btnPrev, btnStop, btnEnd;

    private ImageView ex_image;

    private TextView getReadyText, timerText, exerciseName, txtDone, txtLeft;

    private ProgressBar progressBar;

    private LinearLayout getReadyLayout;

    private int ex_id = 0;
    private boolean isPaused = false;
    private boolean isCanceled = false;
    private long timeRemaining = 0;

    List<Exercises> List = new ArrayList<>();
    int left = 5;
    int done = 0;


    BabyAppDB babyAppDB;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        initData();

        babyAppDB = new BabyAppDB(this);

        startButton = findViewById(R.id.start_button);

        ex_image = findViewById(R.id.image_detail);

        getReadyText = findViewById(R.id.get_ready_text);

        timerText = findViewById(R.id.timer);
        exerciseName = findViewById(R.id.title);

        progressBar = findViewById(R.id.progress_bar);

        getReadyLayout = findViewById(R.id.get_ready_layout);
        txtDone = findViewById(R.id.txtDone);
        txtLeft = findViewById(R.id.txtLeft);

        progressBar.setMax(List.size());

        setExerciseInformation(ex_id);
        txtLeft.setText(left + " Left");



        btnNext = findViewById(R.id.btnNext);
        btnStop = findViewById(R.id.btnStop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startButton.setText("Begin");
                timerText.setVisibility(View.INVISIBLE);
                isCanceled = true;

            }
        });
        btnPrev = findViewById(R.id.btnPrev);
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startButton.setText("Begin");
                timerText.setVisibility(View.INVISIBLE);
                isCanceled = true;
                ex_image.setVisibility(View.VISIBLE);
                startButton.setVisibility(View.VISIBLE);
                getReadyLayout.setVisibility(View.INVISIBLE);
                    ex_id--;
                    if(ex_id<0)
                        ex_id=0;

                    progressBar.setProgress(ex_id);

                    timerText.setText("");
                ex_image.setImageResource(List.get(ex_id).getImage_id());
                exerciseName.setText(List.get(ex_id).getName());

            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startButton.setText("Begin");
                timerText.setVisibility(View.INVISIBLE);
                isCanceled = true;
                    if (ex_id < List.size()) {
                        ex_image.setVisibility(View.VISIBLE);
                        startButton.setVisibility(View.VISIBLE);

                        getReadyLayout.setVisibility(View.INVISIBLE);

                        if (ex_id < List.size() - 1) {
                            ex_id++;

                            progressBar.setProgress(ex_id);

                            timerText.setText("");
                        } else {
                            showDone();
                        }

                        ex_image.setImageResource(List.get(ex_id).getImage_id());
                        exerciseName.setText(List.get(ex_id).getName());
                    } else {
                        showDone();
                    }
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startButton.getText().toString().toLowerCase().equals("begin")) {
                    showExercises();
                    timerText.setVisibility(View.VISIBLE);
                    startButton.setText("PAUSE");
                    isPaused = false;
                    isCanceled = false;
                    CountDownTimer timer = new CountDownTimer(Common.TIME_LIMIT_EASY,1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            if (isPaused||isCanceled){
                                cancel();
                            }
                            else{
                                timerText.setText(""+millisUntilFinished/1000);
                                timeRemaining = millisUntilFinished;
                            }
                            String tText = timerText.getText().toString();
                            if(tText.equals("2")){
                                ex_image.setImageResource(R.drawable.exercise1);
                            }
                            else if (tText.equals("3")){
                                ex_image.setImageResource(R.drawable.exercise2);
                            }
                            else if (tText.equals("4")){
                                ex_image.setImageResource(R.drawable.exercise1);
                            }
                            else if (tText.equals("1")){
                                ex_image.setImageResource(R.drawable.exercise2);
                            }
                        }

                        @Override
                        public void onFinish() {
                            if (ex_id < List.size() - 1) {
                                ex_id++;

                                progressBar.setProgress(ex_id);

                                timerText.setText("");

                                setExerciseInformation(ex_id);
                            } else {
                                showDone();
                            }
                            if (done>5)
                                done=5;
                            done++;
                            txtDone.setText(done + " Done");
                            left--;
                            if (left < 0)
                                left = 0;
                            txtLeft.setText(left + " Left");
                            startButton.setText("BEGIN");
                        }
                    }.start();


                }
                else if (startButton.getText().toString().toLowerCase().equals("pause")){
                    startButton.setText("Resume");
                    isPaused = true;

                }
                else if (startButton.getText().toString().toLowerCase().equals("resume")){
                    isPaused = false;
                    isCanceled = false;
                    long millisInFuture = timeRemaining;
                    new CountDownTimer(millisInFuture,1000){
                        public void onTick(long millisInFinished){
                            startButton.setText("Pause");
                            if(isPaused || isCanceled){
                                cancel();
                            }
                            else {
                                timerText.setText("" + millisInFinished / 1000);
                                timeRemaining = millisInFinished;
                            }
                        }


                        @Override
                        public void onFinish() {
                            if (ex_id < List.size() - 1) {
                                ex_id++;

                                progressBar.setProgress(ex_id);

                                timerText.setText("");

                                setExerciseInformation(ex_id);
                            } else {
                                showDone();
                            }
                            if (done>5)
                                done=5;
                            done++;
                            txtDone.setText(done + " Done");
                            left--;
                            if (left < 0)
                                left = 0;
                            txtLeft.setText(left + " Left");
                            startButton.setText("BEGIN");
                        }
                    }.start();
                }
            }
        });
        btnEnd = findViewById(R.id.btnEnd);
        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);}
        });

    }


    private void showExercises() {
        if (ex_id < List.size()) {
            ex_image.setVisibility(View.VISIBLE);
            startButton.setVisibility(View.VISIBLE);
            getReadyLayout.setVisibility(View.INVISIBLE);
            ex_image.setImageResource(List.get(ex_id).getImage_id());
            exerciseName.setText(List.get(ex_id).getName());
        } else {
            showDone();
        }
    }

    private void showDone() {
        ex_image.setVisibility(View.INVISIBLE);
        startButton.setVisibility(View.INVISIBLE);
        timerText.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        exerciseName.setVisibility(View.INVISIBLE);
        btnNext.setVisibility(View.INVISIBLE);
        btnPrev.setVisibility(View.INVISIBLE);
        btnStop.setVisibility(View.INVISIBLE);
        txtLeft.setVisibility(View.INVISIBLE);
        txtDone.setVisibility(View.INVISIBLE);

        getReadyLayout.setVisibility(View.VISIBLE);

        getReadyText.setText("CONGRATULATIONS!");
    }

    private void setExerciseInformation(int exerciseId) {
        ex_image.setImageResource(List.get(ex_id).getImage_id());
        exerciseName.setText(List.get(exerciseId).getName());

        ex_image.setVisibility(View.VISIBLE);
        startButton.setVisibility(View.VISIBLE);
        timerText.setVisibility(View.VISIBLE);

        getReadyLayout.setVisibility(View.INVISIBLE);
    }
    private void initData() {
        List.add(new Exercises(R.drawable.a5, "1. exercise"));
        List.add(new Exercises(R.drawable.a4, "2. exercise"));


    }



}
