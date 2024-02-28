package com.example.sms3;

import static com.example.sms3.MainActivity.count;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        // getIntent() is a method from the started activity
        Intent myIntent = getIntent(); // gets the previously created intent
        String number = myIntent.getStringExtra("number"); // will return "FirstKeyValue"
        Log.d("msg", "number = " +number);
        String message ="ha ha ha ";
        try {
            // Get the default instance of the SmsManager
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number,
                    null,
                    message,
                    null,
                    null);
            Log.d("msg" ,"sms sent");
            Toast.makeText(getApplicationContext(),"Wysano SMS do dzwoniącego o treści: \n "+message,Toast.LENGTH_LONG).show();
            count = count +1;
            Log.d ("msg", "count = "+count);
        } catch (Exception ex) {
            Log.d("msg" ,"problem with sending of sms");
            Toast.makeText(getApplicationContext(),"Błąd wysyłania SMS! ",Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
        finish();

    }
}