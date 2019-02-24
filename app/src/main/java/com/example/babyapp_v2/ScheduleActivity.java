package com.example.babyapp_v2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.babyapp_v2.Adapter.RecyclerViewAdapter;
import com.example.babyapp_v2.Model.Exercises;

import java.util.ArrayList;
import java.util.List;

public class ScheduleActivity extends AppCompatActivity {
    List<Exercises> exercisesList = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        initData();
        recyclerView = (RecyclerView)findViewById(R.id.exercises);
        adapter = new RecyclerViewAdapter(exercisesList,getBaseContext());
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        exercisesList.add(new Exercises(R.drawable.a2, "first"));
        exercisesList.add(new Exercises(R.drawable.a3, "second"));
        exercisesList.add(new Exercises(R.drawable.a3, "third"));
        exercisesList.add(new Exercises(R.drawable.a3, "fourth"));
        exercisesList.add(new Exercises(R.drawable.a3, "fifth"));


    }
}
