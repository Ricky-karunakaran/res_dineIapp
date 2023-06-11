package com.order.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.customerAuthentication.view.HomeView;
import com.example.fyp_mobile.R;
import com.order.controller.BillController;
import com.order.controller.PaymentController;
import com.reporting.view.ConsumptionView;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;
import com.systemAccount.view.ProfileView;
import com.utils.CustomException;
import com.utils.Dialog;
import com.utils.JDateTime;
import com.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StripPaymentActivity extends AppCompatActivity {
    PaymentController paymentController;
    BillController billController;
    Button button;

    public StripPaymentActivity getThis(){
        return this;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out_stripe);
        billController = new BillController();
        billController.setView(this);

        this.add_bill_row("Item","Quantity","Total Price",true);
        billController.fetch_bill_item();


        paymentController = new PaymentController();
        paymentController.setView(this);
        paymentController.initiatePayment();

        button = findViewById(R.id.stripe_pay_btn);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(paymentController.isSetup()){
                    paymentController.PaymentFlow();
                }

            }
        });
    }


    public LinearLayout getBillList(){
        return (LinearLayout) findViewById(R.id.bill_list);
    }

    public void add_bill_row(String bill_item_name, String bill_item_quantity,String bill_item_total_price,boolean isTitle){
        // layout
        LinearLayout.LayoutParams horizontal_layout_params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        horizontal_layout_params.setMargins(0,10,0,10);
        ViewGroup.MarginLayoutParams textViewParams = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        textViewParams.setMargins(5, 5, 5, 10);

        LinearLayoutCompat.LayoutParams layoutParams2 = new LinearLayoutCompat.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams2.setMargins(5, 5, 5, 10);
        layoutParams2.weight = 1;
        LinearLayout horizontal_layout = new LinearLayout(this);
        horizontal_layout.setOrientation(LinearLayout.HORIZONTAL);
        horizontal_layout.setLayoutParams(horizontal_layout_params);

        TextView l_bill_item_name = new TextView(this);
        l_bill_item_name.setText(bill_item_name==null?"Undefined":bill_item_name);
        l_bill_item_name.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        l_bill_item_name.setTextColor(Color.BLACK);
        l_bill_item_name.setLayoutParams(layoutParams2);
        if(isTitle) {l_bill_item_name.setTypeface(null, Typeface.BOLD);}

        TextView l_bill_item_quantity = new TextView(this);
        l_bill_item_quantity.setText(bill_item_quantity);
        l_bill_item_quantity.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        l_bill_item_quantity.setTextColor(Color.BLACK);
        l_bill_item_quantity.setLayoutParams(layoutParams2);
        if(isTitle) {l_bill_item_quantity.setTypeface(null, Typeface.BOLD);}

        TextView l_bill_item_total_price = new TextView(this);
        l_bill_item_total_price.setText(isTitle?bill_item_total_price:"RM "+bill_item_total_price);
        l_bill_item_total_price.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        l_bill_item_total_price.setTextColor(Color.BLACK);
        l_bill_item_total_price.setLayoutParams(layoutParams2);
        if(isTitle) {l_bill_item_total_price.setTypeface(null, Typeface.BOLD);}

        horizontal_layout.addView(l_bill_item_name);
        horizontal_layout.addView(l_bill_item_quantity);
        horizontal_layout.addView(l_bill_item_total_price);


        this.getBillList().addView(horizontal_layout);

    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(StripPaymentActivity.this, SessionView.class);
        startActivity(intent);
    }
    public void backSessionView(View v){
        this.onBackPressed();
    }

    public void showPaymentSusscuess(){
        String title = "Payment Successful";
        String message = "You have paid the bill, you will be redirect to home screen now";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false); // set whether the dialog is cancelable or not
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(StripPaymentActivity.this, HomeView.class);
                startActivity(intent);
            }
        });
        builder.show();

    }

}
