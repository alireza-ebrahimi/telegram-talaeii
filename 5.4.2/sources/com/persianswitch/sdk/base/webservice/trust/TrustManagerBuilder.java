package com.persianswitch.sdk.base.webservice.trust;

import android.content.Context;
import java.security.KeyStore;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class TrustManagerBuilder {
    /* renamed from: a */
    private CompositeTrustManager f7302a;
    /* renamed from: b */
    private Context f7303b;
    /* renamed from: c */
    private MemorizingTrustManager f7304c;

    public TrustManagerBuilder() {
        this(null);
    }

    public TrustManagerBuilder(Context context) {
        this.f7302a = CompositeTrustManager.m10967a(new X509TrustManager[0]);
        this.f7303b = null;
        this.f7304c = null;
        this.f7303b = context;
    }

    /* renamed from: d */
    private void m10976d() {
        if (this.f7303b == null) {
            throw new IllegalArgumentException("Must use constructor supplying a Context");
        }
    }

    /* renamed from: a */
    public TrustManagerBuilder m10977a(String str) {
        return m10978a(str, "X.509");
    }

    /* renamed from: a */
    public TrustManagerBuilder m10978a(String str, String str2) {
        m10976d();
        m10979a(TrustManagers.m10983a(this.f7303b.getAssets().open(str), str2));
        return this;
    }

    /* renamed from: a */
    public TrustManagerBuilder m10979a(TrustManager[] trustManagerArr) {
        for (TrustManager trustManager : trustManagerArr) {
            if (trustManager instanceof X509TrustManager) {
                this.f7302a.m10969a((X509TrustManager) trustManager);
            }
        }
        return this;
    }

    /* renamed from: a */
    public TrustManager m10980a() {
        return this.f7302a;
    }

    /* renamed from: b */
    public TrustManagerBuilder m10981b() {
        if (this.f7302a.m10971a()) {
            if (this.f7302a.m10972b() < 2) {
                this.f7302a.m10970a(false);
            } else {
                this.f7302a = CompositeTrustManager.m10968b(this.f7302a);
            }
        }
        return this;
    }

    /* renamed from: c */
    public TrustManagerBuilder m10982c() {
        TrustManagerFactory instance = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        instance.init((KeyStore) null);
        m10979a(instance.getTrustManagers());
        return this;
    }
}
