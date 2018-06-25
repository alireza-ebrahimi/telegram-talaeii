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

@Class(creator = "ConditionalUserPropertyParcelCreator")
public final class zzee extends AbstractSafeParcelable {
    public static final Creator<zzee> CREATOR = new zzef();
    @Field(id = 6)
    public boolean active;
    @Field(id = 5)
    public long creationTimestamp;
    @Field(id = 3)
    public String origin;
    @Field(id = 2)
    public String packageName;
    @Field(id = 11)
    public long timeToLive;
    @Field(id = 7)
    public String triggerEventName;
    @Field(id = 9)
    public long triggerTimeout;
    @Field(id = 4)
    public zzjz zzaeq;
    @Field(id = 8)
    public zzew zzaer;
    @Field(id = 10)
    public zzew zzaes;
    @Field(id = 12)
    public zzew zzaet;

    zzee(zzee zzee) {
        Preconditions.checkNotNull(zzee);
        this.packageName = zzee.packageName;
        this.origin = zzee.origin;
        this.zzaeq = zzee.zzaeq;
        this.creationTimestamp = zzee.creationTimestamp;
        this.active = zzee.active;
        this.triggerEventName = zzee.triggerEventName;
        this.zzaer = zzee.zzaer;
        this.triggerTimeout = zzee.triggerTimeout;
        this.zzaes = zzee.zzaes;
        this.timeToLive = zzee.timeToLive;
        this.zzaet = zzee.zzaet;
    }

    @Constructor
    zzee(@Param(id = 2) String str, @Param(id = 3) String str2, @Param(id = 4) zzjz zzjz, @Param(id = 5) long j, @Param(id = 6) boolean z, @Param(id = 7) String str3, @Param(id = 8) zzew zzew, @Param(id = 9) long j2, @Param(id = 10) zzew zzew2, @Param(id = 11) long j3, @Param(id = 12) zzew zzew3) {
        this.packageName = str;
        this.origin = str2;
        this.zzaeq = zzjz;
        this.creationTimestamp = j;
        this.active = z;
        this.triggerEventName = str3;
        this.zzaer = zzew;
        this.triggerTimeout = j2;
        this.zzaes = zzew2;
        this.timeToLive = j3;
        this.zzaet = zzew3;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.packageName, false);
        SafeParcelWriter.writeString(parcel, 3, this.origin, false);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzaeq, i, false);
        SafeParcelWriter.writeLong(parcel, 5, this.creationTimestamp);
        SafeParcelWriter.writeBoolean(parcel, 6, this.active);
        SafeParcelWriter.writeString(parcel, 7, this.triggerEventName, false);
        SafeParcelWriter.writeParcelable(parcel, 8, this.zzaer, i, false);
        SafeParcelWriter.writeLong(parcel, 9, this.triggerTimeout);
        SafeParcelWriter.writeParcelable(parcel, 10, this.zzaes, i, false);
        SafeParcelWriter.writeLong(parcel, 11, this.timeToLive);
        SafeParcelWriter.writeParcelable(parcel, 12, this.zzaet, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
