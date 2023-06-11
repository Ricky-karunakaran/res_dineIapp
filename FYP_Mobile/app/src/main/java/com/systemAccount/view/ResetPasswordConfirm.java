package com.systemAccount.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fyp_mobile.R;
import com.systemAccount.controller.SystemAccountController;
import com.utils.CustomException;
import com.utils.Dialog;

public class ResetPasswordConfirm extends AppCompatActivity {
    private SystemAccountController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_confirm);
        this.controller = new SystemAccountController();
        this.controller.setView(this);
    }

    public void reset_password(View v){
        EditText code = findViewById(R.id.code_input);
        EditText newPassword = findViewById(R.id.new_password_input);
        EditText confirmPassword = findViewById(R.id.confirm_password_input);
        try {
            this.controller.reset_password(code.getText().toString(), newPassword.getText().toString(),confirmPassword.getText().toString());
        } catch (CustomException e) {
            Dialog.dialog(this,"Reset Password Fail",e.getMessage(),false);
            return;
        }
        Intent intent = new Intent(this, LoginView.class);
        startActivity(intent);
        // Dialog.dialog(this,"Reset Password Success","Your password has been reset",false);
    }
}
