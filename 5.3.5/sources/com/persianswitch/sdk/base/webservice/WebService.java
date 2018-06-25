package com.persianswitch.sdk.base.webservice;

import android.content.Context;
import com.persianswitch.sdk.C0770R;
import com.persianswitch.sdk.base.BaseSetting;
import com.persianswitch.sdk.base.Config;
import com.persianswitch.sdk.base.log.SDKLog;
import com.persianswitch.sdk.base.manager.RequestTimeManager;
import com.persianswitch.sdk.base.security.SecurityManager;
import com.persianswitch.sdk.base.utils.strings.StringUtils;
import com.persianswitch.sdk.base.webservice.WSWorker.Builder;
import com.persianswitch.sdk.base.webservice.data.WSRequest;
import com.persianswitch.sdk.base.webservice.data.WSResponse;
import com.persianswitch.sdk.base.webservice.data.WSTranRequest;
import com.persianswitch.sdk.base.webservice.data.WSTranResponse;
import com.persianswitch.sdk.base.webservice.exception.WSConnectTimeoutException;
import com.persianswitch.sdk.base.webservice.exception.WSHttpStatusException;
import com.persianswitch.sdk.base.webservice.exception.WSParseResponseException;
import com.persianswitch.sdk.base.webservice.exception.WSRequestEncryptionException;
import com.persianswitch.sdk.base.webservice.exception.WSSSLConfigurationException;
import com.persianswitch.sdk.base.webservice.exception.WSSocketTimeoutException;
import com.persianswitch.sdk.payment.SDKConfig;

public class WebService {
    private static final String TAG = "WebService";
    private WSRequest mRequest;

    public enum WSStatus {
        CONNECT_ERROR,
        BAD_REQUEST,
        NO_RESPONSE,
        SERVER_INTERNAL_ERROR,
        PARSE_ERROR,
        BUSINESS_ERROR;

        public boolean isUnknown() {
            return this == NO_RESPONSE || this == SERVER_INTERNAL_ERROR || this == PARSE_ERROR;
        }
    }

    WebService(WSRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request can not be null!");
        }
        this.mRequest = request;
    }

    public static WebService create(WSRequest request) {
        return new WebService(request);
    }

    public <T extends WSResponse> void launch(Context context, IWebServiceCallback<T> callback) {
        launch(context, new SDKConfig(), 0, callback);
    }

    public <T extends WSResponse> void launch(Context context, long waitTimeMillis, IWebServiceCallback<T> callback) {
        launch(context, new SDKConfig(), waitTimeMillis, callback);
    }

    public <T extends WSResponse> void launch(Context context, SDKConfig sdkConfig, long waitTimeMillis, IWebServiceCallback<T> callback) {
        buildWorker(context, sdkConfig, waitTimeMillis, callback).executeOnThreadPool();
    }

    <T extends WSResponse> WSWorker buildWorker(final Context context, final Config config, long waitTimeMillis, final IWebServiceCallback<T> callback) {
        this.mRequest.setTransactionId(BaseSetting.getAndIncrementTranId(context));
        this.mRequest.setRequestTime(String.valueOf(new RequestTimeManager().getSyncedTime(context)));
        String rawJsonRequest = this.mRequest.toJson();
        String url = this.mRequest.getUrl(config);
        byte[] aesKey = new byte[0];
        try {
            aesKey = SecurityManager.getInstance(context).generateAesKey();
        } catch (Exception e) {
            SDKLog.m37e(TAG, "error in generate transaction secret key", new Object[0]);
        }
        return new Builder(context, config).url(url).rawRequest(rawJsonRequest).silentRetry(OpCode.getByCode(this.mRequest.getOperationCode()).isSilenceRetry()).aesKey(aesKey).waitTime(waitTimeMillis).listener(new WSWorkerListener() {
            public void onPreExecute() {
                callback.onPreExecute();
            }

            public void onResult(HttpResult result) {
                WebService.this.onProcessResult(context, config, result, callback);
            }
        }).build();
    }

    private <T extends WSResponse> void onProcessResult(Context context, Config config, HttpResult result, IWebServiceCallback<T> callback) {
        callback.onPreProcess();
        if (result.getException() instanceof WSConnectTimeoutException) {
            callback.onError(WSStatus.CONNECT_ERROR, context.getString(C0770R.string.asanpardakht_message_error_connect), null);
        } else if (result.getException() instanceof WSHttpStatusException) {
            WSHttpStatusException httpStatusExp = (WSHttpStatusException) result.getException();
            if (httpStatusExp.getHttpStatusCode() < 500 || httpStatusExp.getHttpStatusCode() >= 600) {
                callback.onError(WSStatus.BAD_REQUEST, context.getString(C0770R.string.asanpardakht_message_error_bad_request), null);
            } else {
                callback.onError(WSStatus.SERVER_INTERNAL_ERROR, context.getString(C0770R.string.asanpardakht_message_error_server_internal_error), null);
            }
        } else if (result.getException() instanceof WSSSLConfigurationException) {
            callback.onError(WSStatus.BAD_REQUEST, context.getString(C0770R.string.asanpardakht_message_error_bad_request), null);
        } else if (result.getException() instanceof WSRequestEncryptionException) {
            callback.onError(WSStatus.BAD_REQUEST, context.getString(C0770R.string.asanpardakht_message_error_bad_request), null);
        } else if (result.getException() instanceof WSParseResponseException) {
            callback.onError(WSStatus.PARSE_ERROR, context.getString(C0770R.string.asanpardakht_message_error_bad_response), null);
        } else if (result.getException() instanceof WSSocketTimeoutException) {
            callback.onError(WSStatus.NO_RESPONSE, context.getString(C0770R.string.asanpardakht_message_error_no_response), null);
        } else if (result.getRawData() != null) {
            try {
                WSResponse response;
                if (this.mRequest instanceof WSTranRequest) {
                    response = WSTranResponse.fromJson(result.getRawData());
                } else {
                    response = WSResponse.fromJson(result.getRawData());
                }
                onProcessWSResponse(context, config, response, callback);
            } catch (Exception e) {
                callback.onError(WSStatus.PARSE_ERROR, context.getString(C0770R.string.asanpardakht_message_error_internal_error), null);
            }
        } else {
            callback.onError(WSStatus.NO_RESPONSE, context.getString(C0770R.string.asanpardakht_message_error_no_response), null);
        }
    }

    private <T extends WSResponse> void onProcessWSResponse(Context context, Config config, T response, IWebServiceCallback<T> callback) {
        if (!callback.handleResponse(context, config, response, this, callback)) {
            if (response.getStatus() == StatusCode.SUCCESS) {
                callback.onSuccessful(StringUtils.toNonNullString(response.getDescription()), response);
                return;
            }
            String errorMessage = response.getDescription();
            if (StringUtils.isEmpty(errorMessage)) {
                errorMessage = StatusCode.getErrorMessage(context, response.getStatusCode());
            }
            callback.onError(WSStatus.BUSINESS_ERROR, errorMessage, response);
        }
    }
}
