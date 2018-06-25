package com.google.android.gms.internal;

import java.io.IOException;

public final class zzcob extends zzflm<zzcob> {
    private static volatile zzcob[] zzjuh;
    public Integer count;
    public String name;
    public zzcoc[] zzjui;
    public Long zzjuj;
    public Long zzjuk;

    public zzcob() {
        this.zzjui = zzcoc.zzbda();
        this.name = null;
        this.zzjuj = null;
        this.zzjuk = null;
        this.count = null;
        this.zzpvl = null;
        this.zzpnr = -1;
    }

    public static zzcob[] zzbcz() {
        if (zzjuh == null) {
            synchronized (zzflq.zzpvt) {
                if (zzjuh == null) {
                    zzjuh = new zzcob[0];
                }
            }
        }
        return zzjuh;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcob)) {
            return false;
        }
        zzcob zzcob = (zzcob) obj;
        if (!zzflq.equals(this.zzjui, zzcob.zzjui)) {
            return false;
        }
        if (this.name == null) {
            if (zzcob.name != null) {
                return false;
            }
        } else if (!this.name.equals(zzcob.name)) {
            return false;
        }
        if (this.zzjuj == null) {
            if (zzcob.zzjuj != null) {
                return false;
            }
        } else if (!this.zzjuj.equals(zzcob.zzjuj)) {
            return false;
        }
        if (this.zzjuk == null) {
            if (zzcob.zzjuk != null) {
                return false;
            }
        } else if (!this.zzjuk.equals(zzcob.zzjuk)) {
            return false;
        }
        if (this.count == null) {
            if (zzcob.count != null) {
                return false;
            }
        } else if (!this.count.equals(zzcob.count)) {
            return false;
        }
        return (this.zzpvl == null || this.zzpvl.isEmpty()) ? zzcob.zzpvl == null || zzcob.zzpvl.isEmpty() : this.zzpvl.equals(zzcob.zzpvl);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((this.count == null ? 0 : this.count.hashCode()) + (((this.zzjuk == null ? 0 : this.zzjuk.hashCode()) + (((this.zzjuj == null ? 0 : this.zzjuj.hashCode()) + (((this.name == null ? 0 : this.name.hashCode()) + ((((getClass().getName().hashCode() + 527) * 31) + zzflq.hashCode(this.zzjui)) * 31)) * 31)) * 31)) * 31)) * 31;
        if (!(this.zzpvl == null || this.zzpvl.isEmpty())) {
            i = this.zzpvl.hashCode();
        }
        return hashCode + i;
    }

    public final /* synthetic */ zzfls zza(zzflj zzflj) throws IOException {
        while (true) {
            int zzcxx = zzflj.zzcxx();
            switch (zzcxx) {
                case 0:
                    break;
                case 10:
                    int zzb = zzflv.zzb(zzflj, 10);
                    zzcxx = this.zzjui == null ? 0 : this.zzjui.length;
                    Object obj = new zzcoc[(zzb + zzcxx)];
                    if (zzcxx != 0) {
                        System.arraycopy(this.zzjui, 0, obj, 0, zzcxx);
                    }
                    while (zzcxx < obj.length - 1) {
                        obj[zzcxx] = new zzcoc();
                        zzflj.zza(obj[zzcxx]);
                        zzflj.zzcxx();
                        zzcxx++;
                    }
                    obj[zzcxx] = new zzcoc();
                    zzflj.zza(obj[zzcxx]);
                    this.zzjui = obj;
                    continue;
                case 18:
                    this.name = zzflj.readString();
                    continue;
                case 24:
                    this.zzjuj = Long.valueOf(zzflj.zzcyr());
                    continue;
                case 32:
                    this.zzjuk = Long.valueOf(zzflj.zzcyr());
                    continue;
                case 40:
                    this.count = Integer.valueOf(zzflj.zzcym());
                    continue;
                default:
                    if (!super.zza(zzflj, zzcxx)) {
                        break;
                    }
                    continue;
            }
            return this;
        }
    }

    public final void zza(zzflk zzflk) throws IOException {
        if (this.zzjui != null && this.zzjui.length > 0) {
            for (zzfls zzfls : this.zzjui) {
                if (zzfls != null) {
                    zzflk.zza(1, zzfls);
                }
            }
        }
        if (this.name != null) {
            zzflk.zzp(2, this.name);
        }
        if (this.zzjuj != null) {
            zzflk.zzf(3, this.zzjuj.longValue());
        }
        if (this.zzjuk != null) {
            zzflk.zzf(4, this.zzjuk.longValue());
        }
        if (this.count != null) {
            zzflk.zzad(5, this.count.intValue());
        }
        super.zza(zzflk);
    }

    protected final int zzq() {
        int zzq = super.zzq();
        if (this.zzjui != null && this.zzjui.length > 0) {
            for (zzfls zzfls : this.zzjui) {
                if (zzfls != null) {
                    zzq += zzflk.zzb(1, zzfls);
                }
            }
        }
        if (this.name != null) {
            zzq += zzflk.zzq(2, this.name);
        }
        if (this.zzjuj != null) {
            zzq += zzflk.zzc(3, this.zzjuj.longValue());
        }
        if (this.zzjuk != null) {
            zzq += zzflk.zzc(4, this.zzjuk.longValue());
        }
        return this.count != null ? zzq + zzflk.zzag(5, this.count.intValue()) : zzq;
    }
}
