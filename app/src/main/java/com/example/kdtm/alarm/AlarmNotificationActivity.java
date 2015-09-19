package com.example.kdtm.alarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;

/**
 * Created by kdtm on 2015/09/09.
 */
public class AlarmNotificationActivity extends Activity {
    private MediaPlayer mp;
    private TextView alarmNowText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_notification);

        // スクリーンロックを解除する
        // 権限が必要
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Toast.makeText(this, "アラーム！", Toast.LENGTH_SHORT).show();

        Button stop = (Button)findViewById(R.id.alarm_stop);
        stop.setOnClickListener(new SaveButtonClickListener());

        Button snooze = (Button)findViewById(R.id.snooze);
        snooze.setOnClickListener(new SnoozeButtonClickListener());
    }

    @Override
    public void onStart() {
        super.onStart();
        Toast.makeText(getApplicationContext(), "アラームスタート！", Toast.LENGTH_LONG).show();
        // 音を鳴らす
        if (mp == null)
            // resのrawディレクトリにtest.mp3を置いてある
            mp = MediaPlayer.create(this, R.raw.test);
        mp.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopAndRelaese();
    }

    class SaveButtonClickListener implements View.OnClickListener {
        public void onClick(View v){
            if (mp != null) {
                mp.stop();
                mp.release();
            }
        }
    }

    class SnoozeButtonClickListener implements View.OnClickListener {
        public void onClick(View v) {
            if (mp != null) {
                mp.stop();
                mp.release();
            }

            Calendar calendar = Calendar.getInstance();
            // Calendarを使って現在の時間をミリ秒で取得
            calendar.setTimeInMillis(System.currentTimeMillis());
            // 5秒後に設定
            calendar.add(Calendar.SECOND, 300);

            Intent intent = new Intent(getApplicationContext(), AlarmBroadcastReceiver.class);
            PendingIntent pending = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

            // アラームをセットする
            AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);

            Toast.makeText(getApplicationContext(), "Set Alarm ", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopAndRelaese() {
        if (mp != null) {
            mp.stop();
            mp.release();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        alarmNowText = (TextView) findViewById(R.id.alarm_now_time);
    }
}
