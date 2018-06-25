package com.persianswitch.sdk.base.webservice;

import android.content.Context;
import com.persianswitch.sdk.base.BaseSetting;
import com.persianswitch.sdk.base.Config;
import com.persianswitch.sdk.base.db.BaseDatabase;
import com.persianswitch.sdk.base.manager.RequestTimeManager;
import com.persianswitch.sdk.base.utils.strings.StringUtils;
import com.persianswitch.sdk.base.webservice.data.WSResponse;

public abstract class WebServiceCallback<T extends WSResponse> implements IWebServiceCallback<T> {
    /* renamed from: a */
    protected boolean f7249a;
    /* renamed from: b */
    private boolean f7250b;

    /* renamed from: a */
    private void m10895a(Context context) {
        this.f7249a = true;
        new BaseDatabase(context).m10491a();
    }

    /* renamed from: a */
    public boolean mo3279a(Context context, Config config, T t, WebService webService, IWebServiceCallback<T> iWebServiceCallback) {
        if (t != null) {
            if (!StringUtils.m10803a(t.m10948i())) {
                new RequestTimeManager().m10680a(context, StringUtils.m10808d(t.m10948i().split(";")[0]));
            }
            if (t.m10939a() == 1 && (t.m10939a() == 2 || t.m10939a() == 1)) {
                if (t.m10942c() == StatusCode.APP_NOT_REGISTERED) {
                    m10895a(context);
                } else if (t.m10942c() == StatusCode.NEED_APP_RE_VERIFICATION) {
                    BaseSetting.m10456a(context, true);
                } else if (t.m10942c() == StatusCode.SYNC_TIME_BY_SERVER_FAILED && !this.f7250b) {
                    this.f7250b = true;
                    if (webService instanceof SyncWebService) {
                        ((SyncWebService) webService).m10869a(context, config, iWebServiceCallback);
                        return true;
                    }
                    webService.m10865b(context, iWebServiceCallback);
                    return true;
                }
            } else if (t.m10939a() == 10) {
                m10895a(context);
            }
        }
        return false;
    }

    /* renamed from: b */
    public void mo3280b() {
    }
}
