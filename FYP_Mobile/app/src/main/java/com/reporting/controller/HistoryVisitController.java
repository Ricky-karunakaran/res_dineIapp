package com.reporting.controller;

import androidx.appcompat.app.AppCompatActivity;

import com.order.model.Notification;
import com.order.model.WaiterCall;
import com.reporting.model.Feedback;
import com.reporting.model.HistoryVisit;
import com.reporting.view.VisitHistoryView;
import com.systemAccount.model.User;
import com.utils.CustomException;
import com.utils.Dialog;
import com.utils.Session;
import com.utils.SessionManager;

import java.util.ArrayList;

public class HistoryVisitController {
    private AppCompatActivity currentView;
    public void setView(AppCompatActivity v){
        this.currentView  = v;
    }
    public AppCompatActivity getView() { return this.currentView; }
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
                        histories.get(i).getRestaurant().getName(),
                        histories.get(i).getSession().getSessionId(),
                        false);
            }

        } catch(Exception e){
            System.out.println(e.getMessage());
        }


    }
    public void add_feedback(String feedback_content,String session_id){
        try{
            Feedback feedback = new Feedback();
            feedback.setFeedbackSessionId(session_id);
            feedback.setFeedbackContent(feedback_content);
            feedback.add_feedback();
            this.notify_restaurant(feedback);
        } catch (Exception e){

        }

    }
    public void update_feedback(String feedback_content,String session_id){
        try{
            Feedback feedback = new Feedback();
            feedback.setFeedbackSessionId(session_id);
            feedback.setFeedbackContent(feedback_content);
            feedback.update_feedback();
            this.notify_restaurant(feedback);
        } catch (Exception e){

        }

    }

    public String fetch_feedback_content() throws CustomException{
        SessionManager sessionManager = SessionManager.getInstance();
        Session session = sessionManager.getSession();
        String session_id = (String) session.getAttributes("add_feedback_session_id");
        try{
            Feedback feedback = new Feedback();
            feedback.setFeedbackSessionId(session_id);
            feedback.get_feedback_by_session_id();
            return feedback.getFeedbackContent();
        } catch (Exception e) {
            throw new CustomException("Fail to fetch feedback");
        }
    }

    public void check_feedback_exist() throws CustomException{
        SessionManager sessionManager = SessionManager.getInstance();
        Session session = sessionManager.getSession();
        String session_id = (String) session.getAttributes("add_feedback_session_id");
        try{
            Feedback feedback = new Feedback();
            feedback.setFeedbackSessionId(session_id);
            feedback.get_feedback_by_session_id();
            if(feedback.getFeedbackId()!=null) {
                throw new CustomException("Feedback exists");
            }


        } catch (CustomException e ){
            throw new CustomException(e.getMessage());
        }
        catch (Exception e) {
            throw new CustomException("Fail to fetch feedback");
        }
    }

    public void notify_restaurant(Feedback feedback){
        try{
            Notification notification = new Notification();
            notification.setNotificationContent(feedback.getFeedbackContent());
            notification.setNotificationType("New Feedback");
            notification.setNotificationSessionId(feedback.getFeedbackSessionId());
            notification.add_notification();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Dialog.dialog(this.currentView,"Fail To Notify Restaurant","Fail to send notification to restaurat, please contact restaurant staff", false);
        }
    }

    public void fetch_user_visit_consumption(){
        SessionManager sessionManager = SessionManager.getInstance();
        Session session = sessionManager.getSession();
        User user = (User) session.getAttributes("user");
        try{
            ArrayList<HistoryVisit> histories = user.get_visited_restaurant();
            for( int i = 0; i < histories.size() ; i++){
                System.out.println(histories.get(i).getSession().getSessionStartTime());
                ((VisitHistoryView) this.currentView).add_history_row(
                        histories.get(i).getSession().getSessionStartTime(),
                        histories.get(i).getRestaurant().getName(),
                        histories.get(i).getSession().getSessionId(),
                        false);
            }

        } catch(Exception e){
            System.out.println(e.getMessage());
        }


    }

}
