package com.google.android.gms.internal;

import com.google.android.gms.internal.zzfhu.zzb;
import com.google.android.gms.internal.zzfhu.zzg;
import com.google.android.gms.internal.zzfhu.zzh;
import java.io.IOException;

public final class zzfkk extends zzfhu<zzfkk, zza> implements zzfje {
    private static volatile zzfjl<zzfkk> zzbbm;
    private static final zzfkk zzpsy;
    private long zzpsw;
    private int zzpsx;

    public static final class zza extends com.google.android.gms.internal.zzfhu.zza<zzfkk, zza> implements zzfje {
        private zza() {
            super(zzfkk.zzpsy);
        }

        public final zza zzdh(long j) {
            zzczv();
            ((zzfkk) this.zzppl).zzdg(j);
            return this;
        }

        public final zza zzmt(int i) {
            zzczv();
            ((zzfkk) this.zzppl).setNanos(i);
            return this;
        }
    }

    static {
        zzfhu zzfkk = new zzfkk();
        zzpsy = zzfkk;
        zzfkk.zza(zzg.zzppw, null, null);
        zzfkk.zzpph.zzbkr();
    }

    private zzfkk() {
    }

    private final void setNanos(int i) {
        this.zzpsx = i;
    }

    public static zza zzdbw() {
        return (zza) ((com.google.android.gms.internal.zzfhu.zza) zzpsy.zza(zzg.zzppy, null, null));
    }

    public static zzfkk zzdbx() {
        return zzpsy;
    }

    private final void zzdg(long j) {
        this.zzpsw = j;
    }

    public final int getNanos() {
        return this.zzpsx;
    }

    public final long getSeconds() {
        return this.zzpsw;
    }

    protected final Object zza(int i, Object obj, Object obj2) {
        boolean z = true;
        boolean z2;
        switch (zzfkl.zzbbk[i - 1]) {
            case 1:
                return new zzfkk();
            case 2:
                return zzpsy;
            case 3:
                return null;
            case 4:
                return new zza();
            case 5:
                zzh zzh = (zzh) obj;
                zzfkk zzfkk = (zzfkk) obj2;
                this.zzpsw = zzh.zza(this.zzpsw != 0, this.zzpsw, zzfkk.zzpsw != 0, zzfkk.zzpsw);
                z2 = this.zzpsx != 0;
                int i2 = this.zzpsx;
                if (zzfkk.zzpsx == 0) {
                    z = false;
                }
                this.zzpsx = zzh.zza(z2, i2, z, zzfkk.zzpsx);
                return this;
            case 6:
                zzfhb zzfhb = (zzfhb) obj;
                if (((zzfhm) obj2) != null) {
                    boolean z3 = false;
                    while (!z3) {
                        try {
                            int zzcxx = zzfhb.zzcxx();
                            switch (zzcxx) {
                                case 0:
                                    z3 = true;
                                    break;
                                case 8:
                                    this.zzpsw = zzfhb.zzcxz();
                                    break;
                                case 16:
                                    this.zzpsx = zzfhb.zzcya();
                                    break;
                                default:
                                    if ((zzcxx & 7) == 4) {
                                        z2 = false;
                                    } else {
                                        if (this.zzpph == zzfko.zzdca()) {
                                            this.zzpph = zzfko.zzdcb();
                                        }
                                        z2 = this.zzpph.zzb(zzcxx, zzfhb);
                                    }
                                    if (!z2) {
                                        z3 = true;
                                        break;
                                    }
                                    break;
                            }
                        } catch (zzfie e) {
                            throw new RuntimeException(e.zzi(this));
                        } catch (IOException e2) {
                            throw new RuntimeException(new zzfie(e2.getMessage()).zzi(this));
                        }
                    }
                    break;
                }
                throw new NullPointerException();
            case 7:
                break;
            case 8:
                if (zzbbm == null) {
                    synchronized (zzfkk.class) {
                        if (zzbbm == null) {
                            zzbbm = new zzb(zzpsy);
                        }
                    }
                }
                return zzbbm;
            case 9:
                return Byte.valueOf((byte) 1);
            case 10:
                return null;
            default:
                throw new UnsupportedOperationException();
        }
        return zzpsy;
    }

    public final void zza(zzfhg zzfhg) throws IOException {
        if (this.zzpsw != 0) {
            zzfhg.zza(1, this.zzpsw);
        }
        if (this.zzpsx != 0) {
            zzfhg.zzad(2, this.zzpsx);
        }
        this.zzpph.zza(zzfhg);
    }

    public final int zzhs() {
        int i = this.zzppi;
        if (i != -1) {
            return i;
        }
        i = 0;
        if (this.zzpsw != 0) {
            i = zzfhg.zzc(1, this.zzpsw) + 0;
        }
        if (this.zzpsx != 0) {
            i += zzfhg.zzag(2, this.zzpsx);
        }
        i += this.zzpph.zzhs();
        this.zzppi = i;
        return i;
    }
}
