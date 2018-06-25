package com.google.android.gms.common;

import android.content.Context;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzba;
import com.google.android.gms.common.internal.zzbb;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.dynamite.DynamiteModule;

@Hide
final class zzg {
    private static volatile zzba zzfra;
    private static final Object zzfrb = new Object();
    private static Context zzfrc;

    static zzp zza(String str, zzh zzh, boolean z) {
        boolean z2 = true;
        try {
            if (zzfra == null) {
                zzbq.checkNotNull(zzfrc);
                synchronized (zzfrb) {
                    if (zzfra == null) {
                        zzfra = zzbb.zzan(DynamiteModule.zza(zzfrc, DynamiteModule.zzhdl, "com.google.android.gms.googlecertificates").zzhk("com.google.android.gms.common.GoogleCertificatesImpl"));
                    }
                }
            }
            zzbq.checkNotNull(zzfrc);
            try {
                if (zzfra.zza(new zzn(str, zzh, z), zzn.zzz(zzfrc.getPackageManager()))) {
                    return zzp.zzahj();
                }
                if (z || !zza(str, zzh, true).zzfrm) {
                    z2 = false;
                }
                return zzp.zza(str, zzh, z, z2);
            } catch (Throwable e) {
                Log.e("GoogleCertificates", "Failed to get Google certificates from remote", e);
                return zzp.zzd("module call", e);
            }
        } catch (Throwable e2) {
            return zzp.zzd("module init", e2);
        }
    }

    static synchronized void zzch(Context context) {
        synchronized (zzg.class) {
            if (zzfrc != null) {
                Log.w("GoogleCertificates", "GoogleCertificates has been initialized already");
            } else if (context != null) {
                zzfrc = context.getApplicationContext();
            }
        }
    }
}
