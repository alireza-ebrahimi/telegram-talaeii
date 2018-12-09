package com.google.android.gms.internal.clearcut;

abstract class zzcy {
    private static final zzcy zzlt = new zzda();
    private static final zzcy zzlu = new zzdb();

    private zzcy() {
    }

    static zzcy zzbv() {
        return zzlt;
    }

    static zzcy zzbw() {
        return zzlu;
    }

    abstract void zza(Object obj, long j);

    abstract <L> void zza(Object obj, Object obj2, long j);
}
