package com.systemAccount.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.customerAuthentication.view.CheckInView;
import com.customerAuthentication.view.HomeView;
import com.example.fyp_mobile.R;
import com.systemAccount.controller.SystemAccountController;
import com.systemAccount.model.User;
import com.utils.Session;
import com.utils.SessionManager;

public class EditProfileView extends AppCompatActivity {
    private SystemAccountController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        controller = new SystemAccountController();
        controller.setView(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        this.setupRoute();

        SessionManager sessionManager = SessionManager.getInstance();
        Session session = sessionManager.getSession();
        User user = (User) session.getAttributes("user");

        TextView email = (TextView)  findViewById(R.id.profile_email_input);
        email.setText(user.getUserEmail());

        EditText name = (EditText)  findViewById(R.id.profile_name_input);
        name.setText(user.getUserName());

        EditText age = (EditText)  findViewById(R.id.profile_age_input);
        int ageInt = user.getUserAge();
        age.setText(Integer.toString(ageInt));

        EditText phone = (EditText)  findViewById(R.id.profile_phone_input);
        phone.setText(user.getUserPhoneNumber());


    }

    public void save(View v){
        TextView email = (TextView)  findViewById(R.id.profile_email_input);
        EditText name = (EditText)  findViewById(R.id.profile_name_input);
        EditText age = (EditText)  findViewById(R.id.profile_age_input);
        EditText phone = (EditText)  findViewById(R.id.profile_phone_input);

        String strEmail = email.getText().toString();
        String strName = name.getText().toString();
        String strAge = age.getText().toString();
        String strPhone = phone.getText().toString();
        this.controller.edit_information(strEmail,strName,strAge,strPhone,"");
    }
    public void back(View v) {
        Intent intent = new Intent(EditProfileView.this, ProfileView.class);
        startActivity(intent);
    }

    public void setupRoute(){
        ImageView home = (ImageView) findViewById(R.id.menu_home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfileView.this, HomeView.class);
                startActivity(intent);
            }
        });

        ImageView checkIn = (ImageView) findViewById(R.id.check_in);
        checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfileView.this, CheckInView.class);
                startActivity(intent);
            }
        });
    }
}
