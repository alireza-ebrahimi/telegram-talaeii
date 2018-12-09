package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;

@Class(creator = "SendVerificationCodeRequestCreator")
public final class zzax extends AbstractSafeParcelable {
    public static final Creator<zzax> CREATOR = new zzay();
    @Field(getter = "getPhoneNumber", id = 1)
    private final String zzbd;
    @Field(getter = "getTimeoutInSeconds", id = 2)
    private final long zzkb;
    @Field(getter = "getForceNewSmsVerificationSession", id = 3)
    private final boolean zzkc;
    @Field(getter = "getLanguageHeader", id = 4)
    private final String zzkd;

    @Constructor
    public zzax(@Param(id = 1) String str, @Param(id = 2) long j, @Param(id = 3) boolean z, @Param(id = 4) String str2) {
        this.zzbd = str;
        this.zzkb = j;
        this.zzkc = z;
        this.zzkd = str2;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzbd, false);
        SafeParcelWriter.writeLong(parcel, 2, this.zzkb);
        SafeParcelWriter.writeBoolean(parcel, 3, this.zzkc);
        SafeParcelWriter.writeString(parcel, 4, this.zzkd, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final /* synthetic */ zzgt zzao() {
        zzgt zzn = new zzn();
        zzn.zzbd = this.zzbd;
        return zzn;
    }
}
