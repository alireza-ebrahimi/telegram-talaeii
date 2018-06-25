package p033b.p034a.p035a.p036a.p037a.p039b;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import p033b.p034a.p035a.p036a.C1230c;
import p033b.p034a.p035a.p036a.p037a.p044f.C1194c;
import p033b.p034a.p035a.p036a.p037a.p044f.C1195d;

/* renamed from: b.a.a.a.a.b.c */
class C1100c {
    /* renamed from: a */
    private final Context f3246a;
    /* renamed from: b */
    private final C1194c f3247b;

    public C1100c(Context context) {
        this.f3246a = context.getApplicationContext();
        this.f3247b = new C1195d(context, "TwitterAdvertisingInfoPreferences");
    }

    /* renamed from: a */
    private void m5966a(final C1097b c1097b) {
        new Thread(new C1098h(this) {
            /* renamed from: b */
            final /* synthetic */ C1100c f3245b;

            /* renamed from: a */
            public void mo1020a() {
                C1097b a = this.f3245b.m5970e();
                if (!c1097b.equals(a)) {
                    C1230c.m6414h().mo1062a("Fabric", "Asychronously getting Advertising Info and storing it to preferences");
                    this.f3245b.m5968b(a);
                }
            }
        }).start();
    }

    @SuppressLint({"CommitPrefEdits"})
    /* renamed from: b */
    private void m5968b(C1097b c1097b) {
        if (m5969c(c1097b)) {
            this.f3247b.mo1052a(this.f3247b.mo1053b().putString("advertising_id", c1097b.f3242a).putBoolean("limit_ad_tracking_enabled", c1097b.f3243b));
        } else {
            this.f3247b.mo1052a(this.f3247b.mo1053b().remove("advertising_id").remove("limit_ad_tracking_enabled"));
        }
    }

    /* renamed from: c */
    private boolean m5969c(C1097b c1097b) {
        return (c1097b == null || TextUtils.isEmpty(c1097b.f3242a)) ? false : true;
    }

    /* renamed from: e */
    private C1097b m5970e() {
        C1097b a = m5973c().mo1021a();
        if (m5969c(a)) {
            C1230c.m6414h().mo1062a("Fabric", "Using AdvertisingInfo from Reflection Provider");
        } else {
            a = m5974d().mo1021a();
            if (m5969c(a)) {
                C1230c.m6414h().mo1062a("Fabric", "Using AdvertisingInfo from Service Provider");
            } else {
                C1230c.m6414h().mo1062a("Fabric", "AdvertisingInfo not present");
            }
        }
        return a;
    }

    /* renamed from: a */
    public C1097b m5971a() {
        C1097b b = m5972b();
        if (m5969c(b)) {
            C1230c.m6414h().mo1062a("Fabric", "Using AdvertisingInfo from Preference Store");
            m5966a(b);
            return b;
        }
        b = m5970e();
        m5968b(b);
        return b;
    }

    /* renamed from: b */
    protected C1097b m5972b() {
        return new C1097b(this.f3247b.mo1051a().getString("advertising_id", TtmlNode.ANONYMOUS_REGION_ID), this.f3247b.mo1051a().getBoolean("limit_ad_tracking_enabled", false));
    }

    /* renamed from: c */
    public C1101f m5973c() {
        return new C1102d(this.f3246a);
    }

    /* renamed from: d */
    public C1101f m5974d() {
        return new C1106e(this.f3246a);
    }
}
