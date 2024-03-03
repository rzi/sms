package com.example.sms3;

import static androidx.core.content.ContextCompat.startActivity;

import static com.example.sms3.MainActivity.cbWhenDecline;
import static com.example.sms3.MainActivity.isCheckBoxDecline;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class PhoneStateReceiver extends BroadcastReceiver {
    String number;
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("android.intent.action.PHONE_STATE")){

            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

            if(state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
                Log.d("msg", "Połączenie odebrane");
                String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                Log.d("msg", "outgoing number : " + number);
            }

            else if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                Log.d("msg", "Dzwoni");
                String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                Log.d("msg", "incoming number : " + number);
            }
            else if(state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                Log.d("msg", "Połączenie odrzucone");
                String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                Log.d("msg", "outgoing number : " + number);
                Log.d("msg", "isChecked = " + isCheckBoxDecline );
                if (cbWhenDecline.isChecked() && number != null){
                    Log.d("msg", "w IF isChecked = " + cbWhenDecline.isChecked() );
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
            }
        }


    }

    private void goToIntent(Context context ,String number) {
        Log.d("msg ", "gotintent");

        Intent myIntent = new Intent(context, SecondActivity.class);
        myIntent.putExtra("number",number);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(myIntent);
    }
}