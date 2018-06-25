package com.google.android.gms.common;

import com.google.android.gms.common.internal.Hide;
import java.lang.ref.WeakReference;

@Hide
abstract class zzj extends zzh {
    private static final WeakReference<byte[]> zzfrg = new WeakReference(null);
    private WeakReference<byte[]> zzfrf = zzfrg;

    zzj(byte[] bArr) {
        super(bArr);
    }

    final byte[] getBytes() {
        byte[] bArr;
        synchronized (this) {
            bArr = (byte[]) this.zzfrf.get();
            if (bArr == null) {
                bArr = zzahi();
                this.zzfrf = new WeakReference(bArr);
            }
        }
        return bArr;
    }

    protected abstract byte[] zzahi();
}
