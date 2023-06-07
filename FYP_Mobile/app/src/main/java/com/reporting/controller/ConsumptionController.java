package com.reporting.controller;

import androidx.appcompat.app.AppCompatActivity;

import com.reporting.model.Consumption;
import com.reporting.model.HistoryVisit;
import com.reporting.view.ConsumptionView;
import com.reporting.view.VisitHistoryView;
import com.systemAccount.model.User;
import com.utils.Session;
import com.utils.SessionManager;

import java.util.ArrayList;

public class ConsumptionController {
    private AppCompatActivity currentView;
    public void setView(AppCompatActivity v){
        this.currentView  = v;
    }
    public AppCompatActivity getView() { return this.currentView; }

    public void fetch_user_visit_consumption(){
        SessionManager sessionManager = SessionManager.getInstance();
        Session session = sessionManager.getSession();
        User user = (User) session.getAttributes("user");
        try{
            ArrayList<Consumption> consumptions = user.get_consumption();
            for( int i = 0; i < consumptions.size() ; i++){
                System.out.println(consumptions.get(i).getSession().getSessionStartTime());
                ((ConsumptionView) this.currentView).add_consumption_row(
                        consumptions.get(i).getSession().getSessionStartTime(),
                        consumptions.get(i).getRestaurant().getName(),
                        consumptions.get(i).getSession().getSessionId(),
                        String.valueOf(consumptions.get(i).getBill().getBillAmount()),
                        false);
            }

        } catch(Exception e){
            System.out.println(e.getMessage());
        }


    }
}
