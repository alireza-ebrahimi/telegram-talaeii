package com.persianswitch.sdk.base.webservice;

public interface ServiceRetryOnTimeNotSyncStrategy {
    void resetCounter();

    boolean retryOnResponse(HttpResult httpResult);
}
