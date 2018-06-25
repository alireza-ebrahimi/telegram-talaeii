package com.google.android.gms.internal.firebase_auth;

public class zzdo {
    private static final zzco zzmc = zzco.zzdk();
    private zzbu zzsn;
    private volatile zzeh zzso;
    private volatile zzbu zzsp;

    private final zzeh zzh(zzeh zzeh) {
        if (this.zzso == null) {
            synchronized (this) {
                if (this.zzso != null) {
                } else {
                    try {
                        this.zzso = zzeh;
                        this.zzsp = zzbu.zzmi;
                    } catch (zzdh e) {
                        this.zzso = zzeh;
                        this.zzsp = zzbu.zzmi;
                    }
                }
            }
        }
        return this.zzso;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzdo)) {
            return false;
        }
        zzdo zzdo = (zzdo) obj;
        zzeh zzeh = this.zzso;
        zzeh zzeh2 = zzdo.zzso;
        return (zzeh == null && zzeh2 == null) ? zzbo().equals(zzdo.zzbo()) : (zzeh == null || zzeh2 == null) ? zzeh != null ? zzeh.equals(zzdo.zzh(zzeh.zzeb())) : zzh(zzeh2.zzeb()).equals(zzeh2) : zzeh.equals(zzeh2);
    }

    public int hashCode() {
        return 1;
    }

    public final zzbu zzbo() {
        if (this.zzsp != null) {
            return this.zzsp;
        }
        synchronized (this) {
            if (this.zzsp != null) {
                zzbu zzbu = this.zzsp;
                return zzbu;
            }
            if (this.zzso == null) {
                this.zzsp = zzbu.zzmi;
            } else {
                this.zzsp = this.zzso.zzbo();
            }
            zzbu = this.zzsp;
            return zzbu;
        }
    }

    public final int zzdq() {
        return this.zzsp != null ? this.zzsp.size() : this.zzso != null ? this.zzso.zzdq() : 0;
    }

    public final zzeh zzi(zzeh zzeh) {
        zzeh zzeh2 = this.zzso;
        this.zzsn = null;
        this.zzsp = null;
        this.zzso = zzeh;
        return zzeh2;
    }
}
