package android.support.v4.p014d;

import android.os.Build.VERSION;

/* renamed from: android.support.v4.d.d */
public final class C0434d {
    /* renamed from: a */
    private boolean f1191a;
    /* renamed from: b */
    private C0433a f1192b;
    /* renamed from: c */
    private Object f1193c;
    /* renamed from: d */
    private boolean f1194d;

    /* renamed from: android.support.v4.d.d$a */
    public interface C0433a {
        /* renamed from: a */
        void m1914a();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    /* renamed from: a */
    public void m1915a() {
        /*
        r2 = this;
        monitor-enter(r2);
        r0 = r2.f1191a;	 Catch:{ all -> 0x0028 }
        if (r0 == 0) goto L_0x0007;
    L_0x0005:
        monitor-exit(r2);	 Catch:{ all -> 0x0028 }
    L_0x0006:
        return;
    L_0x0007:
        r0 = 1;
        r2.f1191a = r0;	 Catch:{ all -> 0x0028 }
        r0 = 1;
        r2.f1194d = r0;	 Catch:{ all -> 0x0028 }
        r0 = r2.f1192b;	 Catch:{ all -> 0x0028 }
        r1 = r2.f1193c;	 Catch:{ all -> 0x0028 }
        monitor-exit(r2);	 Catch:{ all -> 0x0028 }
        if (r0 == 0) goto L_0x0017;
    L_0x0014:
        r0.m1914a();	 Catch:{ all -> 0x002b }
    L_0x0017:
        if (r1 == 0) goto L_0x001c;
    L_0x0019:
        android.support.v4.p014d.C0435e.m1918a(r1);	 Catch:{ all -> 0x002b }
    L_0x001c:
        monitor-enter(r2);
        r0 = 0;
        r2.f1194d = r0;	 Catch:{ all -> 0x0025 }
        r2.notifyAll();	 Catch:{ all -> 0x0025 }
        monitor-exit(r2);	 Catch:{ all -> 0x0025 }
        goto L_0x0006;
    L_0x0025:
        r0 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x0025 }
        throw r0;
    L_0x0028:
        r0 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x0028 }
        throw r0;
    L_0x002b:
        r0 = move-exception;
        monitor-enter(r2);
        r1 = 0;
        r2.f1194d = r1;	 Catch:{ all -> 0x0035 }
        r2.notifyAll();	 Catch:{ all -> 0x0035 }
        monitor-exit(r2);	 Catch:{ all -> 0x0035 }
        throw r0;
    L_0x0035:
        r0 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x0035 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.d.d.a():void");
    }

    /* renamed from: b */
    public Object m1916b() {
        if (VERSION.SDK_INT < 16) {
            return null;
        }
        Object obj;
        synchronized (this) {
            if (this.f1193c == null) {
                this.f1193c = C0435e.m1917a();
                if (this.f1191a) {
                    C0435e.m1918a(this.f1193c);
                }
            }
            obj = this.f1193c;
        }
        return obj;
    }
}
