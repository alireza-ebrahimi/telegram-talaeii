package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import com.google.android.gms.internal.firebase_auth.zzg.zzc;
import com.google.firebase.auth.p104a.p105a.C1794n;
import java.util.List;

@Class(creator = "GetAccountInfoResponseCreator")
@Reserved({1})
public final class zzah extends AbstractSafeParcelable implements C1794n<zzah, zzc> {
    public static final Creator<zzah> CREATOR = new zzai();
    @Field(getter = "getUserList", id = 2)
    private zzal zzjp;

    @Constructor
    zzah(@Param(id = 2) zzal zzal) {
        this.zzjp = zzal == null ? new zzal() : zzal.zza(zzal);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzjp, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final /* synthetic */ C1794n zza(zzgt zzgt) {
        zzc zzc = (zzc) zzgt;
        if (zzc.zzan == null) {
            this.zzjp = null;
        } else {
            this.zzjp = zzal.zza(zzc);
        }
        return this;
    }

    public final Class<zzc> zzag() {
        return zzc.class;
    }

    public final List<zzaj> zzas() {
        return this.zzjp.zzas();
    }
}
