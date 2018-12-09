package com.google.android.gms.internal.firebase_auth;

import org.telegram.messenger.exoplayer2.extractor.ts.PsExtractor;

final class zzgb extends zzfy {
    zzgb() {
    }

    private static int zza(byte[] bArr, int i, long j, int i2) {
        switch (i2) {
            case 0:
                return zzfx.zzaw(i);
            case 1:
                return zzfx.zzp(i, zzfv.zza(bArr, j));
            case 2:
                return zzfx.zzc(i, zzfv.zza(bArr, j), zzfv.zza(bArr, 1 + j));
            default:
                throw new AssertionError();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    final int zzb(int r9, byte[] r10, int r11, int r12) {
        /*
        r8 = this;
        r0 = r11 | r12;
        r1 = r10.length;
        r1 = r1 - r12;
        r0 = r0 | r1;
        if (r0 >= 0) goto L_0x002d;
    L_0x0007:
        r0 = new java.lang.ArrayIndexOutOfBoundsException;
        r1 = "Array length=%d, index=%d, limit=%d";
        r2 = 3;
        r2 = new java.lang.Object[r2];
        r3 = 0;
        r4 = r10.length;
        r4 = java.lang.Integer.valueOf(r4);
        r2[r3] = r4;
        r3 = 1;
        r4 = java.lang.Integer.valueOf(r11);
        r2[r3] = r4;
        r3 = 2;
        r4 = java.lang.Integer.valueOf(r12);
        r2[r3] = r4;
        r1 = java.lang.String.format(r1, r2);
        r0.<init>(r1);
        throw r0;
    L_0x002d:
        r4 = (long) r11;
        r0 = (long) r12;
        r0 = r0 - r4;
        r1 = (int) r0;
        r0 = 16;
        if (r1 >= r0) goto L_0x004b;
    L_0x0035:
        r0 = 0;
    L_0x0036:
        r1 = r1 - r0;
        r2 = (long) r0;
        r2 = r2 + r4;
        r0 = r1;
    L_0x003a:
        r1 = 0;
        r4 = r2;
    L_0x003c:
        if (r0 <= 0) goto L_0x005f;
    L_0x003e:
        r2 = 1;
        r2 = r2 + r4;
        r1 = com.google.android.gms.internal.firebase_auth.zzfv.zza(r10, r4);
        if (r1 < 0) goto L_0x005e;
    L_0x0047:
        r0 = r0 + -1;
        r4 = r2;
        goto L_0x003c;
    L_0x004b:
        r0 = 0;
        r2 = r4;
    L_0x004d:
        if (r0 >= r1) goto L_0x005c;
    L_0x004f:
        r6 = 1;
        r6 = r6 + r2;
        r2 = com.google.android.gms.internal.firebase_auth.zzfv.zza(r10, r2);
        if (r2 < 0) goto L_0x0036;
    L_0x0058:
        r0 = r0 + 1;
        r2 = r6;
        goto L_0x004d;
    L_0x005c:
        r0 = r1;
        goto L_0x0036;
    L_0x005e:
        r4 = r2;
    L_0x005f:
        if (r0 != 0) goto L_0x0063;
    L_0x0061:
        r0 = 0;
    L_0x0062:
        return r0;
    L_0x0063:
        r0 = r0 + -1;
        r2 = -32;
        if (r1 >= r2) goto L_0x0080;
    L_0x0069:
        if (r0 != 0) goto L_0x006d;
    L_0x006b:
        r0 = r1;
        goto L_0x0062;
    L_0x006d:
        r0 = r0 + -1;
        r2 = -62;
        if (r1 < r2) goto L_0x007e;
    L_0x0073:
        r2 = 1;
        r2 = r2 + r4;
        r1 = com.google.android.gms.internal.firebase_auth.zzfv.zza(r10, r4);
        r4 = -65;
        if (r1 <= r4) goto L_0x003a;
    L_0x007e:
        r0 = -1;
        goto L_0x0062;
    L_0x0080:
        r2 = -16;
        if (r1 >= r2) goto L_0x00b7;
    L_0x0084:
        r2 = 2;
        if (r0 >= r2) goto L_0x008c;
    L_0x0087:
        r0 = zza(r10, r1, r4, r0);
        goto L_0x0062;
    L_0x008c:
        r0 = r0 + -2;
        r2 = 1;
        r6 = r4 + r2;
        r2 = com.google.android.gms.internal.firebase_auth.zzfv.zza(r10, r4);
        r3 = -65;
        if (r2 > r3) goto L_0x00b5;
    L_0x009a:
        r3 = -32;
        if (r1 != r3) goto L_0x00a2;
    L_0x009e:
        r3 = -96;
        if (r2 < r3) goto L_0x00b5;
    L_0x00a2:
        r3 = -19;
        if (r1 != r3) goto L_0x00aa;
    L_0x00a6:
        r1 = -96;
        if (r2 >= r1) goto L_0x00b5;
    L_0x00aa:
        r2 = 1;
        r2 = r2 + r6;
        r1 = com.google.android.gms.internal.firebase_auth.zzfv.zza(r10, r6);
        r4 = -65;
        if (r1 <= r4) goto L_0x003a;
    L_0x00b5:
        r0 = -1;
        goto L_0x0062;
    L_0x00b7:
        r2 = 3;
        if (r0 >= r2) goto L_0x00bf;
    L_0x00ba:
        r0 = zza(r10, r1, r4, r0);
        goto L_0x0062;
    L_0x00bf:
        r0 = r0 + -3;
        r2 = 1;
        r2 = r2 + r4;
        r4 = com.google.android.gms.internal.firebase_auth.zzfv.zza(r10, r4);
        r5 = -65;
        if (r4 > r5) goto L_0x00eb;
    L_0x00cc:
        r1 = r1 << 28;
        r4 = r4 + 112;
        r1 = r1 + r4;
        r1 = r1 >> 30;
        if (r1 != 0) goto L_0x00eb;
    L_0x00d5:
        r4 = 1;
        r4 = r4 + r2;
        r1 = com.google.android.gms.internal.firebase_auth.zzfv.zza(r10, r2);
        r2 = -65;
        if (r1 > r2) goto L_0x00eb;
    L_0x00e0:
        r2 = 1;
        r2 = r2 + r4;
        r1 = com.google.android.gms.internal.firebase_auth.zzfv.zza(r10, r4);
        r4 = -65;
        if (r1 <= r4) goto L_0x003a;
    L_0x00eb:
        r0 = -1;
        goto L_0x0062;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_auth.zzgb.zzb(int, byte[], int, int):int");
    }

    final int zzb(CharSequence charSequence, byte[] bArr, int i, int i2) {
        long j = (long) i;
        long j2 = j + ((long) i2);
        int length = charSequence.length();
        if (length > i2 || bArr.length - i2 < i) {
            throw new ArrayIndexOutOfBoundsException("Failed writing " + charSequence.charAt(length - 1) + " at index " + (i + i2));
        }
        int i3 = 0;
        while (i3 < length) {
            char charAt = charSequence.charAt(i3);
            if (charAt >= '') {
                break;
            }
            long j3 = 1 + j;
            zzfv.zza(bArr, j, (byte) charAt);
            i3++;
            j = j3;
        }
        if (i3 == length) {
            return (int) j;
        }
        j3 = j;
        while (i3 < length) {
            charAt = charSequence.charAt(i3);
            if (charAt < '' && j3 < j2) {
                j = 1 + j3;
                zzfv.zza(bArr, j3, (byte) charAt);
            } else if (charAt < 'ࠀ' && j3 <= j2 - 2) {
                r12 = j3 + 1;
                zzfv.zza(bArr, j3, (byte) ((charAt >>> 6) | 960));
                j = 1 + r12;
                zzfv.zza(bArr, r12, (byte) ((charAt & 63) | 128));
            } else if ((charAt < '?' || '?' < charAt) && j3 <= j2 - 3) {
                j = 1 + j3;
                zzfv.zza(bArr, j3, (byte) ((charAt >>> 12) | 480));
                j3 = 1 + j;
                zzfv.zza(bArr, j, (byte) (((charAt >>> 6) & 63) | 128));
                j = 1 + j3;
                zzfv.zza(bArr, j3, (byte) ((charAt & 63) | 128));
            } else if (j3 <= j2 - 4) {
                if (i3 + 1 != length) {
                    i3++;
                    char charAt2 = charSequence.charAt(i3);
                    if (Character.isSurrogatePair(charAt, charAt2)) {
                        int toCodePoint = Character.toCodePoint(charAt, charAt2);
                        j = 1 + j3;
                        zzfv.zza(bArr, j3, (byte) ((toCodePoint >>> 18) | PsExtractor.VIDEO_STREAM_MASK));
                        j3 = 1 + j;
                        zzfv.zza(bArr, j, (byte) (((toCodePoint >>> 12) & 63) | 128));
                        r12 = j3 + 1;
                        zzfv.zza(bArr, j3, (byte) (((toCodePoint >>> 6) & 63) | 128));
                        j = 1 + r12;
                        zzfv.zza(bArr, r12, (byte) ((toCodePoint & 63) | 128));
                    }
                }
                throw new zzga(i3 - 1, length);
            } else if ('?' > charAt || charAt > '?' || (i3 + 1 != length && Character.isSurrogatePair(charAt, charSequence.charAt(i3 + 1)))) {
                throw new ArrayIndexOutOfBoundsException("Failed writing " + charAt + " at index " + j3);
            } else {
                throw new zzga(i3, length);
            }
            i3++;
            j3 = j;
        }
        return (int) j3;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    final void zzb(java.lang.CharSequence r17, java.nio.ByteBuffer r18) {
        /*
        r16 = this;
        r8 = com.google.android.gms.internal.firebase_auth.zzfv.zzb(r18);
        r2 = r18.position();
        r2 = (long) r2;
        r4 = r8 + r2;
        r2 = r18.limit();
        r2 = (long) r2;
        r10 = r8 + r2;
        r3 = r17.length();
        r6 = (long) r3;
        r12 = r10 - r4;
        r2 = (r6 > r12 ? 1 : (r6 == r12 ? 0 : -1));
        if (r2 <= 0) goto L_0x0050;
    L_0x001d:
        r2 = new java.lang.ArrayIndexOutOfBoundsException;
        r3 = r3 + -1;
        r0 = r17;
        r3 = r0.charAt(r3);
        r4 = r18.limit();
        r5 = 37;
        r6 = new java.lang.StringBuilder;
        r6.<init>(r5);
        r5 = "Failed writing ";
        r5 = r6.append(r5);
        r3 = r5.append(r3);
        r5 = " at index ";
        r3 = r3.append(r5);
        r3 = r3.append(r4);
        r3 = r3.toString();
        r2.<init>(r3);
        throw r2;
    L_0x0050:
        r2 = 0;
    L_0x0051:
        if (r2 >= r3) goto L_0x0068;
    L_0x0053:
        r0 = r17;
        r12 = r0.charAt(r2);
        r6 = 128; // 0x80 float:1.794E-43 double:6.32E-322;
        if (r12 >= r6) goto L_0x0068;
    L_0x005d:
        r6 = 1;
        r6 = r6 + r4;
        r12 = (byte) r12;
        com.google.android.gms.internal.firebase_auth.zzfv.zza(r4, r12);
        r2 = r2 + 1;
        r4 = r6;
        goto L_0x0051;
    L_0x0068:
        if (r2 != r3) goto L_0x0194;
    L_0x006a:
        r2 = r4 - r8;
        r2 = (int) r2;
        r0 = r18;
        r0.position(r2);
    L_0x0072:
        return;
    L_0x0073:
        if (r2 >= r3) goto L_0x018a;
    L_0x0075:
        r0 = r17;
        r12 = r0.charAt(r2);
        r4 = 128; // 0x80 float:1.794E-43 double:6.32E-322;
        if (r12 >= r4) goto L_0x008e;
    L_0x007f:
        r4 = (r6 > r10 ? 1 : (r6 == r10 ? 0 : -1));
        if (r4 >= 0) goto L_0x008e;
    L_0x0083:
        r4 = 1;
        r4 = r4 + r6;
        r12 = (byte) r12;
        com.google.android.gms.internal.firebase_auth.zzfv.zza(r6, r12);
    L_0x008a:
        r2 = r2 + 1;
        r6 = r4;
        goto L_0x0073;
    L_0x008e:
        r4 = 2048; // 0x800 float:2.87E-42 double:1.0118E-320;
        if (r12 >= r4) goto L_0x00b2;
    L_0x0092:
        r4 = 2;
        r4 = r10 - r4;
        r4 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1));
        if (r4 > 0) goto L_0x00b2;
    L_0x009a:
        r4 = 1;
        r14 = r6 + r4;
        r4 = r12 >>> 6;
        r4 = r4 | 960;
        r4 = (byte) r4;
        com.google.android.gms.internal.firebase_auth.zzfv.zza(r6, r4);
        r4 = 1;
        r4 = r4 + r14;
        r6 = r12 & 63;
        r6 = r6 | 128;
        r6 = (byte) r6;
        com.google.android.gms.internal.firebase_auth.zzfv.zza(r14, r6);
        goto L_0x008a;
    L_0x00b2:
        r4 = 55296; // 0xd800 float:7.7486E-41 double:2.732E-319;
        if (r12 < r4) goto L_0x00bc;
    L_0x00b7:
        r4 = 57343; // 0xdfff float:8.0355E-41 double:2.8331E-319;
        if (r4 >= r12) goto L_0x00e8;
    L_0x00bc:
        r4 = 3;
        r4 = r10 - r4;
        r4 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1));
        if (r4 > 0) goto L_0x00e8;
    L_0x00c4:
        r4 = 1;
        r4 = r4 + r6;
        r13 = r12 >>> 12;
        r13 = r13 | 480;
        r13 = (byte) r13;
        com.google.android.gms.internal.firebase_auth.zzfv.zza(r6, r13);
        r6 = 1;
        r6 = r6 + r4;
        r13 = r12 >>> 6;
        r13 = r13 & 63;
        r13 = r13 | 128;
        r13 = (byte) r13;
        com.google.android.gms.internal.firebase_auth.zzfv.zza(r4, r13);
        r4 = 1;
        r4 = r4 + r6;
        r12 = r12 & 63;
        r12 = r12 | 128;
        r12 = (byte) r12;
        com.google.android.gms.internal.firebase_auth.zzfv.zza(r6, r12);
        goto L_0x008a;
    L_0x00e8:
        r4 = 4;
        r4 = r10 - r4;
        r4 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1));
        if (r4 > 0) goto L_0x0141;
    L_0x00f0:
        r4 = r2 + 1;
        if (r4 == r3) goto L_0x0102;
    L_0x00f4:
        r2 = r2 + 1;
        r0 = r17;
        r4 = r0.charAt(r2);
        r5 = java.lang.Character.isSurrogatePair(r12, r4);
        if (r5 != 0) goto L_0x010a;
    L_0x0102:
        r4 = new com.google.android.gms.internal.firebase_auth.zzga;
        r2 = r2 + -1;
        r4.<init>(r2, r3);
        throw r4;
    L_0x010a:
        r12 = java.lang.Character.toCodePoint(r12, r4);
        r4 = 1;
        r4 = r4 + r6;
        r13 = r12 >>> 18;
        r13 = r13 | 240;
        r13 = (byte) r13;
        com.google.android.gms.internal.firebase_auth.zzfv.zza(r6, r13);
        r6 = 1;
        r6 = r6 + r4;
        r13 = r12 >>> 12;
        r13 = r13 & 63;
        r13 = r13 | 128;
        r13 = (byte) r13;
        com.google.android.gms.internal.firebase_auth.zzfv.zza(r4, r13);
        r4 = 1;
        r14 = r6 + r4;
        r4 = r12 >>> 6;
        r4 = r4 & 63;
        r4 = r4 | 128;
        r4 = (byte) r4;
        com.google.android.gms.internal.firebase_auth.zzfv.zza(r6, r4);
        r4 = 1;
        r4 = r4 + r14;
        r6 = r12 & 63;
        r6 = r6 | 128;
        r6 = (byte) r6;
        com.google.android.gms.internal.firebase_auth.zzfv.zza(r14, r6);
        goto L_0x008a;
    L_0x0141:
        r4 = 55296; // 0xd800 float:7.7486E-41 double:2.732E-319;
        if (r4 > r12) goto L_0x0163;
    L_0x0146:
        r4 = 57343; // 0xdfff float:8.0355E-41 double:2.8331E-319;
        if (r12 > r4) goto L_0x0163;
    L_0x014b:
        r4 = r2 + 1;
        if (r4 == r3) goto L_0x015d;
    L_0x014f:
        r4 = r2 + 1;
        r0 = r17;
        r4 = r0.charAt(r4);
        r4 = java.lang.Character.isSurrogatePair(r12, r4);
        if (r4 != 0) goto L_0x0163;
    L_0x015d:
        r4 = new com.google.android.gms.internal.firebase_auth.zzga;
        r4.<init>(r2, r3);
        throw r4;
    L_0x0163:
        r2 = new java.lang.ArrayIndexOutOfBoundsException;
        r3 = 46;
        r4 = new java.lang.StringBuilder;
        r4.<init>(r3);
        r3 = "Failed writing ";
        r3 = r4.append(r3);
        r3 = r3.append(r12);
        r4 = " at index ";
        r3 = r3.append(r4);
        r3 = r3.append(r6);
        r3 = r3.toString();
        r2.<init>(r3);
        throw r2;
    L_0x018a:
        r2 = r6 - r8;
        r2 = (int) r2;
        r0 = r18;
        r0.position(r2);
        goto L_0x0072;
    L_0x0194:
        r6 = r4;
        goto L_0x0073;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_auth.zzgb.zzb(java.lang.CharSequence, java.nio.ByteBuffer):void");
    }
}
