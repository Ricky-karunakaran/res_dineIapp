package com.order.view;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.customerAuthentication.controller.CheckInController;
import com.example.fyp_mobile.R;
import com.order.controller.MenuController;

public class SessionView  extends AppCompatActivity {
    private MenuController controller ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.controller = new MenuController();
        this.controller.setView(this);
        setContentView(R.layout.activity_session);
        this.initializeSessionRestaurant();
    }

    private void initializeSessionRestaurant() {
        TextView restaurant_name = (TextView) findViewById(R.id.session_restaurant_name);
        LinearLayout sessionMenuList = (LinearLayout) findViewById(R.id.session_menu_list);
        this.controller.fetchMenu(restaurant_name, sessionMenuList);
    }
}
