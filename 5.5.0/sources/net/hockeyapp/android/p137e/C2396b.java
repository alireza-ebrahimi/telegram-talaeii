package net.hockeyapp.android.p137e;

import java.io.UnsupportedEncodingException;

/* renamed from: net.hockeyapp.android.e.b */
public class C2396b {

    /* renamed from: net.hockeyapp.android.e.b$a */
    static abstract class C2394a {
        /* renamed from: a */
        public byte[] f8070a;
        /* renamed from: b */
        public int f8071b;

        C2394a() {
        }
    }

    /* renamed from: net.hockeyapp.android.e.b$b */
    static class C2395b extends C2394a {
        /* renamed from: g */
        private static final byte[] f8072g = new byte[]{(byte) 65, (byte) 66, (byte) 67, (byte) 68, (byte) 69, (byte) 70, (byte) 71, (byte) 72, (byte) 73, (byte) 74, (byte) 75, (byte) 76, (byte) 77, (byte) 78, (byte) 79, (byte) 80, (byte) 81, (byte) 82, (byte) 83, (byte) 84, (byte) 85, (byte) 86, (byte) 87, (byte) 88, (byte) 89, (byte) 90, (byte) 97, (byte) 98, (byte) 99, (byte) 100, (byte) 101, (byte) 102, (byte) 103, (byte) 104, (byte) 105, (byte) 106, (byte) 107, (byte) 108, (byte) 109, (byte) 110, (byte) 111, (byte) 112, (byte) 113, (byte) 114, (byte) 115, (byte) 116, (byte) 117, (byte) 118, (byte) 119, (byte) 120, (byte) 121, (byte) 122, (byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 43, (byte) 47};
        /* renamed from: h */
        private static final byte[] f8073h = new byte[]{(byte) 65, (byte) 66, (byte) 67, (byte) 68, (byte) 69, (byte) 70, (byte) 71, (byte) 72, (byte) 73, (byte) 74, (byte) 75, (byte) 76, (byte) 77, (byte) 78, (byte) 79, (byte) 80, (byte) 81, (byte) 82, (byte) 83, (byte) 84, (byte) 85, (byte) 86, (byte) 87, (byte) 88, (byte) 89, (byte) 90, (byte) 97, (byte) 98, (byte) 99, (byte) 100, (byte) 101, (byte) 102, (byte) 103, (byte) 104, (byte) 105, (byte) 106, (byte) 107, (byte) 108, (byte) 109, (byte) 110, (byte) 111, (byte) 112, (byte) 113, (byte) 114, (byte) 115, (byte) 116, (byte) 117, (byte) 118, (byte) 119, (byte) 120, (byte) 121, (byte) 122, (byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 45, (byte) 95};
        /* renamed from: c */
        int f8074c;
        /* renamed from: d */
        public final boolean f8075d;
        /* renamed from: e */
        public final boolean f8076e;
        /* renamed from: f */
        public final boolean f8077f;
        /* renamed from: i */
        private final byte[] f8078i;
        /* renamed from: j */
        private int f8079j;
        /* renamed from: k */
        private final byte[] f8080k;

        public C2395b(int i, byte[] bArr) {
            boolean z = true;
            this.a = bArr;
            this.f8075d = (i & 1) == 0;
            this.f8076e = (i & 2) == 0;
            if ((i & 4) == 0) {
                z = false;
            }
            this.f8077f = z;
            this.f8080k = (i & 8) == 0 ? f8072g : f8073h;
            this.f8078i = new byte[2];
            this.f8074c = 0;
            this.f8079j = this.f8076e ? 19 : -1;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        /* renamed from: a */
        public boolean m11834a(byte[] r12, int r13, int r14, boolean r15) {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:37)
	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:61)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
*/
            /*
            r11 = this;
            r6 = r11.f8080k;
            r7 = r11.a;
            r1 = 0;
            r0 = r11.f8079j;
            r8 = r14 + r13;
            r2 = -1;
            r3 = r11.f8074c;
            switch(r3) {
                case 0: goto L_0x00a7;
                case 1: goto L_0x00aa;
                case 2: goto L_0x00cd;
                default: goto L_0x000f;
            };
        L_0x000f:
            r3 = r13;
        L_0x0010:
            r4 = -1;
            if (r2 == r4) goto L_0x023c;
        L_0x0013:
            r4 = 1;
            r5 = r2 >> 18;
            r5 = r5 & 63;
            r5 = r6[r5];
            r7[r1] = r5;
            r1 = 2;
            r5 = r2 >> 12;
            r5 = r5 & 63;
            r5 = r6[r5];
            r7[r4] = r5;
            r4 = 3;
            r5 = r2 >> 6;
            r5 = r5 & 63;
            r5 = r6[r5];
            r7[r1] = r5;
            r1 = 4;
            r2 = r2 & 63;
            r2 = r6[r2];
            r7[r4] = r2;
            r0 = r0 + -1;
            if (r0 != 0) goto L_0x023c;
        L_0x0039:
            r0 = r11.f8077f;
            if (r0 == 0) goto L_0x0240;
        L_0x003d:
            r0 = 5;
            r2 = 13;
            r7[r1] = r2;
        L_0x0042:
            r1 = r0 + 1;
            r2 = 10;
            r7[r0] = r2;
            r0 = 19;
            r5 = r0;
            r4 = r1;
        L_0x004c:
            r0 = r3 + 3;
            if (r0 > r8) goto L_0x00f0;
        L_0x0050:
            r0 = r12[r3];
            r0 = r0 & 255;
            r0 = r0 << 16;
            r1 = r3 + 1;
            r1 = r12[r1];
            r1 = r1 & 255;
            r1 = r1 << 8;
            r0 = r0 | r1;
            r1 = r3 + 2;
            r1 = r12[r1];
            r1 = r1 & 255;
            r0 = r0 | r1;
            r1 = r0 >> 18;
            r1 = r1 & 63;
            r1 = r6[r1];
            r7[r4] = r1;
            r1 = r4 + 1;
            r2 = r0 >> 12;
            r2 = r2 & 63;
            r2 = r6[r2];
            r7[r1] = r2;
            r1 = r4 + 2;
            r2 = r0 >> 6;
            r2 = r2 & 63;
            r2 = r6[r2];
            r7[r1] = r2;
            r1 = r4 + 3;
            r0 = r0 & 63;
            r0 = r6[r0];
            r7[r1] = r0;
            r3 = r3 + 3;
            r1 = r4 + 4;
            r0 = r5 + -1;
            if (r0 != 0) goto L_0x023c;
        L_0x0092:
            r0 = r11.f8077f;
            if (r0 == 0) goto L_0x0239;
        L_0x0096:
            r0 = r1 + 1;
            r2 = 13;
            r7[r1] = r2;
        L_0x009c:
            r1 = r0 + 1;
            r2 = 10;
            r7[r0] = r2;
            r0 = 19;
            r5 = r0;
            r4 = r1;
            goto L_0x004c;
        L_0x00a7:
            r3 = r13;
            goto L_0x0010;
        L_0x00aa:
            r3 = r13 + 2;
            if (r3 > r8) goto L_0x000f;
        L_0x00ae:
            r2 = r11.f8078i;
            r3 = 0;
            r2 = r2[r3];
            r2 = r2 & 255;
            r2 = r2 << 16;
            r3 = r13 + 1;
            r4 = r12[r13];
            r4 = r4 & 255;
            r4 = r4 << 8;
            r2 = r2 | r4;
            r13 = r3 + 1;
            r3 = r12[r3];
            r3 = r3 & 255;
            r2 = r2 | r3;
            r3 = 0;
            r11.f8074c = r3;
            r3 = r13;
            goto L_0x0010;
        L_0x00cd:
            r3 = r13 + 1;
            if (r3 > r8) goto L_0x000f;
        L_0x00d1:
            r2 = r11.f8078i;
            r3 = 0;
            r2 = r2[r3];
            r2 = r2 & 255;
            r2 = r2 << 16;
            r3 = r11.f8078i;
            r4 = 1;
            r3 = r3[r4];
            r3 = r3 & 255;
            r3 = r3 << 8;
            r2 = r2 | r3;
            r3 = r13 + 1;
            r4 = r12[r13];
            r4 = r4 & 255;
            r2 = r2 | r4;
            r4 = 0;
            r11.f8074c = r4;
            goto L_0x0010;
        L_0x00f0:
            if (r15 == 0) goto L_0x0203;
        L_0x00f2:
            r0 = r11.f8074c;
            r0 = r3 - r0;
            r1 = r8 + -1;
            if (r0 != r1) goto L_0x016e;
        L_0x00fa:
            r2 = 0;
            r0 = r11.f8074c;
            if (r0 <= 0) goto L_0x0166;
        L_0x00ff:
            r0 = r11.f8078i;
            r1 = 1;
            r0 = r0[r2];
            r2 = r3;
        L_0x0105:
            r0 = r0 & 255;
            r3 = r0 << 4;
            r0 = r11.f8074c;
            r0 = r0 - r1;
            r11.f8074c = r0;
            r1 = r4 + 1;
            r0 = r3 >> 6;
            r0 = r0 & 63;
            r0 = r6[r0];
            r7[r4] = r0;
            r0 = r1 + 1;
            r3 = r3 & 63;
            r3 = r6[r3];
            r7[r1] = r3;
            r1 = r11.f8075d;
            if (r1 == 0) goto L_0x0130;
        L_0x0124:
            r1 = r0 + 1;
            r3 = 61;
            r7[r0] = r3;
            r0 = r1 + 1;
            r3 = 61;
            r7[r1] = r3;
        L_0x0130:
            r1 = r11.f8076e;
            if (r1 == 0) goto L_0x0146;
        L_0x0134:
            r1 = r11.f8077f;
            if (r1 == 0) goto L_0x013f;
        L_0x0138:
            r1 = r0 + 1;
            r3 = 13;
            r7[r0] = r3;
            r0 = r1;
        L_0x013f:
            r1 = r0 + 1;
            r3 = 10;
            r7[r0] = r3;
            r0 = r1;
        L_0x0146:
            r3 = r2;
            r4 = r0;
        L_0x0148:
            r0 = r11.f8074c;
            if (r0 == 0) goto L_0x0155;
        L_0x014c:
            r0 = "BASE64";
            r1 = "Error during encoding";
            net.hockeyapp.android.p137e.C2400d.m11849d(r0, r1);
        L_0x0155:
            if (r3 == r8) goto L_0x0160;
        L_0x0157:
            r0 = "BASE64";
            r1 = "Error during encoding";
            net.hockeyapp.android.p137e.C2400d.m11849d(r0, r1);
        L_0x0160:
            r11.b = r4;
            r11.f8079j = r5;
            r0 = 1;
            return r0;
        L_0x0166:
            r1 = r3 + 1;
            r0 = r12[r3];
            r10 = r2;
            r2 = r1;
            r1 = r10;
            goto L_0x0105;
        L_0x016e:
            r0 = r11.f8074c;
            r0 = r3 - r0;
            r1 = r8 + -2;
            if (r0 != r1) goto L_0x01e7;
        L_0x0176:
            r2 = 0;
            r0 = r11.f8074c;
            r1 = 1;
            if (r0 <= r1) goto L_0x01da;
        L_0x017c:
            r0 = r11.f8078i;
            r1 = 1;
            r0 = r0[r2];
        L_0x0181:
            r0 = r0 & 255;
            r9 = r0 << 10;
            r0 = r11.f8074c;
            if (r0 <= 0) goto L_0x01e1;
        L_0x0189:
            r0 = r11.f8078i;
            r2 = r1 + 1;
            r0 = r0[r1];
            r1 = r2;
        L_0x0190:
            r0 = r0 & 255;
            r0 = r0 << 2;
            r0 = r0 | r9;
            r2 = r11.f8074c;
            r1 = r2 - r1;
            r11.f8074c = r1;
            r1 = r4 + 1;
            r2 = r0 >> 12;
            r2 = r2 & 63;
            r2 = r6[r2];
            r7[r4] = r2;
            r2 = r1 + 1;
            r4 = r0 >> 6;
            r4 = r4 & 63;
            r4 = r6[r4];
            r7[r1] = r4;
            r1 = r2 + 1;
            r0 = r0 & 63;
            r0 = r6[r0];
            r7[r2] = r0;
            r0 = r11.f8075d;
            if (r0 == 0) goto L_0x0237;
        L_0x01bb:
            r0 = r1 + 1;
            r2 = 61;
            r7[r1] = r2;
        L_0x01c1:
            r1 = r11.f8076e;
            if (r1 == 0) goto L_0x01d7;
        L_0x01c5:
            r1 = r11.f8077f;
            if (r1 == 0) goto L_0x01d0;
        L_0x01c9:
            r1 = r0 + 1;
            r2 = 13;
            r7[r0] = r2;
            r0 = r1;
        L_0x01d0:
            r1 = r0 + 1;
            r2 = 10;
            r7[r0] = r2;
            r0 = r1;
        L_0x01d7:
            r4 = r0;
            goto L_0x0148;
        L_0x01da:
            r1 = r3 + 1;
            r0 = r12[r3];
            r3 = r1;
            r1 = r2;
            goto L_0x0181;
        L_0x01e1:
            r2 = r3 + 1;
            r0 = r12[r3];
            r3 = r2;
            goto L_0x0190;
        L_0x01e7:
            r0 = r11.f8076e;
            if (r0 == 0) goto L_0x0148;
        L_0x01eb:
            if (r4 <= 0) goto L_0x0148;
        L_0x01ed:
            r0 = 19;
            if (r5 == r0) goto L_0x0148;
        L_0x01f1:
            r0 = r11.f8077f;
            if (r0 == 0) goto L_0x0235;
        L_0x01f5:
            r0 = r4 + 1;
            r1 = 13;
            r7[r4] = r1;
        L_0x01fb:
            r4 = r0 + 1;
            r1 = 10;
            r7[r0] = r1;
            goto L_0x0148;
        L_0x0203:
            r0 = r8 + -1;
            if (r3 != r0) goto L_0x0215;
        L_0x0207:
            r0 = r11.f8078i;
            r1 = r11.f8074c;
            r2 = r1 + 1;
            r11.f8074c = r2;
            r2 = r12[r3];
            r0[r1] = r2;
            goto L_0x0160;
        L_0x0215:
            r0 = r8 + -2;
            if (r3 != r0) goto L_0x0160;
        L_0x0219:
            r0 = r11.f8078i;
            r1 = r11.f8074c;
            r2 = r1 + 1;
            r11.f8074c = r2;
            r2 = r12[r3];
            r0[r1] = r2;
            r0 = r11.f8078i;
            r1 = r11.f8074c;
            r2 = r1 + 1;
            r11.f8074c = r2;
            r2 = r3 + 1;
            r2 = r12[r2];
            r0[r1] = r2;
            goto L_0x0160;
        L_0x0235:
            r0 = r4;
            goto L_0x01fb;
        L_0x0237:
            r0 = r1;
            goto L_0x01c1;
        L_0x0239:
            r0 = r1;
            goto L_0x009c;
        L_0x023c:
            r5 = r0;
            r4 = r1;
            goto L_0x004c;
        L_0x0240:
            r0 = r1;
            goto L_0x0042;
            */
            throw new UnsupportedOperationException("Method not decompiled: net.hockeyapp.android.e.b.b.a(byte[], int, int, boolean):boolean");
        }
    }

    /* renamed from: a */
    public static String m11835a(byte[] bArr, int i) {
        try {
            return new String(C2396b.m11837b(bArr, i), "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    /* renamed from: a */
    public static byte[] m11836a(byte[] bArr, int i, int i2, int i3) {
        C2395b c2395b = new C2395b(i3, null);
        int i4 = (i2 / 3) * 4;
        if (!c2395b.f8075d) {
            switch (i2 % 3) {
                case 0:
                    break;
                case 1:
                    i4 += 2;
                    break;
                case 2:
                    i4 += 3;
                    break;
                default:
                    break;
            }
        } else if (i2 % 3 > 0) {
            i4 += 4;
        }
        if (c2395b.f8076e && i2 > 0) {
            i4 += (c2395b.f8077f ? 2 : 1) * (((i2 - 1) / 57) + 1);
        }
        c2395b.a = new byte[i4];
        c2395b.m11834a(bArr, i, i2, true);
        if (c2395b.b == i4) {
            return c2395b.a;
        }
        throw new AssertionError();
    }

    /* renamed from: b */
    public static byte[] m11837b(byte[] bArr, int i) {
        return C2396b.m11836a(bArr, 0, bArr.length, i);
    }
}
