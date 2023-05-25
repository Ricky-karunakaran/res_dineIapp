package com.customerAuthentication.view;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.customerAuthentication.controller.CheckInController;
import com.example.fyp_mobile.R;

import com.systemAccount.model.User;
import com.utils.Session;
import com.utils.SessionManager;

public class CheckInView extends AppCompatActivity {
    private CheckInController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initialize();
        setContentView(R.layout.activity_check_in);
    }

    public void checkIn(View v){
        EditText restaurant_code = (EditText)  findViewById(R.id.check_in_restaurant_code);
        EditText table_no = (EditText) findViewById(R.id.check_in_restaurant_table_no);
        controller.checkIn(restaurant_code.getText().toString());

    }

    private void initialize(){
        controller = new CheckInController();
        controller.setView(this);
    }
}
