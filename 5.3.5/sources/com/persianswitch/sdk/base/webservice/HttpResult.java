package com.persianswitch.sdk.base.webservice;

import com.persianswitch.sdk.base.webservice.exception.WSCallException;

public class HttpResult {
    private WSCallException exception;
    private int httpStatusCode;
    private String mRawData;

    public WSCallException getException() {
        return this.exception;
    }

    public void setException(WSCallException exception) {
        this.exception = exception;
    }

    public int getHttpStatusCode() {
        return this.httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public String getRawData() {
        return this.mRawData;
    }

    public void setRawData(String rawData) {
        this.mRawData = rawData;
    }
}
