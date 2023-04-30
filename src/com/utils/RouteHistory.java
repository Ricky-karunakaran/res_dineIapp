/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

/**
 *
 * @author Ricky
 */
public class RouteHistory {
    private final Stack attributes = new Stack<>();
    
    public void push(String value){
        System.out.println("PUSH");
        attributes.push(value);
    }
    public Object pop(){
        return attributes.pop();
    }
    public void show(){
        System.out.println(Arrays.toString(attributes.toArray()));
    }
};
