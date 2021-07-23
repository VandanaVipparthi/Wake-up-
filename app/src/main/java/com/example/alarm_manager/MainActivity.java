package com.example.alarm_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DigitalClock;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private TextView TimeTextView;
    DigitalClock currentTime;
    Button canceltime;
    boolean cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TimeTextView = findViewById(R.id.timeTextView);
        Button pickTime = findViewById(R.id.picktime);
        currentTime = findViewById(R.id.textClock);
        canceltime=findViewById(R.id.canceltime);
        canceltime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel=true;
            }
        });

        final Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
        pickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int hours = c.get(Calendar.HOUR);
                int mins = c.get(Calendar.MINUTE);
                Timer t1 = new Timer();
                cancel=false;
                t1.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        while (currentTime.getText().toString().equals(TimeTextView.getText().toString())) {
                            while(cancel==false){
                                r.play();
                            }
                            r.stop();
                        }

                        }
                },0,1000);
                TimePickerDialog t = new TimePickerDialog(MainActivity.this, R.style.ThemeOverlay_AppCompat_Dialog_Alert, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar c1 = Calendar.getInstance();
                        c1.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c1.set(Calendar.MINUTE, minute);
                        c1.setTimeZone((TimeZone.getDefault()));
                        SimpleDateFormat format = new SimpleDateFormat("h:mm a");
                        String time = format.format(c1.getTime());
                        TimeTextView.setText(time);
                    }

                }, hours, mins, false);
                t.show();
            }
        });
    }

}