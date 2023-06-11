package com.reporting.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.customerAuthentication.view.HomeView;
import com.example.fyp_mobile.R;
import com.reporting.controller.ConsumptionController;
import com.reporting.controller.HistoryVisitController;
import com.systemAccount.view.ProfileView;
import com.utils.CustomException;
import com.utils.Dialog;
import com.utils.JDateTime;
import com.utils.SessionManager;

import java.util.Calendar;
import java.util.Date;

public class ConsumptionView  extends AppCompatActivity {
    private ConsumptionController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumption_history);
        this.controller = new ConsumptionController();
        this.controller.setView(this);
        this.add_consumption_row("Visit Date Time","Restaurant Name","0","Consumption(RM)",true);
        this.controller.fetch_user_visit_consumption();

    }
    public LinearLayout get_visit_list_layout(){
        return (LinearLayout) findViewById(R.id.consumption_list);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(ConsumptionView.this, ProfileView.class);
        startActivity(intent);
    }

    public void backProfileView(View v){
        Intent intent = new Intent(ConsumptionView.this, ProfileView.class);
        startActivity(intent);
    }

    public void add_consumption_row(String visit_date_time, String visit_restaurant_name,String session_id,String visit_consumption, boolean isTitle){
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
        textViewParams.setMargins(5, 5, 5, 10);

        LinearLayoutCompat.LayoutParams layoutParams2 = new LinearLayoutCompat.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams2.setMargins(5, 5, 5, 10);
        layoutParams2.weight = 1;
        LinearLayout horizontal_layout = new LinearLayout(this);
        horizontal_layout.setOrientation(LinearLayout.HORIZONTAL);
        horizontal_layout.setLayoutParams(horizontal_layout_params);

        TextView l_visit_date_time = new TextView(this);
        l_visit_date_time.setText(visit_date_time==null?"Undefined":visit_date_time);
        l_visit_date_time.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        l_visit_date_time.setTextColor(Color.BLACK);
        l_visit_date_time.setLayoutParams(layoutParams2);
        if(isTitle) {l_visit_date_time.setTypeface(null, Typeface.BOLD);}
        TextView l_visit_restaurant_name = new TextView(this);

        l_visit_restaurant_name.setText(visit_restaurant_name);
        l_visit_restaurant_name.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        l_visit_restaurant_name.setTextColor(Color.BLACK);
        l_visit_restaurant_name.setLayoutParams(layoutParams2);
        if(isTitle) {l_visit_restaurant_name.setTypeface(null, Typeface.BOLD);}


        TextView l_visit_consumption = new TextView(this);
        l_visit_consumption.setText(visit_consumption);
        l_visit_consumption.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        l_visit_consumption.setTextColor(Color.BLACK);
        l_visit_consumption.setLayoutParams(layoutParams2);
        if(isTitle) {l_visit_consumption.setTypeface(null, Typeface.BOLD);}

        horizontal_layout.addView(l_visit_date_time);
        horizontal_layout.addView(l_visit_restaurant_name);
        horizontal_layout.addView(l_visit_consumption);
        this.get_visit_list_layout().addView(horizontal_layout);

    }
}
