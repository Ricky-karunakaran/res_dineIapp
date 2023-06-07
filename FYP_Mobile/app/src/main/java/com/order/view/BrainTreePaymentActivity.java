//package com.order.view;
//
//import android.os.Bundle;
//import android.widget.ProgressBar;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.braintreepayments.api.BraintreeClient;
//import com.braintreepayments.api.DataCollector;
//import com.braintreepayments.api.PayPalAccountNonce;
//import com.braintreepayments.api.PayPalClient;
//import com.braintreepayments.api.PayPalListener;
//import com.example.fyp_mobile.R;
//
//public class BrainTreePaymentActivity extends AppCompatActivity implements PayPalListener {
//    private ProgressBar progressBar;
//    private String payment_submitted;
//
//    private static final String TAG ="BrainTreePaymentActivity";
//    private static final String AMOUNT ="5";
//    private static final String CURRENCY = "USD";
//
//    private BraintreeClient braintreeClient;
//    private PayPalClient payPalClient;
//    private DataCollector dataCollector;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_brain_tree_payment);
//
//        progressBar = findViewById(R.id.progressBar);
//        // handleLoading(false);
//
//        Intent intent = getIntent();
//        payment_submitted = intent.getStringExtra("payment_submitted");
//        braintreeClient = new BraintreeClient(this,new TokenProvider());
//        payPalClient = new PayPalClient(this,braintreeClient);
//        payPalClient.setListener(this);
//
//        dataCollector = new DataCollector(braintreeClient);
//    }
//
//
//    @Override
//    public void onPayPalSuccess(@NonNull PayPalAccountNonce payPalAccountNonce) {
//
//    }
//
//    @Override
//    public void onPayPalFailure(@NonNull Exception error) {
//
//    }
//}
