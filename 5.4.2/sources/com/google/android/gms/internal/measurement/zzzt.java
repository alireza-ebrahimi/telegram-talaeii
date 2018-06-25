package com.google.android.gms.internal.measurement;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import org.telegram.messenger.exoplayer2.C3446C;

public final class zzzt {
    private static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
    static final Charset UTF_8 = Charset.forName(C3446C.UTF8_NAME);
    public static final byte[] zzbta;
    private static final ByteBuffer zzbtb;
    private static final zzzg zzbtc;

    static {
        byte[] bArr = new byte[0];
        zzbta = bArr;
        zzbtb = ByteBuffer.wrap(bArr);
        bArr = zzbta;
        zzbtc = zzzg.zza(bArr, 0, bArr.length, false);
    }

    static <T> T checkNotNull(T t) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException();
    }

    static int zza(int i, byte[] bArr, int i2, int i3) {
        for (int i4 = i2; i4 < i2 + i3; i4++) {
            i = (i * 31) + bArr[i4];
        }
        return i;
    }

    static <T> T zza(T t, String str) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(str);
    }
}
