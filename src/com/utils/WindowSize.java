
package com.utils;

import java.awt.Dimension;
import java.awt.Toolkit;

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
