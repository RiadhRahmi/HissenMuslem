package com.wridmob.hissenmuslem.receivers;


import java.util.Calendar;

import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

public class ScheduleDiker {

    private ScheduleService mBoundService;
    private Context mContext;
    private boolean mIsBound;

    public ScheduleDiker(Context context) {
        mContext = context;
    }


    public void doBindService() {
        mContext.bindService(new Intent(mContext, ScheduleService.class), mConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }


    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            mBoundService = ((ScheduleService.ServiceBinder) service).getService();
        }

        public void onServiceDisconnected(ComponentName className) {
            mBoundService = null;
        }
    };



    public void setAlarmForNotification(Calendar c,int notifyID) {
        mBoundService.setAlarm(c,notifyID);
    }







    public void doUnbindService() {
        if (mIsBound) {
            mContext.unbindService(mConnection);
            mIsBound = false;
        }
    }
}