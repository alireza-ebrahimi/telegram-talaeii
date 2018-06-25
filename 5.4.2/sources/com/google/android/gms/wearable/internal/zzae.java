package com.google.android.gms.wearable.internal;

import com.google.android.gms.wearable.CapabilityClient.OnCapabilityChangedListener;
import com.google.android.gms.wearable.CapabilityInfo;

final class zzae implements OnCapabilityChangedListener {
    private final String zzbc;
    private final OnCapabilityChangedListener zzby;

    zzae(OnCapabilityChangedListener onCapabilityChangedListener, String str) {
        this.zzby = onCapabilityChangedListener;
        this.zzbc = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        zzae zzae = (zzae) obj;
        return this.zzby.equals(zzae.zzby) ? this.zzbc.equals(zzae.zzbc) : false;
    }

    public final int hashCode() {
        return (this.zzby.hashCode() * 31) + this.zzbc.hashCode();
    }

    public final void onCapabilityChanged(CapabilityInfo capabilityInfo) {
        this.zzby.onCapabilityChanged(capabilityInfo);
    }
}
