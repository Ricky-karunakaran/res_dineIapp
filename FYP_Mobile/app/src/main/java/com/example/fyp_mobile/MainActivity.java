package com.example.fyp_mobile;

import com.example.fyp_mobile.config.paypalConfig;
//import com.paypal.android.sdk.payments.PayPalConfiguration;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;

import com.systemAccount.model.User;
import com.systemAccount.view.LoginView;
import com.systemAccount.view.RegisterView;
import com.systemAccount.view.SystemAccountActivity;
import com.utils.Session;
import com.utils.SessionManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SessionManager sessionManager = SessionManager.getInstance();
        sessionManager.createSession();
        TextView signIn = (TextView) findViewById(R.id.signInButton);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginView.class);
                startActivity(intent);
            }
        });

        TextView signUp = (TextView) findViewById(R.id.signUpButton);
        signUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, RegisterView.class);
                startActivity(intent);
            }
        });
    }

}