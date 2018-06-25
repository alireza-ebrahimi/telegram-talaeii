package org.telegram.customization.util.view.p171b.p172a;

import android.view.View;
import java.util.Timer;
import java.util.TimerTask;
import org.telegram.customization.util.view.p171b.C2945c;

/* renamed from: org.telegram.customization.util.view.b.a.b */
public class C2926b {
    /* renamed from: a */
    protected Timer f9670a;
    /* renamed from: b */
    private View f9671b;
    /* renamed from: c */
    private boolean f9672c;

    /* renamed from: a */
    public View m13541a() {
        return this.f9671b;
    }

    /* renamed from: a */
    public void m13542a(Timer timer) {
        this.f9670a = timer;
    }

    /* renamed from: a */
    public void m13543a(C2945c c2945c, int i, long j) {
        Timer timer = new Timer();
        final C2945c c2945c2 = c2945c;
        final int i2 = i;
        final long j2 = j;
        timer.schedule(new TimerTask(this) {
            /* renamed from: d */
            final /* synthetic */ C2926b f9669d;

            public void run() {
                c2945c2.m13596b(this.f9669d.f9671b, i2);
                if (this.f9669d.f9672c) {
                    this.f9669d.m13543a(c2945c2, i2, j2);
                }
            }
        }, j);
        this.f9670a = timer;
    }

    /* renamed from: b */
    public Timer m13544b() {
        return this.f9670a;
    }
}
