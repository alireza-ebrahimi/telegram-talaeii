package com.google.android.gms.internal.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.widget.RemoteViews;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;

@Class(creator = "GetSaveInstrumentDetailsResponseCreator")
public final class zzj extends AbstractSafeParcelable {
    public static final Creator<zzj> CREATOR = new zzk();
    @Field(id = 1)
    private String[] zzex;
    @Field(id = 2)
    private int[] zzey;
    @Field(id = 3)
    private RemoteViews zzez;
    @Field(id = 4)
    private byte[] zzfa;

    private zzj() {
    }

    @Constructor
    public zzj(@Param(id = 1) String[] strArr, @Param(id = 2) int[] iArr, @Param(id = 3) RemoteViews remoteViews, @Param(id = 4) byte[] bArr) {
        this.zzex = strArr;
        this.zzey = iArr;
        this.zzez = remoteViews;
        this.zzfa = bArr;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeStringArray(parcel, 1, this.zzex, false);
        SafeParcelWriter.writeIntArray(parcel, 2, this.zzey, false);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzez, i, false);
        SafeParcelWriter.writeByteArray(parcel, 4, this.zzfa, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
