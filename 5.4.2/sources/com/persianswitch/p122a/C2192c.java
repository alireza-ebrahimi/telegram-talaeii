package com.persianswitch.p122a;

import com.persianswitch.p122a.p123a.C2172c;
import com.persianswitch.p122a.p123a.C2180e;
import java.io.Closeable;
import java.io.Flushable;

/* renamed from: com.persianswitch.a.c */
public final class C2192c implements Closeable, Flushable {
    /* renamed from: a */
    final C2180e f6660a;
    /* renamed from: b */
    private final C2172c f6661b;

    public void close() {
        this.f6661b.close();
    }

    public void flush() {
        this.f6661b.flush();
    }
}
