package com.google.android.gms.internal;

import android.content.SharedPreferences.Editor;
import android.support.annotation.WorkerThread;
import android.util.Pair;
import com.google.android.gms.common.internal.zzbq;

public final class zzcjy {
    private /* synthetic */ zzcju zzjmi;
    private String zzjmk;
    private final String zzjml;
    private final String zzjmm;
    private final long zzjmn;

    private zzcjy(zzcju zzcju, String str, long j) {
        this.zzjmi = zzcju;
        zzbq.zzgv(str);
        zzbq.checkArgument(j > 0);
        this.zzjmk = String.valueOf(str).concat(":start");
        this.zzjml = String.valueOf(str).concat(":count");
        this.zzjmm = String.valueOf(str).concat(":value");
        this.zzjmn = j;
    }

    @WorkerThread
    private final void zzabg() {
        this.zzjmi.zzwj();
        long currentTimeMillis = this.zzjmi.zzxx().currentTimeMillis();
        Editor edit = this.zzjmi.zzbbd().edit();
        edit.remove(this.zzjml);
        edit.remove(this.zzjmm);
        edit.putLong(this.zzjmk, currentTimeMillis);
        edit.apply();
    }

    @WorkerThread
    private final long zzabi() {
        return this.zzjmi.zzbbd().getLong(this.zzjmk, 0);
    }

    @WorkerThread
    public final Pair<String, Long> zzabh() {
        this.zzjmi.zzwj();
        this.zzjmi.zzwj();
        long zzabi = zzabi();
        if (zzabi == 0) {
            zzabg();
            zzabi = 0;
        } else {
            zzabi = Math.abs(zzabi - this.zzjmi.zzxx().currentTimeMillis());
        }
        if (zzabi < this.zzjmn) {
            return null;
        }
        if (zzabi > (this.zzjmn << 1)) {
            zzabg();
            return null;
        }
        String string = this.zzjmi.zzbbd().getString(this.zzjmm, null);
        long j = this.zzjmi.zzbbd().getLong(this.zzjml, 0);
        zzabg();
        return (string == null || j <= 0) ? zzcju.zzjlk : new Pair(string, Long.valueOf(j));
    }

    @WorkerThread
    public final void zzf(String str, long j) {
        this.zzjmi.zzwj();
        if (zzabi() == 0) {
            zzabg();
        }
        if (str == null) {
            str = "";
        }
        long j2 = this.zzjmi.zzbbd().getLong(this.zzjml, 0);
        if (j2 <= 0) {
            Editor edit = this.zzjmi.zzbbd().edit();
            edit.putString(this.zzjmm, str);
            edit.putLong(this.zzjml, 1);
            edit.apply();
            return;
        }
        Object obj = (this.zzjmi.zzayl().zzbcr().nextLong() & Long.MAX_VALUE) < Long.MAX_VALUE / (j2 + 1) ? 1 : null;
        Editor edit2 = this.zzjmi.zzbbd().edit();
        if (obj != null) {
            edit2.putString(this.zzjmm, str);
        }
        edit2.putLong(this.zzjml, j2 + 1);
        edit2.apply();
    }
}
