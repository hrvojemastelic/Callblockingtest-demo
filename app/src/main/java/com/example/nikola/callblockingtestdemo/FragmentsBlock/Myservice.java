package com.example.nikola.callblockingtestdemo.FragmentsBlock;

import android.app.ListFragment;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;
import com.example.nikola.callblockingtestdemo.App;
import com.example.nikola.callblockingtestdemo.MainActivity;
import com.example.nikola.callblockingtestdemo.R;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Myservice extends Service {

    BroadcastReceiver mReciver;

    @Override
    public void onCreate() {
        super.onCreate();



        final IntentFilter it = new IntentFilter();
        it.addAction("android.intent.action.PHONE_STATE");
        it.addAction("android:priority=\"999\"");
        mReciver = new MyReciver();
        registerReceiver(mReciver, it);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);




            Notification notification = new NotificationCompat.Builder(this, App.CHANNEL_ID)
                    .setContentTitle("TelTech")
                    .setContentText("Running Block list")
                    .setSmallIcon(R.drawable.ic_phone_black_24dp)
                    .setContentIntent(pendingIntent)
                    .build();


            startForeground(1, notification);

            return START_NOT_STICKY;


        }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class MyReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            for (int index = 0; index < com.example.nikola.callblockingtestdemo.FragmentsBlock.ListFragment.people.size(); index++) {
                ITelephony telephonyService;
                try {
                    String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
                    String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);

                    if (state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING)) {

                        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                        try {
                            Method m = tm.getClass().getDeclaredMethod("getITelephony");

                            m.setAccessible(true);
                            telephonyService = (ITelephony) m.invoke(tm);


                            if (PhoneNumberUtils.compare(number, String.valueOf(com.example.nikola.callblockingtestdemo.FragmentsBlock.ListFragment.people.get(index)))) {
                                telephonyService.endCall();
                                Toast.makeText(context, "Ending the call from: " + number, Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }




                    }
                    if (state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_OFFHOOK)) {

                    }
                    if (state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_IDLE)) {

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    };


        }


