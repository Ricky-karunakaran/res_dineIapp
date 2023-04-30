/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utils;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import systemAccount.Model.Model;
import systemAccount.Model.Restaurant;

/**
 *
 * @author Ricky
 */
public class ActionCell extends TableCell<Model,Void> {
    private final Button button;
    
    public ActionCell(String actionName){
        button = new Button(actionName);
        button.setOnAction(event->{
            Restaurant restaurant = (Restaurant) getTableView().getItems().get(getIndex());
            getTableView().getItems().remove(restaurant);
            System.out.println(restaurant.getEmail());
        });
       setGraphic(button);
        
    }
    
}
