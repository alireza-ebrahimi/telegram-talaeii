package com.google.android.gms.internal;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class zzedo<K, V> extends zzedq<K, V> {
    private final K[] zzmav;
    private final V[] zzmyg;
    private final Comparator<K> zzmyh;

    public zzedo(Comparator<K> comparator) {
        this.zzmav = new Object[0];
        this.zzmyg = new Object[0];
        this.zzmyh = comparator;
    }

    private zzedo(Comparator<K> comparator, K[] kArr, V[] vArr) {
        this.zzmav = kArr;
        this.zzmyg = vArr;
        this.zzmyh = comparator;
    }

    public static <A, B, C> zzedo<A, C> zza(List<A> list, Map<B, C> map, zzedt<A, B> zzedt, Comparator<A> comparator) {
        Collections.sort(list, comparator);
        int size = list.size();
        Object[] objArr = new Object[size];
        Object[] objArr2 = new Object[size];
        size = 0;
        for (Object next : list) {
            objArr[size] = next;
            objArr2[size] = map.get(zzedt.zzbo(next));
            size++;
        }
        return new zzedo(comparator, objArr, objArr2);
    }

    private static <T> T[] zza(T[] tArr, int i, T t) {
        int length = tArr.length + 1;
        Object obj = new Object[length];
        System.arraycopy(tArr, 0, obj, 0, i);
        obj[i] = t;
        System.arraycopy(tArr, i, obj, i + 1, (length - i) - 1);
        return obj;
    }

    private static <T> T[] zzb(T[] tArr, int i, T t) {
        int length = tArr.length;
        Object obj = new Object[length];
        System.arraycopy(tArr, 0, obj, 0, length);
        obj[i] = t;
        return obj;
    }

    private final int zzbm(K k) {
        int i = 0;
        while (i < this.zzmav.length && this.zzmyh.compare(this.zzmav[i], k) < 0) {
            i++;
        }
        return i;
    }

    private final int zzbn(K k) {
        int i = 0;
        Object[] objArr = this.zzmav;
        int length = objArr.length;
        int i2 = 0;
        while (i2 < length) {
            if (this.zzmyh.compare(k, objArr[i2]) == 0) {
                return i;
            }
            i2++;
            i++;
        }
        return -1;
    }

    private static <T> T[] zzc(T[] tArr, int i) {
        int length = tArr.length - 1;
        Object obj = new Object[length];
        System.arraycopy(tArr, 0, obj, 0, i);
        System.arraycopy(tArr, i + 1, obj, i, length - i);
        return obj;
    }

    private final Iterator<Entry<K, V>> zzj(int i, boolean z) {
        return new zzedp(this, i, z);
    }

    public final boolean containsKey(K k) {
        return zzbn(k) != -1;
    }

    public final V get(K k) {
        int zzbn = zzbn(k);
        return zzbn != -1 ? this.zzmyg[zzbn] : null;
    }

    public final Comparator<K> getComparator() {
        return this.zzmyh;
    }

    public final int indexOf(K k) {
        return zzbn(k);
    }

    public final boolean isEmpty() {
        return this.zzmav.length == 0;
    }

    public final Iterator<Entry<K, V>> iterator() {
        return zzj(0, false);
    }

    public final int size() {
        return this.zzmav.length;
    }

    public final void zza(zzeeb<K, V> zzeeb) {
        for (int i = 0; i < this.zzmav.length; i++) {
            zzeeb.zzh(this.zzmav[i], this.zzmyg[i]);
        }
    }

    public final zzedq<K, V> zzbj(K k) {
        int zzbn = zzbn(k);
        if (zzbn == -1) {
            return this;
        }
        return new zzedo(this.zzmyh, zzc(this.zzmav, zzbn), zzc(this.zzmyg, zzbn));
    }

    public final Iterator<Entry<K, V>> zzbk(K k) {
        return zzj(zzbm(k), false);
    }

    public final K zzbl(K k) {
        int zzbn = zzbn(k);
        if (zzbn != -1) {
            return zzbn > 0 ? this.zzmav[zzbn - 1] : null;
        } else {
            throw new IllegalArgumentException("Can't find predecessor of nonexistent key");
        }
    }

    public final K zzbvp() {
        return this.zzmav.length > 0 ? this.zzmav[0] : null;
    }

    public final K zzbvq() {
        return this.zzmav.length > 0 ? this.zzmav[this.zzmav.length - 1] : null;
    }

    public final Iterator<Entry<K, V>> zzbvr() {
        return zzj(this.zzmav.length - 1, true);
    }

    public final zzedq<K, V> zzg(K k, V v) {
        int zzbn = zzbn(k);
        if (zzbn != -1) {
            if (this.zzmav[zzbn] == k && this.zzmyg[zzbn] == v) {
                return this;
            }
            return new zzedo(this.zzmyh, zzb(this.zzmav, zzbn, k), zzb(this.zzmyg, zzbn, v));
        } else if (this.zzmav.length > 25) {
            Map hashMap = new HashMap(this.zzmav.length + 1);
            for (zzbn = 0; zzbn < this.zzmav.length; zzbn++) {
                hashMap.put(this.zzmav[zzbn], this.zzmyg[zzbn]);
            }
            hashMap.put(k, v);
            return zzeee.zzb(hashMap, this.zzmyh);
        } else {
            zzbn = zzbm(k);
            return new zzedo(this.zzmyh, zza(this.zzmav, zzbn, k), zza(this.zzmyg, zzbn, v));
        }
    }
}
