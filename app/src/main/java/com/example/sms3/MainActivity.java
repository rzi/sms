package com.example.sms3;

import static com.example.sms3.Settings.logs;
import static com.example.sms3.Settings.switch1;
import static com.example.sms3.Settings.switch2;
import static com.example.sms3.Settings.switch3;
import static com.example.sms3.Settings.switch4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    public EditText etSms, etNumber;
    private TextView tvLogs;
    public static TextView tvQty;
    private final int PERMISSION_REQUEST_CODE = 1;
    private static final int PERMISSION_SEND_SMS = 123;
    private String number;
    public Button button1 ,btnAdd , btnSettings;
    public  static boolean isOn;
    public static CheckBox cbWhenDecline;
    public static boolean isCheckBoxDecline;
    public ListView list ;
    public ArrayAdapter<String> adapter ;
    public ArrayList<String> myList2;
    public static List<String> mylist = new ArrayList<String>();
    public static int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("msg", "main");
        etNumber = findViewById(R.id.editTextNumber1);
        etSms = findViewById(R.id.editTextTextMultiLine);
        tvQty =findViewById(R.id.textView3);
        tvQty.setText("");
        btnAdd =(Button) findViewById(R.id.button2);
        button1 =(Button) findViewById(R.id.button);
        btnSettings = (Button) findViewById(R.id.button4) ;
        cbWhenDecline = (CheckBox) findViewById(R.id.checkBox);
        cbWhenDecline.setChecked(true);
        cbWhenDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbWhenDecline.isChecked()){
                    isCheckBoxDecline=true;
                    Log.d("msg", "checkbox Decline is "+ isCheckBoxDecline);
                }else {
                    isCheckBoxDecline=false;
                    Log.d("msg", "checkbox Decline is "+ isCheckBoxDecline);
                }
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                Log.d("msg", "Button click");

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
               // onResume();
            }
        });
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSettings();;
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("msg", "Button Add");
                Log.d("msg", "etNumber = " + etNumber.getText());
                if (false){
                    Log.d("msg", "wypełnij pola mumber = "+ etNumber.getText().toString() + "i trec SMS ="+ etSms.getText().toString() );
                    Toast.makeText(MainActivity.this, "Wypełnij pole numeru i treść SMS", Toast.LENGTH_SHORT).show();
                }else {
                    Log.d("msg", "etNumber = " +etNumber.getText().toString() + "\netSms = " + etSms.getText().toString() );
                    mylist.add(etNumber.getText().toString() + "," + etSms.getText().toString());
                    updateView();
                }
                etNumber.setText("");
                etSms.setText("");
            }
        });

        ListView listView =(android.widget.ListView) findViewById(R.id.listView1);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
//                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//                String message = "abc";
//                intent.putExtra("EXTRA_MESSAGE", message);
//                startActivity(intent);

                Log.d("msg" , "positions = " + position);
                showDialog1(position);
            }
        });

//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED
//        ) {
//
//        }
        Log.d("msg"," Permitiond deined");
        try {
            PackageInfo pi = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_PERMISSIONS);
            for (int i = 0; i < pi.requestedPermissions.length; i++) {
                if ((pi.requestedPermissionsFlags[i] & PackageInfo.REQUESTED_PERMISSION_GRANTED) != 0) {
                    Log.d("msg", "granted " + pi.requestedPermissions[i]);
//                        logs= logs + "\n"+
//                                pi.requestedPermissions[i].toString().substring(18) +
//                                "  = 1";
                } else {
                    Log.d("msg", "deined " + pi.requestedPermissions[i]);
//                        logs= logs + "\n"+
//                                pi.requestedPermissions[i].toString().substring(18) +
//                                "  = 0";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //go to settings
        Log.d("msg", "go to settings");
        Intent intent = new Intent(MainActivity.this, Settings.class);
        startActivity(intent);
    }
    @Override
    protected void onResume() {
        super.onResume();
//        logs = "";
//        logs=logs+"\nAPI = "+String.valueOf(android.os.Build.VERSION.SDK_INT);
//        logs=logs+"\nisOn = " + (isOn ? "włączone": "wyłączone");

//        String counter = null;
//        TextView textView = (TextView) findViewById(R.id.textView3);
//        counter = Integer.toString(count);
//        Log.d("msg", " resume " + counter);
//        textView.setText(counter); //set text for text view


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
//        try {
//            PackageInfo pi = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_PERMISSIONS);
//            for (int i = 0; i < pi.requestedPermissions.length; i++) {
//                if ((pi.requestedPermissionsFlags[i] & PackageInfo.REQUESTED_PERMISSION_GRANTED) != 0) {
//                    Log.d("msg", "granted " + pi.requestedPermissions[i]);
//                    logs= logs + "\n"+
//                            pi.requestedPermissions[i].toString().substring(18) +
//                            "  = 1";
//                } else {
//                    Log.d("msg", "deined " + pi.requestedPermissions[i]);
//                    logs= logs + "\n"+
//                            pi.requestedPermissions[i].toString().substring(18) +
//                            "  = 0";
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        tvLogs.setText(logs); //set text for text view
    }

    private void toSettings() {
        Intent intent = new Intent(MainActivity.this, Settings.class);
        String message = "btnSettings";
        intent.putExtra("EXTRA_MESSAGE", message);
        startActivity(intent);
    }

    private void updateView() {
        list = (ListView) findViewById(R.id.listView1);
        myList2 = new ArrayList<String>();
        myList2.addAll(mylist);
        adapter = new ArrayAdapter<String>(this, R.layout.activity_list, myList2);
        list.setAdapter(adapter);
    }

    public void showDialog1(int position)
    {
        Log.d("msg" , "pos= =" +position);
        Context context =MainActivity.this;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle("Usunięcie");
        builder.setMessage("Usunąć pozycje ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mylist.remove(position);
                updateView();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}