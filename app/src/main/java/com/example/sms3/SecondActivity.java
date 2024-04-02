package com.example.sms3;

import static com.example.sms3.MainActivity.cbWhenDecline;
import static com.example.sms3.MainActivity.count;
import static com.example.sms3.MainActivity.isOn;
import static com.example.sms3.MainActivity.mylist;
import static com.example.sms3.MainActivity.tvQty;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Iterator;

public class SecondActivity extends AppCompatActivity {
    public String mNumber, mText, mState, number,state;
    public String message ="ha ha ha ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        // getIntent() is a method from the started activity
        Intent myIntent = getIntent(); // gets the previously created intent
        number = myIntent.getStringExtra("number"); // will return "FirstKeyValue"
        state = myIntent.getStringExtra("state");
        Log.d("msg", "number from Intent= " +number + " state= "+ state);
        message ="ha ha ha ";

        if (mylist.size() == 0) {
            Log.d("msg" , "mylist.size = " +mylist.size());
            Toast.makeText(getApplicationContext(), "Lista numerów i SMSów jest pusta", Toast.LENGTH_LONG).show();
        }else if(number == "null"){
            Log.d("msg" , "brak numeru z intencji");
        }else {
            for (String item : mylist) {
                Log.d("msg" , "item = " +item);
                String[] words = item.split(",");
                mNumber = words[0];
                mText = words[1];
                Log.d("msg" , "mNumber = " +mNumber);
                Log.d("msg" , "mText = " +mText);
                if (mNumber.equals(number) && state.equals("RINGING")){
                    Log.d("msg" , "mNumber = "+ mNumber + " mText = " + mText);
                    //Toast.makeText(getApplicationContext(), "Wysłanie SMS na numer telefonu  "+ number, Toast.LENGTH_LONG).show();
                    if (isOn && mylist.size() >0 && cbWhenDecline.isChecked()) {
                        Log.d("msg" , "send sms");

                        sendSms();

                    }else {
                        Log.d("msg", "Nie wysano SMS ponieważ nie włączone");
                        Toast.makeText(getApplicationContext(), "Nie wysłano SMS ponieważ nie włączone", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
        finish();
    }

    private void sendSms() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // Your Code
                try {
                    // Get the default instance of the SmsManager
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(number,
                            null,
                            mText,
                            null,
                            null);
                    Log.d("msg", "sms sent");
                    Toast.makeText(getApplicationContext(), "Wysano SMS do dzwoniącego o treści: \n " + mText, Toast.LENGTH_LONG).show();
                    count= count+1;
                    Log.d("msg", "count = " + count);
                    tvQty.setText(String.valueOf(count));

                } catch (Exception ex) {
                    Log.d("msg", "problem with sending of sms");
                    Toast.makeText(getApplicationContext(), "Błąd wysyłania SMS! ", Toast.LENGTH_LONG).show();
                    ex.printStackTrace();
                }
            }
        }, 2000);


    }
}