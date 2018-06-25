package com.google.android.gms.internal.measurement;

abstract class zzjr extends zzjq {
    private boolean zzvo;

    zzjr(zzjs zzjs) {
        super(zzjs);
        this.zzajy.zzb(this);
    }

    final boolean isInitialized() {
        return this.zzvo;
    }

    protected final void zzch() {
        if (!isInitialized()) {
            throw new IllegalStateException("Not initialized");
        }
    }

    protected abstract boolean zzhh();

    public final void zzm() {
        if (this.zzvo) {
            throw new IllegalStateException("Can't initialize twice");
        }
        zzhh();
        this.zzajy.zzli();
        this.zzvo = true;
    }
}
