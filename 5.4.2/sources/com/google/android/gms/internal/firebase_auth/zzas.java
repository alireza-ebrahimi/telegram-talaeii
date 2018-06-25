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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Class(creator = "ProviderUserInfoListCreator")
@Reserved({1})
public final class zzas extends AbstractSafeParcelable {
    public static final Creator<zzas> CREATOR = new zzat();
    @Field(getter = "getProviderUserInfos", id = 2)
    private List<zzaq> zzka;

    public zzas() {
        this.zzka = new ArrayList();
    }

    @Constructor
    zzas(@Param(id = 2) List<zzaq> list) {
        if (list == null || list.isEmpty()) {
            this.zzka = Collections.emptyList();
        } else {
            this.zzka = Collections.unmodifiableList(list);
        }
    }

    public static zzas zza(zzas zzas) {
        Collection collection = zzas.zzka;
        zzas zzas2 = new zzas();
        if (collection != null) {
            zzas2.zzka.addAll(collection);
        }
        return zzas2;
    }

    public static zzas zza(zzt[] zztArr) {
        List arrayList = new ArrayList();
        for (zzt zzt : zztArr) {
            arrayList.add(new zzaq(Strings.emptyToNull(zzt.zzcg), Strings.emptyToNull(zzt.zzbh), Strings.emptyToNull(zzt.zzbr), Strings.emptyToNull(zzt.zzj), null, Strings.emptyToNull(zzt.zzbd), Strings.emptyToNull(zzt.zzah)));
        }
        return new zzas(arrayList);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeTypedList(parcel, 2, this.zzka, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final List<zzaq> zzat() {
        return this.zzka;
    }
}
