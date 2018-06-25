package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.dynamic.IObjectWrapper.zza;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.Arrays;

public class Cap extends zzbgl {
    @Hide
    public static final Creator<Cap> CREATOR = new zzb();
    private static final String TAG = Cap.class.getSimpleName();
    @Nullable
    private final BitmapDescriptor bitmapDescriptor;
    private final int type;
    @Nullable
    private final Float zzjcv;

    @Hide
    protected Cap(int i) {
        this(i, null, null);
    }

    @Hide
    Cap(int i, @Nullable IBinder iBinder, @Nullable Float f) {
        this(i, iBinder == null ? null : new BitmapDescriptor(zza.zzaq(iBinder)), f);
    }

    private Cap(int i, @Nullable BitmapDescriptor bitmapDescriptor, @Nullable Float f) {
        int i2 = (f == null || f.floatValue() <= 0.0f) ? 0 : 1;
        boolean z = (i == 3 && (bitmapDescriptor == null || i2 == 0)) ? false : true;
        zzbq.checkArgument(z, String.format("Invalid Cap: type=%s bitmapDescriptor=%s bitmapRefWidth=%s", new Object[]{Integer.valueOf(i), bitmapDescriptor, f}));
        this.type = i;
        this.bitmapDescriptor = bitmapDescriptor;
        this.zzjcv = f;
    }

    @Hide
    protected Cap(@NonNull BitmapDescriptor bitmapDescriptor, float f) {
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
        return this.type == cap.type && zzbg.equal(this.bitmapDescriptor, cap.bitmapDescriptor) && zzbg.equal(this.zzjcv, cap.zzjcv);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.type), this.bitmapDescriptor, this.zzjcv});
    }

    public String toString() {
        return "[Cap: type=" + this.type + "]";
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 2, this.type);
        zzbgo.zza(parcel, 3, this.bitmapDescriptor == null ? null : this.bitmapDescriptor.zzaxq().asBinder(), false);
        zzbgo.zza(parcel, 4, this.zzjcv, false);
        zzbgo.zzai(parcel, zze);
    }

    @Hide
    final Cap zzaxw() {
        switch (this.type) {
            case 0:
                return new ButtCap();
            case 1:
                return new SquareCap();
            case 2:
                return new RoundCap();
            case 3:
                return new CustomCap(this.bitmapDescriptor, this.zzjcv.floatValue());
            default:
                Log.w(TAG, "Unknown Cap type: " + this.type);
                return this;
        }
    }
}
