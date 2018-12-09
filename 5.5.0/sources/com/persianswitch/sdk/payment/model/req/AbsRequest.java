package com.persianswitch.sdk.payment.model.req;

import android.content.Context;
import com.persianswitch.sdk.base.utils.strings.StringUtils;
import com.persianswitch.sdk.base.webservice.IWebServiceDescriptor;
import com.persianswitch.sdk.base.webservice.OpCode;
import com.persianswitch.sdk.base.webservice.SyncWebService;
import com.persianswitch.sdk.base.webservice.WebService;
import com.persianswitch.sdk.base.webservice.data.WSRequest;
import com.persianswitch.sdk.base.webservice.data.WSTranRequest;
import com.persianswitch.sdk.payment.SDKConfig;
import com.persianswitch.sdk.payment.model.HostDataRequestField;
import org.json.JSONObject;

public abstract class AbsRequest implements IRequest {
    /* renamed from: a */
    private final OpCode f7583a;
    /* renamed from: b */
    private String f7584b;
    /* renamed from: c */
    private String f7585c;
    /* renamed from: d */
    private int f7586d;
    /* renamed from: e */
    private String f7587e;
    /* renamed from: f */
    private String f7588f;
    /* renamed from: g */
    private String f7589g;

    AbsRequest(OpCode opCode) {
        this.f7583a = opCode;
    }

    /* renamed from: b */
    public static String m11282b(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e) {
            return TtmlNode.ANONYMOUS_REGION_ID;
        }
    }

    /* renamed from: a */
    public IWebServiceDescriptor mo3304a(final Context context) {
        final String b = m11282b(context);
        return this.f7583a.m10841c() ? new IWebServiceDescriptor(this) {
            /* renamed from: c */
            final /* synthetic */ AbsRequest f7579c;

            /* renamed from: a */
            public /* synthetic */ WSRequest mo3300a() {
                return m11279b();
            }

            /* renamed from: b */
            public WSTranRequest m11279b() {
                WSTranRequest a = WSTranRequest.m10950a(context, new SDKConfig(), this.f7579c.f7583a.m10839a(), 0);
                Long d = StringUtils.m10808d(this.f7579c.f7585c);
                if (d != null) {
                    a.m10954c(d.longValue());
                }
                a.m10921b(this.f7579c.f7586d);
                a.m10918a(new HostDataRequestField(this.f7579c.m11289b(), this.f7579c.m11292c(), this.f7579c.m11286a(), b).m11152a());
                a.m10924b(this.f7579c.mo3301d());
                a.m10919a(this.f7579c.mo3302e());
                return a;
            }
        } : new IWebServiceDescriptor(this) {
            /* renamed from: c */
            final /* synthetic */ AbsRequest f7582c;

            /* renamed from: a */
            public WSRequest mo3300a() {
                WSRequest a = WSRequest.m10900a(context, new SDKConfig(), this.f7582c.f7583a.m10839a());
                a.m10921b(this.f7582c.f7586d);
                a.m10918a(new HostDataRequestField(this.f7582c.m11289b(), this.f7582c.m11292c(), this.f7582c.m11286a(), b).m11152a());
                a.m10924b(this.f7582c.mo3301d());
                a.m10919a(this.f7582c.mo3302e());
                return a;
            }
        };
    }

    /* renamed from: a */
    public String m11286a() {
        return this.f7589g;
    }

    /* renamed from: a */
    public void m11287a(int i) {
        this.f7586d = i;
    }

    /* renamed from: a */
    public void m11288a(String str) {
        this.f7589g = str;
    }

    /* renamed from: b */
    public String m11289b() {
        return this.f7587e;
    }

    /* renamed from: b */
    public void m11290b(String str) {
        this.f7587e = str;
    }

    /* renamed from: c */
    public final WebService m11291c(Context context) {
        return WebService.m10861b(mo3304a(context).mo3300a());
    }

    /* renamed from: c */
    public String m11292c() {
        return this.f7588f;
    }

    /* renamed from: c */
    public void m11293c(String str) {
        this.f7588f = str;
    }

    /* renamed from: d */
    public final SyncWebService m11294d(Context context) {
        return SyncWebService.m10866a(mo3304a(context).mo3300a());
    }

    /* renamed from: d */
    public abstract JSONObject mo3301d();

    /* renamed from: d */
    public void mo3303d(String str) {
        this.f7584b = str;
    }

    @Deprecated
    /* renamed from: e */
    public abstract String[] mo3302e();

    /* renamed from: f */
    public OpCode m11298f() {
        return this.f7583a;
    }

    /* renamed from: g */
    public int m11299g() {
        return this.f7586d;
    }
}
