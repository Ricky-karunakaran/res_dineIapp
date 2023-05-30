/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utils;

import javafx.scene.control.Alert;

/**
 *
 * @author Ricky
 */
public class DesktopAlert {
    public static void showAlert(String title,String content){
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText("Info: " + title);
        a.setContentText(content);
        a.show();
    }
    
    public static void showError(String title,String content,Exception error){
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText("Info: " + title);
        a.setContentText(content);
        a.show();
        
        FileLogger.logFile("Foodverse Error on "+JDateTime.getCurrentDateTime());
        StackTraceElement[] element = error.getStackTrace();
        for(int i=0;i<element.length;i++){
            System.out.println(i);
            FileLogger.logFile(element[i].toString());
        }
    }
    
}
