package com.jjongman1.alarmapp1 //Version 0.02c for real

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity()
{
    var hoursSelected: Int = 0
    var minutesSelected : Int = 0
    var secondsSelected : Int = 0
    var myAlarmMgr: AlarmManager? = null
    lateinit var myAlarmIntent : PendingIntent



    val timePicker by lazy {findViewById<TimePicker>(R.id.id_TimePicker)  }
    val tv_Display by lazy{findViewById<TextView>(R.id.id_Tv_Display)}
    val btnSetAlarm by lazy {findViewById<Button>(R.id.id_Bt_SetAlarm) }


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_Display.setText("Waiting for Spinner Input")

        //getSystemService(): Return the handle to a system-level service by name.
        myAlarmMgr = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        var myAlarmIntent : PendingIntent = Intent(applicationContext, MyBroadCastReceiver::class.java).let { myIntentYo ->
            PendingIntent.getBroadcast(this, 812, myIntentYo, 0)
            //.let 은 결과값을 return 함! (apply 는 기존 객체 값을 리턴!)
            // '0' 값: 만약 현재 제공된 parameter 와 일치하는 PendingIntent 가 없다면, 새로운 PI 를 만들고 return 해.
            // 만약 현재 제공된 parameter 와 일치하는 PendingIntent 가 있다면, 원래 있는(existing) PI 를 return 해.
        }



    // Spinner 입력 시 호출됨.
        timePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
            displaySpinner()
        }

    // Set Alarm Button v2 (13:25 분에 울리고/ repeat once a day)
    btnSetAlarm.setOnClickListener {

        // 1. Set the alarm to start at chosen Time .apply { 이 안의 function 을 적용}
        val myCalendar : Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis() // int 로 바꾸기 위해 이걸 쓰는듯. January 1, 1970 UTC 자정 기준으로부터 현재까지의 시간(milliseconds) (시간은 60sec = 1초고 이러니깐 계산 어려워서)
            set(Calendar.HOUR_OF_DAY, hoursSelected) // = this.set(xxx) = myCalendar.set(xx)
            set(Calendar.MINUTE, minutesSelected)
            set(Calendar.SECOND, 0)

            Toast.makeText(applicationContext,"알람이 $hoursSelected 시 $minutesSelected 분으로 설정되었습니다.", Toast.LENGTH_SHORT).show()

            // TODO: also check setWeekDate
        }
        // 2. Set repeating 1분마다 반복!
        myAlarmMgr?.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                myCalendar.timeInMillis, // 작동 시작 시점?
                1000 * 60 * 1, // interval
                myAlarmIntent
        )
    }


    // Set Alarm Button v1 (Timer)
//        btnSetAlarm.setOnClickListener {
//            //var secondInput: Int = etSecondInput.text.toString().toInt()
//            var myIntent = Intent(applicationContext, MyBroadCastReceiver::class.java) //where we want to go
//
////PendingIntent.getBroadcast() : Retrieve a PendingIntent that will perform a broadcast, like calling Context
////PendingIntent 란: Future Intent 인것임. "getBroadcast() 가 반환하는 객체를 다른 응용 프로그램으로 전달!"
//// 만약 여기서 getBroadcast 대신 getActivity 를 하면 바로  Activity 로 이동하게 됨. 즉 시간/요일 기다린 후 처리가 안됨.
//// getBroadcast 를 사용하여 알람이 울릴 시간에 브로드캐스트 리시버(MyBroadCastReceiver.kt) 로 보낸다음 거기서 처리해줌.

    //            val myPendingIntent: PendingIntent = PendingIntent.getBroadcast(applicationContext, 812, myIntent, PendingIntent.FLAG_ONE_SHOT)
//
//            val alarmManager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
//            alarmManager.set(AlarmManager.RTC_WAKEUP,
//                    System.currentTimeMillis() + secondInput*1000, myPendingIntent)
//
//            //TODO: 초기 laungching 했을 때 이전 선택값으로 화면에 띄우기 (오준석-BMI app or https://webnautes.tistory.com/1365 참고)
//
//        }
    }
    fun displaySpinner()
    {
        timePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
            hoursSelected = hourOfDay
            minutesSelected = minute

            //Text 로 세팅된 시간 표시 (AM PM 표기 포함)
            when(hoursSelected) {
                in 1..11 -> tv_Display.setText("Alarm set at ${hoursSelected.toString()}:${minutesSelected.toString()} AM")
                12 -> tv_Display.setText("Alarm set at 12: ${minutesSelected.toString()} PM")
                in 13..23 -> tv_Display.setText("Alarm set at ${(hoursSelected-12).toString()}:${minutesSelected.toString()} PM")
                0 -> tv_Display.setText("Alarm set at 00: ${minutesSelected.toString()} AM")
            }
        }
    }

}