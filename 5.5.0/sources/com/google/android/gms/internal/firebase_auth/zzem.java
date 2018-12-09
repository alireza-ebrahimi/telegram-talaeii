package com.google.android.gms.internal.firebase_auth;

import java.util.Iterator;
import java.util.Map.Entry;

final class zzem<T> implements zzev<T> {
    private final zzeh zzto;
    private final boolean zztp;
    private final zzfp<?, ?> zzty;
    private final zzcp<?> zztz;

    private zzem(zzfp<?, ?> zzfp, zzcp<?> zzcp, zzeh zzeh) {
        this.zzty = zzfp;
        this.zztp = zzcp.zze(zzeh);
        this.zztz = zzcp;
        this.zzto = zzeh;
    }

    static <T> zzem<T> zza(zzfp<?, ?> zzfp, zzcp<?> zzcp, zzeh zzeh) {
        return new zzem(zzfp, zzcp, zzeh);
    }

    public final boolean equals(T t, T t2) {
        return !this.zzty.zzr(t).equals(this.zzty.zzr(t2)) ? false : this.zztp ? this.zztz.zzc(t).equals(this.zztz.zzc(t2)) : true;
    }

    public final int hashCode(T t) {
        int hashCode = this.zzty.zzr(t).hashCode();
        return this.zztp ? (hashCode * 53) + this.zztz.zzc(t).hashCode() : hashCode;
    }

    public final T newInstance() {
        return this.zzto.zzea().zzec();
    }

    public final void zza(T t, zzeu zzeu, zzco zzco) {
        zzfp zzfp = this.zzty;
        zzcp zzcp = this.zztz;
        Object zzs = zzfp.zzs(t);
        zzcs zzd = zzcp.zzd(t);
        while (zzeu.zzda() != Integer.MAX_VALUE) {
            try {
                boolean zza;
                int tag = zzeu.getTag();
                Object zza2;
                if (tag != 11) {
                    if ((tag & 7) == 2) {
                        zza2 = zzcp.zza(zzco, this.zzto, tag >>> 3);
                        if (zza2 != null) {
                            zzcp.zza(zzeu, zza2, zzco, zzd);
                        } else {
                            zza = zzfp.zza(zzs, zzeu);
                            continue;
                        }
                    } else {
                        zza = zzeu.zzdb();
                        continue;
                    }
                    if (!zza) {
                        return;
                    }
                }
                zzbu zzbu = null;
                int i = 0;
                zza2 = null;
                while (zzeu.zzda() != Integer.MAX_VALUE) {
                    int tag2 = zzeu.getTag();
                    if (tag2 == 16) {
                        i = zzeu.zzcl();
                        zza2 = zzcp.zza(zzco, this.zzto, i);
                    } else if (tag2 == 26) {
                        if (zza2 != null) {
                            zzcp.zza(zzeu, zza2, zzco, zzd);
                        } else {
                            zzbu = zzeu.zzck();
                        }
                    } else if (!zzeu.zzdb()) {
                        break;
                    }
                }
                if (zzeu.getTag() != 12) {
                    throw zzdh.zzeh();
                } else if (zzbu != null) {
                    if (zza2 != null) {
                        zzcp.zza(zzbu, zza2, zzco, zzd);
                    } else {
                        zzfp.zza(zzs, i, zzbu);
                    }
                }
                zza = true;
                continue;
                if (zza) {
                    return;
                }
            } finally {
                zzfp.zzf(t, zzs);
            }
        }
        zzfp.zzf(t, zzs);
    }

    public final void zza(T t, zzgj zzgj) {
        Iterator it = this.zztz.zzc(t).iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            zzcu zzcu = (zzcu) entry.getKey();
            if (zzcu.zzdu() != zzgi.MESSAGE || zzcu.zzdv() || zzcu.zzdw()) {
                throw new IllegalStateException("Found invalid MessageSet item.");
            } else if (entry instanceof zzdm) {
                zzgj.zza(zzcu.zzds(), ((zzdm) entry).zzen().zzbo());
            } else {
                zzgj.zza(zzcu.zzds(), entry.getValue());
            }
        }
        zzfp zzfp = this.zzty;
        zzfp.zzc(zzfp.zzr(t), zzgj);
    }

    public final void zzc(T t, T t2) {
        zzex.zza(this.zzty, (Object) t, (Object) t2);
        if (this.zztp) {
            zzex.zza(this.zztz, (Object) t, (Object) t2);
        }
    }

    public final void zze(T t) {
        this.zzty.zze(t);
        this.zztz.zze((Object) t);
    }

    public final int zzo(T t) {
        zzfp zzfp = this.zzty;
        int zzt = zzfp.zzt(zzfp.zzr(t)) + 0;
        return this.zztp ? zzt + this.zztz.zzc(t).zzdr() : zzt;
    }

    public final boolean zzp(T t) {
        return this.zztz.zzc(t).isInitialized();
    }
}
