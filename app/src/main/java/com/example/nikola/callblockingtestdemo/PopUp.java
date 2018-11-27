package com.example.nikola.callblockingtestdemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class PopUp extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_lyout);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        TelephonyManager telephonyManager = ((TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE));
        String operatorName = telephonyManager.getNetworkOperatorName();


        String number = getIntent().getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
        TextView text = (TextView)findViewById(R.id.text);
        text.setText("***WARNING***" +"\n"+ "\n"+"Incoming call from " + number +"\n" + "Operator name " + operatorName+"\n"+"This number is on your SCAM list" );
    }
}
