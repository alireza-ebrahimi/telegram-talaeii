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
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;

@Class(creator = "EventParcelCreator")
@Reserved({1})
public final class zzew extends AbstractSafeParcelable {
    public static final Creator<zzew> CREATOR = new zzex();
    @Field(id = 2)
    public final String name;
    @Field(id = 4)
    public final String origin;
    @Field(id = 3)
    public final zzet zzafr;
    @Field(id = 5)
    public final long zzagc;

    zzew(zzew zzew, long j) {
        Preconditions.checkNotNull(zzew);
        this.name = zzew.name;
        this.zzafr = zzew.zzafr;
        this.origin = zzew.origin;
        this.zzagc = j;
    }

    @Constructor
    public zzew(@Param(id = 2) String str, @Param(id = 3) zzet zzet, @Param(id = 4) String str2, @Param(id = 5) long j) {
        this.name = str;
        this.zzafr = zzet;
        this.origin = str2;
        this.zzagc = j;
    }

    public final String toString() {
        String str = this.origin;
        String str2 = this.name;
        String valueOf = String.valueOf(this.zzafr);
        return new StringBuilder(((String.valueOf(str).length() + 21) + String.valueOf(str2).length()) + String.valueOf(valueOf).length()).append("origin=").append(str).append(",name=").append(str2).append(",params=").append(valueOf).toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.name, false);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzafr, i, false);
        SafeParcelWriter.writeString(parcel, 4, this.origin, false);
        SafeParcelWriter.writeLong(parcel, 5, this.zzagc);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
