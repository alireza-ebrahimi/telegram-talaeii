package com.google.android.gms.internal;

import com.google.android.gms.common.internal.Hide;

abstract class zzcli extends zzclh {
    private boolean initialized;

    zzcli(zzckj zzckj) {
        super(zzckj);
        this.zzjev.zzb(this);
    }

    @Hide
    public final void initialize() {
        if (this.initialized) {
            throw new IllegalStateException("Can't initialize twice");
        } else if (!zzazq()) {
            this.zzjev.zzbcb();
            this.initialized = true;
        }
    }

    @Hide
    final boolean isInitialized() {
        return this.initialized;
    }

    @Hide
    protected abstract boolean zzazq();

    @Hide
    protected void zzbap() {
    }

    @Hide
    public final void zzbcf() {
        if (this.initialized) {
            throw new IllegalStateException("Can't initialize twice");
        }
        zzbap();
        this.zzjev.zzbcb();
        this.initialized = true;
    }

    @Hide
    protected final void zzyk() {
        if (!isInitialized()) {
            throw new IllegalStateException("Not initialized");
        }
    }
}
