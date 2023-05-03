package com.systemAccount.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.fyp_mobile.R;

public class SystemAccountActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Hello");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
