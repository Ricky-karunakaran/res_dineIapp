package com.systemAccount.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fyp_mobile.R;
import com.systemAccount.controller.SystemAccountController;

public class ResetPasswordView extends AppCompatActivity {
    private SystemAccountController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        this.controller = new SystemAccountController();
        this.controller.setView(this);
        System.out.println("Hello");


    }

    public void send_verification_code(View v){
        EditText email = (EditText) findViewById(R.id.email_input);
        System.out.println(email.getText().toString());
        this.controller.send_verification_code(email.getText().toString());
        Intent intent = new Intent(this, ResetPasswordConfirm.class);
        startActivity(intent);
    }
}
