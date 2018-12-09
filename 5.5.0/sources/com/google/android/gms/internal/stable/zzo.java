package com.google.android.gms.internal.stable;

final class zzo extends zzl {
    private final zzm zzahm = new zzm();

    zzo() {
    }

    public final void zza(Throwable th, Throwable th2) {
        if (th2 == th) {
            throw new IllegalArgumentException("Self suppression is not allowed.", th2);
        } else if (th2 == null) {
            throw new NullPointerException("The suppressed exception cannot be null.");
        } else {
            this.zzahm.zza(th, true).add(th2);
        }
    }
}
