package com.google.android.gms.vision;

import android.util.SparseArray;
import com.google.android.gms.common.util.VisibleForTesting;
import javax.annotation.concurrent.GuardedBy;

@VisibleForTesting
public final class zzc {
    private static final Object sLock = new Object();
    @GuardedBy("sLock")
    private static int zzas = 0;
    @GuardedBy("sLock")
    private SparseArray<Integer> zzat = new SparseArray();
    @GuardedBy("sLock")
    private SparseArray<Integer> zzau = new SparseArray();

    public final int zzb(int i) {
        int intValue;
        synchronized (sLock) {
            Integer num = (Integer) this.zzat.get(i);
            if (num != null) {
                intValue = num.intValue();
            } else {
                intValue = zzas;
                zzas++;
                this.zzat.append(i, Integer.valueOf(intValue));
                this.zzau.append(intValue, Integer.valueOf(i));
            }
        }
        return intValue;
    }

    public final int zzc(int i) {
        int intValue;
        synchronized (sLock) {
            intValue = ((Integer) this.zzau.get(i)).intValue();
        }
        return intValue;
    }
}
