package p033b.p034a.p035a.p036a.p037a.p039b;

import android.os.SystemClock;
import android.util.Log;

/* renamed from: b.a.a.a.a.b.u */
public class C1132u {
    /* renamed from: a */
    private final String f3327a;
    /* renamed from: b */
    private final String f3328b;
    /* renamed from: c */
    private final boolean f3329c;
    /* renamed from: d */
    private long f3330d;
    /* renamed from: e */
    private long f3331e;

    public C1132u(String str, String str2) {
        this.f3327a = str;
        this.f3328b = str2;
        this.f3329c = !Log.isLoggable(str2, 2);
    }

    /* renamed from: c */
    private void m6105c() {
        Log.v(this.f3328b, this.f3327a + ": " + this.f3331e + "ms");
    }

    /* renamed from: a */
    public synchronized void m6106a() {
        if (!this.f3329c) {
            this.f3330d = SystemClock.elapsedRealtime();
            this.f3331e = 0;
        }
    }

    /* renamed from: b */
    public synchronized void m6107b() {
        if (!this.f3329c) {
            if (this.f3331e == 0) {
                this.f3331e = SystemClock.elapsedRealtime() - this.f3330d;
                m6105c();
            }
        }
    }
}
