package com.google.android.gms.maps.model.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.zzev;
import com.google.android.gms.internal.zzex;

public final class zzy extends zzev implements zzw {
    zzy(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
    }

    public final void clearTileCache() throws RemoteException {
        zzb(2, zzbc());
    }

    public final boolean getFadeIn() throws RemoteException {
        Parcel zza = zza(11, zzbc());
        boolean zza2 = zzex.zza(zza);
        zza.recycle();
        return zza2;
    }

    public final String getId() throws RemoteException {
        Parcel zza = zza(3, zzbc());
        String readString = zza.readString();
        zza.recycle();
        return readString;
    }

    public final float getTransparency() throws RemoteException {
        Parcel zza = zza(13, zzbc());
        float readFloat = zza.readFloat();
        zza.recycle();
        return readFloat;
    }

    public final float getZIndex() throws RemoteException {
        Parcel zza = zza(5, zzbc());
        float readFloat = zza.readFloat();
        zza.recycle();
        return readFloat;
    }

    public final int hashCodeRemote() throws RemoteException {
        Parcel zza = zza(9, zzbc());
        int readInt = zza.readInt();
        zza.recycle();
        return readInt;
    }

    public final boolean isVisible() throws RemoteException {
        Parcel zza = zza(7, zzbc());
        boolean zza2 = zzex.zza(zza);
        zza.recycle();
        return zza2;
    }

    public final void remove() throws RemoteException {
        zzb(1, zzbc());
    }

    public final void setFadeIn(boolean z) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, z);
        zzb(10, zzbc);
    }

    public final void setTransparency(float f) throws RemoteException {
        Parcel zzbc = zzbc();
        zzbc.writeFloat(f);
        zzb(12, zzbc);
    }

    public final void setVisible(boolean z) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, z);
        zzb(6, zzbc);
    }

    public final void setZIndex(float f) throws RemoteException {
        Parcel zzbc = zzbc();
        zzbc.writeFloat(f);
        zzb(4, zzbc);
    }

    public final boolean zza(zzw zzw) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzw);
        zzbc = zza(8, zzbc);
        boolean zza = zzex.zza(zzbc);
        zzbc.recycle();
        return zza;
    }
}
