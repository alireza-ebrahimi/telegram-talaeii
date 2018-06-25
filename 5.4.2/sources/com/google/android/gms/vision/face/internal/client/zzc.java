package com.google.android.gms.vision.face.internal.client;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;

@Class(creator = "FaceSettingsParcelCreator")
@Reserved({1})
public final class zzc extends AbstractSafeParcelable {
    public static final Creator<zzc> CREATOR = new zzd();
    @Field(id = 2)
    public int mode;
    @Field(id = 3)
    public int zzcd;
    @Field(id = 4)
    public int zzce;
    @Field(id = 5)
    public boolean zzcf;
    @Field(id = 6)
    public boolean zzcg;
    @Field(defaultValue = "-1", id = 7)
    public float zzch;

    @Constructor
    public zzc(@Param(id = 2) int i, @Param(id = 3) int i2, @Param(id = 4) int i3, @Param(id = 5) boolean z, @Param(id = 6) boolean z2, @Param(id = 7) float f) {
        this.mode = i;
        this.zzcd = i2;
        this.zzce = i3;
        this.zzcf = z;
        this.zzcg = z2;
        this.zzch = f;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 2, this.mode);
        SafeParcelWriter.writeInt(parcel, 3, this.zzcd);
        SafeParcelWriter.writeInt(parcel, 4, this.zzce);
        SafeParcelWriter.writeBoolean(parcel, 5, this.zzcf);
        SafeParcelWriter.writeBoolean(parcel, 6, this.zzcg);
        SafeParcelWriter.writeFloat(parcel, 7, this.zzch);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
