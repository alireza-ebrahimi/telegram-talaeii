package p033b.p034a.p035a.p036a.p037a.p039b;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import p033b.p034a.p035a.p036a.C1230c;

/* renamed from: b.a.a.a.a.b.g */
public class C1107g {
    /* renamed from: a */
    protected String m5985a() {
        return "Fabric could not be initialized, API key missing from AndroidManifest.xml. Add the following tag to your Application element \n\t<meta-data android:name=\"io.fabric.ApiKey\" android:value=\"YOUR_API_KEY\"/>";
    }

    /* renamed from: a */
    public String m5986a(Context context) {
        Object c = m5988c(context);
        if (TextUtils.isEmpty(c)) {
            c = m5989d(context);
        }
        if (TextUtils.isEmpty(c)) {
            c = m5987b(context);
        }
        if (TextUtils.isEmpty(c)) {
            m5990e(context);
        }
        return c;
    }

    /* renamed from: b */
    protected String m5987b(Context context) {
        return new C1120o().m6049a(context);
    }

    /* renamed from: c */
    protected String m5988c(Context context) {
        Object obj;
        String str = null;
        try {
            Bundle bundle = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData;
            if (bundle != null) {
                String string = bundle.getString("io.fabric.ApiKey");
                try {
                    if ("@string/twitter_consumer_secret".equals(string)) {
                        C1230c.m6414h().mo1062a("Fabric", "Ignoring bad default value for Fabric ApiKey set by FirebaseUI-Auth");
                    } else {
                        str = string;
                    }
                    if (str == null) {
                        C1230c.m6414h().mo1062a("Fabric", "Falling back to Crashlytics key lookup from Manifest");
                        str = bundle.getString("com.crashlytics.ApiKey");
                    }
                } catch (Exception e) {
                    Exception exception = e;
                    str = string;
                    Exception exception2 = exception;
                    C1230c.m6414h().mo1062a("Fabric", "Caught non-fatal exception while retrieving apiKey: " + obj);
                    return str;
                }
            }
        } catch (Exception e2) {
            obj = e2;
            C1230c.m6414h().mo1062a("Fabric", "Caught non-fatal exception while retrieving apiKey: " + obj);
            return str;
        }
        return str;
    }

    /* renamed from: d */
    protected String m5989d(Context context) {
        int a = C1110i.m5994a(context, "io.fabric.ApiKey", "string");
        if (a == 0) {
            C1230c.m6414h().mo1062a("Fabric", "Falling back to Crashlytics key lookup from Strings");
            a = C1110i.m5994a(context, "com.crashlytics.ApiKey", "string");
        }
        return a != 0 ? context.getResources().getString(a) : null;
    }

    /* renamed from: e */
    protected void m5990e(Context context) {
        if (C1230c.m6415i() || C1110i.m6030i(context)) {
            throw new IllegalArgumentException(m5985a());
        }
        C1230c.m6414h().mo1069e("Fabric", m5985a());
    }
}
