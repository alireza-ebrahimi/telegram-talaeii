package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import com.google.android.gms.dynamic.IObjectWrapper.Stub;

@Class(creator = "CapCreator")
@Reserved({1})
public class Cap extends AbstractSafeParcelable {
    public static final Creator<Cap> CREATOR = new zzb();
    private static final String TAG = Cap.class.getSimpleName();
    @Field(getter = "getWrappedBitmapDescriptorImplBinder", id = 3, type = "android.os.IBinder")
    private final BitmapDescriptor bitmapDescriptor;
    @Field(getter = "getType", id = 2)
    private final int type;
    @Field(getter = "getBitmapRefWidth", id = 4)
    private final Float zzcm;

    protected Cap(int i) {
        this(i, null, null);
    }

    @Constructor
    Cap(@Param(id = 2) int i, @Param(id = 3) IBinder iBinder, @Param(id = 4) Float f) {
        this(i, iBinder == null ? null : new BitmapDescriptor(Stub.asInterface(iBinder)), f);
    }

    private Cap(int i, BitmapDescriptor bitmapDescriptor, Float f) {
        int i2 = (f == null || f.floatValue() <= BitmapDescriptorFactory.HUE_RED) ? 0 : 1;
        boolean z = (i == 3 && (bitmapDescriptor == null || i2 == 0)) ? false : true;
        Preconditions.checkArgument(z, String.format("Invalid Cap: type=%s bitmapDescriptor=%s bitmapRefWidth=%s", new Object[]{Integer.valueOf(i), bitmapDescriptor, f}));
        this.type = i;
        this.bitmapDescriptor = bitmapDescriptor;
        this.zzcm = f;
    }

    protected Cap(BitmapDescriptor bitmapDescriptor, float f) {
        this(3, bitmapDescriptor, Float.valueOf(f));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Cap)) {
            return false;
        }
        Cap cap = (Cap) obj;
        return this.type == cap.type && Objects.equal(this.bitmapDescriptor, cap.bitmapDescriptor) && Objects.equal(this.zzcm, cap.zzcm);
    }

    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.type), this.bitmapDescriptor, this.zzcm);
    }

    public String toString() {
        return "[Cap: type=" + this.type + "]";
    }

    public void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 2, this.type);
        SafeParcelWriter.writeIBinder(parcel, 3, this.bitmapDescriptor == null ? null : this.bitmapDescriptor.zza().asBinder(), false);
        SafeParcelWriter.writeFloatObject(parcel, 4, this.zzcm, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    final Cap zzg() {
        switch (this.type) {
            case 0:
                return new ButtCap();
            case 1:
                return new SquareCap();
            case 2:
                return new RoundCap();
            case 3:
                return new CustomCap(this.bitmapDescriptor, this.zzcm.floatValue());
            default:
                Log.w(TAG, "Unknown Cap type: " + this.type);
                return this;
        }
    }
}
