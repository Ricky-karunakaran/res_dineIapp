/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Ricky
 */
public final class dbConnection {
    private static String driver = "com.mysql.cj.jdbc.Driver";
//    private static String dbName = "fyp_res";
//    private static String hostName = "db4free.net:3306";
//    private static String hostPort = "";//"3306";
//    private static String urlParameter = "useSSL=false";
//    private static String user = "fyp_res_ricky";
//    private static String password = "rootFYPRes-22";
//    
    private static String dbName = "fyp_alpha";
    private static String hostName = "localhost";
    private static String urlParameter ="";
    private static String user = "root";
    private static String password = "";
    
    public static Connection getDb() throws ClassNotFoundException, SQLException
    {
        try{
        String url = "jdbc:mysql://"+hostName+"/"+dbName+"?"+urlParameter;
        Class.forName(driver);
        Connection con = DriverManager.getConnection(url, user, password);
        return con;
    } catch (Exception e){
    System.out.println(e.toString());
    }
        return null;
    }
 
}
