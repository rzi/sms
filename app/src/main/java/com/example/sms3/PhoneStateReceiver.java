package com.example.sms3;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class PhoneStateReceiver extends BroadcastReceiver {
    String number;
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Log.d("msg", "Receiver start");
            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            number = incomingNumber.toString();
            Log.d("msg", "number : "+ number);
            goToIntent(context, number);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void goToIntent(Context context, String number) {
        Log.d("msg ", "gotintent");
        Intent myIntent = new Intent(context, SecondActivity.class);
        myIntent.putExtra("number",number);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(myIntent);
    }
}