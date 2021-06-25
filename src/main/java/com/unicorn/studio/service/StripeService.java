package com.unicorn.studio.service;

import com.unicorn.studio.dao.StripeCustomerRepository;
import com.unicorn.studio.dao.StripePaymentIntentRepository;
import com.unicorn.studio.entity.StripeCustomer;
import com.unicorn.studio.entity.StripePaymentIntent;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class StripeService {
    @Value("${stripe_secret_key}")
    private String stripeSecretKey;

    @Autowired
    private StripePaymentIntentRepository stripePaymentIntentRepository;

    @Autowired
    private StripeCustomerRepository customerRepository;

    public String createPayment(String currency, String paymentMethodId, long amount) {
        Stripe.apiKey = stripeSecretKey;
        String customerEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        StripeCustomer customer = customerRepository.findByEmail(customerEmail);
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setCurrency(currency)
                .setAmount(amount)
                .setPaymentMethod(paymentMethodId)
                .addPaymentMethodType("card")
                .setCustomer(customer.getUid())
                .setConfirm(true)
                .setReceiptEmail(customerEmail)
                .setConfirmationMethod(PaymentIntentCreateParams.ConfirmationMethod.AUTOMATIC).build();

        PaymentIntent paymentIntent = createPaymentIntent(params);
        if (paymentIntent.getStatus().equals("requires_action")) {
            stripePaymentIntentRepository.save((StripePaymentIntent) paymentIntent);
            return paymentIntent.getClientSecret();
        } else {
            return null;
        }
    }

    private PaymentIntent createPaymentIntent(PaymentIntentCreateParams params) {
        try {
            return PaymentIntent.create(params);
        } catch (StripeException e) {
            e.printStackTrace();
            return null;
        }
    }
}
