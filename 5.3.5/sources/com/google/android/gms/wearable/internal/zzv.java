package com.google.android.gms.wearable.internal;

import com.google.android.gms.wearable.CapabilityApi.CapabilityListener;
import com.google.android.gms.wearable.CapabilityInfo;

final class zzv implements CapabilityListener {
    private CapabilityListener zzlsp;
    private String zzlsq;

    zzv(CapabilityListener capabilityListener, String str) {
        this.zzlsp = capabilityListener;
        this.zzlsq = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        zzv zzv = (zzv) obj;
        return this.zzlsp.equals(zzv.zzlsp) ? this.zzlsq.equals(zzv.zzlsq) : false;
    }

    public final int hashCode() {
        return (this.zzlsp.hashCode() * 31) + this.zzlsq.hashCode();
    }

    public final void onCapabilityChanged(CapabilityInfo capabilityInfo) {
        this.zzlsp.onCapabilityChanged(capabilityInfo);
    }
}
