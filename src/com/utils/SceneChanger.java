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
    private static Stack<String> stackScene = new Stack<>();
    public static void changeScene(Stage currentStage,String fxmlFile) throws IOException{
        Parent root = FXMLLoader.load(SceneChanger.class.getResource(fxmlFile));
        SceneChanger.addScene(fxmlFile);
        Scene scene = new Scene(root,WindowSize.getScreenWidth(),WindowSize.getScreenHeight());
        currentStage.setScene(scene);
        currentStage.show();
        
    }
    
    public static void addScene(String fxmlFile){
        SceneChanger.stackScene.push(fxmlFile);
    }
    public static void backToPrevious(Stage currentStage) throws IOException{
        String fxmlFile = SceneChanger.backScene();
        Parent root = FXMLLoader.load(SceneChanger.class.getResource(fxmlFile));
        SceneChanger.addScene(fxmlFile);
        Scene scene = new Scene(root,WindowSize.getScreenWidth(),WindowSize.getScreenHeight());
        currentStage.setScene(scene);
        currentStage.show();
    }
    public static String backScene(){
        return SceneChanger.stackScene.pop();
    }
    
    public static void getHistory(){
        System.out.println(SceneChanger.stackScene.size());
        System.out.println(SceneChanger.stackScene.toString());
    }
}
