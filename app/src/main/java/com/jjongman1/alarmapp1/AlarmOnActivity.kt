package com.jjongman1.alarmapp1

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class AlarmOnActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_on)

        var mediaPlayer: MediaPlayer = MediaPlayer.create(this,R.raw.alarm1)
        Toast.makeText(this, "I'm a BC_Receiver and we have received!", Toast.LENGTH_SHORT).show()
        mediaPlayer.start()

        val btnStop = findViewById<Button>(R.id.bt_StopAlarm)
    // 알람 Cancel
        btnStop.setOnClickListener {
            mediaPlayer.stop()
            // 현재 Activity 에서 알람 cancel 하기. 알람 cancel 시에는 PendingIntent 가 동일한 코드를 갖고 있어야 함.
            val cancelingAlarmManger = this.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
            val cancelingIntent = Intent(this,  MyBroadCastReceiver::class.java)
            val cancelingPendingIntent = PendingIntent.getBroadcast(this,812,cancelingIntent, PendingIntent.FLAG_NO_CREATE)
            //FLAG_NO_CREATE: Flag indicating that if the described PendingIntent does not already exist, then simply return null instead of creating it.

            if(cancelingPendingIntent== null){
                Toast.makeText(this,"PI is null!",Toast.LENGTH_SHORT).show()

            }
            if(cancelingPendingIntent!=null && cancelingAlarmManger != null)
            {
                cancelingAlarmManger.cancel(cancelingPendingIntent)
                Toast.makeText(this, "Canceled this Alarm", Toast.LENGTH_SHORT).show()
            }


        }
    }
}