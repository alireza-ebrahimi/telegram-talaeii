package com.persianswitch.sdk.base.webservice.exception;

public class WSHttpStatusException extends WSCallException {
    protected int httpStatusCode;

    public WSHttpStatusException(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public int getHttpStatusCode() {
        return this.httpStatusCode;
    }
}
