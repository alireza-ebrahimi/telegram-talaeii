package com.p057c.p058a.p060a.p061a.p062a;

import com.p054b.p055a.C1288c;
import java.nio.ByteBuffer;
import java.util.Arrays;

@C1302g(a = {5})
/* renamed from: com.c.a.a.a.a.f */
public class C1301f extends C1296b {
    /* renamed from: a */
    byte[] f3942a;

    /* renamed from: a */
    public void mo1120a(ByteBuffer byteBuffer) {
        if (this.V > 0) {
            this.f3942a = new byte[this.V];
            byteBuffer.get(this.f3942a);
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Arrays.equals(this.f3942a, ((C1301f) obj).f3942a);
    }

    public int hashCode() {
        return this.f3942a != null ? Arrays.hashCode(this.f3942a) : 0;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DecoderSpecificInfo");
        stringBuilder.append("{bytes=").append(this.f3942a == null ? "null" : C1288c.m6663a(this.f3942a));
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
