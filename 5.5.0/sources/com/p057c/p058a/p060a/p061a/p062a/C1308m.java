package com.p057c.p058a.p060a.p061a.p062a;

import com.p054b.p055a.C1290e;
import java.nio.ByteBuffer;

@C1302g(a = {20})
/* renamed from: com.c.a.a.a.a.m */
public class C1308m extends C1296b {
    /* renamed from: a */
    int f3962a;

    /* renamed from: a */
    public void mo1120a(ByteBuffer byteBuffer) {
        this.f3962a = C1290e.m6671d(byteBuffer);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.f3962a == ((C1308m) obj).f3962a;
    }

    public int hashCode() {
        return this.f3962a;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ProfileLevelIndicationDescriptor");
        stringBuilder.append("{profileLevelIndicationIndex=").append(Integer.toHexString(this.f3962a));
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
