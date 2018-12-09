package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.VersionField;
import com.google.android.gms.common.util.Strings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Class(creator = "StringListCreator")
public final class zzbd extends AbstractSafeParcelable {
    public static final Creator<zzbd> CREATOR = new zzbe();
    @VersionField(id = 1)
    private final int versionCode;
    @Field(getter = "getValues", id = 2)
    private List<String> zzkh;

    public zzbd() {
        this(null);
    }

    @Constructor
    zzbd(@Param(id = 1) int i, @Param(id = 2) List<String> list) {
        this.versionCode = i;
        if (list == null || list.isEmpty()) {
            this.zzkh = Collections.emptyList();
            return;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            list.set(i2, Strings.emptyToNull((String) list.get(i2)));
        }
        this.zzkh = Collections.unmodifiableList(list);
    }

    private zzbd(List<String> list) {
        this.versionCode = 1;
        this.zzkh = new ArrayList();
        if (list != null && !list.isEmpty()) {
            this.zzkh.addAll(list);
        }
    }

    public static zzbd zza(zzbd zzbd) {
        return new zzbd(zzbd != null ? zzbd.zzkh : null);
    }

    public static zzbd zzbd() {
        return new zzbd(null);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.versionCode);
        SafeParcelWriter.writeStringList(parcel, 2, this.zzkh, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final List<String> zzbc() {
        return this.zzkh;
    }
}
