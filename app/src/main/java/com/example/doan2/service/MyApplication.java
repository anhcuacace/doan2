package com.example.doan2.service;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

 public class MyApplication extends Application {

    public static final String CHANNEL_ID="push_notification_id";
    @Override
    public void onCreate() {
            super.onCreate();
            createChanNelNotification();
    }

    private void createChanNelNotification() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,"hien thi", NotificationManager.IMPORTANCE_LOW);
            NotificationManager manager= getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}
