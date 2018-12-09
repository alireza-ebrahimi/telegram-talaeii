package com.google.firebase.auth.p104a.p105a;

import com.google.firebase.auth.PhoneAuthProvider.C1825a;

/* renamed from: com.google.firebase.auth.a.a.af */
final class af implements Runnable {
    /* renamed from: a */
    private final /* synthetic */ ag f5463a;
    /* renamed from: b */
    private final /* synthetic */ aa f5464b;

    af(aa aaVar, ag agVar) {
        this.f5464b = aaVar;
        this.f5463a = agVar;
    }

    public final void run() {
        synchronized (this.f5464b.f5458a.f5483i) {
            if (!this.f5464b.f5458a.f5483i.isEmpty()) {
                this.f5463a.mo3012a((C1825a) this.f5464b.f5458a.f5483i.get(0), new Object[0]);
            }
        }
    }
}
