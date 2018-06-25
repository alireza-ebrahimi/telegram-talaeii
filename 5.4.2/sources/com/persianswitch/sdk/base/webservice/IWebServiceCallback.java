package com.persianswitch.sdk.base.webservice;

import android.content.Context;
import com.persianswitch.sdk.base.Config;
import com.persianswitch.sdk.base.webservice.WebService.WSStatus;
import com.persianswitch.sdk.base.webservice.data.WSResponse;

public interface IWebServiceCallback<T extends WSResponse> {
    /* renamed from: a */
    void mo3276a();

    /* renamed from: a */
    void mo3277a(WSStatus wSStatus, String str, T t);

    /* renamed from: a */
    void mo3278a(String str, T t);

    /* renamed from: a */
    boolean mo3279a(Context context, Config config, T t, WebService webService, IWebServiceCallback<T> iWebServiceCallback);

    /* renamed from: b */
    void mo3280b();
}
