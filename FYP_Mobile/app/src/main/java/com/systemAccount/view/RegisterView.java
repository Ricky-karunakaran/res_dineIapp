package com.systemAccount.view;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;
import com.example.fyp_mobile.R;
import com.systemAccount.controller.SystemAccountController;
import com.utils.Dialog;

public class RegisterView extends AppCompatActivity {
    private SystemAccountController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.controller = new SystemAccountController();
        this.controller.setView(this);


    }
    public void register(View v){
        System.out.println("Register called");
        EditText email = (EditText) findViewById(R.id.email_input);
        EditText password = (EditText) findViewById(R.id.password_input);
        EditText name = (EditText) findViewById(R.id.name_input);
        EditText age = (EditText) findViewById(R.id.age_input);
        EditText phone_number = (EditText) findViewById(R.id.phone_input);

        String strEmail = email.getText().toString();
        String strPassword = password.getText().toString();
        String strName = name.getText().toString();
        String strAge = age.getText().toString();
        String strPhone = phone_number.getText().toString();
        this.controller.register_user(strEmail,strPassword,strName,strAge,strPhone);
    }
}
