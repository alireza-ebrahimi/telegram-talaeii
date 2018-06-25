package com.stripe.android.exception;

public class AuthenticationException extends StripeException {
    public AuthenticationException(String message, String requestId, Integer statusCode) {
        super(message, requestId, statusCode);
    }
}
