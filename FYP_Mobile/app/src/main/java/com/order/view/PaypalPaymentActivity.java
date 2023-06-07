//package com.order.view;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.fyp_mobile.BuildConfig;
//import com.example.fyp_mobile.R;
//import com.paypal.checkout.PayPalCheckout;
//import com.paypal.checkout.approve.Approval;
//import com.paypal.checkout.approve.OnApprove;
//import com.paypal.checkout.config.CheckoutConfig;
//import com.paypal.checkout.config.Environment;
//import com.paypal.checkout.config.SettingsConfig;
//import com.paypal.checkout.createorder.CreateOrder;
//import com.paypal.checkout.createorder.CreateOrderActions;
//import com.paypal.checkout.createorder.CurrencyCode;
//import com.paypal.checkout.createorder.OrderIntent;
//import com.paypal.checkout.createorder.UserAction;
//import com.paypal.checkout.error.ErrorInfo;
//import com.paypal.checkout.error.OnError;
//import com.paypal.checkout.order.Amount;
//import com.paypal.checkout.order.AppContext;
//import com.paypal.checkout.order.CaptureOrderResult;
//import com.paypal.checkout.order.OnCaptureComplete;
//import com.paypal.checkout.order.Order;
//import com.paypal.checkout.order.PurchaseUnit;
//import com.paypal.checkout.paymentbutton.PaymentButtonContainer;
//
//import org.jetbrains.annotations.NotNull;
//
//import java.util.ArrayList;
//
//public class PaypalPaymentActivity extends AppCompatActivity {
//    private static final String CLIENT_ID = "AUNVczoF_1Q2_0qRIs4huCIccpH5yfwS1vLaVTUbdx2nsG69PY9PJorjd_-1jYfeV1agK45amKovPD_7";
//    PaymentButtonContainer paymentButtonContainer;
//    @Override
//    protected void onCreate (Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_check_out);
//        paymentButtonContainer = findViewById(R.id.payment_button_container);
//        PayPalCheckout.setConfig(new CheckoutConfig(
//                getApplication(),
//                CLIENT_ID,
//                Environment.SANDBOX,
//                "com.android.application://paypalpay",
//                CurrencyCode.USD,
//                UserAction.PAY_NOW
//        ));
//        paymentButtonContainer.setup(
//
//                new CreateOrder() {
//                    @Override
//                    public void create(@NotNull CreateOrderActions createOrderActions) {
//                        ArrayList<PurchaseUnit> purchaseUnits = new ArrayList<>();
//                        purchaseUnits.add(
//                                new PurchaseUnit.Builder()
//                                        .amount(
//                                                new Amount.Builder()
//                                                        .currencyCode(CurrencyCode.USD)
//                                                        .value("10.00")
//                                                        .build()
//                                        )
//                                        .build()
//                        );
//                        Order order = new Order(
//                                OrderIntent.CAPTURE,
//                                new AppContext.Builder()
//                                        .userAction(UserAction.PAY_NOW)
//                                        .build(),
//                                purchaseUnits
//                        );
//                        createOrderActions.create(order, (CreateOrderActions.OnOrderCreated) null);
//                    }
//                },
//                new OnApprove() {
//                    @Override
//                    public void onApprove(@NotNull Approval approval) {
//                        approval.getOrderActions().capture(new OnCaptureComplete() {
//                            @Override
//                            public void onCaptureComplete(@NotNull CaptureOrderResult result) {
//                                Log.i("CaptureOrder", String.format("CaptureOrderResult: %s", result));
//                            }
//                        });
//                    }
//                },
//                new OnError() {
//                    @Override
//                    public void onError(@NotNull ErrorInfo errorInfo) {
//                        Log.d("OnError", String.format("Error: %s", errorInfo));
//                    }
//                }
//
//        );
//    }
//
////    public void processPayment(){
////        Intent paymentIntent = new Intent(this,PaymentActivity)
////    }
//}
