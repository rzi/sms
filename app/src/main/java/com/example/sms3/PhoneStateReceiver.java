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

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Log.d("msg", "Receiver start");
            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            String number = incomingNumber.toString();
            Log.d("msg", "number : "+ number);
            sendSMS(number, " ha, ha, ha");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void sendSMS(String number, String  message) {
        Log.d("msg ", "SMS  z broadcaast");
        try {
            // Get the default instance of the SmsManager
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number,
                    null,
                    message,
                    null,
                    null);
                Log.d("msg" ,"sms sent");
        } catch (Exception ex) {
            Log.d("msg" ,"problem with sending of sms");
            ex.printStackTrace();
        }
    }
}