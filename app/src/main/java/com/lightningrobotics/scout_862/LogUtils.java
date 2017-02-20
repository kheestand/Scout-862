package com.lightningrobotics.scout_862;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by khees on 1/4/2017.
 */

public class LogUtils {

    private File logFile;
    DateFormat dateFormat = new SimpleDateFormat("MMddyyyy");
    DateFormat timeFormat = new SimpleDateFormat("hhmmss");
    private Date today = new Date();

    public LogUtils(){

    }

    public LogUtils(File f) throws java.io.FileNotFoundException{
        PrintStream fileWriter = new PrintStream(logFile);
        logFile = f;
        //output: Log started @ mm/dd/yyy
        fileWriter.println("Log started @   " + dateFormat.format(today));
    }


    public void addToLog(String activity, String method, String message)throws java.io.FileNotFoundException{
        PrintStream fileWriter = new PrintStream(logFile);
        //output: [hh:mm:ss] <activity>, <method name>, message: <message>
        fileWriter.println(" [" + timeFormat.format(today) + "]   " + activity + ", " + method + ": " + "Message: " +
                message);
    }

    public File getLog()
    {
        return logFile;
    }
}