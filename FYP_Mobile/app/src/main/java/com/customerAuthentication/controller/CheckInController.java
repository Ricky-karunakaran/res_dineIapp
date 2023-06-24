package com.customerAuthentication.controller;

import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.customerAuthentication.model.Check_In_Request;
import com.customerAuthentication.model.Dine_In_Session;
import com.customerAuthentication.view.HomeView;
import com.order.view.SessionView;
import com.systemAccount.model.Restaurant;
import com.systemAccount.model.User;
import com.utils.CustomException;
import com.utils.JDateTime;
import com.utils.Session;
import com.utils.SessionManager;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CheckInController {
    private AppCompatActivity currentView;

    public void setView(AppCompatActivity v) {
        this.currentView  = v;
    }

    public void checkIn(String restaurant){
        SessionManager sessionManager = SessionManager.getInstance();
        Session session = sessionManager.getSession();
        User user = (User) session.getAttributes("user");

        try {

            // check user active session

            if(user.check_active_session()){
                throw new CustomException("You have check in to a restaurant. Please check out first before checking in to another restaurant");
            } else {

                try{

                    // check restaurant exist
                    Restaurant.find_restaurant(restaurant);

                    Dine_In_Session dine_in_session = new Dine_In_Session(user.getUserEmail(),restaurant);
                    Check_In_Request check_in_request = new Check_In_Request();
                    check_in_request.setCheckInRestaurantId(restaurant);
                    check_in_request.setCheckInUserEmail(user.getUserEmail());


                    check_in_request.setCheckInRequestDateTime(JDateTime.getCurrentDateTime());

                    check_in_request.create_check_in_request();
                    // update user active session
                    user.update_active_session(dine_in_session.getSessionId());
                    session.setAttributes("session_id",dine_in_session.getSessionId());
                    session.setAttributes("session_cart_id",dine_in_session.getSessionCartId());
                    session.setAttributes("session_restaurant_id",restaurant);
                    session.setAttributes("session_bill_id",dine_in_session.getSessionBillId());

                    Intent intent = new Intent(currentView, SessionView.class);
                    currentView.startActivity(intent);
                } catch (Exception e) {
                    throw new CustomException(e.getMessage());
                }
            }
        } catch (SQLException e) {

        } catch (ClassNotFoundException e) {

        } catch(CustomException e){
            AlertDialog.Builder builder = new AlertDialog.Builder(this.currentView);
            builder.setTitle("Fail to check in");
            builder.setMessage(e.getMessage());
            builder.setCancelable(false); // set whether the dialog is cancelable or not
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    // do something when the positive button is clicked
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    // return check in restaurant name
    public String checkHasCheckIn(){
        SessionManager sessionManager = SessionManager.getInstance();
        Session session = sessionManager.getSession();
        User user = (User) session.getAttributes("user");
        try {
            if(user.check_active_session()){

                Dine_In_Session dine_in_session = new Dine_In_Session();
                dine_in_session.setSessionId(user.getUserActiveSession());
                dine_in_session.read_session_by_id();
                session.setAttributes("session_id",dine_in_session.getSessionId());
                session.setAttributes("session_restaurant_id",dine_in_session.getSessionRestaurantId());
                Restaurant restaurant = new Restaurant();
                restaurant.setRestaurantId(dine_in_session.getSessionRestaurantId());
                restaurant.read_restaurant_by_id();
                return restaurant.getName();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
