package com.example.sms3;

import static com.example.sms3.R.color.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
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
    public Switch switch1,switch2, switch3,switch4;
    public Button button1;
    public  static boolean isOn;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Permission 1 Granted", Toast.LENGTH_SHORT).show();
                Log.d("msg", "permission granted 1");
            } else {
//                Toast.makeText(this, "Permission 1 DENIED", Toast.LENGTH_SHORT).show();
                Log.d("msg", "permission deined 1");
            }
            logs=logs+"\nSEND_SMS = " + checkSelfPermission(android.Manifest.permission.SEND_SMS);
        }else if (requestCode == 2){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Permission  2 Granted", Toast.LENGTH_SHORT).show();
                Log.d("msg", "permission granted 2");
            } else {
//                Toast.makeText(this, "Permission 2 DENIED", Toast.LENGTH_SHORT).show();
                Log.d("msg", "permission deined 2");
            }
            logs=logs+"\nREAD_PHONE_STATE = " + checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
        }else if(requestCode == 3){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Permission  3 Granted", Toast.LENGTH_SHORT).show();
                Log.d("msg", "permission granted 3");
            } else {
//                Toast.makeText(this, "Permission 3 DENIED", Toast.LENGTH_SHORT).show();
                Log.d("msg", "permission deined 3");
            }
            logs=logs+"\nREAD_PHONE_NUMBERS = " + checkSelfPermission(Manifest.permission.READ_PHONE_NUMBERS);
        }else if(requestCode == 4){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Permission  4 Granted", Toast.LENGTH_SHORT).show();
                Log.d("msg", "permission granted 4");
            } else {
//                Toast.makeText(this, "Permission 4 DENIED", Toast.LENGTH_SHORT).show();
                Log.d("msg", "permission deined 4");
            }
            logs=logs+"\nREAD_CALL_LOG = " + checkSelfPermission(Manifest.permission.READ_CALL_LOG);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("msg", String.valueOf(android.os.Build.VERSION.SDK_INT));
        Log.d("msg", String.valueOf(android.os.Build.VERSION_CODES.M));
        tvLogs = (TextView) findViewById(R.id.textView4);
        logs="Logi: ";
        logs=logs+"\nAPI = "+String.valueOf(android.os.Build.VERSION.SDK_INT);
        tvLogs.setText(logs); //set text for text view

        switch1 = (Switch) findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("Switch1 State=", ""+isChecked);
                if (checkSelfPermission(android.Manifest.permission.SEND_SMS)
                        == PackageManager.PERMISSION_DENIED) {
                    Log.d("msg", "permission denied to SEND_SMS - requesting it");
                    logs=logs+"\n.SEND_SMS = " + "Denied";
                    tvLogs.setText(logs); //set text for text view
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.SEND_SMS},
                            1);
                }else {
                    logs=logs+"\n.SEND_SMS = " + "Granted";
                    tvLogs.setText(logs); //set text for text view
                };
            }
        });
        switch2 = (Switch) findViewById(R.id.switch2);
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("Switch2 State=", ""+isChecked);
                if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                        == PackageManager.PERMISSION_DENIED) {
                    Log.d("msg", "permission denied to Phone state - requesting it");
                    logs=logs+"\n.READ_PHONE_STATE = " + "Denied";
                    tvLogs.setText(logs); //set text for text view
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.READ_PHONE_STATE},
                            2);
                }else {
                    logs=logs+"\n.READ_PHONE_STATE = " + "Granted";
                    tvLogs.setText(logs); //set text for text view
                };
            }
        });
        switch3 = (Switch) findViewById(R.id.switch3);
        switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("Switch3 State=", ""+isChecked);
                if (checkSelfPermission(Manifest.permission.READ_PHONE_NUMBERS)
                        == PackageManager.PERMISSION_DENIED) {
                    Log.d("msg", "permission denied to Phone number - requesting it");
                    logs=logs+"\n.READ_PHONE_NUMBERS = " + "Denied";
                    tvLogs.setText(logs); //set text for text view
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.READ_PHONE_NUMBERS},
                            3);
                    Log.d("msg", "permission denied to Phone number po");
                }else {
                    logs=logs+"\n.READ_PHONE_NUMBERS = " + "Granted";
                    tvLogs.setText(logs); //set text for text view
                };
            }
        });
        switch4 = (Switch) findViewById(R.id.switch4);
        switch4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("Switch4 State=", ""+isChecked);
                if (checkSelfPermission(Manifest.permission.READ_CALL_LOG)
                        != PackageManager.PERMISSION_GRANTED ) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.READ_CALL_LOG}, 4);
                    Log.d("msg", "permission denied to read call log");
                    logs=logs+"\n.READ_CALL_LOG = " + "Denied";
                    tvLogs.setText(logs); //set text for text view
                }else {
                    logs=logs+"\n.READ_CALL_LOG = " + "Granted";
                    tvLogs.setText(logs); //set text for text view
                };
            }
        });
        button1 =(Button) findViewById(R.id.button);
        button1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                Log.d("msg", "Button click");
                logs="";

                if(switch1.isChecked() && switch2.isChecked() && switch3.isChecked() &&switch4.isChecked()){

                    if (isOn) {
                        isOn = false;
                        Toast.makeText(MainActivity.this , "Wyłączone", Toast.LENGTH_SHORT).show();
                        button1.setText("Włącz");
                        button1.setBackgroundColor(Color.RED);
                    }else {
                        isOn = true;
                        Toast.makeText(MainActivity.this , "Włączone", Toast.LENGTH_SHORT).show();
                        button1.setText("Wyłącz");
                        button1.setBackgroundColor(Color.GREEN);

                    }
                } else {
                    Toast.makeText(MainActivity.this, "Włącz wszystkie uprawnienia", Toast.LENGTH_SHORT).show();
                }
                onResume();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        logs = "";
        logs=logs+"\nAPI = "+String.valueOf(android.os.Build.VERSION.SDK_INT);
        logs=logs+"\nisOn = " + (isOn ? "włączone": "wyłączone");

        String counter = null;
        TextView textView = (TextView) findViewById(R.id.textView3);
        counter = Integer.toString(count);
        Log.d("msg", " resume " + counter);
        textView.setText(counter); //set text for text view


//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//            Log.d("msg"," jestem w if");
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED
//        ) {
//            Log.d("msg"," jestem w if2 czyli jest jakieś  denide");
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
        try {
            PackageInfo pi = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_PERMISSIONS);
            for (int i = 0; i < pi.requestedPermissions.length; i++) {
                if ((pi.requestedPermissionsFlags[i] & PackageInfo.REQUESTED_PERMISSION_GRANTED) != 0) {
                    Log.d("msg", "granted " + pi.requestedPermissions[i]);
                    logs= logs + "\n"+
                            pi.requestedPermissions[i].toString().substring(18) +
                            "  = 1";
                } else {
                    Log.d("msg", "deined " + pi.requestedPermissions[i]);
                    logs= logs + "\n"+
                            pi.requestedPermissions[i].toString().substring(18) +
                            "  = 0";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        tvLogs.setText(logs); //set text for text view
    }
}