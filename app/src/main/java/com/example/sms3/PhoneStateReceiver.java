package com.example.sms3;

import static com.example.sms3.MainActivity.cbWhenDecline;
import static com.example.sms3.MainActivity.isCheckBoxDecline;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.Date;

public class PhoneStateReceiver extends BroadcastReceiver {
    String number, state;
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        Context myContext = context;

        Log.d("msg", "save in broadcast");
        Log.d("msg", "intent.getAction() = " + intent.getAction() );

        if(intent.getAction().equals("android.intent.action.PHONE_STATE")){

            state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
//            if (number.startsWith("+") && number.length() > 3) {
//                number= number.substring(3);
//            }
            Logger.addRecordToLog("Data "+ new Date() + " Otrzymano Broadcast" +
                    " state =" +state + " number = " + number);
            Log.d("msg", "state = " + state + " isCheckBoxDecline = "+ isCheckBoxDecline + " number = "+ number);
            if(state.equals(TelephonyManager.EXTRA_STATE_RINGING) ){
                if(number!="null"){
                    Log.d("msg", "Połączenie z " +number);
                    doAction(myContext, intent) ;
                }
            }
//            Logger.addRecordToLog("Data "+ new Date() + " Otrzymano Broadcast" +
//            " state =" +state + " number = " + number);
//            if(state.equals(TelephonyManager.EXTRA_STATE_RINGING) ||
//                    state.equals(TelephonyManager.EXTRA_STATE_IDLE)
//                    ){
//                Log.d("msg", "Połączenie z " +number);
//                doAction(myContext, intent);
//            }
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
        if (cbWhenDecline.isChecked() && number != "" && number != null){
            Log.d("msg", "w IF isChecked = " + cbWhenDecline.isChecked() );
            try {
                Log.d("msg", "Receiver start");
                Log.d("msg", "Broadcast number : "+ number);
                Intent myIntent = new Intent(myContext, SecondActivity.class);
                myIntent.putExtra("number",number);
                myIntent.putExtra("state",state);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                myContext.startActivity(myIntent);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}