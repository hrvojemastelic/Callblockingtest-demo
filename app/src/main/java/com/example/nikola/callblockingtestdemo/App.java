package com.example.nikola.callblockingtestdemo;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {

    public static final String CHANNEL_ID="teltech";
    public static final String SECOND_CHANNEL_ID="SECOND TELTECH";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        createsecondNorCh();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel=new NotificationChannel(
                    CHANNEL_ID,
                    "Tel tech",
                    NotificationManager.IMPORTANCE_DEFAULT

            );
            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }

    }
    private void createsecondNorCh(){

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel=new NotificationChannel(
                    SECOND_CHANNEL_ID,
                    "SECOND TELTECH",
                    NotificationManager.IMPORTANCE_DEFAULT

            );
            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
