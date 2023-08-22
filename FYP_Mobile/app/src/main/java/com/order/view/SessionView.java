package com.order.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.FocusFinder;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.customerAuthentication.controller.CheckInController;
import com.customerAuthentication.controller.CheckOutController;
import com.customerAuthentication.view.CheckInView;
import com.customerAuthentication.view.HomeView;
import com.example.fyp_mobile.R;
import com.order.controller.CartController;
import com.order.controller.MenuController;
import com.order.controller.SessionController;
import com.systemAccount.view.ProfileView;
import com.utils.Dialog;

public class SessionView  extends AppCompatActivity {
    private MenuController controller ;
    private CartController cart_controller;
    private CheckOutController check_out_controler;
    private SessionController sessionController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.controller = new MenuController();
        this.controller.setView(this);
        this.cart_controller = new CartController();
        this.cart_controller.setView(this);
        this.check_out_controler = new CheckOutController();
        this.check_out_controler.setView(this);
        this.sessionController = new SessionController();
        this.sessionController.setView(this);
        setContentView(R.layout.activity_session);
        this.setupRoute();
        this.initializeSessionRestaurant();
    }

    private void initializeSessionRestaurant() {
        try{
            TextView restaurant_name = (TextView) findViewById(R.id.session_restaurant_name);
            LinearLayout sessionMenuList = (LinearLayout) findViewById(R.id.session_menu_list);
            this.controller.fetchMenu(restaurant_name, sessionMenuList);
        } catch (Exception e ){
            Dialog.dialog(this,"ERROR",e.getMessage(),false);
        }

    }

    private void setupRoute(){
        ImageView order = (ImageView) findViewById(R.id.session_order_button);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sessionController.checkSessionStatus()){
                    Intent intent = new Intent(SessionView.this, CartView.class);
                    startActivity(intent);
                } else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(SessionView.this);
                    builder.setTitle("No Active Session");
                    builder.setMessage("No active session with this restaurant");
                    builder.setCancelable(false); // set whether the dialog is cancelable or not
                    builder.setPositiveButton("Back To Home", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(SessionView.this, HomeView.class);
                            startActivity(intent);
                        }
                    });
                    builder.show();
                }

            }
        });

        ImageView waiter = (ImageView) findViewById(R.id.session_waiter_button);
        waiter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(sessionController.checkSessionStatus()){
                    Intent intent = new Intent(SessionView.this, WaiterView.class);
                    startActivity(intent);
                } else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(SessionView.this);
                    builder.setTitle("No Active Session");
                    builder.setMessage("No active session with this restaurant");
                    builder.setCancelable(false); // set whether the dialog is cancelable or not
                    builder.setPositiveButton("Back To Home", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(SessionView.this, HomeView.class);
                            startActivity(intent);
                        }
                    });
                    builder.show();
                }

            }
        });

        ImageView payment = (ImageView) findViewById(R.id.session_bill_button);
        payment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(sessionController.checkSessionStatus()){
                    Intent intent = new Intent(SessionView.this, StripPaymentActivity.class);
                    startActivity(intent);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(SessionView.this);
                    builder.setTitle("No Active Session");
                    builder.setMessage("No active session with this restaurant");
                    builder.setCancelable(false); // set whether the dialog is cancelable or not
                    builder.setPositiveButton("Back To Home", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(SessionView.this, HomeView.class);
                            startActivity(intent);
                        }
                    });
                    builder.show();
                }
            }
        });


    }

    public void add_to_cart(int menuItemId){
        this.cart_controller.add_item(String.valueOf(menuItemId));
    }

    public void check_out (View v){
        this.check_out_controler.isBillSolved();
    }

    public void to_home(){
        Intent intent = new Intent(this, HomeView.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, HomeView.class);
        startActivity(intent);
    }
}
