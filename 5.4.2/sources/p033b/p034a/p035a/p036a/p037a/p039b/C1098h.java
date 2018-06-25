package p033b.p034a.p035a.p036a.p037a.p039b;

import android.os.Process;

/* renamed from: b.a.a.a.a.b.h */
public abstract class C1098h implements Runnable {
    /* renamed from: a */
    protected abstract void mo1020a();

    public final void run() {
        Process.setThreadPriority(10);
        mo1020a();
    }
}
