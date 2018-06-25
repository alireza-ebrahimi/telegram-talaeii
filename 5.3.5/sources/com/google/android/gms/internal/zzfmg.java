package com.google.android.gms.internal;

import com.google.android.gms.internal.zzfhu.zzb;
import com.google.android.gms.internal.zzfhu.zzf;
import com.google.android.gms.internal.zzfhu.zzg;
import com.google.android.gms.internal.zzfhu.zzh;
import java.io.IOException;

public final class zzfmg extends zzfhu<zzfmg, zza> implements zzfje {
    private static volatile zzfjl<zzfmg> zzbbm;
    private static final zzfmg zzpxv;
    private int zzmjb;
    private int zzpxs;
    private String zzpxt = "";
    private zzfid<zzfgp> zzpxu = zzfhu.zzczs();

    public static final class zza extends com.google.android.gms.internal.zzfhu.zza<zzfmg, zza> implements zzfje {
        private zza() {
            super(zzfmg.zzpxv);
        }
    }

    static {
        zzfhu zzfmg = new zzfmg();
        zzpxv = zzfmg;
        zzfmg.zza(zzg.zzppw, null, null);
        zzfmg.zzpph.zzbkr();
    }

    private zzfmg() {
    }

    public static zzfmg zzdcu() {
        return zzpxv;
    }

    public final int getCode() {
        return this.zzpxs;
    }

    public final String getMessage() {
        return this.zzpxt;
    }

    protected final Object zza(int i, Object obj, Object obj2) {
        boolean z = false;
        boolean z2 = true;
        switch (zzfmh.zzbbk[i - 1]) {
            case 1:
                return new zzfmg();
            case 2:
                return zzpxv;
            case 3:
                this.zzpxu.zzbkr();
                return null;
            case 4:
                return new zza();
            case 5:
                zzh zzh = (zzh) obj;
                zzfmg zzfmg = (zzfmg) obj2;
                this.zzpxs = zzh.zza(this.zzpxs != 0, this.zzpxs, zzfmg.zzpxs != 0, zzfmg.zzpxs);
                boolean z3 = !this.zzpxt.isEmpty();
                String str = this.zzpxt;
                if (zzfmg.zzpxt.isEmpty()) {
                    z2 = false;
                }
                this.zzpxt = zzh.zza(z3, str, z2, zzfmg.zzpxt);
                this.zzpxu = zzh.zza(this.zzpxu, zzfmg.zzpxu);
                if (zzh != zzf.zzppq) {
                    return this;
                }
                this.zzmjb |= zzfmg.zzmjb;
                return this;
            case 6:
                zzfhb zzfhb = (zzfhb) obj;
                zzfhm zzfhm = (zzfhm) obj2;
                if (zzfhm != null) {
                    while (!z) {
                        try {
                            int zzcxx = zzfhb.zzcxx();
                            switch (zzcxx) {
                                case 0:
                                    z = true;
                                    break;
                                case 8:
                                    this.zzpxs = zzfhb.zzcya();
                                    break;
                                case 18:
                                    this.zzpxt = zzfhb.zzcye();
                                    break;
                                case 26:
                                    if (!this.zzpxu.zzcxk()) {
                                        zzfid zzfid = this.zzpxu;
                                        zzcxx = zzfid.size();
                                        this.zzpxu = zzfid.zzmo(zzcxx == 0 ? 10 : zzcxx << 1);
                                    }
                                    this.zzpxu.add((zzfgp) zzfhb.zza(zzfgp.zzcxo(), zzfhm));
                                    break;
                                default:
                                    z = !zza(zzcxx, zzfhb) ? true : z;
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
                    synchronized (zzfmg.class) {
                        if (zzbbm == null) {
                            zzbbm = new zzb(zzpxv);
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
        return zzpxv;
    }

    public final void zza(zzfhg zzfhg) throws IOException {
        if (this.zzpxs != 0) {
            zzfhg.zzad(1, this.zzpxs);
        }
        if (!this.zzpxt.isEmpty()) {
            zzfhg.zzp(2, this.zzpxt);
        }
        for (int i = 0; i < this.zzpxu.size(); i++) {
            zzfhg.zza(3, (zzfjc) this.zzpxu.get(i));
        }
        this.zzpph.zza(zzfhg);
    }

    public final int zzhs() {
        int i = 0;
        int i2 = this.zzppi;
        if (i2 != -1) {
            return i2;
        }
        i2 = this.zzpxs != 0 ? zzfhg.zzag(1, this.zzpxs) + 0 : 0;
        if (!this.zzpxt.isEmpty()) {
            i2 += zzfhg.zzq(2, this.zzpxt);
        }
        int i3 = i2;
        while (i < this.zzpxu.size()) {
            i++;
            i3 = zzfhg.zzc(3, (zzfjc) this.zzpxu.get(i)) + i3;
        }
        i2 = this.zzpph.zzhs() + i3;
        this.zzppi = i2;
        return i2;
    }
}
