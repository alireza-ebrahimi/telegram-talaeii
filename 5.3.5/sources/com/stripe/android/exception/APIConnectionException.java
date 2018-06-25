package com.stripe.android.exception;

public class APIConnectionException extends StripeException {
    public APIConnectionException(String message) {
        super(message, null, Integer.valueOf(0));
    }

    public APIConnectionException(String message, Throwable e) {
        super(message, null, Integer.valueOf(0), e);
    }
}
