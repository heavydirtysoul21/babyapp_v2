package com.example.babyapp_v2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Debug;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.babyapp_v2.Database.BabyAppDB;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class Settings extends AppCompatActivity {

    Button btnSave;
    RadioButton rdiEasy, rdiMedium, rdiHard;
    RadioGroup rdiGroup;
    BabyAppDB babyAppDB;
    ToggleButton switchAlarm;
    TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        btnSave = (Button)findViewById(R.id.btnSave);
        rdiGroup = (RadioGroup)findViewById(R.id.rdiGroup);
        rdiEasy = (RadioButton)findViewById(R.id.rdiEz);
        rdiMedium = (RadioButton)findViewById(R.id.rdiMedium);
        rdiHard = (RadioButton)findViewById(R.id.rdiHard);
        timePicker = (TimePicker)findViewById(R.id.timePicker);
        babyAppDB = new BabyAppDB(this);
        switchAlarm = (ToggleButton) findViewById(R.id.switchAlarm);

        int mode = babyAppDB.getSettingMode();
        setRadioButton(mode);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                saveMode();
                saveAlarm(switchAlarm.isChecked());
                Toast.makeText(Settings.this,"Saved!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void saveAlarm(boolean checked) {
        if(checked)
        {
            AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            Intent intent;
            PendingIntent pendingIntent;

            intent = new Intent(Settings.this, AlarmNotificationReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);

            Calendar calendar  = Calendar.getInstance();
            Date toDay = Calendar.getInstance().getTime();
            calendar.set(toDay.getYear(),toDay.getMonth(),toDay.getDay(),timePicker.getHour(),timePicker.getMinute());

            manager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
            Log.d("Debug","Alarm will wake at : "+timePicker.getHour()+":"+timePicker.getMinute());


        }
        else
        {
            Intent intent = new Intent(Settings.this, AlarmNotificationReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);
            AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            manager.cancel(pendingIntent);
        }

    }

    private void saveMode() {
        int selectedID = rdiGroup.getCheckedRadioButtonId();
        if (selectedID == rdiEasy.getId())
            babyAppDB.saveSettingMode(0);
        else if (selectedID == rdiMedium.getId())
            babyAppDB.saveSettingMode(1);
        else if (selectedID == rdiHard.getId())
            babyAppDB.saveSettingMode(2);

    }

    private void setRadioButton(int mode) {
        if(mode == 0)
            rdiGroup.check(R.id.rdiEz);
        else if (mode == 1)
            rdiGroup.check(R.id.rdiMedium);
        else if (mode == 2)
            rdiGroup.check(R.id.rdiHard);
    }
}
