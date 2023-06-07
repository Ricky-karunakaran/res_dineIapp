package com.order.controller;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.order.view.StripPaymentActivity;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;
import com.utils.ControllerBase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PaymentController extends ControllerBase {
    private StripPaymentActivity currentView;

    private String SECRET_KEY = "sk_test_51NFBp8CbDYea54mdoBPPH9hWbR8J2hv5LP1vn7CAqv0pgiRF1LFaY4qi2s6xarrzg6cPpq2BgBaUS6QJgqlnKnzg001CyxEN6a";
    private String PUBLISH_KEY="pk_test_51NFBp8CbDYea54mdxYLFmByfFGgS3ecEqgwAucRAYI152QbxVBfdBKVVjgvKtfwC04gvxWJNQpMMGXNz2PiczRd700BYYNF1WQ";
    private PaymentSheet paymentSheet;

    private String customerID;
    private String EphericalKey;
    private String ClientSecret;
    private boolean setup = false;

    public void setView(StripPaymentActivity v) {
        this.currentView  = v;
    }


    public void initiatePayment(){
        PaymentConfiguration.init(currentView.getThis(),this.PUBLISH_KEY);
        paymentSheet = new PaymentSheet(currentView.getThis(),paymentSheetResult -> {
            onPaymentResult(paymentSheetResult);

        });
        this.createCustomer();
    }

    public  void onPaymentResult(PaymentSheetResult paymentSheetResult){
        if(paymentSheetResult instanceof PaymentSheetResult.Completed){
            Toast.makeText(currentView,"Payment Success",Toast.LENGTH_SHORT).show();
        }
    }

    public void PaymentFlow() {
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

    public void createCustomer(){
        // create customer

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                "https://api.stripe.com/v1/customers",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            customerID =object.getString("id");
                            System.out.println("CLIENT IDL"+customerID);
                            getEphericalKey(customerID);
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
                return header;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(currentView.getThis());
        requestQueue.add(stringRequest);
    }

    public void getEphericalKey(String customerID) {
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
                            Toast.makeText(currentView.getThis(),EphericalKey,Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(this.currentView.getThis());
        requestQueue.add(stringRequest);

    }

    public void getClientSceret(String customerID) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                "https://api.stripe.com/v1/payment_intents",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            ClientSecret =object.getString("client_secret");

                            Toast.makeText(currentView.getThis(),ClientSecret,Toast.LENGTH_SHORT).show();
                            System.out.println(ClientSecret);
                            finishSetup();


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
                params.put("amount","2300");
                params.put("currency","myr");
                params.put("automatic_payment_methods[enabled]","true");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this.currentView.getThis());
        requestQueue.add(stringRequest);
    }

    public void finishSetup(){
        this.setup = true;
    }

    public boolean isSetup(){
        return setup;
    }
}
