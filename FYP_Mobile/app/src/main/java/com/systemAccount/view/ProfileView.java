package com.systemAccount.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.customerAuthentication.view.HomeView;
import com.example.fyp_mobile.R;
import com.reporting.model.HistoryVisit;
import com.reporting.view.VisitHistoryView;
import com.systemAccount.controller.SystemAccountController;
import com.systemAccount.model.User;
import com.utils.Session;
import com.utils.SessionManager;

public class ProfileView extends AppCompatActivity {
    private SystemAccountController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.controller = new SystemAccountController();
        this.controller.setView(this);

        setContentView(R.layout.activity_profile);
        this.setupRoute();
        SessionManager sessionManager = SessionManager.getInstance();
        Session session = sessionManager.getSession();
        System.out.println(session == null);
        User user = (User) session.getAttributes("user");
        TextView user_name = (TextView) findViewById(R.id.profile_user_name);
        System.out.println(user.getUserEmail());
        System.out.println(user.getUserName());
        user_name.setText(user.getUserName());

        TextView editProfileButton = (TextView)  findViewById(R.id.editProfileButton);
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileView.this, EditProfileView.class);
                startActivity(intent);
            }
        });
        TextView historyButton = (TextView)  findViewById(R.id.historyButton);
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileView.this, VisitHistoryView.class);
                startActivity(intent);
            }
        });

        TextView consumptionButton = (TextView)  findViewById(R.id.consumptionButton);
        consumptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileView.this, EditProfileView.class);
                startActivity(intent);
            }
        });



    }

    public void setupRoute(){
        ImageView home = (ImageView) findViewById(R.id.menu_home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileView.this, HomeView.class);
                startActivity(intent);
            }
        });
    }
}
