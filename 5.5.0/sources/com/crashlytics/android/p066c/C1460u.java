package com.crashlytics.android.p066c;

import android.content.Context;
import java.io.File;
import java.util.Set;
import org.telegram.messenger.exoplayer2.C3446C;
import p033b.p034a.p035a.p036a.C1230c;
import p033b.p034a.p035a.p036a.p037a.p039b.C1110i;

/* renamed from: com.crashlytics.android.c.u */
class C1460u {
    /* renamed from: a */
    private static final C1459b f4403a = new C1459b();
    /* renamed from: b */
    private final Context f4404b;
    /* renamed from: c */
    private final C1429a f4405c;
    /* renamed from: d */
    private C1388s f4406d;

    /* renamed from: com.crashlytics.android.c.u$a */
    public interface C1429a {
        /* renamed from: a */
        File mo1164a();
    }

    /* renamed from: com.crashlytics.android.c.u$b */
    private static final class C1459b implements C1388s {
        private C1459b() {
        }

        /* renamed from: a */
        public C1395b mo1149a() {
            return null;
        }

        /* renamed from: b */
        public void mo1150b() {
        }

        /* renamed from: c */
        public void mo1151c() {
        }
    }

    C1460u(Context context, C1429a c1429a) {
        this(context, c1429a, null);
    }

    C1460u(Context context, C1429a c1429a, String str) {
        this.f4404b = context;
        this.f4405c = c1429a;
        this.f4406d = f4403a;
        m7270a(str);
    }

    /* renamed from: a */
    private String m7266a(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".temp");
        return lastIndexOf == -1 ? name : name.substring("crashlytics-userlog-".length(), lastIndexOf);
    }

    /* renamed from: b */
    private File m7267b(String str) {
        return new File(this.f4405c.mo1164a(), "crashlytics-userlog-" + str + ".temp");
    }

    /* renamed from: a */
    C1395b m7268a() {
        return this.f4406d.mo1149a();
    }

    /* renamed from: a */
    void m7269a(File file, int i) {
        this.f4406d = new ac(file, i);
    }

    /* renamed from: a */
    final void m7270a(String str) {
        this.f4406d.mo1150b();
        this.f4406d = f4403a;
        if (str != null) {
            if (C1110i.m6014a(this.f4404b, "com.crashlytics.CollectCustomLogs", true)) {
                m7269a(m7267b(str), C3446C.DEFAULT_BUFFER_SEGMENT_SIZE);
            } else {
                C1230c.m6414h().mo1062a("CrashlyticsCore", "Preferences requested no custom logs. Aborting log file creation.");
            }
        }
    }

    /* renamed from: a */
    void m7271a(Set<String> set) {
        File[] listFiles = this.f4405c.mo1164a().listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                if (!set.contains(m7266a(file))) {
                    file.delete();
                }
            }
        }
    }

    /* renamed from: b */
    void m7272b() {
        this.f4406d.mo1151c();
    }
}
