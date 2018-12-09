package com.p096g.p097a;

import android.os.Handler;

/* renamed from: com.g.a.f */
class C1620f {
    /* renamed from: a */
    final Handler f4954a;

    /* renamed from: a */
    void m7988a(C1607a c1607a) {
        this.f4954a.sendMessage(this.f4954a.obtainMessage(1, c1607a));
    }

    /* renamed from: a */
    void m7989a(C1615b c1615b) {
        this.f4954a.sendMessage(this.f4954a.obtainMessage(4, c1615b));
    }

    /* renamed from: b */
    void m7990b(C1607a c1607a) {
        this.f4954a.sendMessage(this.f4954a.obtainMessage(2, c1607a));
    }

    /* renamed from: b */
    void m7991b(C1615b c1615b) {
        this.f4954a.sendMessageDelayed(this.f4954a.obtainMessage(5, c1615b), 500);
    }

    /* renamed from: c */
    void m7992c(C1615b c1615b) {
        this.f4954a.sendMessage(this.f4954a.obtainMessage(6, c1615b));
    }
}
