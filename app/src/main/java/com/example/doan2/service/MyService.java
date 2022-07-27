package com.example.doan2.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.doan2.Data;
import com.example.doan2.activity.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tunanh.firewarning.R;

public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        sendNotification();
        return START_NOT_STICKY;
    }

    private void sendNotification() {
        Intent intent= new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("kiemtra");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    Bitmap bitmap= BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                    Data data = snapshot.getValue(Data.class);
                    assert data != null;
                    Notification notification= new NotificationCompat.Builder(getApplicationContext(),MyApplication.CHANNEL_ID)
                            .setContentTitle("gas: "+ data.getGas()+"%")
                            .setContentText("temperature: "+ data.getTemperature()+"°C")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setLargeIcon(bitmap)
                            .setPriority(NotificationCompat.PRIORITY_LOW)
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(false)
                            .build();
                    notification.flags=notification.FLAG_NO_CLEAR;
//                    NotificationManagerCompat notificationManagerCompat= NotificationManagerCompat.from(getApplicationContext());
//                    notificationManagerCompat.notify(1,notification);
                    startForeground(1,notification);
//                speedometer.speedTo(data,time);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("ahihi", error.getMessage());
            }
        });

    }
}
