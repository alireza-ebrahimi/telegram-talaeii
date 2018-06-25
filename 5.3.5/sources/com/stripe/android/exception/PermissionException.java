package com.stripe.android.exception;

public class PermissionException extends AuthenticationException {
    public PermissionException(String message, String requestId, Integer statusCode) {
        super(message, requestId, statusCode);
    }
}
