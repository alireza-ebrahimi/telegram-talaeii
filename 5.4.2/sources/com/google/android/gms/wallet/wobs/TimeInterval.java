package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;

@Class(creator = "TimeIntervalCreator")
@Reserved({1})
public final class TimeInterval extends AbstractSafeParcelable {
    public static final Creator<TimeInterval> CREATOR = new zzk();
    @Field(id = 2)
    private long zzhb;
    @Field(id = 3)
    private long zzhc;

    TimeInterval() {
    }

    @Constructor
    public TimeInterval(@Param(id = 2) long j, @Param(id = 3) long j2) {
        this.zzhb = j;
        this.zzhc = j2;
    }

    public final long getEndTimestamp() {
        return this.zzhc;
    }

    public final long getStartTimestamp() {
        return this.zzhb;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeLong(parcel, 2, this.zzhb);
        SafeParcelWriter.writeLong(parcel, 3, this.zzhc);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
