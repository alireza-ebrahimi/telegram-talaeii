package com.google.android.gms.internal;

import java.io.UnsupportedEncodingException;

public class zzav extends zzr<String> {
    private final Object mLock = new Object();
    private zzz<String> zzci;

    public zzav(int i, String str, zzz<String> zzz, zzy zzy) {
        super(i, str, zzy);
        this.zzci = zzz;
    }

    protected final zzx<String> zza(zzp zzp) {
        Object str;
        try {
            str = new String(zzp.data, zzap.zzb(zzp.zzab));
        } catch (UnsupportedEncodingException e) {
            str = new String(zzp.data);
        }
        return zzx.zza(str, zzap.zzb(zzp));
    }

    protected /* synthetic */ void zza(Object obj) {
        zzh((String) obj);
    }

    protected void zzh(String str) {
        synchronized (this.mLock) {
            zzz zzz = this.zzci;
        }
        if (zzz != null) {
            zzz.zzb(str);
        }
    }
}
