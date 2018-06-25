package com.google.android.gms.internal.measurement;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;

@Class(creator = "UserAttributeParcelCreator")
public final class zzjz extends AbstractSafeParcelable {
    public static final Creator<zzjz> CREATOR = new zzka();
    @Field(id = 2)
    public final String name;
    @Field(id = 7)
    public final String origin;
    @Field(id = 1)
    private final int versionCode;
    @Field(id = 6)
    private final String zzajo;
    @Field(id = 3)
    public final long zzarl;
    @Field(id = 4)
    private final Long zzarm;
    @Field(id = 5)
    private final Float zzarn;
    @Field(id = 8)
    private final Double zzaro;

    @Constructor
    zzjz(@Param(id = 1) int i, @Param(id = 2) String str, @Param(id = 3) long j, @Param(id = 4) Long l, @Param(id = 5) Float f, @Param(id = 6) String str2, @Param(id = 7) String str3, @Param(id = 8) Double d) {
        Double d2 = null;
        this.versionCode = i;
        this.name = str;
        this.zzarl = j;
        this.zzarm = l;
        this.zzarn = null;
        if (i == 1) {
            if (f != null) {
                d2 = Double.valueOf(f.doubleValue());
            }
            this.zzaro = d2;
        } else {
            this.zzaro = d;
        }
        this.zzajo = str2;
        this.origin = str3;
    }

    zzjz(zzkb zzkb) {
        this(zzkb.name, zzkb.zzarl, zzkb.value, zzkb.origin);
    }

    zzjz(String str, long j, Object obj, String str2) {
        Preconditions.checkNotEmpty(str);
        this.versionCode = 2;
        this.name = str;
        this.zzarl = j;
        this.origin = str2;
        if (obj == null) {
            this.zzarm = null;
            this.zzarn = null;
            this.zzaro = null;
            this.zzajo = null;
        } else if (obj instanceof Long) {
            this.zzarm = (Long) obj;
            this.zzarn = null;
            this.zzaro = null;
            this.zzajo = null;
        } else if (obj instanceof String) {
            this.zzarm = null;
            this.zzarn = null;
            this.zzaro = null;
            this.zzajo = (String) obj;
        } else if (obj instanceof Double) {
            this.zzarm = null;
            this.zzarn = null;
            this.zzaro = (Double) obj;
            this.zzajo = null;
        } else {
            throw new IllegalArgumentException("User attribute given of un-supported type");
        }
    }

    public final Object getValue() {
        return this.zzarm != null ? this.zzarm : this.zzaro != null ? this.zzaro : this.zzajo != null ? this.zzajo : null;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.versionCode);
        SafeParcelWriter.writeString(parcel, 2, this.name, false);
        SafeParcelWriter.writeLong(parcel, 3, this.zzarl);
        SafeParcelWriter.writeLongObject(parcel, 4, this.zzarm, false);
        SafeParcelWriter.writeFloatObject(parcel, 5, null, false);
        SafeParcelWriter.writeString(parcel, 6, this.zzajo, false);
        SafeParcelWriter.writeString(parcel, 7, this.origin, false);
        SafeParcelWriter.writeDoubleObject(parcel, 8, this.zzaro, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
