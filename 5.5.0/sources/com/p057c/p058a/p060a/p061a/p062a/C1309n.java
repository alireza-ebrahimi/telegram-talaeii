package com.p057c.p058a.p060a.p061a.p062a;

import com.p054b.p055a.C1290e;
import com.p054b.p055a.C1291f;
import java.nio.ByteBuffer;

@C1302g(a = {6})
/* renamed from: com.c.a.a.a.a.n */
public class C1309n extends C1296b {
    /* renamed from: a */
    int f3963a;

    /* renamed from: a */
    public int m6739a() {
        return 3;
    }

    /* renamed from: a */
    public void m6740a(int i) {
        this.f3963a = i;
    }

    /* renamed from: a */
    public void mo1120a(ByteBuffer byteBuffer) {
        this.f3963a = C1290e.m6671d(byteBuffer);
    }

    /* renamed from: b */
    public ByteBuffer m6742b() {
        ByteBuffer allocate = ByteBuffer.allocate(3);
        C1291f.m6687c(allocate, 6);
        C1291f.m6687c(allocate, 1);
        C1291f.m6687c(allocate, this.f3963a);
        return allocate;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.f3963a == ((C1309n) obj).f3963a;
    }

    public int hashCode() {
        return this.f3963a;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SLConfigDescriptor");
        stringBuilder.append("{predefined=").append(this.f3963a);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
