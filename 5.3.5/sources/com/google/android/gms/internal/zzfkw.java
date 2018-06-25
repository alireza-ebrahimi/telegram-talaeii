package com.google.android.gms.internal;

import org.telegram.customization.fetch.FetchService;
import org.telegram.messenger.exoplayer2.extractor.ts.PsExtractor;

final class zzfkw extends zzfkt {
    zzfkw() {
    }

    private static int zza(byte[] bArr, int i, long j, int i2) {
        switch (i2) {
            case 0:
                return zzfks.zzmu(i);
            case 1:
                return zzfks.zzam(i, zzfkq.zzb(bArr, j));
            case 2:
                return zzfks.zzi(i, zzfkq.zzb(bArr, j), zzfkq.zzb(bArr, 1 + j));
            default:
                throw new AssertionError();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int zzb(byte[] r9, long r10, int r12) {
        /*
        r0 = 16;
        if (r12 >= r0) goto L_0x001b;
    L_0x0004:
        r0 = 0;
    L_0x0005:
        r1 = r12 - r0;
        r2 = (long) r0;
        r2 = r2 + r10;
        r0 = r1;
    L_0x000a:
        r1 = 0;
        r4 = r2;
    L_0x000c:
        if (r0 <= 0) goto L_0x002f;
    L_0x000e:
        r2 = 1;
        r2 = r2 + r4;
        r1 = com.google.android.gms.internal.zzfkq.zzb(r9, r4);
        if (r1 < 0) goto L_0x002e;
    L_0x0017:
        r0 = r0 + -1;
        r4 = r2;
        goto L_0x000c;
    L_0x001b:
        r0 = 0;
        r2 = r10;
    L_0x001d:
        if (r0 >= r12) goto L_0x002c;
    L_0x001f:
        r4 = 1;
        r4 = r4 + r2;
        r1 = com.google.android.gms.internal.zzfkq.zzb(r9, r2);
        if (r1 < 0) goto L_0x0005;
    L_0x0028:
        r0 = r0 + 1;
        r2 = r4;
        goto L_0x001d;
    L_0x002c:
        r0 = r12;
        goto L_0x0005;
    L_0x002e:
        r4 = r2;
    L_0x002f:
        if (r0 != 0) goto L_0x0033;
    L_0x0031:
        r0 = 0;
    L_0x0032:
        return r0;
    L_0x0033:
        r0 = r0 + -1;
        r2 = -32;
        if (r1 >= r2) goto L_0x0050;
    L_0x0039:
        if (r0 != 0) goto L_0x003d;
    L_0x003b:
        r0 = r1;
        goto L_0x0032;
    L_0x003d:
        r0 = r0 + -1;
        r2 = -62;
        if (r1 < r2) goto L_0x004e;
    L_0x0043:
        r2 = 1;
        r2 = r2 + r4;
        r1 = com.google.android.gms.internal.zzfkq.zzb(r9, r4);
        r4 = -65;
        if (r1 <= r4) goto L_0x000a;
    L_0x004e:
        r0 = -1;
        goto L_0x0032;
    L_0x0050:
        r2 = -16;
        if (r1 >= r2) goto L_0x0087;
    L_0x0054:
        r2 = 2;
        if (r0 >= r2) goto L_0x005c;
    L_0x0057:
        r0 = zza(r9, r1, r4, r0);
        goto L_0x0032;
    L_0x005c:
        r0 = r0 + -2;
        r2 = 1;
        r6 = r4 + r2;
        r2 = com.google.android.gms.internal.zzfkq.zzb(r9, r4);
        r3 = -65;
        if (r2 > r3) goto L_0x0085;
    L_0x006a:
        r3 = -32;
        if (r1 != r3) goto L_0x0072;
    L_0x006e:
        r3 = -96;
        if (r2 < r3) goto L_0x0085;
    L_0x0072:
        r3 = -19;
        if (r1 != r3) goto L_0x007a;
    L_0x0076:
        r1 = -96;
        if (r2 >= r1) goto L_0x0085;
    L_0x007a:
        r2 = 1;
        r2 = r2 + r6;
        r1 = com.google.android.gms.internal.zzfkq.zzb(r9, r6);
        r4 = -65;
        if (r1 <= r4) goto L_0x000a;
    L_0x0085:
        r0 = -1;
        goto L_0x0032;
    L_0x0087:
        r2 = 3;
        if (r0 >= r2) goto L_0x008f;
    L_0x008a:
        r0 = zza(r9, r1, r4, r0);
        goto L_0x0032;
    L_0x008f:
        r0 = r0 + -3;
        r2 = 1;
        r2 = r2 + r4;
        r4 = com.google.android.gms.internal.zzfkq.zzb(r9, r4);
        r5 = -65;
        if (r4 > r5) goto L_0x00bb;
    L_0x009c:
        r1 = r1 << 28;
        r4 = r4 + 112;
        r1 = r1 + r4;
        r1 = r1 >> 30;
        if (r1 != 0) goto L_0x00bb;
    L_0x00a5:
        r4 = 1;
        r4 = r4 + r2;
        r1 = com.google.android.gms.internal.zzfkq.zzb(r9, r2);
        r2 = -65;
        if (r1 > r2) goto L_0x00bb;
    L_0x00b0:
        r2 = 1;
        r2 = r2 + r4;
        r1 = com.google.android.gms.internal.zzfkq.zzb(r9, r4);
        r4 = -65;
        if (r1 <= r4) goto L_0x000a;
    L_0x00bb:
        r0 = -1;
        goto L_0x0032;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzfkw.zzb(byte[], long, int):int");
    }

    final int zzb(int i, byte[] bArr, int i2, int i3) {
        if (((i2 | i3) | (bArr.length - i3)) < 0) {
            throw new ArrayIndexOutOfBoundsException(String.format("Array length=%d, index=%d, limit=%d", new Object[]{Integer.valueOf(bArr.length), Integer.valueOf(i2), Integer.valueOf(i3)}));
        }
        long j = (long) i2;
        return zzb(bArr, j, (int) (((long) i3) - j));
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
            zzfkq.zza(bArr, j, (byte) charAt);
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
                zzfkq.zza(bArr, j3, (byte) charAt);
            } else if (charAt < 'ࠀ' && j3 <= j2 - 2) {
                r12 = j3 + 1;
                zzfkq.zza(bArr, j3, (byte) ((charAt >>> 6) | 960));
                j = 1 + r12;
                zzfkq.zza(bArr, r12, (byte) ((charAt & 63) | 128));
            } else if ((charAt < '?' || '?' < charAt) && j3 <= j2 - 3) {
                j = 1 + j3;
                zzfkq.zza(bArr, j3, (byte) ((charAt >>> 12) | FetchService.QUERY_SINGLE));
                j3 = 1 + j;
                zzfkq.zza(bArr, j, (byte) (((charAt >>> 6) & 63) | 128));
                j = 1 + j3;
                zzfkq.zza(bArr, j3, (byte) ((charAt & 63) | 128));
            } else if (j3 <= j2 - 4) {
                if (i3 + 1 != length) {
                    i3++;
                    char charAt2 = charSequence.charAt(i3);
                    if (Character.isSurrogatePair(charAt, charAt2)) {
                        int toCodePoint = Character.toCodePoint(charAt, charAt2);
                        j = 1 + j3;
                        zzfkq.zza(bArr, j3, (byte) ((toCodePoint >>> 18) | PsExtractor.VIDEO_STREAM_MASK));
                        j3 = 1 + j;
                        zzfkq.zza(bArr, j, (byte) (((toCodePoint >>> 12) & 63) | 128));
                        r12 = j3 + 1;
                        zzfkq.zza(bArr, j3, (byte) (((toCodePoint >>> 6) & 63) | 128));
                        j = 1 + r12;
                        zzfkq.zza(bArr, r12, (byte) ((toCodePoint & 63) | 128));
                    }
                }
                throw new zzfkv(i3 - 1, length);
            } else if ('?' > charAt || charAt > '?' || (i3 + 1 != length && Character.isSurrogatePair(charAt, charSequence.charAt(i3 + 1)))) {
                throw new ArrayIndexOutOfBoundsException("Failed writing " + charAt + " at index " + j3);
            } else {
                throw new zzfkv(i3, length);
            }
            i3++;
            j3 = j;
        }
        return (int) j3;
    }
}
