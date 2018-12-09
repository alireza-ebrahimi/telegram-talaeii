package com.crashlytics.android.p066c;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;
import org.telegram.messenger.exoplayer2.C3446C;
import p033b.p034a.p035a.p036a.C1230c;
import p033b.p034a.p035a.p036a.p037a.p039b.C1110i;

/* renamed from: com.crashlytics.android.c.w */
class C1462w {
    /* renamed from: a */
    private static final Charset f4409a = Charset.forName(C3446C.UTF8_NAME);
    /* renamed from: b */
    private final File f4410b;

    public C1462w(File file) {
        this.f4410b = file;
    }

    /* renamed from: a */
    private static String m7274a(JSONObject jSONObject, String str) {
        return !jSONObject.isNull(str) ? jSONObject.optString(str, null) : null;
    }

    /* renamed from: c */
    private File m7275c(String str) {
        return new File(this.f4410b, str + "user" + ".meta");
    }

    /* renamed from: d */
    private File m7276d(String str) {
        return new File(this.f4410b, str + "keys" + ".meta");
    }

    /* renamed from: e */
    private static al m7277e(String str) {
        JSONObject jSONObject = new JSONObject(str);
        return new al(C1462w.m7274a(jSONObject, "userId"), C1462w.m7274a(jSONObject, "userName"), C1462w.m7274a(jSONObject, "userEmail"));
    }

    /* renamed from: f */
    private static Map<String, String> m7278f(String str) {
        JSONObject jSONObject = new JSONObject(str);
        Map<String, String> hashMap = new HashMap();
        Iterator keys = jSONObject.keys();
        while (keys.hasNext()) {
            String str2 = (String) keys.next();
            hashMap.put(str2, C1462w.m7274a(jSONObject, str2));
        }
        return hashMap;
    }

    /* renamed from: a */
    public al m7279a(String str) {
        Closeable fileInputStream;
        Throwable e;
        File c = m7275c(str);
        if (!c.exists()) {
            return al.f4225a;
        }
        try {
            fileInputStream = new FileInputStream(c);
            try {
                al e2 = C1462w.m7277e(C1110i.m6000a((InputStream) fileInputStream));
                C1110i.m6011a(fileInputStream, "Failed to close user metadata file.");
                return e2;
            } catch (Exception e3) {
                e = e3;
                try {
                    C1230c.m6414h().mo1070e("CrashlyticsCore", "Error deserializing user metadata.", e);
                    C1110i.m6011a(fileInputStream, "Failed to close user metadata file.");
                    return al.f4225a;
                } catch (Throwable th) {
                    e = th;
                    C1110i.m6011a(fileInputStream, "Failed to close user metadata file.");
                    throw e;
                }
            }
        } catch (Exception e4) {
            e = e4;
            fileInputStream = null;
            C1230c.m6414h().mo1070e("CrashlyticsCore", "Error deserializing user metadata.", e);
            C1110i.m6011a(fileInputStream, "Failed to close user metadata file.");
            return al.f4225a;
        } catch (Throwable th2) {
            e = th2;
            fileInputStream = null;
            C1110i.m6011a(fileInputStream, "Failed to close user metadata file.");
            throw e;
        }
    }

    /* renamed from: b */
    public Map<String, String> m7280b(String str) {
        Closeable fileInputStream;
        Throwable e;
        File d = m7276d(str);
        if (!d.exists()) {
            return Collections.emptyMap();
        }
        try {
            fileInputStream = new FileInputStream(d);
            try {
                Map<String, String> f = C1462w.m7278f(C1110i.m6000a((InputStream) fileInputStream));
                C1110i.m6011a(fileInputStream, "Failed to close user metadata file.");
                return f;
            } catch (Exception e2) {
                e = e2;
                try {
                    C1230c.m6414h().mo1070e("CrashlyticsCore", "Error deserializing user metadata.", e);
                    C1110i.m6011a(fileInputStream, "Failed to close user metadata file.");
                    return Collections.emptyMap();
                } catch (Throwable th) {
                    e = th;
                    C1110i.m6011a(fileInputStream, "Failed to close user metadata file.");
                    throw e;
                }
            }
        } catch (Exception e3) {
            e = e3;
            fileInputStream = null;
            C1230c.m6414h().mo1070e("CrashlyticsCore", "Error deserializing user metadata.", e);
            C1110i.m6011a(fileInputStream, "Failed to close user metadata file.");
            return Collections.emptyMap();
        } catch (Throwable th2) {
            e = th2;
            fileInputStream = null;
            C1110i.m6011a(fileInputStream, "Failed to close user metadata file.");
            throw e;
        }
    }
}
