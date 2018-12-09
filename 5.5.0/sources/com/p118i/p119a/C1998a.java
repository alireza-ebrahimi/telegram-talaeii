package com.p118i.p119a;

import org.telegram.messenger.exoplayer2.DefaultLoadControl;

/* renamed from: com.i.a.a */
public class C1998a implements C1997h {
    /* renamed from: a */
    private int f5872a;
    /* renamed from: b */
    private int f5873b;
    /* renamed from: c */
    private final int f5874c;
    /* renamed from: d */
    private final float f5875d;

    public C1998a() {
        this(DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_AFTER_REBUFFER_MS, 1, 1.0f);
    }

    public C1998a(int i, int i2, float f) {
        this.f5872a = i;
        this.f5874c = i2;
        this.f5875d = f;
    }

    /* renamed from: a */
    public int mo3059a() {
        return this.f5872a;
    }

    /* renamed from: b */
    public void mo3060b() {
        this.f5873b++;
        this.f5872a = (int) (((float) this.f5872a) + (((float) this.f5872a) * this.f5875d));
        if (!m9029c()) {
            throw new C2011g();
        }
    }

    /* renamed from: c */
    protected boolean m9029c() {
        return this.f5873b <= this.f5874c;
    }
}
