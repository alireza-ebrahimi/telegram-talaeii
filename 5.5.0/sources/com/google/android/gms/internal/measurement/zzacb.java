package com.google.android.gms.internal.measurement;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public final class zzacb<M extends zzaca<M>, T> {
    public final int tag;
    private final int type;
    protected final Class<T> zzbxh;
    protected final boolean zzbxi;
    private final zzzs<?, ?> zzbxj;

    private zzacb(int i, Class<T> cls, int i2, boolean z) {
        this(11, cls, null, 810, false);
    }

    private zzacb(int i, Class<T> cls, zzzs<?, ?> zzzs, int i2, boolean z) {
        this.type = i;
        this.zzbxh = cls;
        this.tag = i2;
        this.zzbxi = false;
        this.zzbxj = null;
    }

    public static <M extends zzaca<M>, T extends zzacg> zzacb<M, T> zza(int i, Class<T> cls, long j) {
        return new zzacb(11, cls, 810, false);
    }

    private final Object zzf(zzabx zzabx) {
        String valueOf;
        Class componentType = this.zzbxi ? this.zzbxh.getComponentType() : this.zzbxh;
        try {
            zzacg zzacg;
            switch (this.type) {
                case 10:
                    zzacg = (zzacg) componentType.newInstance();
                    zzabx.zza(zzacg, this.tag >>> 3);
                    return zzacg;
                case 11:
                    zzacg = (zzacg) componentType.newInstance();
                    zzabx.zza(zzacg);
                    return zzacg;
                default:
                    throw new IllegalArgumentException("Unknown type " + this.type);
            }
        } catch (Throwable e) {
            valueOf = String.valueOf(componentType);
            throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 33).append("Error creating instance of class ").append(valueOf).toString(), e);
        } catch (Throwable e2) {
            valueOf = String.valueOf(componentType);
            throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 33).append("Error creating instance of class ").append(valueOf).toString(), e2);
        } catch (Throwable e22) {
            throw new IllegalArgumentException("Error reading extension field", e22);
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzacb)) {
            return false;
        }
        zzacb zzacb = (zzacb) obj;
        return this.type == zzacb.type && this.zzbxh == zzacb.zzbxh && this.tag == zzacb.tag && this.zzbxi == zzacb.zzbxi;
    }

    public final int hashCode() {
        return (this.zzbxi ? 1 : 0) + ((((((this.type + 1147) * 31) + this.zzbxh.hashCode()) * 31) + this.tag) * 31);
    }

    protected final void zza(Object obj, zzaby zzaby) {
        try {
            zzaby.zzar(this.tag);
            switch (this.type) {
                case 10:
                    int i = this.tag >>> 3;
                    ((zzacg) obj).zza(zzaby);
                    zzaby.zzg(i, 4);
                    return;
                case 11:
                    zzaby.zzb((zzacg) obj);
                    return;
                default:
                    throw new IllegalArgumentException("Unknown type " + this.type);
            }
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
        throw new IllegalStateException(e);
    }

    final T zzi(List<zzaci> list) {
        int i = 0;
        if (list == null) {
            return null;
        }
        if (this.zzbxi) {
            int i2;
            List arrayList = new ArrayList();
            for (i2 = 0; i2 < list.size(); i2++) {
                zzaci zzaci = (zzaci) list.get(i2);
                if (zzaci.zzbrm.length != 0) {
                    arrayList.add(zzf(zzabx.zzi(zzaci.zzbrm)));
                }
            }
            i2 = arrayList.size();
            if (i2 == 0) {
                return null;
            }
            T cast = this.zzbxh.cast(Array.newInstance(this.zzbxh.getComponentType(), i2));
            while (i < i2) {
                Array.set(cast, i, arrayList.get(i));
                i++;
            }
            return cast;
        } else if (list.isEmpty()) {
            return null;
        } else {
            return this.zzbxh.cast(zzf(zzabx.zzi(((zzaci) list.get(list.size() - 1)).zzbrm)));
        }
    }

    protected final int zzv(Object obj) {
        int i = this.tag >>> 3;
        switch (this.type) {
            case 10:
                return (zzaby.zzaq(i) << 1) + ((zzacg) obj).zzvv();
            case 11:
                return zzaby.zzb(i, (zzacg) obj);
            default:
                throw new IllegalArgumentException("Unknown type " + this.type);
        }
    }
}
