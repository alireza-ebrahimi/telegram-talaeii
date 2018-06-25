package com.persianswitch.sdk.base.webservice;

import com.persianswitch.sdk.base.webservice.WebService.WSStatus;
import com.persianswitch.sdk.base.webservice.data.WSResponse;
import com.persianswitch.sdk.payment.model.TransactionStatus;

public final class ResultPack<T extends WSResponse> {
    private String mErrorMessage;
    private WSStatus mErrorType;
    private T mResponse;
    private TransactionStatus mStatus;

    public TransactionStatus getStatus() {
        return this.mStatus;
    }

    public T getResponse() {
        return this.mResponse;
    }

    public String getErrorMessage() {
        return this.mErrorMessage;
    }

    public WSStatus getErrorType() {
        return this.mErrorType;
    }

    public ResultPack setStatus(TransactionStatus status) {
        this.mStatus = status;
        return this;
    }

    public ResultPack setResponse(T response) {
        this.mResponse = response;
        return this;
    }

    public ResultPack setErrorMessage(String errorMessage) {
        this.mErrorMessage = errorMessage;
        return this;
    }

    public ResultPack setErrorType(WSStatus errorType) {
        this.mErrorType = errorType;
        return this;
    }
}
