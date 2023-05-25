package com.reporting.model;

import com.customerAuthentication.model.Dine_In_Session;
import com.systemAccount.model.Restaurant;

public class HistoryVisit {
    private Restaurant restaurant;
    private Dine_In_Session session;

    public HistoryVisit(){
        restaurant = new Restaurant();
        session = new Dine_In_Session();
    }
    public Restaurant getRestaurant(){ return this.restaurant; }
    public Dine_In_Session getSession() { return this.session; }
}
