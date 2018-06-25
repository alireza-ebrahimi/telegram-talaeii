package com.google.android.gms.internal;

import java.util.Comparator;

public abstract class zzeed<K, V> implements zzedz<K, V> {
    private final V value;
    private final K zzmbd;
    private zzedz<K, V> zzmyv;
    private final zzedz<K, V> zzmyw;

    zzeed(K k, V v, zzedz<K, V> zzedz, zzedz<K, V> zzedz2) {
        zzedz zzbvx;
        zzedz zzbvx2;
        this.zzmbd = k;
        this.value = v;
        if (zzedz == null) {
            zzbvx = zzedy.zzbvx();
        }
        this.zzmyv = zzbvx;
        if (zzedz2 == null) {
            zzbvx2 = zzedy.zzbvx();
        }
        this.zzmyw = zzbvx2;
    }

    private static int zzb(zzedz zzedz) {
        return zzedz.zzbvw() ? zzeea.zzmyt : zzeea.zzmys;
    }

    private final zzeed<K, V> zzb(K k, V v, Integer num, zzedz<K, V> zzedz, zzedz<K, V> zzedz2) {
        zzedz zzedz3;
        zzedz zzedz4;
        Object obj = this.zzmbd;
        Object obj2 = this.value;
        if (zzedz == null) {
            zzedz3 = this.zzmyv;
        }
        if (zzedz2 == null) {
            zzedz4 = this.zzmyw;
        }
        return num == zzeea.zzmys ? new zzeec(obj, obj2, zzedz3, zzedz4) : new zzedx(obj, obj2, zzedz3, zzedz4);
    }

    private final zzedz<K, V> zzbwc() {
        if (this.zzmyv.isEmpty()) {
            return zzedy.zzbvx();
        }
        if (!(this.zzmyv.zzbvw() || this.zzmyv.zzbvy().zzbvw())) {
            this = zzbwd();
        }
        return zza(null, null, ((zzeed) this.zzmyv).zzbwc(), null).zzbwe();
    }

    private final zzeed<K, V> zzbwd() {
        zzeed<K, V> zzbwh = zzbwh();
        return zzbwh.zzmyw.zzbvy().zzbvw() ? zzbwh.zza(null, null, null, ((zzeed) zzbwh.zzmyw).zzbwg()).zzbwf().zzbwh() : zzbwh;
    }

    private final zzeed<K, V> zzbwe() {
        zzeed<K, V> zzbwf;
        if (this.zzmyw.zzbvw() && !this.zzmyv.zzbvw()) {
            zzbwf = zzbwf();
        }
        if (zzbwf.zzmyv.zzbvw() && ((zzeed) zzbwf.zzmyv).zzmyv.zzbvw()) {
            zzbwf = zzbwf.zzbwg();
        }
        return (zzbwf.zzmyv.zzbvw() && zzbwf.zzmyw.zzbvw()) ? zzbwf.zzbwh() : zzbwf;
    }

    private final zzeed<K, V> zzbwf() {
        return (zzeed) this.zzmyw.zza(null, null, zzbvv(), zzb(null, null, zzeea.zzmys, null, ((zzeed) this.zzmyw).zzmyv), null);
    }

    private final zzeed<K, V> zzbwg() {
        return (zzeed) this.zzmyv.zza(null, null, zzbvv(), null, zzb(null, null, zzeea.zzmys, ((zzeed) this.zzmyv).zzmyw, null));
    }

    private final zzeed<K, V> zzbwh() {
        return zzb(null, null, zzb(this), this.zzmyv.zza(null, null, zzb(this.zzmyv), null, null), this.zzmyw.zza(null, null, zzb(this.zzmyw), null, null));
    }

    public final K getKey() {
        return this.zzmbd;
    }

    public final V getValue() {
        return this.value;
    }

    public final boolean isEmpty() {
        return false;
    }

    public final /* synthetic */ zzedz zza(Object obj, Object obj2, int i, zzedz zzedz, zzedz zzedz2) {
        return zzb(null, null, i, zzedz, zzedz2);
    }

    public final zzedz<K, V> zza(K k, V v, Comparator<K> comparator) {
        int compare = comparator.compare(k, this.zzmbd);
        zzeed zza = compare < 0 ? zza(null, null, this.zzmyv.zza(k, v, comparator), null) : compare == 0 ? zza(k, v, null, null) : zza(null, null, null, this.zzmyw.zza(k, v, comparator));
        return zza.zzbwe();
    }

    public final zzedz<K, V> zza(K k, Comparator<K> comparator) {
        zzeed zza;
        if (comparator.compare(k, this.zzmbd) < 0) {
            if (!(this.zzmyv.isEmpty() || this.zzmyv.zzbvw() || ((zzeed) this.zzmyv).zzmyv.zzbvw())) {
                this = zzbwd();
            }
            zza = zza(null, null, this.zzmyv.zza(k, comparator), null);
        } else {
            if (this.zzmyv.zzbvw()) {
                this = zzbwg();
            }
            if (!(this.zzmyw.isEmpty() || this.zzmyw.zzbvw() || ((zzeed) this.zzmyw).zzmyv.zzbvw())) {
                zza = zzbwh();
                if (zza.zzmyv.zzbvy().zzbvw()) {
                    zza = zza.zzbwg().zzbwh();
                }
                this = zza;
            }
            if (comparator.compare(k, this.zzmbd) == 0) {
                if (this.zzmyw.isEmpty()) {
                    return zzedy.zzbvx();
                }
                zzedz zzbwa = this.zzmyw.zzbwa();
                this = zza(zzbwa.getKey(), zzbwa.getValue(), null, ((zzeed) this.zzmyw).zzbwc());
            }
            zza = zza(null, null, null, this.zzmyw.zza(k, comparator));
        }
        return zza.zzbwe();
    }

    protected abstract zzeed<K, V> zza(K k, V v, zzedz<K, V> zzedz, zzedz<K, V> zzedz2);

    void zza(zzedz<K, V> zzedz) {
        this.zzmyv = zzedz;
    }

    public final void zza(zzeeb<K, V> zzeeb) {
        this.zzmyv.zza(zzeeb);
        zzeeb.zzh(this.zzmbd, this.value);
        this.zzmyw.zza(zzeeb);
    }

    protected abstract int zzbvv();

    public final zzedz<K, V> zzbvy() {
        return this.zzmyv;
    }

    public final zzedz<K, V> zzbvz() {
        return this.zzmyw;
    }

    public final zzedz<K, V> zzbwa() {
        return this.zzmyv.isEmpty() ? this : this.zzmyv.zzbwa();
    }

    public final zzedz<K, V> zzbwb() {
        return this.zzmyw.isEmpty() ? this : this.zzmyw.zzbwb();
    }
}
