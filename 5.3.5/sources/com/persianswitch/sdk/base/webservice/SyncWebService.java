package com.persianswitch.sdk.base.webservice;

import android.content.Context;
import com.persianswitch.sdk.base.Config;
import com.persianswitch.sdk.base.webservice.WebService.WSStatus;
import com.persianswitch.sdk.base.webservice.data.WSRequest;
import com.persianswitch.sdk.base.webservice.data.WSResponse;
import com.persianswitch.sdk.payment.SDKConfig;
import com.persianswitch.sdk.payment.model.TransactionStatus;
import java.util.concurrent.CountDownLatch;

public final class SyncWebService extends WebService {
    private final CountDownLatch mLatch = new CountDownLatch(1);

    private SyncWebService(WSRequest request) {
        super(request);
    }

    public static SyncWebService create(WSRequest request) {
        return new SyncWebService(request);
    }

    public <T extends WSResponse> ResultPack syncLaunch(Context context, IWebServiceCallback<T> delegateCallback) {
        return syncLaunch(context, new SDKConfig(), delegateCallback);
    }

    public <T extends WSResponse> ResultPack syncLaunch(Context context, Config config, IWebServiceCallback<T> delegateCallback) {
        return syncLaunch(context, 0, config, delegateCallback);
    }

    public <T extends WSResponse> ResultPack syncLaunch(Context context, long waitTimeMillis, Config config, final IWebServiceCallback<T> delegateCallback) {
        final ResultPack<T> resultPack = new ResultPack();
        buildWorker(context, config, waitTimeMillis, new IWebServiceCallback<T>() {
            public void onPreExecute() {
                delegateCallback.onPreExecute();
            }

            public void onPreProcess() {
                delegateCallback.onPreProcess();
            }

            public boolean handleResponse(Context context, Config config, T response, WebService webService, IWebServiceCallback<T> callback) {
                return delegateCallback.handleResponse(context, config, response, webService, callback);
            }

            public void onSuccessful(String message, T result) {
                delegateCallback.onSuccessful(message, result);
                resultPack.setStatus(TransactionStatus.SUCCESS);
                resultPack.setResponse(result);
                SyncWebService.this.mLatch.countDown();
            }

            public void onError(WSStatus error, String errorMessage, T response) {
                delegateCallback.onError(error, errorMessage, response);
                resultPack.setStatus(TransactionStatus.isUnknown(error, response) ? TransactionStatus.UNKNOWN : TransactionStatus.FAIL);
                resultPack.setErrorType(error);
                resultPack.setErrorMessage(errorMessage);
                resultPack.setResponse(response);
                SyncWebService.this.mLatch.countDown();
            }
        }).executeOnCurrentThread();
        try {
            this.mLatch.await();
        } catch (InterruptedException e) {
        }
        return resultPack;
    }
}
