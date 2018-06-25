package com.persianswitch.sdk.payment.webservice;

import android.content.Context;
import com.persianswitch.sdk.base.Config;
import com.persianswitch.sdk.base.webservice.IWebServiceCallback;
import com.persianswitch.sdk.base.webservice.WebService;
import com.persianswitch.sdk.base.webservice.WebServiceCallback;
import com.persianswitch.sdk.base.webservice.data.WSResponse;
import com.persianswitch.sdk.payment.database.SDKDatabase;

public abstract class SDKWebServiceCallback<T extends WSResponse> extends WebServiceCallback<T> {
    public boolean handleResponse(Context context, Config config, T response, WebService ws, IWebServiceCallback<T> callback) {
        boolean superReturn = super.handleResponse(context, config, response, ws, callback);
        if (this.mWipeData) {
            new SDKDatabase(context).clearAllData();
        }
        return superReturn;
    }
}
