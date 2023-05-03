package com.utils;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class dbConnection {
    private static String driver = "com.mysql.jdbc.Driver";
    private static String dbName = "fyp_res";
//    private static String hostName = "db4free.net:3306";
//    private static String hostPort = "";//"3306";
//    private static String urlParameter = "useSSL=false";
//    private static String user = "fyp_res_ricky";
//    private static String password = "rootFYPRes-22";

    private static String hostName = "10.213.97.240";
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
