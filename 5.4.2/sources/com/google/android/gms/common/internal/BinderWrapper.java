package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.annotation.KeepName;

@KeepName
public final class BinderWrapper implements Parcelable {
    public static final Creator<BinderWrapper> CREATOR = new zza();
    private IBinder zzry;

    public BinderWrapper() {
        this.zzry = null;
    }

    public BinderWrapper(IBinder iBinder) {
        this.zzry = null;
        this.zzry = iBinder;
    }

    private BinderWrapper(Parcel parcel) {
        this.zzry = null;
        this.zzry = parcel.readStrongBinder();
    }

    public final int describeContents() {
        return 0;
    }

    public final IBinder getBinder() {
        return this.zzry;
    }

    public final void setBinder(IBinder iBinder) {
        this.zzry = iBinder;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.zzry);
    }
}
