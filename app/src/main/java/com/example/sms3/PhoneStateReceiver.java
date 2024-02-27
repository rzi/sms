package com.example.sms3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class PhoneStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Log.d("msg","Receiver start");
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            Log.d("msg", "Number is ," + incomingNumber);
            Log.d("msg", "State is ," + state);
            if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                Toast.makeText(context,"Incoming Call State",Toast.LENGTH_SHORT).show();
                Toast.makeText(context,"Ringing State Number is -"+incomingNumber,Toast.LENGTH_SHORT).show();
                Log.d("msg","Incoming Call State" );
                Log.d("msg","Ringing State Number is " +incomingNumber );
            }
            if ((state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))){
                Toast.makeText(context,"Call Received State",Toast.LENGTH_SHORT).show();
                Log.d("msg","Call Received State "  );
            }
            if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                Toast.makeText(context,"Call Idle State",Toast.LENGTH_SHORT).show();
                Log.d("msg","Call Idle State "  );
            }


        }
        catch (Exception e){
            e.printStackTrace();
        }
    }




}