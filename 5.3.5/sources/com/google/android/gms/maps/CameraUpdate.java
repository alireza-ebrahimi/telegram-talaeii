package com.google.android.gms.maps;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.dynamic.IObjectWrapper;

public final class CameraUpdate {
    private final IObjectWrapper zzizp;

    CameraUpdate(IObjectWrapper iObjectWrapper) {
        this.zzizp = (IObjectWrapper) zzbq.checkNotNull(iObjectWrapper);
    }

    @Hide
    public final IObjectWrapper zzaxq() {
        return this.zzizp;
    }
}
