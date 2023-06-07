package com.order.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fyp_mobile.R;
import com.order.controller.PaymentController;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StripPaymentActivity extends AppCompatActivity {
    PaymentController paymentController;
    Button button;
    private String SECRET_KEY = "sk_test_51NFBp8CbDYea54mdoBPPH9hWbR8J2hv5LP1vn7CAqv0pgiRF1LFaY4qi2s6xarrzg6cPpq2BgBaUS6QJgqlnKnzg001CyxEN6a";
    private String PUBLISH_KEY="pk_test_51NFBp8CbDYea54mdxYLFmByfFGgS3ecEqgwAucRAYI152QbxVBfdBKVVjgvKtfwC04gvxWJNQpMMGXNz2PiczRd700BYYNF1WQ";
    private PaymentSheet paymentSheet;

    private String customerID;
    private String EphericalKey;
    private String ClientSecret;

    public StripPaymentActivity getThis(){
        return this;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out_stripe);
        paymentController = new PaymentController();
        paymentController.setView(this);
        paymentController.initiatePayment();
        button = findViewById(R.id.stripe_pay_btn);
//        PaymentConfiguration.init(this,this.PUBLISH_KEY);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(paymentController.isSetup()){
                    paymentController.PaymentFlow();
                }

            }
        });
//        paymentSheet = new PaymentSheet(this,paymentSheetResult -> {
//            onPaymentResult(paymentSheetResult);
//
//        });
//
//        StringRequest stringRequest = new StringRequest(
//                Request.Method.POST,
//                "https://api.stripe.com/v1/customers",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject object = new JSONObject(response);
//                            customerID =object.getString("id");
//                            System.out.println("CLIENT IDL"+customerID);
//                            getEphericalKey(customerID);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                }
//        ){
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> header = new HashMap<>();
//                header.put("Authorization","Bearer "+SECRET_KEY);
//                return header;
//            }
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(StripPaymentActivity.this);
//        requestQueue.add(stringRequest);
    }

    private void getEphericalKey(String customerID) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                "https://api.stripe.com/v1/ephemeral_keys",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            EphericalKey =object.getString("id");
                            System.out.println("E KEY"+EphericalKey);
                            Toast.makeText(StripPaymentActivity.this,EphericalKey,Toast.LENGTH_SHORT).show();
                            getClientSceret(customerID);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization","Bearer "+SECRET_KEY);
                header.put("Stripe-Version","2022-11-15");
                return header;
            }

            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customer",customerID);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(StripPaymentActivity.this);
        requestQueue.add(stringRequest);

    }

    private void getClientSceret(String customerID) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                "https://api.stripe.com/v1/payment_intents",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            ClientSecret =object.getString("client_secret");

                            Toast.makeText(StripPaymentActivity.this,ClientSecret,Toast.LENGTH_SHORT).show();
                            System.out.println(ClientSecret);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization","Bearer "+SECRET_KEY);
                header.put("Stripe-Version","2022-11-15");
                return header;
            }

            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customer",customerID);
                params.put("amount","100000");
                params.put("currency","usd");
                params.put("automatic_payment_methods[enabled]","true");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(StripPaymentActivity.this);
        requestQueue.add(stringRequest);
    }

    private void PaymentFlow() {
        paymentSheet.presentWithPaymentIntent(
                ClientSecret,
                new PaymentSheet.Configuration(
                        "Ricky Restaurant",
                        new PaymentSheet.CustomerConfiguration(
                                customerID,
                                EphericalKey
                        ))
        );
    }
    private  void onPaymentResult(PaymentSheetResult paymentSheetResult){
        if(paymentSheetResult instanceof PaymentSheetResult.Completed){
            Toast.makeText(this,"Payment Success",Toast.LENGTH_SHORT).show();
        }
    }
}
