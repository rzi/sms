package com.example.sms3;

import static com.example.sms3.MainActivity.isOn;
import static com.example.sms3.MainActivity.mylist;
import static com.example.sms3.Settings.count;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Iterator;

public class SecondActivity extends AppCompatActivity {
    public String mNumber, mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        // getIntent() is a method from the started activity
        Intent myIntent = getIntent(); // gets the previously created intent
        String number = myIntent.getStringExtra("number"); // will return "FirstKeyValue"
        Log.d("msg", "number ggg= " +number);
        String message ="ha ha ha ";
//        for (String item : mylist) {
//            Log.d("msg" , "item = " +item);
//        }
//        Iterator<String> someNumbersIterator = mylist.iterator();
//
//        while(someNumbersIterator.hasNext()) {
//            Log.d("msg" , "item = " +someNumbersIterator.next());
//        }
//        String element = mylist.get(0);

        if (mylist.size() ==0) {
            Log.d("msg" , "mylist.size = " +mylist.size());
            Toast.makeText(getApplicationContext(), "Lista numerów i SMSów jest pusta", Toast.LENGTH_LONG).show();
        }else{
            for (String item : mylist) {
            Log.d("msg" , "item = " +item);
                String[] words = item.split(",");
                mNumber = words[0];
                mText = words[1];
                Log.d("msg" , "mNumber = " +mNumber);
                Log.d("msg" , "mText = " +mText);
                if (mNumber.equals(number) ){
                    Log.d("msg" , "mNumber = "+ mNumber + " mText = " + mText);
                    Toast.makeText(getApplicationContext(), "Wysłanie SMS na numer telefonu + "+ number, Toast.LENGTH_LONG).show();
                }else {
                    Log.d("msg" , "Nie znaleziono numeru telefonu w liście");
                    Toast.makeText(getApplicationContext(), "Nie znaleziono numeru telefonu w liście", Toast.LENGTH_LONG).show();
                }
            }
        }

        if (isOn && mylist.size() >0) {
            try {
                // Get the default instance of the SmsManager
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(number,
                        null,
                        message,
                        null,
                        null);
                Log.d("msg", "sms sent");
                Toast.makeText(getApplicationContext(), "Wysano SMS do dzwoniącego o treści: \n " + message, Toast.LENGTH_LONG).show();
                count = count + 1;
                Log.d("msg", "count = " + count);
            } catch (Exception ex) {
                Log.d("msg", "problem with sending of sms");
                Toast.makeText(getApplicationContext(), "Błąd wysyłania SMS! ", Toast.LENGTH_LONG).show();
                ex.printStackTrace();
            }
        }else {
            Log.d("msg", "Nie wysano SMS ponieważ nie włączone");
            Toast.makeText(getApplicationContext(), "Nie wysłano SMS ponieważ nie włączone", Toast.LENGTH_LONG).show();
        }
        finish();

    }
}