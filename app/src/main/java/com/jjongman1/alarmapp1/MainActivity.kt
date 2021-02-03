package com.jjongman1.alarmapp1

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//추가추가
// 2월3일 오후 10시 30분에 추가로 변경했다 칩시다.
// 변경 변경 오후 10시30 분~

        val etSecondInput = findViewById<EditText>(R.id.et_InputSeconds)
        val btnSetAlarm = findViewById<Button>(R.id.bt_SetAlarm)
        btnSetAlarm.setOnClickListener {
            var secondInput: Int = etSecondInput.text.toString().toInt()
            var myIntent = Intent(applicationContext, MyBroadCastReceiver::class.java) //where we want to go

//PendingIntent.getBroadcast() : Retrieve a PendingIntent that will perform a broadcast, like calling Context
//PendingIntent 란: Future Intent 인것임. "getBroadcast() 가 반환하는 객체를 다른 응용 프로그램으로 전달!"
// 만약 여기서 getBroadcast 대신 getActivity 를 하면 바로  Activity 로 이동하게 됨. 즉 시간/요일 기다린 후 처리가 안됨.
// getBroadcast 를 사용하여 알람이 울릴 시간에 브로드캐스트 리시버(MyBroadCastReceiver.kt) 로 보낸다음 거기서 처리해줌.
            val myPendingIntent: PendingIntent = PendingIntent.getBroadcast(applicationContext, 812, myIntent, PendingIntent.FLAG_ONE_SHOT)

            val alarmManager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.set(AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis() + secondInput*1000, myPendingIntent)
        }
    }
}