package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;

public class zzaj implements Releasable, Result {
    private Status mStatus;
    protected final DataHolder zzfxb;

    protected zzaj(DataHolder dataHolder, Status status) {
        this.mStatus = status;
        this.zzfxb = dataHolder;
    }

    public Status getStatus() {
        return this.mStatus;
    }

    public void release() {
        if (this.zzfxb != null) {
            this.zzfxb.close();
        }
    }
}
