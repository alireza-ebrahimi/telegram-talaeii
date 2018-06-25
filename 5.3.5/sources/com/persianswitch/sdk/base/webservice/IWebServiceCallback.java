package com.persianswitch.sdk.base.webservice;

import android.content.Context;
import com.persianswitch.sdk.base.Config;
import com.persianswitch.sdk.base.webservice.WebService.WSStatus;
import com.persianswitch.sdk.base.webservice.data.WSResponse;

public interface IWebServiceCallback<T extends WSResponse> {
    boolean handleResponse(Context context, Config config, T t, WebService webService, IWebServiceCallback<T> iWebServiceCallback);

    void onError(WSStatus wSStatus, String str, T t);

    void onPreExecute();

    void onPreProcess();

    void onSuccessful(String str, T t);
}
