package com.example.sms3;

import static com.example.sms3.MainActivity.cbWhenDecline;
import static com.example.sms3.MainActivity.isCheckBoxDecline;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

public class PhoneStateReceiver extends BroadcastReceiver {
    String number;
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        Context myContext = context;
        if(intent.getAction().equals("android.intent.action.PHONE_STATE")){

            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            Log.d("msg", "state = " + state);
            if(state.equals(TelephonyManager.EXTRA_STATE_RINGING) ||
                    state.equals(TelephonyManager.EXTRA_STATE_IDLE)
                    ){
                Log.d("msg", "Połączenie z " +number);
                doAction(myContext, intent);
            }
//            if(state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
//                Log.d("msg", "Połączenie odebrane");
//                Log.d("msg", "outgoing number : " + number);
//            }
//            else if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
//                Log.d("msg", "Dzwoni");
//                Log.d("msg", "incoming number : " + number);
//                doAction(myContext, intent);
//            }
//            else if(state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
//                Log.d("msg", "Połączenie odrzucone (zakończone)");
//                Log.d("msg", "outgoing number : " + number);
//               doAction(myContext, intent);
//            }
        }
    }

    private void doAction(Context myContext, Intent intent) {
        Log.d("msg", "isChecked = " + isCheckBoxDecline );
        if (cbWhenDecline.isChecked() && number != ""){
            Log.d("msg", "w IF isChecked = " + cbWhenDecline.isChecked() );
            try {
                Log.d("msg", "Receiver start");
                Log.d("msg", "Broadcast number : "+ number);
                Intent myIntent = new Intent(myContext, SecondActivity.class);
                myIntent.putExtra("number",number);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                myContext.startActivity(myIntent);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}