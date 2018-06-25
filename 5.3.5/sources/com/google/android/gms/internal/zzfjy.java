package com.google.android.gms.internal;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

class zzfjy<K extends Comparable<K>, V> extends AbstractMap<K, V> {
    private boolean zzldh;
    private final int zzpsk;
    private List<zzfkd> zzpsl;
    private Map<K, V> zzpsm;
    private volatile zzfkf zzpsn;
    private Map<K, V> zzpso;

    private zzfjy(int i) {
        this.zzpsk = i;
        this.zzpsl = Collections.emptyList();
        this.zzpsm = Collections.emptyMap();
        this.zzpso = Collections.emptyMap();
    }

    private final int zza(K k) {
        int compareTo;
        int size = this.zzpsl.size() - 1;
        if (size >= 0) {
            compareTo = k.compareTo((Comparable) ((zzfkd) this.zzpsl.get(size)).getKey());
            if (compareTo > 0) {
                return -(size + 2);
            }
            if (compareTo == 0) {
                return size;
            }
        }
        int i = 0;
        int i2 = size;
        while (i <= i2) {
            size = (i + i2) / 2;
            compareTo = k.compareTo((Comparable) ((zzfkd) this.zzpsl.get(size)).getKey());
            if (compareTo < 0) {
                i2 = size - 1;
            } else if (compareTo <= 0) {
                return size;
            } else {
                i = size + 1;
            }
        }
        return -(i + 1);
    }

    private final void zzdbr() {
        if (this.zzldh) {
            throw new UnsupportedOperationException();
        }
    }

    private final SortedMap<K, V> zzdbs() {
        zzdbr();
        if (this.zzpsm.isEmpty() && !(this.zzpsm instanceof TreeMap)) {
            this.zzpsm = new TreeMap();
            this.zzpso = ((TreeMap) this.zzpsm).descendingMap();
        }
        return (SortedMap) this.zzpsm;
    }

    static <FieldDescriptorType extends zzfhs<FieldDescriptorType>> zzfjy<FieldDescriptorType, Object> zzmq(int i) {
        return new zzfjz(i);
    }

    private final V zzms(int i) {
        zzdbr();
        V value = ((zzfkd) this.zzpsl.remove(i)).getValue();
        if (!this.zzpsm.isEmpty()) {
            Iterator it = zzdbs().entrySet().iterator();
            this.zzpsl.add(new zzfkd(this, (Entry) it.next()));
            it.remove();
        }
        return value;
    }

    public void clear() {
        zzdbr();
        if (!this.zzpsl.isEmpty()) {
            this.zzpsl.clear();
        }
        if (!this.zzpsm.isEmpty()) {
            this.zzpsm.clear();
        }
    }

    public boolean containsKey(Object obj) {
        Comparable comparable = (Comparable) obj;
        return zza(comparable) >= 0 || this.zzpsm.containsKey(comparable);
    }

    public Set<Entry<K, V>> entrySet() {
        if (this.zzpsn == null) {
            this.zzpsn = new zzfkf();
        }
        return this.zzpsn;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzfjy)) {
            return super.equals(obj);
        }
        zzfjy zzfjy = (zzfjy) obj;
        int size = size();
        if (size != zzfjy.size()) {
            return false;
        }
        int zzdbp = zzdbp();
        if (zzdbp != zzfjy.zzdbp()) {
            return entrySet().equals(zzfjy.entrySet());
        }
        for (int i = 0; i < zzdbp; i++) {
            if (!zzmr(i).equals(zzfjy.zzmr(i))) {
                return false;
            }
        }
        return zzdbp != size ? this.zzpsm.equals(zzfjy.zzpsm) : true;
    }

    public V get(Object obj) {
        Comparable comparable = (Comparable) obj;
        int zza = zza(comparable);
        return zza >= 0 ? ((zzfkd) this.zzpsl.get(zza)).getValue() : this.zzpsm.get(comparable);
    }

    public int hashCode() {
        int i = 0;
        for (int i2 = 0; i2 < zzdbp(); i2++) {
            i += ((zzfkd) this.zzpsl.get(i2)).hashCode();
        }
        return this.zzpsm.size() > 0 ? this.zzpsm.hashCode() + i : i;
    }

    public final boolean isImmutable() {
        return this.zzldh;
    }

    public /* synthetic */ Object put(Object obj, Object obj2) {
        return zza((Comparable) obj, obj2);
    }

    public V remove(Object obj) {
        zzdbr();
        Comparable comparable = (Comparable) obj;
        int zza = zza(comparable);
        return zza >= 0 ? zzms(zza) : this.zzpsm.isEmpty() ? null : this.zzpsm.remove(comparable);
    }

    public int size() {
        return this.zzpsl.size() + this.zzpsm.size();
    }

    public final V zza(K k, V v) {
        zzdbr();
        int zza = zza((Comparable) k);
        if (zza >= 0) {
            return ((zzfkd) this.zzpsl.get(zza)).setValue(v);
        }
        zzdbr();
        if (this.zzpsl.isEmpty() && !(this.zzpsl instanceof ArrayList)) {
            this.zzpsl = new ArrayList(this.zzpsk);
        }
        int i = -(zza + 1);
        if (i >= this.zzpsk) {
            return zzdbs().put(k, v);
        }
        if (this.zzpsl.size() == this.zzpsk) {
            zzfkd zzfkd = (zzfkd) this.zzpsl.remove(this.zzpsk - 1);
            zzdbs().put((Comparable) zzfkd.getKey(), zzfkd.getValue());
        }
        this.zzpsl.add(i, new zzfkd(this, k, v));
        return null;
    }

    public void zzbkr() {
        if (!this.zzldh) {
            this.zzpsm = this.zzpsm.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(this.zzpsm);
            this.zzpso = this.zzpso.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(this.zzpso);
            this.zzldh = true;
        }
    }

    public final int zzdbp() {
        return this.zzpsl.size();
    }

    public final Iterable<Entry<K, V>> zzdbq() {
        return this.zzpsm.isEmpty() ? zzfka.zzdbt() : this.zzpsm.entrySet();
    }

    public final Entry<K, V> zzmr(int i) {
        return (Entry) this.zzpsl.get(i);
    }
}
