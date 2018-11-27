package com.example.nikola.callblockingtestdemo.ScamFragmentPackeg;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;
import com.example.nikola.callblockingtestdemo.App;
import com.example.nikola.callblockingtestdemo.MainActivity;
import com.example.nikola.callblockingtestdemo.PopUp;
import com.example.nikola.callblockingtestdemo.R;

import java.lang.reflect.Method;

public class ScamService extends Service {

    BroadcastReceiver sReciver;


    @Override
    public void onCreate() {
        super.onCreate();

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


        final IntentFilter it = new IntentFilter();
        it.addAction("android.intent.action.PHONE_STATE");
        sReciver = new ScamService.MyReciver();
        registerReceiver(sReciver, it);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notificationIntent=new Intent(this,MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,notificationIntent,0);



            Notification notification = new NotificationCompat.Builder(this, App.SECOND_CHANNEL_ID)
                    .setContentTitle("TelTech")
                    .setContentText("Running Scam list")
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



            for (int index = 0; index < ScamFragment.scam.size(); index++) {
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


                            if (PhoneNumberUtils.compare(number, String.valueOf(ScamFragment.scam.get(index)))) {

                                Intent i = new Intent(context, PopUp.class);
                                i.putExtras(intent);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

                                context.startActivity(i);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                    if (state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_OFFHOOK)) {

                        Toast.makeText(context, "Answered " + number, Toast.LENGTH_SHORT).show();
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