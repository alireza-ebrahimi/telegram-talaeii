package com.google.android.gms.dynamite;

import android.content.Context;
import com.google.android.gms.dynamite.DynamiteModule.zzc;
import com.google.android.gms.dynamite.DynamiteModule.zzd;

final class zze implements zzd {
    zze() {
    }

    public final zzj zza(Context context, String str, zzi zzi) throws zzc {
        zzj zzj = new zzj();
        zzj.zzhds = zzi.zzx(context, str);
        if (zzj.zzhds != 0) {
            zzj.zzhdt = zzi.zzc(context, str, false);
        } else {
            zzj.zzhdt = zzi.zzc(context, str, true);
        }
        if (zzj.zzhds == 0 && zzj.zzhdt == 0) {
            zzj.zzhdu = 0;
        } else if (zzj.zzhds >= zzj.zzhdt) {
            zzj.zzhdu = -1;
        } else {
            zzj.zzhdu = 1;
        }
        return zzj;
    }
}
