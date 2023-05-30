package com.reporting.model;

import com.customerAuthentication.model.Dine_In_Session;
import com.order.model.Bill;
import com.systemAccount.model.Restaurant;

public class Consumption {
    private Restaurant restaurant;
    private Dine_In_Session session;
    private Bill bill;
    public Consumption(){
        restaurant = new Restaurant();
        session = new Dine_In_Session();
        bill = new Bill();
    }
    public Restaurant getRestaurant(){ return this.restaurant; }
    public Dine_In_Session getSession() { return this.session; }
    public Bill getBill() { return this.bill; }
}
