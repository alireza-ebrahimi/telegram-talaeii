package com.persianswitch.sdk.payment.webservice;

import android.content.Context;
import com.persianswitch.sdk.base.Config;
import com.persianswitch.sdk.base.webservice.IWebServiceCallback;
import com.persianswitch.sdk.base.webservice.WebService;
import com.persianswitch.sdk.base.webservice.WebServiceCallback;
import com.persianswitch.sdk.base.webservice.data.WSResponse;
import com.persianswitch.sdk.payment.database.SDKDatabase;

public abstract class SDKWebServiceCallback<T extends WSResponse> extends WebServiceCallback<T> {
    /* renamed from: a */
    public boolean mo3279a(Context context, Config config, T t, WebService webService, IWebServiceCallback<T> iWebServiceCallback) {
        boolean a = super.mo3279a(context, config, t, webService, iWebServiceCallback);
        if (this.a) {
            new SDKDatabase(context).m11060a();
        }
        return a;
    }
}
