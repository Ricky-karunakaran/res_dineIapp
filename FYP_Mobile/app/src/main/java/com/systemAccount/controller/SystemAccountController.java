package com.systemAccount.controller;

import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.customerAuthentication.model.Dine_In_Session;
import com.systemAccount.model.User;
import com.customerAuthentication.view.HomeView;
import com.utils.CustomException;
import com.utils.Session;
import com.utils.SessionManager;

public class SystemAccountController {
    private AppCompatActivity currentView;

    public void setView(AppCompatActivity v){
        this.currentView  = v;
    }

    public void login(String email, String password){

        User user = new User(email);
        try{
            if(user.login(password))
            {
                SessionManager sessionManager = SessionManager.getInstance();
                Session session = sessionManager.getSession();
                session.setAttributes("user",user);
                if(user.getUserActiveSession() != null){
                    Dine_In_Session dine_in_session = new Dine_In_Session();
                    dine_in_session.setSessionId(user.getUserActiveSession());
                    dine_in_session.read_session_by_id();
                    session.setAttributes("session_restaurant_id",dine_in_session.getSessionRestaurantId());
                    session.setAttributes("session_cart_id",dine_in_session.getSessionCartId());
                    session.setAttributes("session_bill_id",dine_in_session.getSessionBillId());

                }

                Intent intent = new Intent(currentView, HomeView.class);
                currentView.startActivity(intent);
            }
        } catch(CustomException e){
            AlertDialog.Builder builder = new AlertDialog.Builder(this.currentView);
            builder.setTitle("Login Fail");
            builder.setMessage(e.getMessage());
            builder.setCancelable(false); // set whether the dialog is cancelable or not
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    // do something when the positive button is clicked
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // do something when the negative button is clicked
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } catch (Exception e){
            AlertDialog.Builder builder = new AlertDialog.Builder(this.currentView);
            builder.setTitle("System Error");
            builder.setMessage("This is an error with the system, please try again later");
            builder.setCancelable(false); // set whether the dialog is cancelable or not
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    // do something when the positive button is clicked
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // do something when the negative button is clicked
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            System.out.println(e);
        }


    }

    public void register_user(String email,String password,String name,String age,String phoneNumber){
        User user = new User(email);
        user.setUserName(name);
        user.setUserAge(Integer.parseInt(age));
        user.setUserPhone(phoneNumber);
        try{
            if(user.register(password))
            {
                SessionManager sessionManager = SessionManager.getInstance();
                Session session = sessionManager.getSession();
                session.setAttributes("user",user);
                Intent intent = new Intent(currentView,HomeView.class);
                currentView.startActivity(intent);
            }
        } catch(Exception e){

        }

    }

    public void edit_information(String email, String name, String age, String phoneNumber, String allergy) {
        try{
            User user = new User (email);
            System.out.println(email);
            System.out.println(name);
            System.out.println(phoneNumber);
            user.setUserName(name);
            user.setUserAge(Integer.parseInt(age));
            user.setUserPhone(phoneNumber);
            user.setUserAllergy(allergy);
            user.edit_info();
            SessionManager sessionManager = SessionManager.getInstance();
            Session session = sessionManager.getSession();
            session.setAttributes("user",user);
            Intent intent = new Intent(currentView,HomeView.class);
            currentView.startActivity(intent);

        } catch (Exception e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.currentView);
            builder.setTitle("System Error");
            builder.setMessage(e.toString());
            builder.setCancelable(false); // set whether the dialog is cancelable or not
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    // do something when the positive button is clicked
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // do something when the negative button is clicked
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }

    }

}


