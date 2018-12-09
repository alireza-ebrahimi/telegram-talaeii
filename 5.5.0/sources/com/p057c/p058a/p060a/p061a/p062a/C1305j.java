package com.p057c.p058a.p060a.p061a.p062a;

import com.p054b.p055a.C1288c;
import java.nio.ByteBuffer;

@C1302g(a = {19})
/* renamed from: com.c.a.a.a.a.j */
public class C1305j extends C1296b {
    /* renamed from: a */
    byte[] f3959a;

    /* renamed from: a */
    public void mo1120a(ByteBuffer byteBuffer) {
        if (m6696c() > 0) {
            this.f3959a = new byte[m6696c()];
            byteBuffer.get(this.f3959a);
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ExtensionDescriptor");
        stringBuilder.append("{bytes=").append(this.f3959a == null ? "null" : C1288c.m6663a(this.f3959a));
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
