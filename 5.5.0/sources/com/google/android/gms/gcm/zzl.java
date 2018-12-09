package com.google.android.gms.gcm;

import android.os.Bundle;

public final class zzl {
    public static final zzl zzao = new zzl(0, 30, 3600);
    private static final zzl zzap = new zzl(1, 30, 3600);
    private final int zzaq;
    private final int zzar = 30;
    private final int zzas = 3600;

    private zzl(int i, int i2, int i3) {
        this.zzaq = i;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzl)) {
            return false;
        }
        zzl zzl = (zzl) obj;
        return zzl.zzaq == this.zzaq && zzl.zzar == this.zzar && zzl.zzas == this.zzas;
    }

    public final int hashCode() {
        return (((((this.zzaq + 1) ^ 1000003) * 1000003) ^ this.zzar) * 1000003) ^ this.zzas;
    }

    public final String toString() {
        int i = this.zzaq;
        int i2 = this.zzar;
        return "policy=" + i + " initial_backoff=" + i2 + " maximum_backoff=" + this.zzas;
    }

    public final Bundle zzf(Bundle bundle) {
        bundle.putInt("retry_policy", this.zzaq);
        bundle.putInt("initial_backoff_seconds", this.zzar);
        bundle.putInt("maximum_backoff_seconds", this.zzas);
        return bundle;
    }

    public final int zzh() {
        return this.zzaq;
    }

    public final int zzi() {
        return this.zzar;
    }

    public final int zzj() {
        return this.zzas;
    }
}
