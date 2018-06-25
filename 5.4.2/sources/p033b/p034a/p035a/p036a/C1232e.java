package p033b.p034a.p035a.p036a;

import android.os.SystemClock;
import android.text.TextUtils;
import java.io.Closeable;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import p033b.p034a.p035a.p036a.p037a.p039b.C1110i;

/* renamed from: b.a.a.a.e */
class C1232e implements Callable<Map<String, C1239k>> {
    /* renamed from: a */
    final String f3575a;

    C1232e(String str) {
        this.f3575a = str;
    }

    /* renamed from: a */
    private C1239k m6428a(ZipEntry zipEntry, ZipFile zipFile) {
        Throwable e;
        Closeable inputStream;
        try {
            inputStream = zipFile.getInputStream(zipEntry);
            try {
                Properties properties = new Properties();
                properties.load(inputStream);
                Object property = properties.getProperty("fabric-identifier");
                Object property2 = properties.getProperty("fabric-version");
                String property3 = properties.getProperty("fabric-build-type");
                if (TextUtils.isEmpty(property) || TextUtils.isEmpty(property2)) {
                    throw new IllegalStateException("Invalid format of fabric file," + zipEntry.getName());
                }
                C1239k c1239k = new C1239k(property, property2, property3);
                C1110i.m6010a(inputStream);
                return c1239k;
            } catch (IOException e2) {
                e = e2;
                try {
                    C1230c.m6414h().mo1070e("Fabric", "Error when parsing fabric properties " + zipEntry.getName(), e);
                    C1110i.m6010a(inputStream);
                    return null;
                } catch (Throwable th) {
                    e = th;
                    C1110i.m6010a(inputStream);
                    throw e;
                }
            }
        } catch (IOException e3) {
            e = e3;
            inputStream = null;
            C1230c.m6414h().mo1070e("Fabric", "Error when parsing fabric properties " + zipEntry.getName(), e);
            C1110i.m6010a(inputStream);
            return null;
        } catch (Throwable th2) {
            e = th2;
            inputStream = null;
            C1110i.m6010a(inputStream);
            throw e;
        }
    }

    /* renamed from: c */
    private Map<String, C1239k> m6429c() {
        Map<String, C1239k> hashMap = new HashMap();
        try {
            Class.forName("com.google.android.gms.ads.AdView");
            C1239k c1239k = new C1239k("com.google.firebase.firebase-ads", "0.0.0", "binary");
            hashMap.put(c1239k.m6458a(), c1239k);
            C1230c.m6414h().mo1065b("Fabric", "Found kit: com.google.firebase.firebase-ads");
        } catch (Exception e) {
        }
        return hashMap;
    }

    /* renamed from: d */
    private Map<String, C1239k> m6430d() {
        Map<String, C1239k> hashMap = new HashMap();
        ZipFile b = m6432b();
        Enumeration entries = b.entries();
        while (entries.hasMoreElements()) {
            ZipEntry zipEntry = (ZipEntry) entries.nextElement();
            if (zipEntry.getName().startsWith("fabric/") && zipEntry.getName().length() > "fabric/".length()) {
                C1239k a = m6428a(zipEntry, b);
                if (a != null) {
                    hashMap.put(a.m6458a(), a);
                    C1230c.m6414h().mo1065b("Fabric", String.format("Found kit:[%s] version:[%s]", new Object[]{a.m6458a(), a.m6459b()}));
                }
            }
        }
        if (b != null) {
            try {
                b.close();
            } catch (IOException e) {
            }
        }
        return hashMap;
    }

    /* renamed from: a */
    public Map<String, C1239k> m6431a() {
        Map<String, C1239k> hashMap = new HashMap();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        hashMap.putAll(m6429c());
        hashMap.putAll(m6430d());
        C1230c.m6414h().mo1065b("Fabric", "finish scanning in " + (SystemClock.elapsedRealtime() - elapsedRealtime));
        return hashMap;
    }

    /* renamed from: b */
    protected ZipFile m6432b() {
        return new ZipFile(this.f3575a);
    }

    public /* synthetic */ Object call() {
        return m6431a();
    }
}
