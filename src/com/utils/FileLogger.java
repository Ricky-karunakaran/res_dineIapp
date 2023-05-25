package com.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Ricky
 */
public class FileLogger {
    
    public static void logFile(String content){
        Date date = Calendar.getInstance().getTime();
        TimeZone gmt8TimeZone = TimeZone.getTimeZone("GMT+8");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(gmt8TimeZone);
        String filename = "log/"+dateFormat.format(date)+".txt";
        System.out.println(filename);
         try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
            
            DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss :");
            dateTimeFormat.setTimeZone(gmt8TimeZone);
            writer.append(dateFormat.format(date));
            writer.append(content);
            writer.newLine();
            writer.close();
            System.out.println("String appended successfully to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
     }
}
