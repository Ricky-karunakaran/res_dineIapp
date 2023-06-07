//package com.utils;
//
//import com.stripe.Stripe;
//import com.stripe.exception.StripeException;
//import com.stripe.model.PaymentIntent;
//import com.stripe.param.PaymentIntentCreateParams;
//
//public class OnlinePaymentService {
//        public PaymentIntent getPaymentIntent(){
//            Stripe.apiKey = "sk_test_51NFBp8CbDYea54mdoBPPH9hWbR8J2hv5LP1vn7CAqv0pgiRF1LFaY4qi2s6xarrzg6cPpq2BgBaUS6QJgqlnKnzg001CyxEN6a";
//
//            PaymentIntentCreateParams params =
//                    PaymentIntentCreateParams.builder()
//                            .addPaymentMethodType("fpx")
//                            .setAmount(1099L)
//                            .setCurrency("myr")
//                            .build();
//
//            try {
//                PaymentIntent paymentIntent = PaymentIntent.create(params);
//                return paymentIntent;
//            } catch (StripeException e) {
//                e.printStackTrace();
//            }
//                return null;
//        }
//
//}
