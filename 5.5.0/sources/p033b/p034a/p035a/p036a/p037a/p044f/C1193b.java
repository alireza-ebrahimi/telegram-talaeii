package p033b.p034a.p035a.p036a.p037a.p044f;

import android.content.Context;
import java.io.File;
import p033b.p034a.p035a.p036a.C1230c;
import p033b.p034a.p035a.p036a.C1237i;

/* renamed from: b.a.a.a.a.f.b */
public class C1193b implements C1192a {
    /* renamed from: a */
    private final Context f3441a;
    /* renamed from: b */
    private final String f3442b;
    /* renamed from: c */
    private final String f3443c;

    public C1193b(C1237i c1237i) {
        if (c1237i.m6452q() == null) {
            throw new IllegalStateException("Cannot get directory before context has been set. Call Fabric.with() first");
        }
        this.f3441a = c1237i.m6452q();
        this.f3442b = c1237i.m6454s();
        this.f3443c = "Android/" + this.f3441a.getPackageName();
    }

    /* renamed from: a */
    public File mo1050a() {
        return m6314a(this.f3441a.getFilesDir());
    }

    /* renamed from: a */
    File m6314a(File file) {
        if (file == null) {
            C1230c.m6414h().mo1062a("Fabric", "Null File");
        } else if (file.exists() || file.mkdirs()) {
            return file;
        } else {
            C1230c.m6414h().mo1067d("Fabric", "Couldn't create file");
        }
        return null;
    }
}
