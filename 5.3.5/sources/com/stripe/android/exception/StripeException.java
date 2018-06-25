package com.stripe.android.exception;

public abstract class StripeException extends Exception {
    protected static final long serialVersionUID = 1;
    private String requestId;
    private Integer statusCode;

    public StripeException(String message, String requestId, Integer statusCode) {
        super(message, null);
        this.requestId = requestId;
        this.statusCode = statusCode;
    }

    public StripeException(String message, String requestId, Integer statusCode, Throwable e) {
        super(message, e);
        this.statusCode = statusCode;
        this.requestId = requestId;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public Integer getStatusCode() {
        return this.statusCode;
    }

    public String toString() {
        String reqIdStr = "";
        if (this.requestId != null) {
            reqIdStr = "; request-id: " + this.requestId;
        }
        return super.toString() + reqIdStr;
    }
}
