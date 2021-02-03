package com.jjongman1.alarmapp1

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.widget.Toast

class MyBroadCastReceiver : BroadcastReceiver() //extends from..
{
    override fun onReceive(context: Context?, intent: Intent?) {
        var myIntent2 = Intent(context, AlarmOnActivity::class.java)

        // Task 란?
        // Android defines the unit of a sequence of user interactions as Task. A Task is a collection of activities that user interact when performing a certain job.

        //새로운 Task를 생성하여 그 Task 안에 Activity를 추가할 때 사용, 기존에 있던 Task들 중에 생성하려는 Activity와 동일한 Affinity를 가지고 있는 Task가 있다면 그 곳으로 Activity가 들어가게 됨
        // Intent.FLAG_ACTIVITY_NEW_TASK: If set, this activity will become the start of a new task on this history stack.
        // When using this flag, if a task is already running for the activity you are now starting, then a new activity will not be started;
        // 한마디로 알람 중복 설정 불가. 10초 후 울리게 한뒤 바로 15초 울리게 하면, 이미 10초 울리는 task 가 실행중이기에 15초뒤 울리는건 진행 안됨.

        // instead, the current task will simply be brought to the front of the screen with the state it was last in.
        myIntent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context?.startActivity(myIntent2)

    }
}