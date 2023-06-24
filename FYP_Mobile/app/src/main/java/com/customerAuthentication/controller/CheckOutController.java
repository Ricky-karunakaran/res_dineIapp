package com.customerAuthentication.controller;

import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.order.model.Bill;
import com.order.view.SessionView;
import com.systemAccount.model.User;
import com.utils.CustomException;
import com.utils.Session;
import com.utils.SessionManager;

public class CheckOutController {
    private AppCompatActivity currentView;
    public void setView(AppCompatActivity v) {
        this.currentView  = v;
    }
    // check out session
    public void isBillSolved(){
        SessionManager sessionManager = SessionManager.getInstance();
        Session session = sessionManager.getSession();
        String bill_id = (String) session.getAttributes("session_bill_id");

        try{
            Bill bill = new Bill();
            bill.setBillId(bill_id);
            bill.read_bill_by_id();
            if(bill.getBillStatus().equals("SOLVED") || bill.getBillAmount()==0.0) {
                User user = (User) session.getAttributes("user");
                user.update_active_session(null);
                ((SessionView) this.currentView).to_home();
            } else {
                throw new CustomException("Please pay your bill before check out");
            }
        } catch (Exception e){
            AlertDialog.Builder builder = new AlertDialog.Builder(this.currentView);
            builder.setTitle("Fail to check out");
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
                System.out.println(e);
        }
    }
}
