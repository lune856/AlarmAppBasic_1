package com.jjongman1.alarmapp1

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
        btnStop.setOnClickListener {
            mediaPlayer.stop()
        }
    }
}