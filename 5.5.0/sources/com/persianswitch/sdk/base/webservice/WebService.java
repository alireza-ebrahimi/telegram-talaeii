package com.persianswitch.sdk.base.webservice;

import android.content.Context;
import com.persianswitch.sdk.C2262R;
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
import org.telegram.ui.ChatActivity;

public class WebService {
    /* renamed from: a */
    private WSRequest f7220a;

    public enum WSStatus {
        CONNECT_ERROR,
        BAD_REQUEST,
        NO_RESPONSE,
        SERVER_INTERNAL_ERROR,
        PARSE_ERROR,
        BUSINESS_ERROR;

        /* renamed from: a */
        public boolean m10894a() {
            return this == NO_RESPONSE || this == SERVER_INTERNAL_ERROR || this == PARSE_ERROR;
        }
    }

    WebService(WSRequest wSRequest) {
        if (wSRequest == null) {
            throw new IllegalArgumentException("request can not be null!");
        }
        this.f7220a = wSRequest;
    }

    /* renamed from: a */
    private <T extends WSResponse> void m10858a(Context context, Config config, HttpResult httpResult, IWebServiceCallback<T> iWebServiceCallback) {
        iWebServiceCallback.mo3280b();
        if (httpResult.m10826a() instanceof WSConnectTimeoutException) {
            iWebServiceCallback.mo3277a(WSStatus.CONNECT_ERROR, context.getString(C2262R.string.asanpardakht_message_error_connect), null);
        } else if (httpResult.m10826a() instanceof WSHttpStatusException) {
            WSHttpStatusException wSHttpStatusException = (WSHttpStatusException) httpResult.m10826a();
            if (wSHttpStatusException.m10966a() < ChatActivity.startAllServices || wSHttpStatusException.m10966a() >= 600) {
                iWebServiceCallback.mo3277a(WSStatus.BAD_REQUEST, context.getString(C2262R.string.asanpardakht_message_error_bad_request), null);
            } else {
                iWebServiceCallback.mo3277a(WSStatus.SERVER_INTERNAL_ERROR, context.getString(C2262R.string.asanpardakht_message_error_server_internal_error), null);
            }
        } else if (httpResult.m10826a() instanceof WSSSLConfigurationException) {
            iWebServiceCallback.mo3277a(WSStatus.BAD_REQUEST, context.getString(C2262R.string.asanpardakht_message_error_bad_request), null);
        } else if (httpResult.m10826a() instanceof WSRequestEncryptionException) {
            iWebServiceCallback.mo3277a(WSStatus.BAD_REQUEST, context.getString(C2262R.string.asanpardakht_message_error_bad_request), null);
        } else if (httpResult.m10826a() instanceof WSParseResponseException) {
            iWebServiceCallback.mo3277a(WSStatus.PARSE_ERROR, context.getString(C2262R.string.asanpardakht_message_error_bad_response), null);
        } else if (httpResult.m10826a() instanceof WSSocketTimeoutException) {
            iWebServiceCallback.mo3277a(WSStatus.NO_RESPONSE, context.getString(C2262R.string.asanpardakht_message_error_no_response), null);
        } else if (httpResult.m10831c() != null) {
            try {
                m10859a(context, config, this.f7220a instanceof WSTranRequest ? WSTranResponse.m10962b(httpResult.m10831c()) : WSResponse.m10938a(httpResult.m10831c()), (IWebServiceCallback) iWebServiceCallback);
            } catch (Exception e) {
                iWebServiceCallback.mo3277a(WSStatus.PARSE_ERROR, context.getString(C2262R.string.asanpardakht_message_error_internal_error), null);
            }
        } else {
            iWebServiceCallback.mo3277a(WSStatus.NO_RESPONSE, context.getString(C2262R.string.asanpardakht_message_error_no_response), null);
        }
    }

    /* renamed from: a */
    private <T extends WSResponse> void m10859a(Context context, Config config, T t, IWebServiceCallback<T> iWebServiceCallback) {
        if (!iWebServiceCallback.mo3279a(context, config, t, this, iWebServiceCallback)) {
            if (t.m10942c() == StatusCode.SUCCESS) {
                iWebServiceCallback.mo3278a(StringUtils.m10800a(t.m10943d()), t);
                return;
            }
            String d = t.m10943d();
            if (StringUtils.m10803a(d)) {
                d = StatusCode.m10850a(context, t.m10941b());
            }
            iWebServiceCallback.mo3277a(WSStatus.BUSINESS_ERROR, d, t);
        }
    }

    /* renamed from: b */
    public static WebService m10861b(WSRequest wSRequest) {
        return new WebService(wSRequest);
    }

    /* renamed from: a */
    <T extends WSResponse> WSWorker m10862a(final Context context, final Config config, long j, final IWebServiceCallback<T> iWebServiceCallback) {
        this.f7220a.m10916a(BaseSetting.m10458b(context));
        this.f7220a.m10930e(String.valueOf(new RequestTimeManager().m10679a(context)));
        String h = this.f7220a.mo3283h();
        String a = this.f7220a.m10913a(config);
        byte[] bArr = new byte[0];
        try {
            bArr = SecurityManager.m10728a(context).m10731a();
        } catch (Exception e) {
            SDKLog.m10661c("WebService", "error in generate transaction secret key", new Object[0]);
        }
        return new Builder(context, config).m10881a(a).m10885b(h).m10882a(OpCode.m10838a(this.f7220a.m10925c()).m10840b()).m10883a(bArr).m10879a(j).m10880a(new WSWorkerListener(this) {
            /* renamed from: d */
            final /* synthetic */ WebService f7241d;

            /* renamed from: a */
            public void mo3281a() {
                iWebServiceCallback.mo3276a();
            }

            /* renamed from: a */
            public void mo3282a(HttpResult httpResult) {
                this.f7241d.m10858a(context, config, httpResult, iWebServiceCallback);
            }
        }).m10884a();
    }

    /* renamed from: a */
    public <T extends WSResponse> void m10863a(Context context, long j, IWebServiceCallback<T> iWebServiceCallback) {
        m10864a(context, new SDKConfig(), j, (IWebServiceCallback) iWebServiceCallback);
    }

    /* renamed from: a */
    public <T extends WSResponse> void m10864a(Context context, SDKConfig sDKConfig, long j, IWebServiceCallback<T> iWebServiceCallback) {
        m10862a(context, (Config) sDKConfig, j, (IWebServiceCallback) iWebServiceCallback).m10889a();
    }

    /* renamed from: b */
    public <T extends WSResponse> void m10865b(Context context, IWebServiceCallback<T> iWebServiceCallback) {
        m10864a(context, new SDKConfig(), 0, (IWebServiceCallback) iWebServiceCallback);
    }
}
