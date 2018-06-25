package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import java.util.Iterator;

@Class(creator = "EventParamsCreator")
@Reserved({1})
public final class zzet extends AbstractSafeParcelable implements Iterable<String> {
    public static final Creator<zzet> CREATOR = new zzev();
    @Field(getter = "z", id = 2)
    private final Bundle zzafz;

    @Constructor
    zzet(@Param(id = 2) Bundle bundle) {
        this.zzafz = bundle;
    }

    final Object get(String str) {
        return this.zzafz.get(str);
    }

    final Long getLong(String str) {
        return Long.valueOf(this.zzafz.getLong(str));
    }

    final String getString(String str) {
        return this.zzafz.getString(str);
    }

    public final Iterator<String> iterator() {
        return new zzeu(this);
    }

    public final int size() {
        return this.zzafz.size();
    }

    public final String toString() {
        return this.zzafz.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeBundle(parcel, 2, zzij(), false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    final Double zzbg(String str) {
        return Double.valueOf(this.zzafz.getDouble(str));
    }

    public final Bundle zzij() {
        return new Bundle(this.zzafz);
    }
}
