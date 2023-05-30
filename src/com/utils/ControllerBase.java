/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utils;

import customerAuthentication.Controller.CheckInController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import systemAccount.Controller.SystemAccountController;

/**
 *
 * @author Ricky
 */
public class ControllerBase {
    @FXML protected Region main_container;
    
    @FXML protected Text menuMenuItem;
    @FXML protected Text sessionMenuItem;
    @FXML protected Text accountMenuItem;
    @FXML protected Text checkInMenuItem;
    @FXML protected Text notificationMenuItem;
    @FXML protected Text callListMenuItem;
    @FXML protected Text feedbackMenuItem;
    @FXML protected Text reportMenuItem;
    @FXML protected Text backMenuItem;
    
    public void setupMenuRoute(){
        if(this.menuMenuItem != null ){
            this.menuMenuItem.setOnMouseClicked(event -> {
            try{
                this.beforeRoute();
                SceneChanger.changeScene((Stage)this.main_container.getScene().getWindow(), "/order/View/menuView.fxml");
            }   catch (IOException ex) {
                    DesktopAlert.showError("System error","Fail to change screen",ex);
                }
            });
        }
        
        if(this.checkInMenuItem != null ){
            this.checkInMenuItem.setOnMouseClicked(event -> {
            try{
                this.beforeRoute();
                SceneChanger.changeScene((Stage)this.main_container.getScene().getWindow(), "/customerAuthentication/View/checkInRequestView.fxml");
            }   catch (IOException ex) {
                    DesktopAlert.showError("System error","Fail to change screen",ex);
                }
            });
        }
        
        if(this.accountMenuItem != null ){
            this.accountMenuItem.setOnMouseClicked(event -> {
            try{
                this.beforeRoute();
                SceneChanger.changeScene((Stage)this.main_container.getScene().getWindow(), "/systemAccount/View/editAccountView.fxml");
            }   catch (IOException ex) {
                    DesktopAlert.showError("System error","Fail to change screen",ex);
                }
            });
        }
        
        if(this.sessionMenuItem != null ){
            this.sessionMenuItem.setOnMouseClicked(event -> {
            try{
                this.beforeRoute();
                SceneChanger.changeScene((Stage)this.main_container.getScene().getWindow(), "/customerAuthentication/View/sessionView.fxml");
            }   catch (IOException ex) {
                    DesktopAlert.showError("System error","Fail to change screen",ex);
                }
            });
        }
        
         if(this.notificationMenuItem != null ){
            this.notificationMenuItem.setOnMouseClicked(event -> {
            try{
                this.beforeRoute();
                SceneChanger.changeScene((Stage)this.main_container.getScene().getWindow(), "/order/View/notificationView.fxml");
            }   catch (IOException ex) {
                    DesktopAlert.showError("System error","Fail to change screen",ex);
                }
            });
        }

        if(this.callListMenuItem != null ){
            this.callListMenuItem.setOnMouseClicked(event -> {
            try{
                this.beforeRoute();
                SceneChanger.changeScene((Stage)this.main_container.getScene().getWindow(), "/order/View/callListView.fxml");
            }   catch (IOException ex) {
                    DesktopAlert.showError("System error","Fail to change screen",ex);
                }
            });
        }
        
        if(this.feedbackMenuItem != null ){
            this.feedbackMenuItem.setOnMouseClicked(event -> {
            try{
                this.beforeRoute();
                SceneChanger.changeScene((Stage)this.main_container.getScene().getWindow(), "/reporting/View/feedbackView.fxml");
            }   catch (IOException ex) {
                    DesktopAlert.showError("System error","Fail to change screen",ex);
                }
            });
        }
        
        if(this.reportMenuItem != null ){
            this.reportMenuItem.setOnMouseClicked(event -> {
            try{
                this.beforeRoute();
                SceneChanger.changeScene((Stage)this.main_container.getScene().getWindow(), "/reporting/View/reportView.fxml");
            }   catch (IOException ex) {
                    DesktopAlert.showError("System error","Fail to change screen",ex);
                }
            });
        }
        
    }
    
    public void setupBackRoute(){
    }
    
    public void beforeRoute(){}
}
