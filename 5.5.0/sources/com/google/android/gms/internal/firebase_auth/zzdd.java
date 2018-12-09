package com.google.android.gms.internal.firebase_auth;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import org.telegram.messenger.exoplayer2.C3446C;

public final class zzdd {
    public static final byte[] EMPTY_BYTE_ARRAY;
    private static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
    static final Charset UTF_8 = Charset.forName(C3446C.UTF8_NAME);
    private static final ByteBuffer zzru;
    private static final zzcd zzrv;

    static {
        byte[] bArr = new byte[0];
        EMPTY_BYTE_ARRAY = bArr;
        zzru = ByteBuffer.wrap(bArr);
        bArr = EMPTY_BYTE_ARRAY;
        zzrv = zzcd.zza(bArr, 0, bArr.length, false);
    }

    static <T> T checkNotNull(T t) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException();
    }

    public static int hashCode(byte[] bArr) {
        int length = bArr.length;
        length = zza(length, bArr, 0, length);
        return length == 0 ? 1 : length;
    }

    static int zza(int i, byte[] bArr, int i2, int i3) {
        for (int i4 = i2; i4 < i2 + i3; i4++) {
            i = (i * 31) + bArr[i4];
        }
        return i;
    }

    static Object zza(Object obj, Object obj2) {
        return ((zzeh) obj).zzdz().zza((zzeh) obj2).zzec();
    }

    static <T> T zza(T t, String str) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(str);
    }

    public static boolean zzd(byte[] bArr) {
        return zzfx.zzd(bArr);
    }

    public static String zze(byte[] bArr) {
        return new String(bArr, UTF_8);
    }

    static boolean zzf(zzeh zzeh) {
        return false;
    }

    public static int zzh(boolean z) {
        return z ? 1231 : 1237;
    }

    public static int zzk(long j) {
        return (int) ((j >>> 32) ^ j);
    }
}
