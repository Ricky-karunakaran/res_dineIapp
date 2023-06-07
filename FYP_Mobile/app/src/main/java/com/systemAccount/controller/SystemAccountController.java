package com.systemAccount.controller;

import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.customerAuthentication.model.Dine_In_Session;
import com.systemAccount.model.Reset_Password_Request;
import com.systemAccount.model.User;
import com.customerAuthentication.view.HomeView;
import com.systemAccount.view.LoginView;
import com.utils.BusinessMessage;
import com.utils.CustomException;
import com.utils.Dialog;
import com.utils.EmailService;
import com.utils.FormatVerifier;
import com.utils.Session;
import com.utils.SessionManager;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            Dialog.dialog(currentView,"Login Fail",e.getMessage(),false);
        } catch (Exception e){
            Dialog.dialog(currentView,"System Error", BusinessMessage.system_error,false);
        }


    }

    public void register_user(String email,String password,String name,String age,String phoneNumber){
        if(email.isEmpty()||password.isEmpty()||name.isEmpty()||age.isEmpty()||phoneNumber.isEmpty()||!age.matches("[0-9]+")){
            Dialog.dialog(currentView,"Invalid input","Input cannot be empty",false);
            return;
        }
        if(!FormatVerifier.isValidPassword(password)){
            Dialog.dialog(currentView,"Invalid input","Invalid password format",false);
            return;
        }
        if(!FormatVerifier.isEmail(email)){
            Dialog.dialog(currentView,"Invalid input","Invalid email format",false);
            return;
        }
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
                Intent intent = new Intent(currentView, LoginView.class);
                currentView.startActivity(intent);
            }
        } catch (CustomException e){
            Dialog.dialog(currentView,"Register Fail.",e.getMessage(),false);
        } catch(Exception e){
            Dialog.dialog(currentView,"System Error",BusinessMessage.system_error,false);
        }

    }

    public void edit_information(String email, String name, String age, String phoneNumber, String allergy) {
        try{
            if(name.isEmpty()){
                Dialog.dialog(currentView,"Invalid input","Input cannot be empty",false);
                return;
            }
            if(!age.matches("[0-9]+")){
                Dialog.dialog(currentView,"Invalid input","Invalid age",false);
                return;
            }
            User user = new User (email);
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

    public void send_verification_code(String email){

        if(FormatVerifier.isEmail(email)){
            SessionManager sessionManager = SessionManager.getInstance();
            sessionManager.getSession().setAttributes("reset_user_email", email);
            Reset_Password_Request reset = new Reset_Password_Request();
            reset.setResetPasswordUserEmail(email);
            reset.add_request();
            EmailService service = new EmailService();
            try {
                String title = "Foodverse password reset";
                String content = "This is your verification code for password reset : "+reset.getResetPasswordRandomCode();
                service.sendEmail("ricky.k@graduate.utm.my",title,content);
                Dialog.dialog(this.currentView,"Verification Code Sent","A verification code has been sent to the email, please check your mail box.",false);
            } catch (GeneralSecurityException ex) {
                Logger.getLogger(RestaurantAccountController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(RestaurantAccountController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Dialog.dialog(this.currentView,
                    "Invalid email address",
                    "Please enter correct email address",
                    false);
        }
    }

    public void reset_password(String code, String password) throws CustomException{
        try{
            SessionManager sessionManager = SessionManager.getInstance();
            Session session = sessionManager.getSession();
            User user = new User();
            String email = (String) session.getAttributes("reset_user_email");
            Reset_Password_Request reset = new Reset_Password_Request();
            reset.setResetPasswordUserEmail(email);
            reset.get_request_by_user_email();
            if(code.equals(reset.getResetPasswordRandomCode())){
                if(user.reset_password(email,password));
            } else {
                throw new CustomException("Verification Code Do Not Match!");
            }
        }catch(Exception e){

        }
    }

}


