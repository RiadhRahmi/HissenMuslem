package com.wridmob.hissenmuslem.receivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.wridmob.hissenmuslem.R;
import com.wridmob.hissenmuslem.activites.SplashScreenActivity;


public class NotifyService extends Service {
    private static final int NOTIFICATION = 123;
    public static final String INTENT_NOTIFY = "com.wridmob.hissenmuslem.receivers";
    private NotificationManager mNM;


    public class ServiceBinder extends Binder {
        NotifyService getService() {
            return NotifyService.this;
        }
    }

    @Override
    public void onCreate() {
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getBooleanExtra(INTENT_NOTIFY, false))
            showNotification(intent.getIntExtra("notifyID",1));
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private final IBinder mBinder = new ServiceBinder();


    private void showNotification(int notifyID) {
        createNotification(notifyID);
       // stopSelf();
    }

    private final void createNotification(int notifyID){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.hessin_muslem)
                .setContentTitle(getResources().getString(R.string.app_name));
                if(notifyID == 12){
                    mBuilder.setContentText(getResources().getString(R.string.sousTitre));
                }else if(notifyID == 13){
                    mBuilder.setContentText(getResources().getString(R.string.sousTitre1));
                }else if (notifyID == 14){
                    mBuilder.setContentText(getResources().getString(R.string.sousTitre2));
                }
        Intent resultIntent = new Intent(this, SplashScreenActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(SplashScreenActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mBuilder.setSound(alarmSound);
        mNotificationManager.notify(notifyID, mBuilder.build());
    }

}