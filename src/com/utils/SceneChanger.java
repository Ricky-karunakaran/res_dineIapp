/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utils;

import java.io.IOException;
import java.util.Stack;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Ricky
 */
public class SceneChanger {
    private static SceneChanger instance; 
    private  RouteHistory routeHistory;
    private SceneChanger(){};
    public static SceneChanger getInstance(){
        if(instance == null) {
            instance = new SceneChanger();
        }
        return instance;
    }

    public void createRouteHistory(){
        routeHistory = new RouteHistory(){};
    }

    public RouteHistory getRouteHistory(){
        return routeHistory;
    }
   
    public static void changeScene(Stage currentStage,String fxmlFile) throws IOException{
        Parent root = FXMLLoader.load(SceneChanger.class.getResource(fxmlFile));
        
        SceneChanger.getInstance().addScene(fxmlFile);
        Scene scene = new Scene(root,WindowSize.getScreenWidth(),WindowSize.getScreenHeight());
        currentStage.setTitle("FoodVerse");
        currentStage.setScene(scene);
        currentStage.show();
        
    }

    public void addScene(String fxmlFile){
        SceneChanger.getInstance().getRouteHistory().push(fxmlFile);
    }
    public static void getHistory(){
        System.out.println(getInstance().routeHistory.toString());
    }
}
