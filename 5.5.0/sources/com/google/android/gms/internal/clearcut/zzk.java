package com.google.android.gms.internal.clearcut;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class zzk {
    private static int zza(byte[] bArr, int i) {
        return (((bArr[i] & 255) | ((bArr[i + 1] & 255) << 8)) | ((bArr[i + 2] & 255) << 16)) | ((bArr[i + 3] & 255) << 24);
    }

    private static long zza(long j, long j2, long j3) {
        long j4 = (j ^ j2) * j3;
        j4 = ((j4 ^ (j4 >>> 47)) ^ j2) * j3;
        return (j4 ^ (j4 >>> 47)) * j3;
    }

    public static long zza(byte[] bArr) {
        int length = bArr.length;
        if (length < 0 || length > bArr.length) {
            throw new IndexOutOfBoundsException("Out of bound index with offput: 0 and length: " + length);
        } else if (length <= 32) {
            if (length > 16) {
                r6 = -7286425919675154353L + ((long) (length << 1));
                r4 = -5435081209227447693L * zzb(bArr, 0);
                r8 = zzb(bArr, 8);
                r10 = zzb(bArr, (length + 0) - 8) * r6;
                return zza((zzb(bArr, (length + 0) - 16) * -7286425919675154353L) + (Long.rotateRight(r4 + r8, 43) + Long.rotateRight(r10, 30)), (r4 + Long.rotateRight(r8 - 7286425919675154353L, 18)) + r10, r6);
            } else if (length >= 8) {
                r6 = -7286425919675154353L + ((long) (length << 1));
                r4 = -7286425919675154353L + zzb(bArr, 0);
                r8 = zzb(bArr, (length + 0) - 8);
                return zza((Long.rotateRight(r8, 37) * r6) + r4, (Long.rotateRight(r4, 25) + r8) * r6, r6);
            } else if (length >= 4) {
                return zza(((((long) zza(bArr, 0)) & 4294967295L) << 3) + ((long) length), ((long) zza(bArr, (length + 0) - 4)) & 4294967295L, -7286425919675154353L + ((long) (length << 1)));
            } else if (length <= 0) {
                return -7286425919675154353L;
            } else {
                int i = (bArr[0] & 255) + ((bArr[(length >> 1) + 0] & 255) << 8);
                r2 = (((long) (((bArr[(length - 1) + 0] & 255) << 2) + length)) * -4348849565147123417L) ^ (((long) i) * -7286425919675154353L);
                return (r2 ^ (r2 >>> 47)) * -7286425919675154353L;
            }
        } else if (length <= 64) {
            r6 = -7286425919675154353L + ((long) (length << 1));
            r8 = zzb(bArr, 0) * -7286425919675154353L;
            r4 = zzb(bArr, 8);
            r10 = zzb(bArr, (length + 0) - 8) * r6;
            r2 = (zzb(bArr, (length + 0) - 16) * -7286425919675154353L) + (Long.rotateRight(r8 + r4, 43) + Long.rotateRight(r10, 30));
            r4 = zza(r2, (Long.rotateRight(r4 - 7286425919675154353L, 18) + r8) + r10, r6);
            r10 = zzb(bArr, 16) * r6;
            long zzb = zzb(bArr, 24);
            long zzb2 = (r2 + zzb(bArr, (length + 0) - 32)) * r6;
            return zza(((zzb(bArr, (length + 0) - 24) + r4) * r6) + (Long.rotateRight(r10 + zzb, 43) + Long.rotateRight(zzb2, 30)), (Long.rotateRight(zzb + r8, 18) + r10) + zzb2, r6);
        } else {
            long[] jArr = new long[2];
            long[] jArr2 = new long[2];
            int i2 = (((length - 1) / 64) << 6) + 0;
            int i3 = (((length - 1) & 63) + i2) - 63;
            r10 = 95310865018149119L + zzb(bArr, 0);
            r6 = 2480279821605975764L;
            r4 = 1390051526045402406L;
            int i4 = 0;
            while (true) {
                r10 = Long.rotateRight(((r10 + r6) + jArr[0]) + zzb(bArr, i4 + 8), 37) * -5435081209227447693L;
                long j = r10 ^ jArr2[1];
                long rotateRight = (Long.rotateRight((r6 + jArr[1]) + zzb(bArr, i4 + 48), 42) * -5435081209227447693L) + (jArr[0] + zzb(bArr, i4 + 40));
                long rotateRight2 = Long.rotateRight(r4 + jArr2[0], 33) * -5435081209227447693L;
                zza(bArr, i4, jArr[1] * -5435081209227447693L, jArr2[0] + j, jArr);
                byte[] bArr2 = bArr;
                zza(bArr2, i4 + 32, rotateRight2 + jArr2[1], rotateRight + zzb(bArr, i4 + 16), jArr2);
                i4 += 64;
                if (i4 == i2) {
                    long j2 = -5435081209227447693L + ((255 & j) << 1);
                    jArr2[0] = jArr2[0] + ((long) ((length - 1) & 63));
                    jArr[0] = jArr[0] + jArr2[0];
                    jArr2[0] = jArr2[0] + jArr[0];
                    r2 = Long.rotateRight(((rotateRight2 + rotateRight) + jArr[0]) + zzb(bArr, i3 + 8), 37) * j2;
                    r4 = Long.rotateRight((jArr[1] + rotateRight) + zzb(bArr, i3 + 48), 42) * j2;
                    rotateRight = r2 ^ (jArr2[1] * 9);
                    rotateRight2 = r4 + ((jArr[0] * 9) + zzb(bArr, i3 + 40));
                    j = Long.rotateRight(jArr2[0] + j, 33) * j2;
                    zza(bArr, i3, jArr[1] * j2, rotateRight + jArr2[0], jArr);
                    bArr2 = bArr;
                    zza(bArr2, i3 + 32, j + jArr2[1], rotateRight2 + zzb(bArr, i3 + 16), jArr2);
                    return zza((zza(jArr[0], jArr2[0], j2) + (((rotateRight2 >>> 47) ^ rotateRight2) * -4348849565147123417L)) + rotateRight, zza(jArr[1], jArr2[1], j2) + j, j2);
                }
                r4 = j;
                r6 = rotateRight;
                r10 = rotateRight2;
            }
        }
    }

    private static void zza(byte[] bArr, int i, long j, long j2, long[] jArr) {
        long zzb = zzb(bArr, i);
        long zzb2 = zzb(bArr, i + 8);
        long zzb3 = zzb(bArr, i + 16);
        long zzb4 = zzb(bArr, i + 24);
        zzb += j;
        zzb2 = (zzb2 + zzb) + zzb3;
        zzb3 = Long.rotateRight(zzb2, 44) + Long.rotateRight((j2 + zzb) + zzb4, 21);
        jArr[0] = zzb2 + zzb4;
        jArr[1] = zzb + zzb3;
    }

    private static long zzb(byte[] bArr, int i) {
        ByteBuffer wrap = ByteBuffer.wrap(bArr, i, 8);
        wrap.order(ByteOrder.LITTLE_ENDIAN);
        return wrap.getLong();
    }
}
