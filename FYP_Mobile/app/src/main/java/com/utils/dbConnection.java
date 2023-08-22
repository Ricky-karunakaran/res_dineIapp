package com.utils;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class dbConnection {
    private static String driver = "com.mysql.jdbc.Driver";


    private static String dbName = "fyp_alpha";
    private static String hostName ="192.168.0.104";
//    private static String hostName = "192.168.118.232";
    private static String urlParameter ="";
    private static String user = "ricky";
    private static String password = "1234";
    
    public static Connection getDb() throws ClassNotFoundException, SQLException
    {
        try{
        String url = "jdbc:mysql://"+hostName+"/"+dbName+"?"+urlParameter;
        Class.forName(driver);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        DriverManager.setLoginTimeout(10);
        Connection con = DriverManager.getConnection(url, user, password);
        return con;
        } catch (Exception e){

            System.out.println(e);
                throw e;
        }
    }
 
}
