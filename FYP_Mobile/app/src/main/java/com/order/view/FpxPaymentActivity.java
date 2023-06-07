//package com.order.view;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.core.content.ContextCompat;
//
//import com.example.fyp_mobile.R;
//import com.stripe.android.PaymentConfiguration;
//import com.stripe.android.model.PaymentMethod;
//import com.stripe.android.view.AddPaymentMethodActivityStarter;
//import com.stripe.android.view.FpxBank;
//import java.util.Objects;
//
//
//public class FpxPaymentActivity extends Activity {
//    private String PUBLISHABLE_KEY="pk_test_51NFBp8CbDYea54mdxYLFmByfFGgS3ecEqgwAucRAYI152QbxVBfdBKVVjgvKtfwC04gvxWJNQpMMGXNz2PiczRd700BYYNF1WQ";
//    private FpxPaymentActivity viewBinding;
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        viewBinding = this;
//
//        setContentView(R.layout.bacnk_selector_payment_activity);
//        setTitle("Payment");
//
//        PaymentConfiguration.init(this, this.PUBLISHABLE_KEY);
//
//        findViewById(R.id.select_payment_method_button)
//                .setOnClickListener(view -> launchAddPaymentMethod());
//    }
//
//    private void launchAddPaymentMethod() {
//        new AddPaymentMethodActivityStarter(this)
//                .startForResult(new AddPaymentMethodActivityStarter.Args.Builder()
//                        .setPaymentMethodType(PaymentMethod.Type.Fpx)
//                        .build()
//                );
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        final AddPaymentMethodActivityStarter.Result result =
//                AddPaymentMethodActivityStarter.Result.fromIntent(data);
//        if (result instanceof AddPaymentMethodActivityStarter.Result.Success) {
//            final AddPaymentMethodActivityStarter.Result.Success successResult =
//                    (AddPaymentMethodActivityStarter.Result.Success) result;
//            onPaymentMethodResult(successResult.getPaymentMethod());
//        }
//    }
//
//    private void onPaymentMethodResult(@NonNull PaymentMethod paymentMethod) {
//        final String fpxBankCode = Objects.requireNonNull(paymentMethod.fpx).bank;
//
//        final String resultMessage = "Created Payment Method\n" +
//                "\nType: " + paymentMethod.type +
//                "\nId: " + paymentMethod.id +
//                "\nBank code: " + fpxBankCode;
//        TextView paymentMethodResult = findViewById(R.id.payment_method_result);
//        paymentMethodResult.setText(resultMessage);
//
//        final FpxBank fpxBank = FpxBank.get(fpxBankCode);
//        TextView bankInfo = findViewById(R.id.bank_info);
//        if (fpxBank != null) {
//
//            bankInfo.setVisibility(View.VISIBLE);
//            bankInfo.setCompoundDrawablesRelativeWithIntrinsicBounds(
//                    ContextCompat.getDrawable(this, fpxBank.getBrandIconResId()),
//                    null,
//                    null,
//                    null
//            );
//            bankInfo.setText(fpxBank.getDisplayName());
//        } else {
//                bankInfo.setVisibility(View.GONE);
//        }
//    }
//}