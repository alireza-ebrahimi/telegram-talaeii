package com.google.android.gms.wearable.internal;

import com.google.android.gms.wearable.CapabilityClient.OnCapabilityChangedListener;
import com.google.android.gms.wearable.CapabilityInfo;

final class zzae implements OnCapabilityChangedListener {
    private String zzlsq;
    private OnCapabilityChangedListener zzlsv;

    zzae(OnCapabilityChangedListener onCapabilityChangedListener, String str) {
        this.zzlsv = onCapabilityChangedListener;
        this.zzlsq = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        zzae zzae = (zzae) obj;
        return this.zzlsv.equals(zzae.zzlsv) ? this.zzlsq.equals(zzae.zzlsq) : false;
    }

    public final int hashCode() {
        return (this.zzlsv.hashCode() * 31) + this.zzlsq.hashCode();
    }

    public final void onCapabilityChanged(CapabilityInfo capabilityInfo) {
        this.zzlsv.onCapabilityChanged(capabilityInfo);
    }
}
