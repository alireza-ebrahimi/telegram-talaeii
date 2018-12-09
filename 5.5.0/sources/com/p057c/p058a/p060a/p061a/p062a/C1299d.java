package com.p057c.p058a.p060a.p061a.p062a;

import java.nio.ByteBuffer;

/* renamed from: com.c.a.a.a.a.d */
public class C1299d {
    /* renamed from: c */
    static final /* synthetic */ boolean f3927c = (!C1299d.class.desiredAssertionStatus());
    /* renamed from: a */
    int f3928a;
    /* renamed from: b */
    int f3929b = 0;
    /* renamed from: d */
    private ByteBuffer f3930d;

    public C1299d(ByteBuffer byteBuffer) {
        this.f3930d = byteBuffer;
        this.f3928a = byteBuffer.position();
    }

    /* renamed from: a */
    public void m6716a(int i, int i2) {
        if (f3927c || i <= (1 << i2) - 1) {
            int i3 = 8 - (this.f3929b % 8);
            int i4;
            if (i2 <= i3) {
                i4 = this.f3930d.get(this.f3928a + (this.f3929b / 8));
                if (i4 < 0) {
                    i4 += 256;
                }
                i4 += i << (i3 - i2);
                ByteBuffer byteBuffer = this.f3930d;
                int i5 = this.f3928a + (this.f3929b / 8);
                if (i4 > 127) {
                    i4 -= 256;
                }
                byteBuffer.put(i5, (byte) i4);
                this.f3929b += i2;
            } else {
                i4 = i2 - i3;
                m6716a(i >> i4, i3);
                m6716a(((1 << i4) - 1) & i, i4);
            }
            this.f3930d.position((this.f3929b % 8 > 0 ? 1 : 0) + ((this.f3929b / 8) + this.f3928a));
            return;
        }
        throw new AssertionError(String.format("Trying to write a value bigger (%s) than the number bits (%s) allows. Please mask the value before writing it and make your code is really working as intended.", new Object[]{Integer.valueOf(i), Integer.valueOf((1 << i2) - 1)}));
    }
}
