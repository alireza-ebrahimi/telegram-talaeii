package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public final class zzeee<K, V> extends zzedq<K, V> {
    private Comparator<K> zzmyh;
    private zzedz<K, V> zzmyx;

    private zzeee(zzedz<K, V> zzedz, Comparator<K> comparator) {
        this.zzmyx = zzedz;
        this.zzmyh = comparator;
    }

    public static <A, B> zzeee<A, B> zzb(Map<A, B> map, Comparator<A> comparator) {
        return zzeeg.zzc(new ArrayList(map.keySet()), map, zzedr.zzbvs(), comparator);
    }

    private final zzedz<K, V> zzbs(K k) {
        zzedz<K, V> zzedz = this.zzmyx;
        while (!zzedz.isEmpty()) {
            int compare = this.zzmyh.compare(k, zzedz.getKey());
            if (compare < 0) {
                zzedz = zzedz.zzbvy();
            } else if (compare == 0) {
                return zzedz;
            } else {
                zzedz = zzedz.zzbvz();
            }
        }
        return null;
    }

    public final boolean containsKey(K k) {
        return zzbs(k) != null;
    }

    public final V get(K k) {
        zzedz zzbs = zzbs(k);
        return zzbs != null ? zzbs.getValue() : null;
    }

    public final Comparator<K> getComparator() {
        return this.zzmyh;
    }

    public final int indexOf(K k) {
        int i = 0;
        zzedz zzedz = this.zzmyx;
        while (!zzedz.isEmpty()) {
            int compare = this.zzmyh.compare(k, zzedz.getKey());
            if (compare == 0) {
                return zzedz.zzbvy().size() + i;
            }
            if (compare < 0) {
                zzedz = zzedz.zzbvy();
            } else {
                i += zzedz.zzbvy().size() + 1;
                zzedz = zzedz.zzbvz();
            }
        }
        return -1;
    }

    public final boolean isEmpty() {
        return this.zzmyx.isEmpty();
    }

    public final Iterator<Entry<K, V>> iterator() {
        return new zzedu(this.zzmyx, null, this.zzmyh, false);
    }

    public final int size() {
        return this.zzmyx.size();
    }

    public final void zza(zzeeb<K, V> zzeeb) {
        this.zzmyx.zza(zzeeb);
    }

    public final zzedq<K, V> zzbj(K k) {
        if (!containsKey(k)) {
            return this;
        }
        return new zzeee(this.zzmyx.zza(k, this.zzmyh).zza(null, null, zzeea.zzmyt, null, null), this.zzmyh);
    }

    public final Iterator<Entry<K, V>> zzbk(K k) {
        return new zzedu(this.zzmyx, k, this.zzmyh, false);
    }

    public final K zzbl(K k) {
        zzedz zzedz = this.zzmyx;
        zzedz zzedz2 = null;
        while (!zzedz.isEmpty()) {
            int compare = this.zzmyh.compare(k, zzedz.getKey());
            if (compare == 0) {
                if (zzedz.zzbvy().isEmpty()) {
                    return zzedz2 != null ? zzedz2.getKey() : null;
                } else {
                    zzedz2 = zzedz.zzbvy();
                    while (!zzedz2.zzbvz().isEmpty()) {
                        zzedz2 = zzedz2.zzbvz();
                    }
                    return zzedz2.getKey();
                }
            } else if (compare < 0) {
                zzedz = zzedz.zzbvy();
            } else {
                zzedz zzedz3 = zzedz;
                zzedz = zzedz.zzbvz();
                zzedz2 = zzedz3;
            }
        }
        String valueOf = String.valueOf(k);
        throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 50).append("Couldn't find predecessor key of non-present key: ").append(valueOf).toString());
    }

    public final K zzbvp() {
        return this.zzmyx.zzbwa().getKey();
    }

    public final K zzbvq() {
        return this.zzmyx.zzbwb().getKey();
    }

    public final Iterator<Entry<K, V>> zzbvr() {
        return new zzedu(this.zzmyx, null, this.zzmyh, true);
    }

    public final zzedq<K, V> zzg(K k, V v) {
        return new zzeee(this.zzmyx.zza(k, v, this.zzmyh).zza(null, null, zzeea.zzmyt, null, null), this.zzmyh);
    }
}
