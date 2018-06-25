package com.google.android.gms.internal;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class zzbfo {
    private static long zza(long j, long j2, long j3) {
        long j4 = (j ^ j2) * j3;
        j4 = ((j4 ^ (j4 >>> 47)) ^ j2) * j3;
        return (j4 ^ (j4 >>> 47)) * j3;
    }

    private static long zza(byte[] bArr, int i, int i2) {
        long[] jArr = new long[2];
        long[] jArr2 = new long[2];
        int i3 = (((i2 - 1) / 64) << 6) + 0;
        int i4 = (((i2 - 1) & 63) + i3) - 63;
        long zzc = 95310865018149119L + zzc(bArr, 0);
        long j = 2480279821605975764L;
        long j2 = 1390051526045402406L;
        int i5 = i;
        while (true) {
            zzc = Long.rotateRight(((zzc + j) + jArr[0]) + zzc(bArr, i5 + 8), 37) * -5435081209227447693L;
            long j3 = zzc ^ jArr2[1];
            long rotateRight = (Long.rotateRight((j + jArr[1]) + zzc(bArr, i5 + 48), 42) * -5435081209227447693L) + (jArr[0] + zzc(bArr, i5 + 40));
            long rotateRight2 = Long.rotateRight(j2 + jArr2[0], 33) * -5435081209227447693L;
            zza(bArr, i5, jArr[1] * -5435081209227447693L, jArr2[0] + j3, jArr);
            byte[] bArr2 = bArr;
            zza(bArr2, i5 + 32, rotateRight2 + jArr2[1], rotateRight + zzc(bArr, i5 + 16), jArr2);
            i5 += 64;
            if (i5 == i3) {
                long j4 = -5435081209227447693L + ((255 & j3) << 1);
                jArr2[0] = jArr2[0] + ((long) ((i2 - 1) & 63));
                jArr[0] = jArr[0] + jArr2[0];
                jArr2[0] = jArr2[0] + jArr[0];
                long rotateRight3 = Long.rotateRight(((rotateRight2 + rotateRight) + jArr[0]) + zzc(bArr, i4 + 8), 37) * j4;
                j2 = Long.rotateRight((jArr[1] + rotateRight) + zzc(bArr, i4 + 48), 42) * j4;
                rotateRight = rotateRight3 ^ (jArr2[1] * 9);
                rotateRight2 = j2 + ((jArr[0] * 9) + zzc(bArr, i4 + 40));
                j3 = Long.rotateRight(jArr2[0] + j3, 33) * j4;
                zza(bArr, i4, jArr[1] * j4, rotateRight + jArr2[0], jArr);
                bArr2 = bArr;
                zza(bArr2, i4 + 32, j3 + jArr2[1], rotateRight2 + zzc(bArr, i4 + 16), jArr2);
                return zza((zza(jArr[0], jArr2[0], j4) + (((rotateRight2 >>> 47) ^ rotateRight2) * -4348849565147123417L)) + rotateRight, zza(jArr[1], jArr2[1], j4) + j3, j4);
            }
            j2 = j3;
            j = rotateRight;
            zzc = rotateRight2;
        }
    }

    private static void zza(byte[] bArr, int i, long j, long j2, long[] jArr) {
        long zzc = zzc(bArr, i);
        long zzc2 = zzc(bArr, i + 8);
        long zzc3 = zzc(bArr, i + 16);
        long zzc4 = zzc(bArr, i + 24);
        zzc += j;
        zzc2 = (zzc2 + zzc) + zzc3;
        zzc3 = Long.rotateRight(zzc2, 44) + Long.rotateRight((j2 + zzc) + zzc4, 21);
        jArr[0] = zzc2 + zzc4;
        jArr[1] = zzc + zzc3;
    }

    private static int zzb(byte[] bArr, int i) {
        return (((bArr[i] & 255) | ((bArr[i + 1] & 255) << 8)) | ((bArr[i + 2] & 255) << 16)) | ((bArr[i + 3] & 255) << 24);
    }

    private static long zzc(byte[] bArr, int i) {
        ByteBuffer wrap = ByteBuffer.wrap(bArr, i, 8);
        wrap.order(ByteOrder.LITTLE_ENDIAN);
        return wrap.getLong();
    }

    public static long zzi(byte[] bArr) {
        int length = bArr.length;
        if (length < 0 || length > bArr.length) {
            throw new IndexOutOfBoundsException("Out of bound index with offput: 0 and length: " + length);
        } else if (length <= 32) {
            if (length > 16) {
                r6 = -7286425919675154353L + ((long) (length << 1));
                r4 = -5435081209227447693L * zzc(bArr, 0);
                r10 = zzc(bArr, 8);
                r12 = zzc(bArr, (length + 0) - 8) * r6;
                return zza((zzc(bArr, (length + 0) - 16) * -7286425919675154353L) + (Long.rotateRight(r4 + r10, 43) + Long.rotateRight(r12, 30)), (r4 + Long.rotateRight(-7286425919675154353L + r10, 18)) + r12, r6);
            } else if (length >= 8) {
                r6 = -7286425919675154353L + ((long) (length << 1));
                r4 = -7286425919675154353L + zzc(bArr, 0);
                long zzc = zzc(bArr, (length + 0) - 8);
                return zza((Long.rotateRight(zzc, 37) * r6) + r4, (Long.rotateRight(r4, 25) + zzc) * r6, r6);
            } else if (length >= 4) {
                return zza(((((long) zzb(bArr, 0)) & 4294967295L) << 3) + ((long) length), ((long) zzb(bArr, (length + 0) - 4)) & 4294967295L, -7286425919675154353L + ((long) (length << 1)));
            } else if (length <= 0) {
                return -7286425919675154353L;
            } else {
                int i = (bArr[0] & 255) + ((bArr[(length >> 1) + 0] & 255) << 8);
                r2 = (((long) (((bArr[(length - 1) + 0] & 255) << 2) + length)) * -4348849565147123417L) ^ (((long) i) * -7286425919675154353L);
                return (r2 ^ (r2 >>> 47)) * -7286425919675154353L;
            }
        } else if (length > 64) {
            return zza(bArr, 0, length);
        } else {
            r6 = -7286425919675154353L + ((long) (length << 1));
            r10 = zzc(bArr, 0) * -7286425919675154353L;
            r4 = zzc(bArr, 8);
            r12 = zzc(bArr, (length + 0) - 8) * r6;
            r2 = (zzc(bArr, (length + 0) - 16) * -7286425919675154353L) + (Long.rotateRight(r10 + r4, 43) + Long.rotateRight(r12, 30));
            r4 = zza(r2, (Long.rotateRight(r4 - 7286425919675154353L, 18) + r10) + r12, r6);
            r12 = zzc(bArr, 16) * r6;
            long zzc2 = zzc(bArr, 24);
            long zzc3 = (r2 + zzc(bArr, (length + 0) - 32)) * r6;
            return zza(((zzc(bArr, (length + 0) - 24) + r4) * r6) + (Long.rotateRight(r12 + zzc2, 43) + Long.rotateRight(zzc3, 30)), (Long.rotateRight(zzc2 + r10, 18) + r12) + zzc3, r6);
        }
    }
}
