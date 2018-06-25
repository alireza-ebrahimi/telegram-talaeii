package com.google.android.gms.common.api.internal;

import android.os.IBinder;
import android.os.IBinder.DeathRecipient;
import com.google.android.gms.common.api.zze;
import java.lang.ref.WeakReference;
import java.util.NoSuchElementException;

final class zzdm implements DeathRecipient, zzdn {
    private final WeakReference<BasePendingResult<?>> zzgbv;
    private final WeakReference<zze> zzgbw;
    private final WeakReference<IBinder> zzgbx;

    private zzdm(BasePendingResult<?> basePendingResult, zze zze, IBinder iBinder) {
        this.zzgbw = new WeakReference(zze);
        this.zzgbv = new WeakReference(basePendingResult);
        this.zzgbx = new WeakReference(iBinder);
    }

    private final void zzale() {
        BasePendingResult basePendingResult = (BasePendingResult) this.zzgbv.get();
        zze zze = (zze) this.zzgbw.get();
        if (!(zze == null || basePendingResult == null)) {
            zze.remove(basePendingResult.zzaid().intValue());
        }
        IBinder iBinder = (IBinder) this.zzgbx.get();
        if (iBinder != null) {
            try {
                iBinder.unlinkToDeath(this, 0);
            } catch (NoSuchElementException e) {
            }
        }
    }

    public final void binderDied() {
        zzale();
    }

    public final void zzc(BasePendingResult<?> basePendingResult) {
        zzale();
    }
}
