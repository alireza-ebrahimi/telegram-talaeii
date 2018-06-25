package com.google.android.gms.vision;

import android.util.SparseArray;
import com.google.android.gms.common.internal.Hide;

@Hide
public final class zzc {
    private static final Object sLock = new Object();
    private static int zzlgg = 0;
    private SparseArray<Integer> zzlgh = new SparseArray();
    private SparseArray<Integer> zzlgi = new SparseArray();

    @Hide
    public final int zzfm(int i) {
        int intValue;
        synchronized (sLock) {
            Integer num = (Integer) this.zzlgh.get(i);
            if (num != null) {
                intValue = num.intValue();
            } else {
                intValue = zzlgg;
                zzlgg++;
                this.zzlgh.append(i, Integer.valueOf(intValue));
                this.zzlgi.append(intValue, Integer.valueOf(i));
            }
        }
        return intValue;
    }

    @Hide
    public final int zzfn(int i) {
        int intValue;
        synchronized (sLock) {
            intValue = ((Integer) this.zzlgi.get(i)).intValue();
        }
        return intValue;
    }
}
