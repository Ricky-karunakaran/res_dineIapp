/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utils;

import java.awt.Dimension;
import java.awt.Toolkit;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

/**
 *
 * @author Ricky
 */
public class WindowSize {
    public static double getScreenWidth(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.getWidth();
        return screenWidth;
    }
    
    public static double getScreenHeight(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenHeight = screenSize.getHeight();
        return screenHeight-80;
    }
}
