package com.example.kdtm.alarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.Calendar;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 開始ボタン
        Button btn1 = (Button) this.findViewById(R.id.button1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 時間をセットする
                Calendar calendar = Calendar.getInstance();
                // Calendarを使って現在の時間をミリ秒で取得
                calendar.setTimeInMillis(System.currentTimeMillis());
                // 5秒後に設定
                calendar.add(Calendar.SECOND, 5);

                Intent intent = new Intent(getApplicationContext(), AlarmBroadcastReceiver.class);
                PendingIntent pending = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

                // アラームをセットする
                AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);

                Toast.makeText(getApplicationContext(), "Set Alarm ", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
