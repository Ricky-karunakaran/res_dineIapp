package com.order.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


import com.example.fyp_mobile.R;
import com.order.controller.MenuController;
import com.order.controller.SessionController;
import com.order.controller.WaiterCallController;
import com.utils.ControllerBase;


public class WaiterView extends AppCompatActivity {
    private WaiterCallController controller ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.controller = new WaiterCallController();
        this.controller.setView(this);
        setContentView(R.layout.activity_waiter_view);

    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(WaiterView.this, SessionView.class);
        startActivity(intent);
    }

    public void backSessionView(View v){
        Intent intent = new Intent(this, SessionView.class);
        startActivity(intent);
    }

    public void submitCall(View v){
        EditText content = (EditText)findViewById(R.id.waiterCallReason);
        this.controller.call_waiter(content.getText().toString());
    }
}
