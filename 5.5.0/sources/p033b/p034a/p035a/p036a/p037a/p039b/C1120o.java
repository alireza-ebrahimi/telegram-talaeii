package p033b.p034a.p035a.p036a.p037a.p039b;

import android.content.Context;
import android.text.TextUtils;
import p033b.p034a.p035a.p036a.C1230c;

/* renamed from: b.a.a.a.a.b.o */
public class C1120o {
    /* renamed from: a */
    protected String m6049a(Context context) {
        int a = C1110i.m5994a(context, "google_app_id", "string");
        if (a == 0) {
            return null;
        }
        C1230c.m6414h().mo1062a("Fabric", "Generating Crashlytics ApiKey from google_app_id in Strings");
        return m6050a(context.getResources().getString(a));
    }

    /* renamed from: a */
    protected String m6050a(String str) {
        return C1110i.m6019b(str).substring(0, 40);
    }

    /* renamed from: b */
    public boolean m6051b(Context context) {
        if (C1110i.m6014a(context, "com.crashlytics.useFirebaseAppId", false)) {
            return true;
        }
        boolean z = C1110i.m5994a(context, "google_app_id", "string") != 0;
        boolean z2 = (TextUtils.isEmpty(new C1107g().m5988c(context)) && TextUtils.isEmpty(new C1107g().m5989d(context))) ? false : true;
        return z && !z2;
    }
}
