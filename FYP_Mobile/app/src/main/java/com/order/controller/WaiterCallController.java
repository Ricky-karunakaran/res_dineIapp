package com.order.controller;

import androidx.appcompat.app.AppCompatActivity;

import com.order.model.Notification;
import com.order.model.WaiterCall;
import com.systemAccount.model.User;
import com.utils.ControllerBase;
import com.utils.Dialog;
import com.utils.JDateTime;
import com.utils.Session;
import com.utils.SessionManager;

public class WaiterCallController extends ControllerBase {
    private AppCompatActivity currentView;
    public void setView(AppCompatActivity v) {
        this.currentView  = v;
    }
    public void call_waiter(String content){
        try{
            SessionManager sessionManager = SessionManager.getInstance();
            Session session = sessionManager.getSession();
            String session_restaurant_id = (String) session.getAttributes("session_restaurant_id");
            String session_id = (String) session.getAttributes("session_id");
            User user = (User) session.getAttributes("user");
            WaiterCall waiter_call = new WaiterCall();

            waiter_call.setWaiterCallUserEmail(user.getUserEmail());
            waiter_call.setWaiterCallSessionId(session_id);
            waiter_call.setWaiterCallCreatedAt(JDateTime.getCurrentDateTime());
            waiter_call.setWaiterCallRestaurantId(session_restaurant_id);
            waiter_call.setWaiterCallContent(content);
            waiter_call.add_new_waiter_call();
            this.notify_restaurant(waiter_call);
            Dialog.dialog(this.currentView,"Calling Waiter","Your call has been submitted.", false);

        } catch (Exception e){

        }
    }

    public void notify_restaurant(WaiterCall waiter_call){
        try{
            Notification  notification = new Notification();
            notification.setNotificationContent(waiter_call.getWaiterCallContent());
            notification.setNotificationType("Calling Waiter");
            notification.setNotificationSessionId(waiter_call.getWaiterCallSessionId());
            notification.add_notification();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Dialog.dialog(this.currentView,"Fail To Notify Restaurant","Fail to send notification to restaurat, please contact restaurant staff", false);
        }
    }
}
