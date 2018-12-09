package com.p057c.p058a.p060a.p061a.p062a;

import java.nio.ByteBuffer;

/* renamed from: com.c.a.a.a.a.c */
public class C1298c {
    /* renamed from: a */
    int f3924a;
    /* renamed from: b */
    int f3925b;
    /* renamed from: c */
    private ByteBuffer f3926c;

    public C1298c(ByteBuffer byteBuffer) {
        this.f3926c = byteBuffer;
        this.f3924a = byteBuffer.position();
    }

    /* renamed from: a */
    public int m6713a(int i) {
        int i2 = this.f3926c.get(this.f3924a + (this.f3925b / 8));
        if (i2 < 0) {
            i2 += 256;
        }
        int i3 = 8 - (this.f3925b % 8);
        if (i <= i3) {
            i2 = ((i2 << (this.f3925b % 8)) & 255) >> ((i3 - i) + (this.f3925b % 8));
            this.f3925b += i;
        } else {
            i2 = i - i3;
            i2 = m6713a(i2) + (m6713a(i3) << i2);
        }
        this.f3926c.position(this.f3924a + ((int) Math.ceil(((double) this.f3925b) / 8.0d)));
        return i2;
    }

    /* renamed from: a */
    public boolean m6714a() {
        return m6713a(1) == 1;
    }

    /* renamed from: b */
    public int m6715b() {
        return (this.f3926c.limit() * 8) - this.f3925b;
    }
}
