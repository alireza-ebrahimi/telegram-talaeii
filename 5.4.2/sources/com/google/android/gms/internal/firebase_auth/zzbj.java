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
import com.google.android.gms.common.util.Strings;
import com.google.android.gms.internal.firebase_auth.zzg.zzh;
import com.google.firebase.auth.p104a.p105a.C1794n;

@Class(creator = "VerifyCustomTokenResponseCreator")
@Reserved({1})
public final class zzbj extends AbstractSafeParcelable implements C1794n<zzbj, zzh> {
    public static final Creator<zzbj> CREATOR = new zzbk();
    @Field(getter = "getIdToken", id = 2)
    private String zzaf;
    @Field(getter = "getRefreshToken", id = 3)
    private String zzai;
    @Field(getter = "getExpiresIn", id = 4)
    private long zzaj;
    @Field(getter = "isNewUser", id = 5)
    private boolean zzak;

    @Constructor
    zzbj(@Param(id = 2) String str, @Param(id = 3) String str2, @Param(id = 4) long j, @Param(id = 5) boolean z) {
        this.zzaf = str;
        this.zzai = str2;
        this.zzaj = j;
        this.zzak = z;
    }

    public final String getIdToken() {
        return this.zzaf;
    }

    public final boolean isNewUser() {
        return this.zzak;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzaf, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzai, false);
        SafeParcelWriter.writeLong(parcel, 4, this.zzaj);
        SafeParcelWriter.writeBoolean(parcel, 5, this.zzak);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final /* synthetic */ C1794n zza(zzgt zzgt) {
        zzh zzh = (zzh) zzgt;
        this.zzaf = Strings.emptyToNull(zzh.zzaf);
        this.zzai = Strings.emptyToNull(zzh.zzai);
        this.zzaj = zzh.zzaj;
        this.zzak = zzh.zzak;
        return this;
    }

    public final Class<zzh> zzag() {
        return zzh.class;
    }

    public final String zzap() {
        return this.zzai;
    }

    public final long zzaq() {
        return this.zzaj;
    }
}
