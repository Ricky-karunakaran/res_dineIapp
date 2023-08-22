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
import com.customerAuthentication.model.Check_In_Request;
import com.customerAuthentication.model.Dine_In_Session;
import com.order.model.Bill;
import com.order.model.BillItem;
import com.order.view.StripPaymentActivity;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;
import com.systemAccount.model.User;
import com.utils.ControllerBase;
import com.utils.Session;
import com.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PaymentController extends ControllerBase {
    private StripPaymentActivity currentView;

    private String SECRET_KEY = "";
    private String PUBLISH_KEY="";
    private PaymentSheet paymentSheet;

    private String customerID;
    private String EphericalKey;
    private String ClientSecret;
    private boolean setup = false;

    public void setView(StripPaymentActivity v) {
        this.currentView  = v;
    }


    public void initiatePayment(){
        System.out.println("Hello");
        PaymentConfiguration.init(currentView.getThis(),this.PUBLISH_KEY);
        paymentSheet = new PaymentSheet(currentView.getThis(),paymentSheetResult -> {
            if(onPaymentResult(paymentSheetResult)){
                currentView.showPaymentSusscuess();
            }

        });
        System.out.println("Hello");
        this.createCustomer();
    }

    public  boolean onPaymentResult(PaymentSheetResult paymentSheetResult){
        if(paymentSheetResult instanceof PaymentSheetResult.Completed){
            Toast.makeText(currentView,"Payment Success",Toast.LENGTH_SHORT).show();
            SessionManager sessionManager = SessionManager.getInstance();
            Session session = sessionManager.getSession();

            String bill_id =(String) session.getAttributes("session_bill_id");

            Bill bill = new Bill();
            bill.setBillId(bill_id);
            bill.update_status();

            User user = (User) session.getAttributes("user");
            Dine_In_Session dine_in_session = new Dine_In_Session();
            dine_in_session.setSessionId((String)session.getAttributes("session_id"));
            dine_in_session.setSessionStatus("CLOSED");
            Check_In_Request check_in_request = new Check_In_Request();
            check_in_request.setCheckInUserEmail(user.getUserEmail());
            try {
                user.update_active_session(null);
                dine_in_session.update_session_status_by_id();
                check_in_request.close_check_in_request();

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e){

            }

            return true;
        }
        return false;

    }

    public void PaymentFlow() {
        System.out.println(ClientSecret);
        System.out.println(customerID);
        System.out.println(EphericalKey);
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
        System.out.println("Hello");
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
                        System.out.println("Error");
                        System.out.println(error.getMessage());
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                System.out.println("Hello");
                Map<String, String> header = new HashMap<>();
                header.put("Authorization","Bearer "+SECRET_KEY);
                return header;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(currentView.getThis());
        requestQueue.add(stringRequest);
        System.out.println("End Get Customer");
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
                            // Toast.makeText(currentView.getThis(),EphericalKey,Toast.LENGTH_SHORT).show();
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
        double bill_amount = this.fetchBillPrice();
        DecimalFormat df = new DecimalFormat("#0.00");
        String formatted = df.format(bill_amount);
        String s_bill_amount = formatted.replace(".","");
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                "https://api.stripe.com/v1/payment_intents",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            ClientSecret =object.getString("client_secret");

                            // Toast.makeText(currentView.getThis(),ClientSecret,Toast.LENGTH_SHORT).show();
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
                params.put("amount",s_bill_amount);
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

    public double fetchBillPrice(){
        SessionManager sessionManager = SessionManager.getInstance();
        Session session = sessionManager.getSession();

        String bill_id =(String) session.getAttributes("session_bill_id");

        Bill bill = new Bill();
        bill.setBillId(bill_id);
        bill.read_bill_by_id();
        ArrayList<BillItem> bill_list= bill.getBillItems();
        double total_price = 0;
        for(int i=0;i<bill_list.size();i++){
            BillItem bill_item = bill_list.get(i);
            total_price+=bill_item.getBillItemUnitPrice()*bill_item.getBillItemQuantity();
        }
        return total_price;
    }
}
