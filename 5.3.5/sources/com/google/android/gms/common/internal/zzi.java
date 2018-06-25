package com.google.android.gms.common.internal;

import android.util.Log;

@Hide
public abstract class zzi<TListener> {
    private TListener zzgat;
    private /* synthetic */ zzd zzgfk;
    private boolean zzgfl = false;

    public zzi(zzd zzd, TListener tListener) {
        this.zzgfk = zzd;
        this.zzgat = tListener;
    }

    public final void removeListener() {
        synchronized (this) {
            this.zzgat = null;
        }
    }

    public final void unregister() {
        removeListener();
        synchronized (this.zzgfk.zzgey) {
            this.zzgfk.zzgey.remove(this);
        }
    }

    protected abstract void zzamb();

    public final void zzamc() {
        synchronized (this) {
            Object obj = this.zzgat;
            if (this.zzgfl) {
                String valueOf = String.valueOf(this);
                Log.w("GmsClient", new StringBuilder(String.valueOf(valueOf).length() + 47).append("Callback proxy ").append(valueOf).append(" being reused. This is not safe.").toString());
            }
        }
        if (obj != null) {
            try {
                zzw(obj);
            } catch (RuntimeException e) {
                zzamb();
                throw e;
            }
        }
        zzamb();
        synchronized (this) {
            this.zzgfl = true;
        }
        unregister();
    }

    protected abstract void zzw(TListener tListener);
}
