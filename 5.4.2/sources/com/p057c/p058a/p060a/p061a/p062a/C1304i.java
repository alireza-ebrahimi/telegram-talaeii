package com.p057c.p058a.p060a.p061a.p062a;

import com.p054b.p055a.C1288c;
import java.nio.ByteBuffer;
import java.util.logging.Logger;

@C1302g(a = {19, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 192, 193, 194, 195, 196, 197, 198, 199, 200, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213, 214, 215, 216, 217, 218, 219, 220, 221, 222, 223, 224, 225, 226, 227, 228, 229, 230, 231, 232, 233, 234, 235, 236, 237, 238, 239, 240, 241, 242, 243, 244, 245, 246, 247, 248, 249, 250, 251, 252, 253})
/* renamed from: com.c.a.a.a.a.i */
public class C1304i extends C1296b {
    /* renamed from: b */
    private static Logger f3957b = Logger.getLogger(C1304i.class.getName());
    /* renamed from: a */
    byte[] f3958a;

    /* renamed from: a */
    public void mo1120a(ByteBuffer byteBuffer) {
        if (m6696c() > 0) {
            this.f3958a = new byte[this.V];
            byteBuffer.get(this.f3958a);
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ExtensionDescriptor");
        stringBuilder.append("{bytes=").append(this.f3958a == null ? "null" : C1288c.m6663a(this.f3958a));
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
