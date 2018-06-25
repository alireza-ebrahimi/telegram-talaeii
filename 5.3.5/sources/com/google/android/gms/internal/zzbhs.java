package com.google.android.gms.internal;

import com.google.android.gms.common.internal.Hide;

public abstract class zzbhs extends zzbhp implements zzbgp {
    @Hide
    public final int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!getClass().isInstance(obj)) {
            return false;
        }
        zzbhp zzbhp = (zzbhp) obj;
        for (zzbhq zzbhq : zzabz().values()) {
            if (zza(zzbhq)) {
                if (!zzbhp.zza(zzbhq)) {
                    return false;
                }
                if (!zzb(zzbhq).equals(zzbhp.zzb(zzbhq))) {
                    return false;
                }
            } else if (zzbhp.zza(zzbhq)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int i = 0;
        for (zzbhq zzbhq : zzabz().values()) {
            int hashCode;
            if (zza(zzbhq)) {
                hashCode = zzb(zzbhq).hashCode() + (i * 31);
            } else {
                hashCode = i;
            }
            i = hashCode;
        }
        return i;
    }

    public Object zzgx(String str) {
        return null;
    }

    public boolean zzgy(String str) {
        return false;
    }
}
