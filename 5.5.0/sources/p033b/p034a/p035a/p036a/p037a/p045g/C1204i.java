package p033b.p034a.p035a.p036a.p037a.p045g;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import org.json.JSONObject;
import p033b.p034a.p035a.p036a.C1230c;
import p033b.p034a.p035a.p036a.C1237i;
import p033b.p034a.p035a.p036a.p037a.p039b.C1110i;
import p033b.p034a.p035a.p036a.p037a.p044f.C1193b;

/* renamed from: b.a.a.a.a.g.i */
class C1204i implements C1202g {
    /* renamed from: a */
    private final C1237i f3481a;

    public C1204i(C1237i c1237i) {
        this.f3481a = c1237i;
    }

    /* renamed from: a */
    public JSONObject mo1055a() {
        Throwable e;
        Closeable closeable = null;
        C1230c.m6414h().mo1062a("Fabric", "Reading cached settings...");
        Closeable fileInputStream;
        try {
            JSONObject jSONObject;
            File file = new File(new C1193b(this.f3481a).mo1050a(), "com.crashlytics.settings.json");
            if (file.exists()) {
                fileInputStream = new FileInputStream(file);
                try {
                    jSONObject = new JSONObject(C1110i.m6000a((InputStream) fileInputStream));
                    closeable = fileInputStream;
                } catch (Exception e2) {
                    e = e2;
                    try {
                        C1230c.m6414h().mo1070e("Fabric", "Failed to fetch cached settings", e);
                        C1110i.m6011a(fileInputStream, "Error while closing settings cache file.");
                        return null;
                    } catch (Throwable th) {
                        e = th;
                        closeable = fileInputStream;
                        C1110i.m6011a(closeable, "Error while closing settings cache file.");
                        throw e;
                    }
                }
            }
            C1230c.m6414h().mo1062a("Fabric", "No cached settings found.");
            jSONObject = null;
            C1110i.m6011a(closeable, "Error while closing settings cache file.");
            return jSONObject;
        } catch (Exception e3) {
            e = e3;
            fileInputStream = null;
            C1230c.m6414h().mo1070e("Fabric", "Failed to fetch cached settings", e);
            C1110i.m6011a(fileInputStream, "Error while closing settings cache file.");
            return null;
        } catch (Throwable th2) {
            e = th2;
            C1110i.m6011a(closeable, "Error while closing settings cache file.");
            throw e;
        }
    }

    /* renamed from: a */
    public void mo1056a(long j, JSONObject jSONObject) {
        Closeable fileWriter;
        Throwable e;
        C1230c.m6414h().mo1062a("Fabric", "Writing settings to cache file...");
        if (jSONObject != null) {
            Closeable closeable = null;
            try {
                jSONObject.put("expires_at", j);
                fileWriter = new FileWriter(new File(new C1193b(this.f3481a).mo1050a(), "com.crashlytics.settings.json"));
                try {
                    fileWriter.write(jSONObject.toString());
                    fileWriter.flush();
                    C1110i.m6011a(fileWriter, "Failed to close settings writer.");
                } catch (Exception e2) {
                    e = e2;
                    try {
                        C1230c.m6414h().mo1070e("Fabric", "Failed to cache settings", e);
                        C1110i.m6011a(fileWriter, "Failed to close settings writer.");
                    } catch (Throwable th) {
                        e = th;
                        closeable = fileWriter;
                        C1110i.m6011a(closeable, "Failed to close settings writer.");
                        throw e;
                    }
                }
            } catch (Exception e3) {
                e = e3;
                fileWriter = null;
                C1230c.m6414h().mo1070e("Fabric", "Failed to cache settings", e);
                C1110i.m6011a(fileWriter, "Failed to close settings writer.");
            } catch (Throwable th2) {
                e = th2;
                C1110i.m6011a(closeable, "Failed to close settings writer.");
                throw e;
            }
        }
    }
}
