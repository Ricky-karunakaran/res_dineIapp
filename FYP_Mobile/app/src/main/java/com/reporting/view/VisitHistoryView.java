package com.reporting.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fyp_mobile.R;
import com.reporting.controller.HistoryVisitController;
import com.systemAccount.controller.SystemAccountController;
import com.systemAccount.model.User;
import com.systemAccount.view.EditProfileView;
import com.systemAccount.view.ProfileView;
import com.utils.Session;
import com.utils.SessionManager;

public class VisitHistoryView extends AppCompatActivity {
    private HistoryVisitController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new HistoryVisitController();
        this.controller.setView(this);
        setContentView(R.layout.activity_visit_history);

        this.controller.fetch_user_visit_history();
        this.add_history_row("Visit Date Time","Restaurant Name");


    }

    public LinearLayout get_visit_list_layout(){
        return (LinearLayout) findViewById(R.id.visit_history_list);
    }

    public void add_history_row(String visit_date_time, String visit_restaurant_name){
        // layout
        LinearLayout.LayoutParams horizontal_layout_params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        horizontal_layout_params.setMargins(0,10,0,10);
        ViewGroup.MarginLayoutParams textViewParams = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        textViewParams.setMargins(20, 5, 20, 5);

        LinearLayout horizontal_layout = new LinearLayout(this);
        horizontal_layout.setOrientation(LinearLayout.HORIZONTAL);
        horizontal_layout.setLayoutParams(horizontal_layout_params);


        TextView l_visit_date_time = new TextView(this);
        l_visit_date_time.setText(visit_date_time==null?"Undefined":visit_date_time);
        l_visit_date_time.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
        l_visit_date_time.setTextColor(Color.BLACK);
        l_visit_date_time.setLayoutParams(textViewParams);

        TextView l_visit_restaurant_name = new TextView(this);
        l_visit_restaurant_name.setText(visit_restaurant_name);
        l_visit_restaurant_name.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
        l_visit_restaurant_name.setTextColor(Color.BLACK);
        l_visit_restaurant_name.setLayoutParams(textViewParams);

        Button action_button = new Button(this);

        horizontal_layout.addView(l_visit_date_time);
        horizontal_layout.addView(l_visit_restaurant_name);
        this.get_visit_list_layout().addView(horizontal_layout);

    }
}
