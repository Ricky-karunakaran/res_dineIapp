//package com.order.view;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.core.content.ContextCompat;
//
//import com.example.fyp_mobile.R;
//import com.example.fyp_mobile.config.paypalConfig;
//import com.paypal.android.sdk.payments.PayPalConfiguration;
//import com.paypal.android.sdk.payments.PayPalPayment;
//import com.paypal.android.sdk.payments.PayPalService;
//import com.paypal.android.sdk.payments.PaymentActivity;
//import com.paypal.android.sdk.payments.PaymentConfirmation;
//
//import org.json.JSONException;
//
//import java.math.BigDecimal;
//import java.util.Objects;
//
//
//public class CheckOutActivity extends Activity {
//    private static PayPalConfiguration config = new PayPalConfiguration()
//            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
//            .clientId(paypalConfig.PAYPAL_CLIENT_ID);
//    @Override
//    protected void onDestroy(){
//        stopService(new Intent(this,PayPalService.class));
//        super.onDestroy();
//    }
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_check_out);
//
//        Intent intent = new Intent(this,PayPalService.class);
//        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
//        startService(intent);
//
//        findViewById(R.id.pay_button).setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                processPayment();
//            }
//        });
//    }
//
//    private void processPayment(){
//        try{
//            String amount = "20";
//            PayPalPayment payPalPayment = new PayPalPayment(
//                    new BigDecimal(20),
//                    "USD",
//                    "Test",
//                    PayPalPayment.PAYMENT_INTENT_SALE);
//            Intent intent = new Intent(this, PaymentActivity.class);
//            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
//            intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
//            startActivityForResult(intent,7171);
//        } catch(Exception e ){
//            System.out.println(e.getMessage());
//
//        }
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data){
//        System.out.println("PAYPAL REQUEST CODE"+requestCode);
//        System.out.println(resultCode);
//        if(requestCode == 7171){
//            if(resultCode == RESULT_OK){
//                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
//                if(confirmation!=null){
//                    try{
//                        String paymentDetails = confirmation.toJSONObject().toString(4);
//
//                        startActivity (new Intent(this,PaymentResult.class).putExtra("PaymentDetails",paymentDetails));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }else Toast.makeText(this,"Cancel",Toast.LENGTH_SHORT).show();
//            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID){
//
//            }
//
//
//        }
//    }
//
//
//}