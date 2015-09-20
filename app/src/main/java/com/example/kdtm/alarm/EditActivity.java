package com.example.kdtm.alarm;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

/**
 * Created by kdtm on 2015/09/20.
 */
public class EditActivity extends Activity {
    private Integer hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);

        Button saveButton = (Button)findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new SaveButtonClickListener());
    }

    class SaveButtonClickListener implements View.OnClickListener {
        public void onClick(View v) {
            Alarm alarm = new Alarm();
            TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
            hour = timePicker.getCurrentHour();
            minute = timePicker.getCurrentMinute();
            Log.d("tim", Integer.toString(hour)+":"+Integer.toString(minute));
            alarm.time = Integer.toString(hour)+":"+Integer.toString(minute);
            AlarmList.insert(getApplicationContext(),alarm);
            finish();
        }
    }
}
