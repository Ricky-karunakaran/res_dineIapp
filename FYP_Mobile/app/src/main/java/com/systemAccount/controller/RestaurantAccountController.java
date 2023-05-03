package com.systemAccount.controller;

import androidx.appcompat.app.AppCompatActivity;

import com.systemAccount.model.Restaurant;

import java.util.ArrayList;

public class RestaurantAccountController {
    private AppCompatActivity currentView;

    public void setView(AppCompatActivity v){
        this.currentView  = v;
    }

    public static ArrayList<Restaurant> read_all_restaurant(){
        return Restaurant.getAll();
    }
}
