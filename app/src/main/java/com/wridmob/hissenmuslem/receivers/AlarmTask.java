package com.wridmob.hissenmuslem.receivers;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

public class AlarmTask implements Runnable {
    private final Calendar date;
    private final AlarmManager alarmManager;
    private final Context context;
    private  int notifyID;
    public AlarmTask(Context context, Calendar date,int notifyID) {
        this.context = context;
        this.alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        this.date = date;
        this.notifyID = notifyID;
    }

    @Override
    public void run() {
        Intent intent = new Intent(context, NotifyService.class);
        intent.putExtra(NotifyService.INTENT_NOTIFY, true);
        intent.putExtra("notifyID",notifyID);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, date.getTimeInMillis(),60*1000, pendingIntent);
        //24*60*
     }
}