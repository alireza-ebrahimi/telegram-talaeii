package com.google.android.gms.internal.firebase_auth;

import java.util.List;

final class zzcg implements zzeu {
    private int tag;
    private final zzcd zznb;
    private int zznc;
    private int zznd = 0;

    private zzcg(zzcd zzcd) {
        this.zznb = (zzcd) zzdd.zza((Object) zzcd, "input");
        this.zznb.zzmt = this;
    }

    public static zzcg zza(zzcd zzcd) {
        return zzcd.zzmt != null ? zzcd.zzmt : new zzcg(zzcd);
    }

    private final Object zza(zzgd zzgd, Class<?> cls, zzco zzco) {
        switch (zzch.zzne[zzgd.ordinal()]) {
            case 1:
                return Boolean.valueOf(zzci());
            case 2:
                return zzck();
            case 3:
                return Double.valueOf(readDouble());
            case 4:
                return Integer.valueOf(zzcm());
            case 5:
                return Integer.valueOf(zzch());
            case 6:
                return Long.valueOf(zzcg());
            case 7:
                return Float.valueOf(readFloat());
            case 8:
                return Integer.valueOf(zzcf());
            case 9:
                return Long.valueOf(zzce());
            case 10:
                zzs(2);
                return zzc(zzes.zzfg().zzf(cls), zzco);
            case 11:
                return Integer.valueOf(zzcn());
            case 12:
                return Long.valueOf(zzco());
            case 13:
                return Integer.valueOf(zzcp());
            case 14:
                return Long.valueOf(zzcq());
            case 15:
                return zzcj();
            case 16:
                return Integer.valueOf(zzcl());
            case 17:
                return Long.valueOf(zzcd());
            default:
                throw new RuntimeException("unsupported field type.");
        }
    }

    private final void zza(List<String> list, boolean z) {
        if ((this.tag & 7) != 2) {
            throw zzdh.zzei();
        } else if (!(list instanceof zzdq) || z) {
            do {
                list.add(z ? zzcj() : readString());
                if (!this.zznb.zzcs()) {
                    r0 = this.zznb.zzcc();
                } else {
                    return;
                }
            } while (r0 == this.tag);
            this.zznd = r0;
        } else {
            zzdq zzdq = (zzdq) list;
            do {
                zzdq.zzc(zzck());
                if (!this.zznb.zzcs()) {
                    r0 = this.zznb.zzcc();
                } else {
                    return;
                }
            } while (r0 == this.tag);
            this.zznd = r0;
        }
    }

    private final <T> T zzc(zzev<T> zzev, zzco zzco) {
        int zzcl = this.zznb.zzcl();
        if (this.zznb.zzmq >= this.zznb.zzmr) {
            throw zzdh.zzej();
        }
        zzcl = this.zznb.zzp(zzcl);
        T newInstance = zzev.newInstance();
        zzcd zzcd = this.zznb;
        zzcd.zzmq++;
        zzev.zza(newInstance, this, zzco);
        zzev.zze(newInstance);
        this.zznb.zzm(0);
        zzcd = this.zznb;
        zzcd.zzmq--;
        this.zznb.zzq(zzcl);
        return newInstance;
    }

    private final <T> T zzd(zzev<T> zzev, zzco zzco) {
        int i = this.zznc;
        this.zznc = ((this.tag >>> 3) << 3) | 4;
        try {
            T newInstance = zzev.newInstance();
            zzev.zza(newInstance, this, zzco);
            zzev.zze(newInstance);
            if (this.tag == this.zznc) {
                return newInstance;
            }
            throw zzdh.zzek();
        } finally {
            this.zznc = i;
        }
    }

    private final void zzs(int i) {
        if ((this.tag & 7) != i) {
            throw zzdh.zzei();
        }
    }

    private static void zzt(int i) {
        if ((i & 7) != 0) {
            throw zzdh.zzek();
        }
    }

    private static void zzu(int i) {
        if ((i & 3) != 0) {
            throw zzdh.zzek();
        }
    }

    private final void zzv(int i) {
        if (this.zznb.zzct() != i) {
            throw zzdh.zzee();
        }
    }

    public final int getTag() {
        return this.tag;
    }

    public final double readDouble() {
        zzs(1);
        return this.zznb.readDouble();
    }

    public final float readFloat() {
        zzs(5);
        return this.zznb.readFloat();
    }

    public final String readString() {
        zzs(2);
        return this.zznb.readString();
    }

    public final void readStringList(List<String> list) {
        zza((List) list, false);
    }

    public final <T> T zza(zzev<T> zzev, zzco zzco) {
        zzs(2);
        return zzc(zzev, zzco);
    }

    public final <T> void zza(List<T> list, zzev<T> zzev, zzco zzco) {
        if ((this.tag & 7) != 2) {
            throw zzdh.zzei();
        }
        int zzcc;
        int i = this.tag;
        do {
            list.add(zzc(zzev, zzco));
            if (!this.zznb.zzcs() && this.zznd == 0) {
                zzcc = this.zznb.zzcc();
            } else {
                return;
            }
        } while (zzcc == i);
        this.zznd = zzcc;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final <K, V> void zza(java.util.Map<K, V> r7, com.google.android.gms.internal.firebase_auth.zzea<K, V> r8, com.google.android.gms.internal.firebase_auth.zzco r9) {
        /*
        r6 = this;
        r0 = 2;
        r6.zzs(r0);
        r0 = r6.zznb;
        r0 = r0.zzcl();
        r1 = r6.zznb;
        r2 = r1.zzp(r0);
        r1 = r8.zztc;
        r0 = r8.zzte;
    L_0x0014:
        r3 = r6.zzda();	 Catch:{ all -> 0x0047 }
        r4 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
        if (r3 == r4) goto L_0x0064;
    L_0x001d:
        r4 = r6.zznb;	 Catch:{ all -> 0x0047 }
        r4 = r4.zzcs();	 Catch:{ all -> 0x0047 }
        if (r4 != 0) goto L_0x0064;
    L_0x0025:
        switch(r3) {
            case 1: goto L_0x004e;
            case 2: goto L_0x0057;
            default: goto L_0x0028;
        };
    L_0x0028:
        r3 = r6.zzdb();	 Catch:{ zzdi -> 0x0037 }
        if (r3 != 0) goto L_0x0014;
    L_0x002e:
        r3 = new com.google.android.gms.internal.firebase_auth.zzdh;	 Catch:{ zzdi -> 0x0037 }
        r4 = "Unable to parse map entry.";
        r3.<init>(r4);	 Catch:{ zzdi -> 0x0037 }
        throw r3;	 Catch:{ zzdi -> 0x0037 }
    L_0x0037:
        r3 = move-exception;
        r3 = r6.zzdb();	 Catch:{ all -> 0x0047 }
        if (r3 != 0) goto L_0x0014;
    L_0x003e:
        r0 = new com.google.android.gms.internal.firebase_auth.zzdh;	 Catch:{ all -> 0x0047 }
        r1 = "Unable to parse map entry.";
        r0.<init>(r1);	 Catch:{ all -> 0x0047 }
        throw r0;	 Catch:{ all -> 0x0047 }
    L_0x0047:
        r0 = move-exception;
        r1 = r6.zznb;
        r1.zzq(r2);
        throw r0;
    L_0x004e:
        r3 = r8.zztb;	 Catch:{ zzdi -> 0x0037 }
        r4 = 0;
        r5 = 0;
        r1 = r6.zza(r3, r4, r5);	 Catch:{ zzdi -> 0x0037 }
        goto L_0x0014;
    L_0x0057:
        r3 = r8.zztd;	 Catch:{ zzdi -> 0x0037 }
        r4 = r8.zzte;	 Catch:{ zzdi -> 0x0037 }
        r4 = r4.getClass();	 Catch:{ zzdi -> 0x0037 }
        r0 = r6.zza(r3, r4, r9);	 Catch:{ zzdi -> 0x0037 }
        goto L_0x0014;
    L_0x0064:
        r7.put(r1, r0);	 Catch:{ all -> 0x0047 }
        r0 = r6.zznb;
        r0.zzq(r2);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_auth.zzcg.zza(java.util.Map, com.google.android.gms.internal.firebase_auth.zzea, com.google.android.gms.internal.firebase_auth.zzco):void");
    }

    public final <T> T zzb(zzev<T> zzev, zzco zzco) {
        zzs(3);
        return zzd(zzev, zzco);
    }

    public final <T> void zzb(List<T> list, zzev<T> zzev, zzco zzco) {
        if ((this.tag & 7) != 3) {
            throw zzdh.zzei();
        }
        int zzcc;
        int i = this.tag;
        do {
            list.add(zzd(zzev, zzco));
            if (!this.zznb.zzcs() && this.zznd == 0) {
                zzcc = this.zznb.zzcc();
            } else {
                return;
            }
        } while (zzcc == i);
        this.zznd = zzcc;
    }

    public final void zzc(List<Double> list) {
        int zzcl;
        if (list instanceof zzcl) {
            zzcl zzcl2 = (zzcl) list;
            switch (this.tag & 7) {
                case 1:
                    break;
                case 2:
                    zzcl = this.zznb.zzcl();
                    zzt(zzcl);
                    zzcl += this.zznb.zzct();
                    do {
                        zzcl2.zzc(this.zznb.readDouble());
                    } while (this.zznb.zzct() < zzcl);
                    return;
                default:
                    throw zzdh.zzei();
            }
            do {
                zzcl2.zzc(this.zznb.readDouble());
                if (!this.zznb.zzcs()) {
                    zzcl = this.zznb.zzcc();
                } else {
                    return;
                }
            } while (zzcl == this.tag);
            this.zznd = zzcl;
            return;
        }
        switch (this.tag & 7) {
            case 1:
                break;
            case 2:
                zzcl = this.zznb.zzcl();
                zzt(zzcl);
                zzcl += this.zznb.zzct();
                do {
                    list.add(Double.valueOf(this.zznb.readDouble()));
                } while (this.zznb.zzct() < zzcl);
                return;
            default:
                throw zzdh.zzei();
        }
        do {
            list.add(Double.valueOf(this.zznb.readDouble()));
            if (!this.zznb.zzcs()) {
                zzcl = this.zznb.zzcc();
            } else {
                return;
            }
        } while (zzcl == this.tag);
        this.zznd = zzcl;
    }

    public final long zzcd() {
        zzs(0);
        return this.zznb.zzcd();
    }

    public final long zzce() {
        zzs(0);
        return this.zznb.zzce();
    }

    public final int zzcf() {
        zzs(0);
        return this.zznb.zzcf();
    }

    public final long zzcg() {
        zzs(1);
        return this.zznb.zzcg();
    }

    public final int zzch() {
        zzs(5);
        return this.zznb.zzch();
    }

    public final boolean zzci() {
        zzs(0);
        return this.zznb.zzci();
    }

    public final String zzcj() {
        zzs(2);
        return this.zznb.zzcj();
    }

    public final zzbu zzck() {
        zzs(2);
        return this.zznb.zzck();
    }

    public final int zzcl() {
        zzs(0);
        return this.zznb.zzcl();
    }

    public final int zzcm() {
        zzs(0);
        return this.zznb.zzcm();
    }

    public final int zzcn() {
        zzs(5);
        return this.zznb.zzcn();
    }

    public final long zzco() {
        zzs(1);
        return this.zznb.zzco();
    }

    public final int zzcp() {
        zzs(0);
        return this.zznb.zzcp();
    }

    public final long zzcq() {
        zzs(0);
        return this.zznb.zzcq();
    }

    public final void zzd(List<Float> list) {
        int zzcl;
        if (list instanceof zzcy) {
            zzcy zzcy = (zzcy) list;
            switch (this.tag & 7) {
                case 2:
                    zzcl = this.zznb.zzcl();
                    zzu(zzcl);
                    zzcl += this.zznb.zzct();
                    do {
                        zzcy.zzc(this.zznb.readFloat());
                    } while (this.zznb.zzct() < zzcl);
                    return;
                case 5:
                    break;
                default:
                    throw zzdh.zzei();
            }
            do {
                zzcy.zzc(this.zznb.readFloat());
                if (!this.zznb.zzcs()) {
                    zzcl = this.zznb.zzcc();
                } else {
                    return;
                }
            } while (zzcl == this.tag);
            this.zznd = zzcl;
            return;
        }
        switch (this.tag & 7) {
            case 2:
                zzcl = this.zznb.zzcl();
                zzu(zzcl);
                zzcl += this.zznb.zzct();
                do {
                    list.add(Float.valueOf(this.zznb.readFloat()));
                } while (this.zznb.zzct() < zzcl);
                return;
            case 5:
                break;
            default:
                throw zzdh.zzei();
        }
        do {
            list.add(Float.valueOf(this.zznb.readFloat()));
            if (!this.zznb.zzcs()) {
                zzcl = this.zznb.zzcc();
            } else {
                return;
            }
        } while (zzcl == this.tag);
        this.zznd = zzcl;
    }

    public final int zzda() {
        if (this.zznd != 0) {
            this.tag = this.zznd;
            this.zznd = 0;
        } else {
            this.tag = this.zznb.zzcc();
        }
        return (this.tag == 0 || this.tag == this.zznc) ? Integer.MAX_VALUE : this.tag >>> 3;
    }

    public final boolean zzdb() {
        return (this.zznb.zzcs() || this.tag == this.zznc) ? false : this.zznb.zzn(this.tag);
    }

    public final void zze(List<Long> list) {
        int zzcl;
        if (list instanceof zzdv) {
            zzdv zzdv = (zzdv) list;
            switch (this.tag & 7) {
                case 0:
                    break;
                case 2:
                    zzcl = this.zznb.zzcl() + this.zznb.zzct();
                    do {
                        zzdv.zzl(this.zznb.zzcd());
                    } while (this.zznb.zzct() < zzcl);
                    zzv(zzcl);
                    return;
                default:
                    throw zzdh.zzei();
            }
            do {
                zzdv.zzl(this.zznb.zzcd());
                if (!this.zznb.zzcs()) {
                    zzcl = this.zznb.zzcc();
                } else {
                    return;
                }
            } while (zzcl == this.tag);
            this.zznd = zzcl;
            return;
        }
        switch (this.tag & 7) {
            case 0:
                break;
            case 2:
                zzcl = this.zznb.zzcl() + this.zznb.zzct();
                do {
                    list.add(Long.valueOf(this.zznb.zzcd()));
                } while (this.zznb.zzct() < zzcl);
                zzv(zzcl);
                return;
            default:
                throw zzdh.zzei();
        }
        do {
            list.add(Long.valueOf(this.zznb.zzcd()));
            if (!this.zznb.zzcs()) {
                zzcl = this.zznb.zzcc();
            } else {
                return;
            }
        } while (zzcl == this.tag);
        this.zznd = zzcl;
    }

    public final void zzf(List<Long> list) {
        int zzcl;
        if (list instanceof zzdv) {
            zzdv zzdv = (zzdv) list;
            switch (this.tag & 7) {
                case 0:
                    break;
                case 2:
                    zzcl = this.zznb.zzcl() + this.zznb.zzct();
                    do {
                        zzdv.zzl(this.zznb.zzce());
                    } while (this.zznb.zzct() < zzcl);
                    zzv(zzcl);
                    return;
                default:
                    throw zzdh.zzei();
            }
            do {
                zzdv.zzl(this.zznb.zzce());
                if (!this.zznb.zzcs()) {
                    zzcl = this.zznb.zzcc();
                } else {
                    return;
                }
            } while (zzcl == this.tag);
            this.zznd = zzcl;
            return;
        }
        switch (this.tag & 7) {
            case 0:
                break;
            case 2:
                zzcl = this.zznb.zzcl() + this.zznb.zzct();
                do {
                    list.add(Long.valueOf(this.zznb.zzce()));
                } while (this.zznb.zzct() < zzcl);
                zzv(zzcl);
                return;
            default:
                throw zzdh.zzei();
        }
        do {
            list.add(Long.valueOf(this.zznb.zzce()));
            if (!this.zznb.zzcs()) {
                zzcl = this.zznb.zzcc();
            } else {
                return;
            }
        } while (zzcl == this.tag);
        this.zznd = zzcl;
    }

    public final void zzg(List<Integer> list) {
        int zzcl;
        if (list instanceof zzdc) {
            zzdc zzdc = (zzdc) list;
            switch (this.tag & 7) {
                case 0:
                    break;
                case 2:
                    zzcl = this.zznb.zzcl() + this.zznb.zzct();
                    do {
                        zzdc.zzal(this.zznb.zzcf());
                    } while (this.zznb.zzct() < zzcl);
                    zzv(zzcl);
                    return;
                default:
                    throw zzdh.zzei();
            }
            do {
                zzdc.zzal(this.zznb.zzcf());
                if (!this.zznb.zzcs()) {
                    zzcl = this.zznb.zzcc();
                } else {
                    return;
                }
            } while (zzcl == this.tag);
            this.zznd = zzcl;
            return;
        }
        switch (this.tag & 7) {
            case 0:
                break;
            case 2:
                zzcl = this.zznb.zzcl() + this.zznb.zzct();
                do {
                    list.add(Integer.valueOf(this.zznb.zzcf()));
                } while (this.zznb.zzct() < zzcl);
                zzv(zzcl);
                return;
            default:
                throw zzdh.zzei();
        }
        do {
            list.add(Integer.valueOf(this.zznb.zzcf()));
            if (!this.zznb.zzcs()) {
                zzcl = this.zznb.zzcc();
            } else {
                return;
            }
        } while (zzcl == this.tag);
        this.zznd = zzcl;
    }

    public final void zzh(List<Long> list) {
        int zzcl;
        if (list instanceof zzdv) {
            zzdv zzdv = (zzdv) list;
            switch (this.tag & 7) {
                case 1:
                    break;
                case 2:
                    zzcl = this.zznb.zzcl();
                    zzt(zzcl);
                    zzcl += this.zznb.zzct();
                    do {
                        zzdv.zzl(this.zznb.zzcg());
                    } while (this.zznb.zzct() < zzcl);
                    return;
                default:
                    throw zzdh.zzei();
            }
            do {
                zzdv.zzl(this.zznb.zzcg());
                if (!this.zznb.zzcs()) {
                    zzcl = this.zznb.zzcc();
                } else {
                    return;
                }
            } while (zzcl == this.tag);
            this.zznd = zzcl;
            return;
        }
        switch (this.tag & 7) {
            case 1:
                break;
            case 2:
                zzcl = this.zznb.zzcl();
                zzt(zzcl);
                zzcl += this.zznb.zzct();
                do {
                    list.add(Long.valueOf(this.zznb.zzcg()));
                } while (this.zznb.zzct() < zzcl);
                return;
            default:
                throw zzdh.zzei();
        }
        do {
            list.add(Long.valueOf(this.zznb.zzcg()));
            if (!this.zznb.zzcs()) {
                zzcl = this.zznb.zzcc();
            } else {
                return;
            }
        } while (zzcl == this.tag);
        this.zznd = zzcl;
    }

    public final void zzi(List<Integer> list) {
        int zzcl;
        if (list instanceof zzdc) {
            zzdc zzdc = (zzdc) list;
            switch (this.tag & 7) {
                case 2:
                    zzcl = this.zznb.zzcl();
                    zzu(zzcl);
                    zzcl += this.zznb.zzct();
                    do {
                        zzdc.zzal(this.zznb.zzch());
                    } while (this.zznb.zzct() < zzcl);
                    return;
                case 5:
                    break;
                default:
                    throw zzdh.zzei();
            }
            do {
                zzdc.zzal(this.zznb.zzch());
                if (!this.zznb.zzcs()) {
                    zzcl = this.zznb.zzcc();
                } else {
                    return;
                }
            } while (zzcl == this.tag);
            this.zznd = zzcl;
            return;
        }
        switch (this.tag & 7) {
            case 2:
                zzcl = this.zznb.zzcl();
                zzu(zzcl);
                zzcl += this.zznb.zzct();
                do {
                    list.add(Integer.valueOf(this.zznb.zzch()));
                } while (this.zznb.zzct() < zzcl);
                return;
            case 5:
                break;
            default:
                throw zzdh.zzei();
        }
        do {
            list.add(Integer.valueOf(this.zznb.zzch()));
            if (!this.zznb.zzcs()) {
                zzcl = this.zznb.zzcc();
            } else {
                return;
            }
        } while (zzcl == this.tag);
        this.zznd = zzcl;
    }

    public final void zzj(List<Boolean> list) {
        int zzcl;
        if (list instanceof zzbs) {
            zzbs zzbs = (zzbs) list;
            switch (this.tag & 7) {
                case 0:
                    break;
                case 2:
                    zzcl = this.zznb.zzcl() + this.zznb.zzct();
                    do {
                        zzbs.addBoolean(this.zznb.zzci());
                    } while (this.zznb.zzct() < zzcl);
                    zzv(zzcl);
                    return;
                default:
                    throw zzdh.zzei();
            }
            do {
                zzbs.addBoolean(this.zznb.zzci());
                if (!this.zznb.zzcs()) {
                    zzcl = this.zznb.zzcc();
                } else {
                    return;
                }
            } while (zzcl == this.tag);
            this.zznd = zzcl;
            return;
        }
        switch (this.tag & 7) {
            case 0:
                break;
            case 2:
                zzcl = this.zznb.zzcl() + this.zznb.zzct();
                do {
                    list.add(Boolean.valueOf(this.zznb.zzci()));
                } while (this.zznb.zzct() < zzcl);
                zzv(zzcl);
                return;
            default:
                throw zzdh.zzei();
        }
        do {
            list.add(Boolean.valueOf(this.zznb.zzci()));
            if (!this.zznb.zzcs()) {
                zzcl = this.zznb.zzcc();
            } else {
                return;
            }
        } while (zzcl == this.tag);
        this.zznd = zzcl;
    }

    public final void zzk(List<String> list) {
        zza((List) list, true);
    }

    public final void zzl(List<zzbu> list) {
        if ((this.tag & 7) != 2) {
            throw zzdh.zzei();
        }
        int zzcc;
        do {
            list.add(zzck());
            if (!this.zznb.zzcs()) {
                zzcc = this.zznb.zzcc();
            } else {
                return;
            }
        } while (zzcc == this.tag);
        this.zznd = zzcc;
    }

    public final void zzm(List<Integer> list) {
        int zzcl;
        if (list instanceof zzdc) {
            zzdc zzdc = (zzdc) list;
            switch (this.tag & 7) {
                case 0:
                    break;
                case 2:
                    zzcl = this.zznb.zzcl() + this.zznb.zzct();
                    do {
                        zzdc.zzal(this.zznb.zzcl());
                    } while (this.zznb.zzct() < zzcl);
                    zzv(zzcl);
                    return;
                default:
                    throw zzdh.zzei();
            }
            do {
                zzdc.zzal(this.zznb.zzcl());
                if (!this.zznb.zzcs()) {
                    zzcl = this.zznb.zzcc();
                } else {
                    return;
                }
            } while (zzcl == this.tag);
            this.zznd = zzcl;
            return;
        }
        switch (this.tag & 7) {
            case 0:
                break;
            case 2:
                zzcl = this.zznb.zzcl() + this.zznb.zzct();
                do {
                    list.add(Integer.valueOf(this.zznb.zzcl()));
                } while (this.zznb.zzct() < zzcl);
                zzv(zzcl);
                return;
            default:
                throw zzdh.zzei();
        }
        do {
            list.add(Integer.valueOf(this.zznb.zzcl()));
            if (!this.zznb.zzcs()) {
                zzcl = this.zznb.zzcc();
            } else {
                return;
            }
        } while (zzcl == this.tag);
        this.zznd = zzcl;
    }

    public final void zzn(List<Integer> list) {
        int zzcl;
        if (list instanceof zzdc) {
            zzdc zzdc = (zzdc) list;
            switch (this.tag & 7) {
                case 0:
                    break;
                case 2:
                    zzcl = this.zznb.zzcl() + this.zznb.zzct();
                    do {
                        zzdc.zzal(this.zznb.zzcm());
                    } while (this.zznb.zzct() < zzcl);
                    zzv(zzcl);
                    return;
                default:
                    throw zzdh.zzei();
            }
            do {
                zzdc.zzal(this.zznb.zzcm());
                if (!this.zznb.zzcs()) {
                    zzcl = this.zznb.zzcc();
                } else {
                    return;
                }
            } while (zzcl == this.tag);
            this.zznd = zzcl;
            return;
        }
        switch (this.tag & 7) {
            case 0:
                break;
            case 2:
                zzcl = this.zznb.zzcl() + this.zznb.zzct();
                do {
                    list.add(Integer.valueOf(this.zznb.zzcm()));
                } while (this.zznb.zzct() < zzcl);
                zzv(zzcl);
                return;
            default:
                throw zzdh.zzei();
        }
        do {
            list.add(Integer.valueOf(this.zznb.zzcm()));
            if (!this.zznb.zzcs()) {
                zzcl = this.zznb.zzcc();
            } else {
                return;
            }
        } while (zzcl == this.tag);
        this.zznd = zzcl;
    }

    public final void zzo(List<Integer> list) {
        int zzcl;
        if (list instanceof zzdc) {
            zzdc zzdc = (zzdc) list;
            switch (this.tag & 7) {
                case 2:
                    zzcl = this.zznb.zzcl();
                    zzu(zzcl);
                    zzcl += this.zznb.zzct();
                    do {
                        zzdc.zzal(this.zznb.zzcn());
                    } while (this.zznb.zzct() < zzcl);
                    return;
                case 5:
                    break;
                default:
                    throw zzdh.zzei();
            }
            do {
                zzdc.zzal(this.zznb.zzcn());
                if (!this.zznb.zzcs()) {
                    zzcl = this.zznb.zzcc();
                } else {
                    return;
                }
            } while (zzcl == this.tag);
            this.zznd = zzcl;
            return;
        }
        switch (this.tag & 7) {
            case 2:
                zzcl = this.zznb.zzcl();
                zzu(zzcl);
                zzcl += this.zznb.zzct();
                do {
                    list.add(Integer.valueOf(this.zznb.zzcn()));
                } while (this.zznb.zzct() < zzcl);
                return;
            case 5:
                break;
            default:
                throw zzdh.zzei();
        }
        do {
            list.add(Integer.valueOf(this.zznb.zzcn()));
            if (!this.zznb.zzcs()) {
                zzcl = this.zznb.zzcc();
            } else {
                return;
            }
        } while (zzcl == this.tag);
        this.zznd = zzcl;
    }

    public final void zzp(List<Long> list) {
        int zzcl;
        if (list instanceof zzdv) {
            zzdv zzdv = (zzdv) list;
            switch (this.tag & 7) {
                case 1:
                    break;
                case 2:
                    zzcl = this.zznb.zzcl();
                    zzt(zzcl);
                    zzcl += this.zznb.zzct();
                    do {
                        zzdv.zzl(this.zznb.zzco());
                    } while (this.zznb.zzct() < zzcl);
                    return;
                default:
                    throw zzdh.zzei();
            }
            do {
                zzdv.zzl(this.zznb.zzco());
                if (!this.zznb.zzcs()) {
                    zzcl = this.zznb.zzcc();
                } else {
                    return;
                }
            } while (zzcl == this.tag);
            this.zznd = zzcl;
            return;
        }
        switch (this.tag & 7) {
            case 1:
                break;
            case 2:
                zzcl = this.zznb.zzcl();
                zzt(zzcl);
                zzcl += this.zznb.zzct();
                do {
                    list.add(Long.valueOf(this.zznb.zzco()));
                } while (this.zznb.zzct() < zzcl);
                return;
            default:
                throw zzdh.zzei();
        }
        do {
            list.add(Long.valueOf(this.zznb.zzco()));
            if (!this.zznb.zzcs()) {
                zzcl = this.zznb.zzcc();
            } else {
                return;
            }
        } while (zzcl == this.tag);
        this.zznd = zzcl;
    }

    public final void zzq(List<Integer> list) {
        int zzcl;
        if (list instanceof zzdc) {
            zzdc zzdc = (zzdc) list;
            switch (this.tag & 7) {
                case 0:
                    break;
                case 2:
                    zzcl = this.zznb.zzcl() + this.zznb.zzct();
                    do {
                        zzdc.zzal(this.zznb.zzcp());
                    } while (this.zznb.zzct() < zzcl);
                    zzv(zzcl);
                    return;
                default:
                    throw zzdh.zzei();
            }
            do {
                zzdc.zzal(this.zznb.zzcp());
                if (!this.zznb.zzcs()) {
                    zzcl = this.zznb.zzcc();
                } else {
                    return;
                }
            } while (zzcl == this.tag);
            this.zznd = zzcl;
            return;
        }
        switch (this.tag & 7) {
            case 0:
                break;
            case 2:
                zzcl = this.zznb.zzcl() + this.zznb.zzct();
                do {
                    list.add(Integer.valueOf(this.zznb.zzcp()));
                } while (this.zznb.zzct() < zzcl);
                zzv(zzcl);
                return;
            default:
                throw zzdh.zzei();
        }
        do {
            list.add(Integer.valueOf(this.zznb.zzcp()));
            if (!this.zznb.zzcs()) {
                zzcl = this.zznb.zzcc();
            } else {
                return;
            }
        } while (zzcl == this.tag);
        this.zznd = zzcl;
    }

    public final void zzr(List<Long> list) {
        int zzcl;
        if (list instanceof zzdv) {
            zzdv zzdv = (zzdv) list;
            switch (this.tag & 7) {
                case 0:
                    break;
                case 2:
                    zzcl = this.zznb.zzcl() + this.zznb.zzct();
                    do {
                        zzdv.zzl(this.zznb.zzcq());
                    } while (this.zznb.zzct() < zzcl);
                    zzv(zzcl);
                    return;
                default:
                    throw zzdh.zzei();
            }
            do {
                zzdv.zzl(this.zznb.zzcq());
                if (!this.zznb.zzcs()) {
                    zzcl = this.zznb.zzcc();
                } else {
                    return;
                }
            } while (zzcl == this.tag);
            this.zznd = zzcl;
            return;
        }
        switch (this.tag & 7) {
            case 0:
                break;
            case 2:
                zzcl = this.zznb.zzcl() + this.zznb.zzct();
                do {
                    list.add(Long.valueOf(this.zznb.zzcq()));
                } while (this.zznb.zzct() < zzcl);
                zzv(zzcl);
                return;
            default:
                throw zzdh.zzei();
        }
        do {
            list.add(Long.valueOf(this.zznb.zzcq()));
            if (!this.zznb.zzcs()) {
                zzcl = this.zznb.zzcc();
            } else {
                return;
            }
        } while (zzcl == this.tag);
        this.zznd = zzcl;
    }
}
