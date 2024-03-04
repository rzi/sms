package com.example.sms3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends AppCompatActivity {
    public Button btnExit;
    public static Switch switch1,switch2, switch3,switch4;
    public static String logs;
    public TextView tvLogs;
    public static int count = 0;
    public boolean fromIntent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Log.d("mag", "settings");
        Log.d("msg", String.valueOf(android.os.Build.VERSION.SDK_INT));
        Log.d("msg", String.valueOf(android.os.Build.VERSION_CODES.M));
        Log.d("msg", "permission denied");
        logs="Logi: ";
        logs=logs+"\nAPI = " + String.valueOf(android.os.Build.VERSION.SDK_INT);
        tvLogs = (TextView) findViewById(R.id.tvLogs);
        tvLogs.setText(logs); //set text for text view

        btnExit = (Button) findViewById(R.id.button3);
        btnExit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        switch1 = (Switch) findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("Switch1 State=", ""+isChecked);
                if (checkSelfPermission(android.Manifest.permission.SEND_SMS)
                        == PackageManager.PERMISSION_DENIED) {
                    Log.d("msg", "permission denied to SEND_SMS - requesting it");
                    logs = logs+"\nSEND_SMS = " + "Denied";
                    tvLogs.setText(logs); //set text for text view
                    ActivityCompat.requestPermissions(Settings.this,
                            new String[]{Manifest.permission.SEND_SMS},
                            1);
                }else {
                    logs=logs+"\nSEND_SMS = " + "Granted";
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
                    logs=logs+"\nREAD_PHONE_STATE = " + "Denied";
                    tvLogs.setText(logs); //set text for text view
                    ActivityCompat.requestPermissions(Settings.this,
                            new String[]{Manifest.permission.READ_PHONE_STATE},
                            2);
                }else {
                    logs=logs+"\nREAD_PHONE_STATE = " + "Granted";
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
                    logs=logs+"\nREAD_PHONE_NUMBERS = " + "Denied";
                    tvLogs.setText(logs); //set text for text view
                    ActivityCompat.requestPermissions(Settings.this,
                            new String[]{Manifest.permission.READ_PHONE_NUMBERS},
                            3);
                    Log.d("msg", "permission denied to Phone number po");
                }else {
                    logs=logs+"\nREAD_PHONE_NUMBERS = " + "Granted";
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
                    ActivityCompat.requestPermissions(Settings.this,
                            new String[]{Manifest.permission.READ_CALL_LOG}, 4);
                    Log.d("msg", "permission denied to read call log");
                    logs=logs+"\nREAD_CALL_LOG = " + "Denied";
                    tvLogs.setText(logs); //set text for text view
                }else {
                    logs=logs+"\nREAD_CALL_LOG = " + "Granted";
                    tvLogs.setText(logs); //set text for text view
                };
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        String value;
        Bundle bundle = getIntent().getExtras();
        Log.d("msg", "bundle = "+ bundle);
        if (bundle != null) {
            value = bundle.getString("EXTRA_MESSAGE");
            Log.d("msg", "value = "+ value);
            if (value.equals("btnSettings")){
                fromIntent = true;
            }else {
                fromIntent = false;
            }
            Log.d("msg", "fromIntent = "+ fromIntent);
        }
        try {
            PackageInfo pi = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_PERMISSIONS);
            for (int i = 0; i < pi.requestedPermissions.length; i++) {
                if ((pi.requestedPermissionsFlags[i] & PackageInfo.REQUESTED_PERMISSION_GRANTED) != 0) {
                    Log.d("msg", "granted " + pi.requestedPermissions[i]);
                    logs= logs + "\n"+
                            pi.requestedPermissions[i].toString().substring(19) +
                            "  = 1";
                    setIfGranted( pi.requestedPermissions[i].toString().substring(19));
                } else {
                    Log.d("msg", "deined " + pi.requestedPermissions[i]);
                    logs= logs + "\n"+
                            pi.requestedPermissions[i].toString().substring(19) +
                            "  = 0";
                    setIfDenied( pi.requestedPermissions[i].toString().substring(19));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        tvLogs.setText(logs); //set text for text view

        if (!fromIntent) {
            if (switch1.isChecked() && switch2.isChecked() && switch3.isChecked() && switch4.isChecked()) {
                finish();
            } else {
                Toast.makeText(this, "Brak wsystkich zezwoleń", Toast.LENGTH_SHORT).show();
            }
        }



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

    }

    private void setIfDenied(String substring) {
        switch (substring) {
            case "SEND_SMS":
                switch1.setChecked(false);
                switch1.refreshDrawableState();
                Log.d("msg", "SEND_SMS = 0");
                break;
            case "READ_PHONE_STATE":
                switch2.setChecked(false);
                switch2.refreshDrawableState();
                Log.d("msg", "READ_PHONE_STATE = 0");
                break;
            case "READ_PHONE_NUMBERS":
                switch3.setChecked(false);
                switch3.refreshDrawableState();
                Log.d("msg", "READ_PHONE_NUMBERS = 0");
                break;
            case "READ_CALL_LOG":
                switch4.setChecked(false);
                Log.d("msg", "READ_CALL_LOG = 0");
                switch4.refreshDrawableState();
                break;
        }
    }

    private void setIfGranted(String requestedPermission) {
        switch (requestedPermission) {
            case "SEND_SMS":
                switch1.setChecked(true);
                switch1.refreshDrawableState();
                Log.d("msg", "SEND_SMS = 1");
                break;
            case "READ_PHONE_STATE":
                switch2.setChecked(true);
                Log.d("msg", "READ_PHONE_STATE = 1");
                switch2.refreshDrawableState();
                break;
            case "READ_PHONE_NUMBERS":
                switch3.setChecked(true);
                Log.d("msg", "READ_PHONE_NUMBERS = 1");
                switch3.refreshDrawableState();
                break;
            case "READ_CALL_LOG":
                switch4.setChecked(true);
                Log.d("msg", "READ_CALL_LOG = 1");
                switch4.refreshDrawableState();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Permission 1 Granted", Toast.LENGTH_SHORT).show();
                switch1.setChecked(true);
                Log.d("msg", "permission granted 1");
            } else {
//                Toast.makeText(this, "Permission 1 DENIED", Toast.LENGTH_SHORT).show();
                Log.d("msg", "permission deined 1");
                switch1.setChecked(false);
            }
            logs=logs+"\nSEND_SMS = " + checkSelfPermission(android.Manifest.permission.SEND_SMS);
        }else if (requestCode == 2){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Permission  2 Granted", Toast.LENGTH_SHORT).show();
                switch2.setChecked(true);
                Log.d("msg", "permission granted 2");
            } else {
                switch2.setChecked(false);
//                Toast.makeText(this, "Permission 2 DENIED", Toast.LENGTH_SHORT).show();
                Log.d("msg", "permission deined 2");
            }
            logs=logs+"\nREAD_PHONE_STATE = " + checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
        }else if(requestCode == 3){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                switch3.setChecked(true);
//                Toast.makeText(this, "Permission  3 Granted", Toast.LENGTH_SHORT).show();
                Log.d("msg", "permission granted 3");
            } else {
                switch3.setChecked(false);
//                Toast.makeText(this, "Permission 3 DENIED", Toast.LENGTH_SHORT).show();
                Log.d("msg", "permission deined 3");
            }
            logs=logs+"\nREAD_PHONE_NUMBERS = " + checkSelfPermission(Manifest.permission.READ_PHONE_NUMBERS);
        }else if(requestCode == 4){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                switch4.setChecked(true);
//                Toast.makeText(this, "Permission  4 Granted", Toast.LENGTH_SHORT).show();
                Log.d("msg", "permission granted 4");
            } else {
//                Toast.makeText(this, "Permission 4 DENIED", Toast.LENGTH_SHORT).show();
                switch4.setChecked(false);
                Log.d("msg", "permission deined 4");
            }

            logs=logs+"\nREAD_CALL_LOG = " + checkSelfPermission(Manifest.permission.READ_CALL_LOG);
        }
        switch1.refreshDrawableState();
    }

}