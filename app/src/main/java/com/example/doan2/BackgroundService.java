package com.example.doan2;
//import android.app.IntentService;
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Handler;
//import android.os.IBinder;
//import android.widget.Toast;
//
//import androidx.annotation.Nullable;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.time.Duration;
//import java.util.Timer;
//import java.util.TimerTask;
//
//public class BackgroundService extends IntentService {
//    public static final int notify = 5000;  //interval between two services(Here Service run every 5 seconds)
//    private Handler mHandler = new Handler();   //run on another Thread to avoid crash
//    private Timer mTimer = null;    //timer handling
//    private FirebaseService firebaseService;
//    private String[] keys = {"khigatram1", "khigatram2"};
//    private Context appContext;
//
//    /**
//     * Creates an IntentService.  Invoked by your subclass's constructor.
//     *
//     * @param name Used to name the worker thread, important only for debugging.
//     */
//    public BackgroundService(String name) {
//        super(name);
//    }
//
//
//    @Override
//    protected void onHandleIntent(@Nullable Intent intent) {
//
//    }
//
////
////    @Override
////    public int onStartCommand(Intent intent, int flags, int startId) {
////        appContext = getBaseContext();
////        return super.onStartCommand(intent, flags, startId);
////    }
////
//    @Override
//    public void onCreate() {
//        if (mTimer != null) // Cancel if already existed
//            mTimer.cancel();
//        else
//            mTimer = new Timer();   //recreate new
//        mTimer.scheduleAtFixedRate(new GetGasData(), 0, notify);   //Schedule task
//        firebaseService = new FirebaseService();
//    }
////
////    @Override
////    public void onDestroy() {
////        super.onDestroy();
////        mTimer.cancel();    //For Cancel Timer
////    }
//
//    class GetGasData extends TimerTask {
//        @Override
//        public void run() {
//            // run on another thread
//            mHandler.post(new Runnable() {
//                @Override
//                public void run() {
//                    JSONObject data = firebaseService.getData();
//                    System.out.println("run");
//                    for (String key: keys) {
//                        try {
//                            if (data.getInt(key) > 50) {
//                                baoDong();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//
//                private void baoDong() {
//                    Toast.makeText(appContext, "bao dong", Toast.LENGTH_LONG);
//                }
//            });
//
//        }
//
//    }
//}


import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;


import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.doan2.fragment.FirebaseService;
import com.tunanh.firewarning.R;

public class BackgroundService extends IntentService {
    public BackgroundService() {
        super("BackgroundService");
    }
    public static final int delay = 5000;  //interval between two services(Here Service run every 5 seconds)
    private FirebaseService firebaseService;
    private String[] keys = {"khigatram1", "khigatram2"};
    int id = 0;

    @Override
    public void onHandleIntent(Intent i) {
        firebaseService = new FirebaseService();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            startMyOwnForeground();
        else
            startForeground(1, new Notification());

        try {
            while (true) {
                Thread.sleep(delay);
                JSONObject data = firebaseService.getData();
                    for (String key: keys) {
                        try {
                            if (data.getInt(key) > 50) {
                                raiseNotification(key);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            stopForeground(true);
        }
    }

    private void raiseNotification(String key) {
        String description;
        if (key.equals(keys[0]))
            description = "Khí gas trạm 1 báo động";
        else
            description = "Khí gas trạm 2 báo động";
        NotificationCompat.Builder b = new NotificationCompat.Builder(this);
//        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        b.setDefaults(Notification.DEFAULT_SOUND)
                .setChannelId("com.example.test")
                .setWhen(System.currentTimeMillis());
        b.setContentTitle("Cảnh báo khí gas")
                .setContentText(description)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
//                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setSmallIcon(android.R.drawable.stat_notify_error);

        NotificationManager mgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        mgr.notify(++id, b.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startMyOwnForeground() {
        String NOTIFICATION_CHANNEL_ID = "com.example.test";
        String channelName = "My Background Service";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_HIGH);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("App is running in background")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(101, notification);
    }
}