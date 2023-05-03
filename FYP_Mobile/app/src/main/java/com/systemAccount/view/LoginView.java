package com.systemAccount.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.fyp_mobile.R;
import com.systemAccount.controller.SystemAccountController;

public class LoginView extends AppCompatActivity {
    private SystemAccountController controller;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mProgressBar = findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.INVISIBLE);
        this.controller = new SystemAccountController();
        this.controller.setView(this);
        System.out.println("Hello");


    }

    public void login(View v){
        EditText email = (EditText) findViewById(R.id.email_input);
        EditText password = (EditText) findViewById(R.id.password_input);
        controller.login(email.getText().toString(),password.getText().toString());
    }
}
