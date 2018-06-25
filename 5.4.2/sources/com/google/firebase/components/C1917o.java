package com.google.firebase.components;

import com.google.firebase.p106a.C1808a;
import com.google.firebase.p106a.C1809b;
import java.util.Map.Entry;

/* renamed from: com.google.firebase.components.o */
final /* synthetic */ class C1917o implements Runnable {
    /* renamed from: a */
    private final Entry f5627a;
    /* renamed from: b */
    private final C1808a f5628b;

    C1917o(Entry entry, C1808a c1808a) {
        this.f5627a = entry;
        this.f5628b = c1808a;
    }

    public final void run() {
        ((C1809b) this.f5627a.getKey()).m8468a(this.f5628b);
    }
}
