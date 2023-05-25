package com.reporting.controller;

import androidx.appcompat.app.AppCompatActivity;

import com.reporting.model.HistoryVisit;
import com.reporting.view.VisitHistoryView;
import com.systemAccount.model.User;
import com.utils.Session;
import com.utils.SessionManager;

import java.util.ArrayList;

public class HistoryVisitController {
    private AppCompatActivity currentView;
    public void setView(AppCompatActivity v){
        this.currentView  = v;
    }

    public void fetch_user_visit_history(){
        SessionManager sessionManager = SessionManager.getInstance();
        Session session = sessionManager.getSession();
        User user = (User) session.getAttributes("user");
        try{
            ArrayList<HistoryVisit> histories = user.get_visited_restaurant();
            for( int i = 0; i < histories.size() ; i++){
                System.out.println(histories.get(i).getSession().getSessionStartTime());
                ((VisitHistoryView) this.currentView).add_history_row(
                        histories.get(i).getSession().getSessionStartTime(),
                        histories.get(i).getRestaurant().getName());
            }

        } catch(Exception e){

        }


    }
}
