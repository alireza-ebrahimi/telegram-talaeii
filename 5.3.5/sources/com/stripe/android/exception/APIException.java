package com.stripe.android.exception;

public class APIException extends StripeException {
    public APIException(String message, String requestId, Integer statusCode, Throwable e) {
        super(message, requestId, statusCode, e);
    }
}
