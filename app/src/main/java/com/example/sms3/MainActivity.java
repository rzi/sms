package com.example.sms3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    private EditText txtMobile;
    private EditText txtMessage;
    private TextView tvLogs;
    private Button btnSms;
    private final int PERMISSION_REQUEST_CODE = 1;
    private static final int PERMISSION_SEND_SMS = 123;
    private String number;
    public static int count =0;
    public static String logs;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        Log.d("msg" , "GR = "+String.valueOf(grantResults.length));
//        Log.d("msg" , "permission0 = "+permissions[0]);
//        Log.d("msg" , "permission1 = "+permissions[1]);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission 1 Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission 1 DENIED", Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode == 2){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission  2 Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission 2 DENIED", Toast.LENGTH_SHORT).show();
            }
        }else if(requestCode == 3){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission  3 Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission 3 DENIED", Toast.LENGTH_SHORT).show();
            }
        }else if(requestCode == 4){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission  4 Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission 4 DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("msg", String.valueOf(android.os.Build.VERSION.SDK_INT));
        Log.d("msg", String.valueOf(android.os.Build.VERSION_CODES.M));
        tvLogs = (TextView) findViewById(R.id.textView4);
        logs="";
        logs=logs+"\nAPI = "+String.valueOf(android.os.Build.VERSION.SDK_INT);
        tvLogs.setText(logs); //set text for text view

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            Log.d("msg"," jestem w if");

            if (checkSelfPermission(android.Manifest.permission.SEND_SMS)
                    == PackageManager.PERMISSION_DENIED) {
                Log.d("msg", "permission denied to SEND_SMS - requesting it");
                logs=logs+"\nManifest.permission.SEND_SMS = false";
                tvLogs.setText(logs); //set text for text view
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.SEND_SMS},
                        1);
            }else {
                logs=logs+"\nManifest.permission.SEND_SMS = true";
                tvLogs.setText(logs); //set text for text view
            };
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                    == PackageManager.PERMISSION_DENIED) {
                Log.d("msg", "permission denied to Phone state - requesting it");
                logs=logs+"\nManifest.permission.READ_PHONE_STATE = false";
                tvLogs.setText(logs); //set text for text view
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        2);
            }else {
                logs=logs+"\nManifest.permission.READ_PHONE_STATE = true";
                tvLogs.setText(logs); //set text for text view
            };
            if (checkSelfPermission(Manifest.permission.READ_PHONE_NUMBERS)
                    == PackageManager.PERMISSION_DENIED) {
                Log.d("msg", "permission denied to Phone number - requesting it");
                logs=logs+"\nManifest.permission.READ_PHONE_NUMBERS = false";
                tvLogs.setText(logs); //set text for text view
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_PHONE_NUMBERS},
                        3);
                Log.d("msg", "permission denied to Phone number po");
            }else {
                logs=logs+"\nManifest.permission.READ_PHONE_NUMBERS = true";
                tvLogs.setText(logs); //set text for text view
            };
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_CALL_LOG}, 4);
                Log.d("msg", "permission denied to read call log");
                logs=logs+"\nManifest.permission.READ_CALL_LOG = false";
                tvLogs.setText(logs); //set text for text view
            }else {
                logs=logs+"\nManifest.permission.READ_CALL_LOG = true";
                tvLogs.setText(logs); //set text for text view
            };


        } else {
            TelephonyManager mTelephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            Log.d("msg"," jestem w if2");
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        TextView textView = (TextView) findViewById(R.id.textView3);
        String counter = Integer.toString(count);

        Log.d("msg", " resume "+ counter);
        textView.setText(counter); //set text for text view
//        tvLogs.refreshDrawableState();
    }
}