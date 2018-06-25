package com.p057c.p058a.p060a.p061a.p062a;

import java.nio.ByteBuffer;
import java.util.logging.Logger;

/* renamed from: com.c.a.a.a.a.o */
public class C1310o extends C1296b {
    /* renamed from: b */
    private static Logger f3964b = Logger.getLogger(C1310o.class.getName());
    /* renamed from: a */
    private ByteBuffer f3965a;

    /* renamed from: a */
    public void mo1120a(ByteBuffer byteBuffer) {
        this.f3965a = (ByteBuffer) byteBuffer.slice().limit(m6697d());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UnknownDescriptor");
        stringBuilder.append("{tag=").append(this.U);
        stringBuilder.append(", sizeOfInstance=").append(this.V);
        stringBuilder.append(", data=").append(this.f3965a);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
