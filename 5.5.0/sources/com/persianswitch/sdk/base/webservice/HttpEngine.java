package com.persianswitch.sdk.base.webservice;

import android.content.Context;
import com.persianswitch.p122a.C2222t;
import com.persianswitch.p122a.C2225u;
import com.persianswitch.p122a.C2225u.C2224a;
import com.persianswitch.p122a.C2231x.C2230a;
import com.persianswitch.p122a.p123a.p124d.C2175d;
import com.persianswitch.sdk.base.Config;
import com.persianswitch.sdk.base.log.SDKLog;
import com.persianswitch.sdk.base.webservice.trust.TrustManagerBuilder;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpEngine {
    /* renamed from: a */
    public static final C2222t f7141a = C2222t.m10083a("text/plain; charset=utf-8");
    /* renamed from: b */
    private static HttpEngine f7142b;
    /* renamed from: c */
    private final C2225u f7143c;
    /* renamed from: d */
    private final C2225u f7144d;

    private HttpEngine(C2225u c2225u, C2225u c2225u2) {
        this.f7143c = c2225u;
        this.f7144d = c2225u2;
    }

    /* renamed from: a */
    private static C2225u m10819a(X509TrustManager x509TrustManager) {
        return new C2224a().m10094a(15, TimeUnit.SECONDS).m10099b(60, TimeUnit.SECONDS).m10100c(60, TimeUnit.SECONDS).m10097a(false).m10096a(m10821a((TrustManager) x509TrustManager), x509TrustManager).m10095a(new APMBHostNameVerifier(C2175d.f6613a)).m10098a();
    }

    /* renamed from: a */
    public static HttpEngine m10820a(Context context, Config config) {
        if (f7142b == null) {
            f7142b = new HttpEngine(m10819a(m10823b(context, config)), m10822b(m10823b(context, config)));
        }
        return f7142b;
    }

    /* renamed from: a */
    private static SSLSocketFactory m10821a(TrustManager trustManager) {
        SSLSocketFactory sSLSocketFactory = null;
        try {
            SSLContext instance = SSLContext.getInstance("TLS");
            instance.init(null, new TrustManager[]{trustManager}, null);
            sSLSocketFactory = instance.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sSLSocketFactory;
    }

    /* renamed from: b */
    private static C2225u m10822b(X509TrustManager x509TrustManager) {
        return new C2224a().m10094a(15, TimeUnit.SECONDS).m10099b(60, TimeUnit.SECONDS).m10100c(60, TimeUnit.SECONDS).m10097a(true).m10096a(m10821a((TrustManager) x509TrustManager), x509TrustManager).m10095a(new APMBHostNameVerifier(C2175d.f6613a)).m10098a();
    }

    /* renamed from: b */
    private static X509TrustManager m10823b(Context context, Config config) {
        TrustManagerBuilder trustManagerBuilder = new TrustManagerBuilder(context);
        try {
            trustManagerBuilder.m10977a("cert/root.cer").m10981b().m10977a("cert/inter.cer").m10981b().m10977a("cert/asanNet_ca.cer").m10981b().m10977a("cert/apms_ssl_test_ca.cer").m10982c();
        } catch (Throwable e) {
            SDKLog.m10659b("HttpEngine", "exception in build custom trust manager.", e, new Object[0]);
        }
        return (X509TrustManager) trustManagerBuilder.m10980a();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    /* renamed from: a */
    public com.persianswitch.sdk.base.webservice.HttpResult m10824a(android.content.Context r17, java.lang.String r18, java.lang.String r19, boolean r20, byte[] r21) {
        /*
        r16 = this;
        r3 = new com.persianswitch.sdk.base.webservice.HttpResult;
        r3.<init>();
        r4 = "";
        r2 = com.persianswitch.sdk.base.security.SecurityManager.m10728a(r17);	 Catch:{ Exception -> 0x007d }
        r0 = r19;
        r1 = r21;
        r2 = r2.m10730a(r0, r1);	 Catch:{ Exception -> 0x007d }
        r4 = r2;
    L_0x0015:
        if (r20 == 0) goto L_0x008b;
    L_0x0017:
        r0 = r16;
        r2 = r0.f7144d;
    L_0x001b:
        r5 = f7141a;
        r4 = com.persianswitch.sdk.base.utils.strings.StringUtils.m10800a(r4);
        r4 = com.persianswitch.p122a.C2232y.m10165a(r5, r4);
        r5 = new com.persianswitch.a.x$a;
        r5.<init>();
        r0 = r18;
        r5 = r5.m10147a(r0);
        r4 = r5.m10146a(r4);
        r10 = r4.m10150a();
        r7 = 0;
        r6 = 3;
        r4 = 30000; // 0x7530 float:4.2039E-41 double:1.4822E-319;
        r14 = r4;
        r5 = r6;
        r4 = r7;
        r6 = r14;
    L_0x0040:
        if (r5 <= 0) goto L_0x0060;
    L_0x0042:
        r8 = 0;
        r8 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1));
        if (r8 <= 0) goto L_0x0060;
    L_0x0048:
        r8 = r5 + -1;
        r12 = java.lang.System.currentTimeMillis();
        r5 = r2.m10106a(r10);	 Catch:{ ConnectException -> 0x0090, UnknownHostException -> 0x0108, IOException -> 0x00aa }
        r4 = r5.mo3168a();	 Catch:{ ConnectException -> 0x0090, UnknownHostException -> 0x010a, IOException -> 0x00aa }
        r5 = 0;
        r3.m10828a(r5);	 Catch:{ ConnectException -> 0x0090, UnknownHostException -> 0x010c, IOException -> 0x00aa }
        r8 = java.lang.System.currentTimeMillis();
        r8 = r8 - r12;
        r6 = r6 - r8;
    L_0x0060:
        if (r4 == 0) goto L_0x00e0;
    L_0x0062:
        r2 = r4.m10217b();
        r3.m10827a(r2);
        r2 = r4.m10218c();
        if (r2 != 0) goto L_0x00c5;
    L_0x006f:
        r2 = new com.persianswitch.sdk.base.webservice.exception.WSHttpStatusException;
        r4 = r3.m10830b();
        r2.<init>(r4);
        r3.m10828a(r2);
        r2 = r3;
    L_0x007c:
        return r2;
    L_0x007d:
        r2 = move-exception;
        r5 = "HttpEngine";
        r6 = "error in encrypt request body";
        r7 = 0;
        r7 = new java.lang.Object[r7];
        com.persianswitch.sdk.base.log.SDKLog.m10659b(r5, r6, r2, r7);
        goto L_0x0015;
    L_0x008b:
        r0 = r16;
        r2 = r0.f7143c;
        goto L_0x001b;
    L_0x0090:
        r5 = move-exception;
    L_0x0091:
        r9 = r4;
        r4 = new com.persianswitch.sdk.base.webservice.exception.WSConnectTimeoutException;	 Catch:{ all -> 0x00bc }
        r4.<init>();	 Catch:{ all -> 0x00bc }
        r3.m10828a(r4);	 Catch:{ all -> 0x00bc }
        r4 = 5000; // 0x1388 float:7.006E-42 double:2.4703E-320;
        java.lang.Thread.sleep(r4);	 Catch:{ InterruptedException -> 0x00fd }
    L_0x009f:
        r4 = java.lang.System.currentTimeMillis();
        r4 = r4 - r12;
        r4 = r6 - r4;
        r6 = r4;
        r5 = r8;
        r4 = r9;
        goto L_0x0040;
    L_0x00aa:
        r2 = move-exception;
        r2 = new com.persianswitch.sdk.base.webservice.exception.WSSocketTimeoutException;	 Catch:{ all -> 0x00bc }
        r2.<init>();	 Catch:{ all -> 0x00bc }
        r3.m10828a(r2);	 Catch:{ all -> 0x00bc }
        r4 = java.lang.System.currentTimeMillis();
        r4 = r4 - r12;
        r4 = r6 - r4;
        r2 = r3;
        goto L_0x007c;
    L_0x00bc:
        r2 = move-exception;
        r4 = java.lang.System.currentTimeMillis();
        r4 = r4 - r12;
        r4 = r6 - r4;
        throw r2;
    L_0x00c5:
        r2 = 0;
        r2 = r4.m10221f();	 Catch:{ Exception -> 0x00e2, all -> 0x00f3 }
        r4 = r2.m9701f();	 Catch:{ Exception -> 0x00e2 }
        r5 = com.persianswitch.sdk.base.security.SecurityManager.m10728a(r17);	 Catch:{ Exception -> 0x00e2 }
        r0 = r21;
        r4 = r5.m10732b(r4, r0);	 Catch:{ Exception -> 0x00e2 }
        r3.m10829a(r4);	 Catch:{ Exception -> 0x00e2 }
        if (r2 == 0) goto L_0x00e0;
    L_0x00dd:
        r2.close();	 Catch:{ Exception -> 0x00ff }
    L_0x00e0:
        r2 = r3;
        goto L_0x007c;
    L_0x00e2:
        r4 = move-exception;
        r4 = new com.persianswitch.sdk.base.webservice.exception.WSParseResponseException;	 Catch:{ all -> 0x0103 }
        r4.<init>();	 Catch:{ all -> 0x0103 }
        r3.m10828a(r4);	 Catch:{ all -> 0x0103 }
        if (r2 == 0) goto L_0x00e0;
    L_0x00ed:
        r2.close();	 Catch:{ Exception -> 0x00f1 }
        goto L_0x00e0;
    L_0x00f1:
        r2 = move-exception;
        goto L_0x00e0;
    L_0x00f3:
        r3 = move-exception;
        r14 = r3;
        r3 = r2;
        r2 = r14;
    L_0x00f7:
        if (r3 == 0) goto L_0x00fc;
    L_0x00f9:
        r3.close();	 Catch:{ Exception -> 0x0101 }
    L_0x00fc:
        throw r2;
    L_0x00fd:
        r4 = move-exception;
        goto L_0x009f;
    L_0x00ff:
        r2 = move-exception;
        goto L_0x00e0;
    L_0x0101:
        r3 = move-exception;
        goto L_0x00fc;
    L_0x0103:
        r3 = move-exception;
        r14 = r3;
        r3 = r2;
        r2 = r14;
        goto L_0x00f7;
    L_0x0108:
        r5 = move-exception;
        goto L_0x0091;
    L_0x010a:
        r5 = move-exception;
        goto L_0x0091;
    L_0x010c:
        r5 = move-exception;
        goto L_0x0091;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.persianswitch.sdk.base.webservice.HttpEngine.a(android.content.Context, java.lang.String, java.lang.String, boolean, byte[]):com.persianswitch.sdk.base.webservice.HttpResult");
    }

    /* renamed from: a */
    public InputStream m10825a(String str) {
        try {
            return this.f7144d.m10106a(new C2230a().m10147a(str).m10150a()).mo3168a().m10221f().m9698c();
        } catch (Exception e) {
            return null;
        }
    }
}
