package com.example.sms3;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.FileHandler;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

/**
 * @author Rakesh.Jha
 * Date - 07/10/2013
 * Definition - Logger file use to keep Log info to external SD with the simple method
 */

public class Logger {

    public static void addRecordToLog(String message) {

        try {
            //BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter("/storage/emulated/0/Documents/log.txt", true));
            buf.append(message);
            buf.newLine();
            buf.flush();
            buf.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
