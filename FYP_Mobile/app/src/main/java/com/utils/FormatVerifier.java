package com.utils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatVerifier {
    public static boolean isEmail(String input){
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
    public static boolean isValidPassword(String input){
        if(input.length()<6){
            return false;
        }
        String regex = ".*[!@#$%^&*(),.?\":{}|<>].*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if(!matcher.matches()){
            return false;
        }
        return true;
    }
}
