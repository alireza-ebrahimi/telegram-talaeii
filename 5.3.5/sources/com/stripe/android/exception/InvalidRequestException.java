package com.stripe.android.exception;

public class InvalidRequestException extends StripeException {
    private final String param;

    public InvalidRequestException(String message, String param, String requestId, Integer statusCode, Throwable e) {
        super(message, requestId, statusCode, e);
        this.param = param;
    }

    public String getParam() {
        return this.param;
    }
}
