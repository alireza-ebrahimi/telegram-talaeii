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
import com.google.android.gms.internal.firebase_auth.zzg.zza;
import com.google.firebase.auth.p104a.p105a.C1794n;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Class(creator = "CreateAuthUriResponseCreator")
@Reserved({1})
public final class zzx extends AbstractSafeParcelable implements C1794n<zzx, zza> {
    public static final Creator<zzx> CREATOR = new zzy();
    @Field(getter = "getProviderId", id = 4)
    private String zzj;
    @Field(getter = "isForExistingProvider", id = 5)
    private boolean zzjf;
    @Field(getter = "getStringList", id = 6)
    private zzbd zzjg;
    @Field(getter = "getSignInMethods", id = 7)
    private List<String> zzjh;
    @Field(getter = "getAuthUri", id = 2)
    private String zzx;
    @Field(getter = "isRegistered", id = 3)
    private boolean zzz;

    public zzx() {
        this.zzjg = zzbd.zzbd();
    }

    @Constructor
    public zzx(@Param(id = 2) String str, @Param(id = 3) boolean z, @Param(id = 4) String str2, @Param(id = 5) boolean z2, @Param(id = 6) zzbd zzbd, @Param(id = 7) List<String> list) {
        this.zzx = str;
        this.zzz = z;
        this.zzj = str2;
        this.zzjf = z2;
        this.zzjg = zzbd == null ? zzbd.zzbd() : zzbd.zza(zzbd);
        this.zzjh = list;
    }

    public final List<String> getAllProviders() {
        return this.zzjg.zzbc();
    }

    public final List<String> getSignInMethods() {
        return this.zzjh;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzx, false);
        SafeParcelWriter.writeBoolean(parcel, 3, this.zzz);
        SafeParcelWriter.writeString(parcel, 4, this.zzj, false);
        SafeParcelWriter.writeBoolean(parcel, 5, this.zzjf);
        SafeParcelWriter.writeParcelable(parcel, 6, this.zzjg, i, false);
        SafeParcelWriter.writeStringList(parcel, 7, this.zzjh, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final /* synthetic */ C1794n zza(zzgt zzgt) {
        zza zza = (zza) zzgt;
        this.zzx = Strings.emptyToNull(zza.zzx);
        this.zzz = zza.zzz;
        this.zzj = Strings.emptyToNull(zza.zzj);
        this.zzjf = zza.zzaa;
        this.zzjg = zza.zzy == null ? zzbd.zzbd() : new zzbd(1, Arrays.asList(zza.zzy));
        this.zzjh = zza.zzac == null ? new ArrayList(0) : Arrays.asList(zza.zzac);
        return this;
    }

    public final Class<zza> zzag() {
        return zza.class;
    }
}
