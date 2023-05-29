/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Ricky
 */
public class FormatVerifier {
    public static boolean isEmail(String input){
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}
