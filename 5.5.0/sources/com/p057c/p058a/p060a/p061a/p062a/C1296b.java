package com.p057c.p058a.p060a.p061a.p062a;

import com.p054b.p055a.C1290e;
import java.nio.ByteBuffer;

@C1302g(a = {0})
/* renamed from: com.c.a.a.a.a.b */
public abstract class C1296b {
    /* renamed from: X */
    static final /* synthetic */ boolean f3874X = (!C1296b.class.desiredAssertionStatus());
    /* renamed from: U */
    int f3875U;
    /* renamed from: V */
    int f3876V;
    /* renamed from: W */
    int f3877W;

    /* renamed from: a */
    public final void m6694a(int i, ByteBuffer byteBuffer) {
        this.f3875U = i;
        int d = C1290e.m6671d(byteBuffer);
        this.f3876V = d & 127;
        int i2 = 1;
        while ((d >>> 7) == 1) {
            d = C1290e.m6671d(byteBuffer);
            i2++;
            this.f3876V = (this.f3876V << 7) | (d & 127);
        }
        this.f3877W = i2;
        ByteBuffer slice = byteBuffer.slice();
        slice.limit(this.f3876V);
        mo1120a(slice);
        if (f3874X || slice.remaining() == 0) {
            byteBuffer.position(byteBuffer.position() + this.f3876V);
            return;
        }
        throw new AssertionError(new StringBuilder(String.valueOf(getClass().getSimpleName())).append(" has not been fully parsed").toString());
    }

    /* renamed from: a */
    public abstract void mo1120a(ByteBuffer byteBuffer);

    /* renamed from: c */
    public int m6696c() {
        return (this.f3876V + 1) + this.f3877W;
    }

    /* renamed from: d */
    public int m6697d() {
        return this.f3876V;
    }

    /* renamed from: e */
    public int m6698e() {
        return this.f3877W;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("BaseDescriptor");
        stringBuilder.append("{tag=").append(this.f3875U);
        stringBuilder.append(", sizeOfInstance=").append(this.f3876V);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
