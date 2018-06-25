package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.Node;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class zzah extends zzbgl implements CapabilityInfo {
    public static final Creator<zzah> CREATOR = new zzai();
    private final Object mLock = new Object();
    private final String mName;
    private Set<Node> zzlsr;
    private final List<zzfo> zzlsx;

    public zzah(String str, List<zzfo> list) {
        this.mName = str;
        this.zzlsx = list;
        this.zzlsr = null;
        zzbq.checkNotNull(this.mName);
        zzbq.checkNotNull(this.zzlsx);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        zzah zzah = (zzah) obj;
        if (this.mName == null ? zzah.mName != null : !this.mName.equals(zzah.mName)) {
            return false;
        }
        if (this.zzlsx != null) {
            if (this.zzlsx.equals(zzah.zzlsx)) {
                return true;
            }
        } else if (zzah.zzlsx == null) {
            return true;
        }
        return false;
    }

    public final String getName() {
        return this.mName;
    }

    public final Set<Node> getNodes() {
        Set<Node> set;
        synchronized (this.mLock) {
            if (this.zzlsr == null) {
                this.zzlsr = new HashSet(this.zzlsx);
            }
            set = this.zzlsr;
        }
        return set;
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((this.mName != null ? this.mName.hashCode() : 0) + 31) * 31;
        if (this.zzlsx != null) {
            i = this.zzlsx.hashCode();
        }
        return hashCode + i;
    }

    public final String toString() {
        String str = this.mName;
        String valueOf = String.valueOf(this.zzlsx);
        return new StringBuilder((String.valueOf(str).length() + 18) + String.valueOf(valueOf).length()).append("CapabilityInfo{").append(str).append(", ").append(valueOf).append("}").toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 2, getName(), false);
        zzbgo.zzc(parcel, 3, this.zzlsx, false);
        zzbgo.zzai(parcel, zze);
    }
}
