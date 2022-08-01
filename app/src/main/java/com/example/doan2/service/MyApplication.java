package com.example.doan2.service;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

 public class MyApplication extends Application {

    public static final String CHANNEL_ID1="push_notification_id1";
     public static final String CHANNEL_ID2="push_notification_id2";
    @Override
    public void onCreate() {
            super.onCreate();
            createChanNelNotification();
    }

    private void createChanNelNotification() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel1 = new NotificationChannel(CHANNEL_ID1,"cảnh báo", NotificationManager.IMPORTANCE_HIGH);
            NotificationChannel channel2 = new NotificationChannel(CHANNEL_ID2,"hien thi", NotificationManager.IMPORTANCE_LOW);
            NotificationManager manager= getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
        }
    }
}
