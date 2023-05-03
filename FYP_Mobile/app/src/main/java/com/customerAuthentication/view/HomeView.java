package com.customerAuthentication.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.customerAuthentication.controller.CheckInController;
import com.example.fyp_mobile.R;
import com.order.view.SessionView;
import com.systemAccount.view.ProfileView;

public class HomeView extends AppCompatActivity {
    private CheckInController controller ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.controller = new CheckInController();
        setContentView(R.layout.activity_home);
        this.setupRoute();
        this.initializeHomeText();


    }

    private void setupRoute() {
        ImageView profile = (ImageView) findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeView.this, ProfileView.class);
                startActivity(intent);
            }
        });

        ImageView checkIn = (ImageView) findViewById(R.id.check_in);
        checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeView.this, CheckInView.class);
                startActivity(intent);
            }
        });
    }
    private void initializeHomeText(){

        String restaurant_name = controller.checkHasCheckIn();
        TextView home_active_session = findViewById(R.id.home_active_session);
        Button home_to_session_button = findViewById(R.id.home_to_session);

        if(restaurant_name != null){
            home_active_session.setText("Having active session with restaurant: "+restaurant_name+"\n Click the button below to view your session");
            home_to_session_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeView.this, SessionView.class);
                    startActivity(intent);
                }
            });
        } else {
            home_active_session.setText("No Active Session Found");
            home_to_session_button.setVisibility(View.GONE);
        }
    }

}
