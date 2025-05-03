package com.example.sms3;

import static com.example.sms3.Settings.switch1;
import static com.example.sms3.Settings.switch2;
import static com.example.sms3.Settings.switch3;
import static com.example.sms3.Settings.switch4;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    Logger logger ;
    private static String filename1 = "AppLogs";

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
                    String text =etNumber.getText().toString() + "," + etSms.getText().toString();
                    mylist.add(text);
                    appendLineToFile(getBaseContext(), "myList.txt" , text);
                    updateView();
                }
                etNumber.setText("");
                etSms.setText("");
                hideKeyboard(v.getContext(), v);
            }
        });
        list = (ListView) findViewById(R.id.listView1);
        myList2 = new ArrayList<String>();

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
        Log.d("msg","Permition");
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
        Log.d("msg", "save");

        if (!doesFileExist(this, "log.txt")) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Files.FileColumns.DISPLAY_NAME, "log.txt");
            values.put(MediaStore.Files.FileColumns.MIME_TYPE, "text/plain");
            values.put(MediaStore.Files.FileColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS);

            ContentResolver resolver = getContentResolver();
            Uri uri = resolver.insert(MediaStore.Files.getContentUri("external"), values);

            if (uri != null) {
                try (OutputStream outputStream = resolver.openOutputStream(uri)) {
                    outputStream.write("Start\n".getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            String text ="Create file, data: " + new Date();
            logger.addRecordToLog(text);
        } else {
            Log.d("MainActivity", "Plik już istnieje – pomijam zapis.");
        }
        if (!doesFileExist(this, "myList.txt")) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Files.FileColumns.DISPLAY_NAME, "myList.txt");
            values.put(MediaStore.Files.FileColumns.MIME_TYPE, "text/plain");
            values.put(MediaStore.Files.FileColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS);

            ContentResolver resolver = getContentResolver();
            Uri uri = resolver.insert(MediaStore.Files.getContentUri("external"), values);

            if (uri != null) {
                try (OutputStream outputStream = resolver.openOutputStream(uri)) {
                    outputStream.write("602599761,Nie mogę rozmawiać\n".getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            String text ="Create file, data: " + new Date();
            logger.addRecordToLog(text);
        } else {
            Log.d("MainActivity", "Plik już istnieje – pomijam zapis.");
        }

        //go to settings
        Log.d("msg", "go to settings");
        Intent intent = new Intent(MainActivity.this, Settings.class);
        startActivity(intent);
    }
    @Override
    protected void onResume() {
        super.onResume();
        logger.addRecordToLog("onResume");
        updateView();
        Log.d("msg", "onResume");

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
        logger.addRecordToLog("btnSettings");
    }
    private void updateView() {
        mylist.clear();
        String contents = readTextFileFromDocuments(this, "myList.txt");
        mylist.add(contents);
        Log.d("msg", "Line: "+ contents);
        myList2.clear();
        myList2.addAll(mylist);
        adapter = new ArrayAdapter<String>(this, R.layout.activity_list, myList2);
        list.setAdapter(adapter);
        Log.d("msg", "Koniec update ");
    }
    public void showDialog1(int position) {
        Log.d("msg" , "pos = " +position);
        Context context =MainActivity.this;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle("Usunięcie");
        builder.setMessage("Usunąć pozycje ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("msg", "click Yes");
                mylist.remove(position);
                deletePosition(position);
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
    private void deletePosition(int position) {
        Log.d("msg", "pozycja do usunięcia = " +position);
//        removeLineByIndexFromFile(this, "myList.txt", position); // usuwa 3. linię (indeks 2)
        clearFileContentInDocuments(this, "myList.txt");
        Log.d("msg", "text: " + position);
    }
    public boolean doesFileExist(Context context, String fileName) {
        Uri collection = MediaStore.Files.getContentUri("external");

        String[] projection = new String[] {
                MediaStore.Files.FileColumns._ID
        };

        String selection = MediaStore.Files.FileColumns.DISPLAY_NAME + "=? AND " +
                MediaStore.Files.FileColumns.RELATIVE_PATH + "=?";
        String[] selectionArgs = new String[] {
                fileName,
                Environment.DIRECTORY_DOCUMENTS + "/"
        };

        try (Cursor cursor = context.getContentResolver().query(
                collection,
                projection,
                selection,
                selectionArgs,
                null
        )) {
            return cursor != null && cursor.moveToFirst();
        }
    }
    public void appendLineToFile(Context context, String fileName, String newLine) {
        Uri uri = findFileUri(context, fileName);
        if (uri == null) {
            Log.e("Append", "Plik nie znaleziony");
            return;
        }

        ContentResolver resolver = context.getContentResolver();

        try {
            // 1. Odczytaj całą zawartość
            InputStream inputStream = resolver.openInputStream(uri);
            StringBuilder content = new StringBuilder();
            if (inputStream != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                inputStream.close();
            }

            // 2. Dodaj nową linijkę
            content.append(newLine).append("\n");

            // 3. Nadpisz plik
            OutputStream outputStream = resolver.openOutputStream(uri, "wt"); // "wt" = write & truncate
            if (outputStream != null) {
                outputStream.write(content.toString().getBytes());
                outputStream.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    Uri findFileUri(Context context, String fileName) {
        Uri collection = MediaStore.Files.getContentUri("external");

        String[] projection = new String[] {
                MediaStore.Files.FileColumns._ID
        };

        String selection = MediaStore.Files.FileColumns.DISPLAY_NAME + "=? AND " +
                MediaStore.Files.FileColumns.RELATIVE_PATH + "=?";
        String[] selectionArgs = new String[] {
                fileName,
                Environment.DIRECTORY_DOCUMENTS + "/"
        };

        try (Cursor cursor = context.getContentResolver().query(
                collection,
                projection,
                selection,
                selectionArgs,
                null
        )) {
            if (cursor != null && cursor.moveToFirst()) {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID));
                return ContentUris.withAppendedId(collection, id);
            }
        }
        return null;
    }
    public String readTextFileFromDocuments(Context context, String fileName) {
        Uri collection = MediaStore.Files.getContentUri("external");

        String[] projection = new String[] {
                MediaStore.Files.FileColumns._ID
        };

        String selection = MediaStore.Files.FileColumns.DISPLAY_NAME + "=? AND " +
                MediaStore.Files.FileColumns.RELATIVE_PATH + "=?";
        String[] selectionArgs = new String[] {
                fileName,
                Environment.DIRECTORY_DOCUMENTS + "/"
        };

        try (Cursor cursor = context.getContentResolver().query(
                collection,
                projection,
                selection,
                selectionArgs,
                null
        )) {
            if (cursor != null && cursor.moveToFirst()) {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID));
                Uri fileUri = ContentUris.withAppendedId(collection, id);

                InputStream inputStream = context.getContentResolver().openInputStream(fileUri);
                if (inputStream != null) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder builder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line).append("\n");
                    }
                    reader.close();
                    return builder.toString();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null; // plik nie istnieje lub błąd
    }
    public void clearFileContentInDocuments(Context context, String fileName) {
        Uri collection = MediaStore.Files.getContentUri("external");

        String[] projection = new String[] {
                MediaStore.Files.FileColumns._ID
        };

        String selection = MediaStore.Files.FileColumns.DISPLAY_NAME + "=? AND " +
                MediaStore.Files.FileColumns.RELATIVE_PATH + "=?";
        String[] selectionArgs = new String[] {
                fileName,
                Environment.DIRECTORY_DOCUMENTS + "/"
        };

        try (Cursor cursor = context.getContentResolver().query(
                collection,
                projection,
                selection,
                selectionArgs,
                null
        )) {
            if (cursor != null && cursor.moveToFirst()) {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID));
                Uri fileUri = ContentUris.withAppendedId(collection, id);

                // 🔄 Otwórz w trybie "write & truncate"
                OutputStream outputStream = context.getContentResolver().openOutputStream(fileUri, "wt");
                if (outputStream != null) {
                    outputStream.write("".getBytes()); // zapisz pustą zawartość
                    outputStream.close();
                    Log.d("MainActivity", "Zawartość pliku wyczyszczona.");
                }
            } else {
                Log.d("MainActivity", "Plik nie istnieje.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void removeLineByIndexFromFile(Context context, String fileName, int lineIndex) {
        Uri collection = MediaStore.Files.getContentUri("external");

        String[] projection = new String[] {
                MediaStore.Files.FileColumns._ID
        };

        String selection = MediaStore.Files.FileColumns.DISPLAY_NAME + "=? AND " +
                MediaStore.Files.FileColumns.RELATIVE_PATH + "=?";
        String[] selectionArgs = new String[] {
                fileName,
                Environment.DIRECTORY_DOCUMENTS + "/"
        };

        try (Cursor cursor = context.getContentResolver().query(
                collection,
                projection,
                selection,
                selectionArgs,
                null
        )) {
            if (cursor != null && cursor.moveToFirst()) {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID));
                Uri fileUri = ContentUris.withAppendedId(collection, id);

                ContentResolver resolver = context.getContentResolver();

                // 1. Odczytaj wszystkie linie
                List<String> lines = new ArrayList<>();
                InputStream inputStream = resolver.openInputStream(fileUri);
                if (inputStream != null) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        lines.add(line);
                    }
                    reader.close();
                    inputStream.close();
                }

                // 2. Usuń wybraną linię jeśli istnieje
                if (lineIndex >= 0 && lineIndex < lines.size()) {
                    lines.remove(lineIndex);
                } else {
                    Log.w("MainActivity", "Podany indeks linii jest poza zakresem");
                    return;
                }

                // 3. Nadpisz plik
                OutputStream outputStream = resolver.openOutputStream(fileUri, "wt");
                if (outputStream != null) {
                    for (String l : lines) {
                        outputStream.write((l + "\n").getBytes());
                    }
                    outputStream.close();
                    Log.d("MainActivity", "Usunięto linię o indeksie: " + lineIndex);
                }
            } else {
                Log.d("MainActivity", "Plik nie został znaleziony.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}