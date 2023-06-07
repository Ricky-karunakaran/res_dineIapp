package com.order.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fyp_mobile.R;

import org.json.JSONObject;

public class PaymentResult extends AppCompatActivity {
    TextView txtAmount ;

    @Override
    protected  void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out_result);

        txtAmount = (TextView) findViewById(R.id.txtAmount);
        Intent intent = getIntent();
        try{
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("PaymentDetails"));
            showDetails(jsonObject.getJSONObject("response"),intent.getStringExtra("PaymentDetails"));
        } catch (Exception e) {

        }

    }
    private void showDetails(JSONObject response, String paymentAmount){
        try{
            txtAmount.setText(response.getString("id"));
        }catch(Exception e){

        }
    }
}
