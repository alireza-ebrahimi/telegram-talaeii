package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.location.zzj;
import java.util.Collections;
import java.util.List;

@Hide
public final class zzcfu extends zzbgl {
    public static final Creator<zzcfu> CREATOR = new zzcfv();
    static final List<zzcfs> zzitm = Collections.emptyList();
    static final zzj zzitn = new zzj();
    @Nullable
    private String mTag;
    private List<zzcfs> zzira;
    private zzj zzito;

    zzcfu(zzj zzj, List<zzcfs> list, String str) {
        this.zzito = zzj;
        this.zzira = list;
        this.mTag = str;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzcfu)) {
            return false;
        }
        zzcfu zzcfu = (zzcfu) obj;
        return zzbg.equal(this.zzito, zzcfu.zzito) && zzbg.equal(this.zzira, zzcfu.zzira) && zzbg.equal(this.mTag, zzcfu.mTag);
    }

    public final int hashCode() {
        return this.zzito.hashCode();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 1, this.zzito, i, false);
        zzbgo.zzc(parcel, 2, this.zzira, false);
        zzbgo.zza(parcel, 3, this.mTag, false);
        zzbgo.zzai(parcel, zze);
    }
}
