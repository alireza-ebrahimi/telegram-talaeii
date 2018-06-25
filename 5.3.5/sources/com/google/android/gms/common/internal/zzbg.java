package com.google.android.gms.common.internal;

import android.support.annotation.Nullable;

@Hide
public final class zzbg {
    public static boolean equal(@Nullable Object obj, @Nullable Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    public static zzbi zzx(Object obj) {
        return new zzbi(obj);
    }
}
