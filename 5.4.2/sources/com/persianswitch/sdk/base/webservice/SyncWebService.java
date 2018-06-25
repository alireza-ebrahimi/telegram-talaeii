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
    /* renamed from: a */
    private final CountDownLatch f7221a = new CountDownLatch(1);

    private SyncWebService(WSRequest wSRequest) {
        super(wSRequest);
    }

    /* renamed from: a */
    public static SyncWebService m10866a(WSRequest wSRequest) {
        return new SyncWebService(wSRequest);
    }

    /* renamed from: a */
    public <T extends WSResponse> ResultPack m10868a(Context context, long j, Config config, final IWebServiceCallback<T> iWebServiceCallback) {
        final ResultPack resultPack = new ResultPack();
        m10862a(context, config, j, new IWebServiceCallback<T>(this) {
            /* renamed from: c */
            final /* synthetic */ SyncWebService f7219c;

            /* renamed from: a */
            public void mo3276a() {
                iWebServiceCallback.mo3276a();
            }

            /* renamed from: a */
            public void mo3277a(WSStatus wSStatus, String str, T t) {
                iWebServiceCallback.mo3277a(wSStatus, str, t);
                resultPack.m10844a(TransactionStatus.m11229a(wSStatus, t) ? TransactionStatus.UNKNOWN : TransactionStatus.FAIL);
                resultPack.m10842a(wSStatus);
                resultPack.m10845a(str);
                resultPack.m10843a((WSResponse) t);
                this.f7219c.f7221a.countDown();
            }

            /* renamed from: a */
            public void mo3278a(String str, T t) {
                iWebServiceCallback.mo3278a(str, t);
                resultPack.m10844a(TransactionStatus.SUCCESS);
                resultPack.m10843a((WSResponse) t);
                this.f7219c.f7221a.countDown();
            }

            /* renamed from: a */
            public boolean mo3279a(Context context, Config config, T t, WebService webService, IWebServiceCallback<T> iWebServiceCallback) {
                return iWebServiceCallback.mo3279a(context, config, t, webService, iWebServiceCallback);
            }

            /* renamed from: b */
            public void mo3280b() {
                iWebServiceCallback.mo3280b();
            }
        }).m10891b();
        try {
            this.f7221a.await();
        } catch (InterruptedException e) {
        }
        return resultPack;
    }

    /* renamed from: a */
    public <T extends WSResponse> ResultPack m10869a(Context context, Config config, IWebServiceCallback<T> iWebServiceCallback) {
        return m10868a(context, 0, config, iWebServiceCallback);
    }

    /* renamed from: a */
    public <T extends WSResponse> ResultPack m10870a(Context context, IWebServiceCallback<T> iWebServiceCallback) {
        return m10869a(context, new SDKConfig(), iWebServiceCallback);
    }
}
