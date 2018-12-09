package com.google.android.gms.common;

import java.util.Arrays;

final class zzb extends CertData {
    private final byte[] zzbd;

    zzb(byte[] bArr) {
        super(Arrays.copyOfRange(bArr, 0, 25));
        this.zzbd = bArr;
    }

    final byte[] getBytes() {
        return this.zzbd;
    }
}
