package com.google.android.gms.maps.model;

import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.maps.zzq;

public final class IndoorLevel {
    private final zzq zzdf;

    public IndoorLevel(zzq zzq) {
        this.zzdf = (zzq) Preconditions.checkNotNull(zzq);
    }

    public final void activate() {
        try {
            this.zzdf.activate();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof IndoorLevel)) {
            return false;
        }
        try {
            return this.zzdf.zzb(((IndoorLevel) obj).zzdf);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final String getName() {
        try {
            return this.zzdf.getName();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final String getShortName() {
        try {
            return this.zzdf.getShortName();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final int hashCode() {
        try {
            return this.zzdf.zzi();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }
}
