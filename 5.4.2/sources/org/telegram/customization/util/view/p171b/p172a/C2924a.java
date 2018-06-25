package org.telegram.customization.util.view.p171b.p172a;

import android.view.View;
import java.util.Timer;
import java.util.TimerTask;
import org.telegram.customization.util.view.p171b.C2945c;

/* renamed from: org.telegram.customization.util.view.b.a.a */
public class C2924a {
    /* renamed from: a */
    protected Timer f9663a;
    /* renamed from: b */
    private View f9664b;
    /* renamed from: c */
    private int f9665c;

    /* renamed from: a */
    public int m13533a() {
        return this.f9665c;
    }

    /* renamed from: a */
    public void m13534a(int i) {
        this.f9665c = i;
    }

    /* renamed from: a */
    public void m13535a(Timer timer) {
        this.f9663a = timer;
    }

    /* renamed from: a */
    public void m13536a(final C2945c c2945c, final int i, long j) {
        Timer timer = new Timer();
        this.f9665c = i;
        timer.schedule(new TimerTask(this) {
            /* renamed from: c */
            final /* synthetic */ C2924a f9662c;

            public void run() {
                c2945c.m13594a(this.f9662c);
                c2945c.m13600e(this.f9662c.f9664b, i);
            }
        }, j);
        this.f9663a = timer;
    }

    /* renamed from: b */
    public View m13537b() {
        return this.f9664b;
    }

    /* renamed from: c */
    public Timer m13538c() {
        return this.f9663a;
    }
}
