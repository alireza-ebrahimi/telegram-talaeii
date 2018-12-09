package p033b.p034a.p035a.p036a.p037a.p039b;

import android.content.Context;
import p033b.p034a.p035a.p036a.C1230c;

/* renamed from: b.a.a.a.a.b.d */
class C1102d implements C1101f {
    /* renamed from: a */
    private final Context f3248a;

    public C1102d(Context context) {
        this.f3248a = context.getApplicationContext();
    }

    /* renamed from: b */
    private String m5976b() {
        try {
            return (String) Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient$Info").getMethod("getId", new Class[0]).invoke(m5978d(), new Object[0]);
        } catch (Exception e) {
            C1230c.m6414h().mo1067d("Fabric", "Could not call getId on com.google.android.gms.ads.identifier.AdvertisingIdClient$Info");
            return null;
        }
    }

    /* renamed from: c */
    private boolean m5977c() {
        try {
            return ((Boolean) Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient$Info").getMethod("isLimitAdTrackingEnabled", new Class[0]).invoke(m5978d(), new Object[0])).booleanValue();
        } catch (Exception e) {
            C1230c.m6414h().mo1067d("Fabric", "Could not call isLimitAdTrackingEnabled on com.google.android.gms.ads.identifier.AdvertisingIdClient$Info");
            return false;
        }
    }

    /* renamed from: d */
    private Object m5978d() {
        Object obj = null;
        try {
            obj = Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient").getMethod("getAdvertisingIdInfo", new Class[]{Context.class}).invoke(null, new Object[]{this.f3248a});
        } catch (Exception e) {
            C1230c.m6414h().mo1067d("Fabric", "Could not call getAdvertisingIdInfo on com.google.android.gms.ads.identifier.AdvertisingIdClient");
        }
        return obj;
    }

    /* renamed from: a */
    public C1097b mo1021a() {
        return m5980a(this.f3248a) ? new C1097b(m5976b(), m5977c()) : null;
    }

    /* renamed from: a */
    boolean m5980a(Context context) {
        try {
            return ((Integer) Class.forName("com.google.android.gms.common.GooglePlayServicesUtil").getMethod("isGooglePlayServicesAvailable", new Class[]{Context.class}).invoke(null, new Object[]{context})).intValue() == 0;
        } catch (Exception e) {
            return false;
        }
    }
}
