package com.order.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.customerAuthentication.view.HomeView;
import com.order.model.Menu;
import com.order.model.MenuItem;
import com.order.model.WaiterCall;
import com.order.view.SessionView;
import com.order.view.StripPaymentActivity;
import com.systemAccount.model.Restaurant;
import com.systemAccount.model.User;
import com.utils.Dialog;
import com.utils.JDateTime;
import com.utils.Session;
import com.utils.SessionManager;

import java.sql.SQLException;
import java.util.ArrayList;

public class SessionController {

    private AppCompatActivity currentView;
    public void setView(AppCompatActivity v) {
        this.currentView  = v;
    }
    public boolean checkSessionStatus(){
        SessionManager sessionManager = SessionManager.getInstance();
        Session session = sessionManager.getSession();
        User user = (User) session.getAttributes("user");
        try {
            if(!user.check_active_session()){
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }


}
