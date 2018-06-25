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
import com.google.android.gms.internal.firebase_auth.zzg.zzc;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Class(creator = "GetAccountInfoUserListCreator")
@Reserved({1})
public final class zzal extends AbstractSafeParcelable {
    public static final Creator<zzal> CREATOR = new zzam();
    @Field(getter = "getUsers", id = 2)
    private List<zzaj> zzjv;

    public zzal() {
        this.zzjv = new ArrayList();
    }

    @Constructor
    zzal(@Param(id = 2) List<zzaj> list) {
        this.zzjv = list == null ? Collections.emptyList() : Collections.unmodifiableList(list);
    }

    public static zzal zza(zzal zzal) {
        Collection collection = zzal.zzjv;
        zzal zzal2 = new zzal();
        if (!(collection == null || collection.isEmpty())) {
            zzal2.zzjv.addAll(collection);
        }
        return zzal2;
    }

    public static zzal zza(zzc zzc) {
        ArrayList arrayList = new ArrayList(zzc.zzan.length);
        for (zzu zzu : zzc.zzan) {
            arrayList.add(new zzaj(Strings.emptyToNull(zzu.zzad), Strings.emptyToNull(zzu.zzah), zzu.zzbk, Strings.emptyToNull(zzu.zzbh), Strings.emptyToNull(zzu.zzbr), zzas.zza(zzu.zzbx), Strings.emptyToNull(zzu.zzdr), Strings.emptyToNull(zzu.zzbd), zzu.zzbw, zzu.zzbv, false, null));
        }
        return new zzal(arrayList);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeTypedList(parcel, 2, this.zzjv, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final List<zzaj> zzas() {
        return this.zzjv;
    }
}
