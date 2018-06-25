package com.google.android.gms.internal.clearcut;

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

class zzei<K extends Comparable<K>, V> extends AbstractMap<K, V> {
    private boolean zzgu;
    private final int zzol;
    private List<zzep> zzom;
    private Map<K, V> zzon;
    private volatile zzer zzoo;
    private Map<K, V> zzop;
    private volatile zzel zzoq;

    private zzei(int i) {
        this.zzol = i;
        this.zzom = Collections.emptyList();
        this.zzon = Collections.emptyMap();
        this.zzop = Collections.emptyMap();
    }

    private final int zza(K k) {
        int compareTo;
        int size = this.zzom.size() - 1;
        if (size >= 0) {
            compareTo = k.compareTo((Comparable) ((zzep) this.zzom.get(size)).getKey());
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
            compareTo = k.compareTo((Comparable) ((zzep) this.zzom.get(size)).getKey());
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

    static <FieldDescriptorType extends zzca<FieldDescriptorType>> zzei<FieldDescriptorType, Object> zzaj(int i) {
        return new zzej(i);
    }

    private final V zzal(int i) {
        zzdu();
        V value = ((zzep) this.zzom.remove(i)).getValue();
        if (!this.zzon.isEmpty()) {
            Iterator it = zzdv().entrySet().iterator();
            this.zzom.add(new zzep(this, (Entry) it.next()));
            it.remove();
        }
        return value;
    }

    private final void zzdu() {
        if (this.zzgu) {
            throw new UnsupportedOperationException();
        }
    }

    private final SortedMap<K, V> zzdv() {
        zzdu();
        if (this.zzon.isEmpty() && !(this.zzon instanceof TreeMap)) {
            this.zzon = new TreeMap();
            this.zzop = ((TreeMap) this.zzon).descendingMap();
        }
        return (SortedMap) this.zzon;
    }

    public void clear() {
        zzdu();
        if (!this.zzom.isEmpty()) {
            this.zzom.clear();
        }
        if (!this.zzon.isEmpty()) {
            this.zzon.clear();
        }
    }

    public boolean containsKey(Object obj) {
        Comparable comparable = (Comparable) obj;
        return zza(comparable) >= 0 || this.zzon.containsKey(comparable);
    }

    public Set<Entry<K, V>> entrySet() {
        if (this.zzoo == null) {
            this.zzoo = new zzer();
        }
        return this.zzoo;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzei)) {
            return super.equals(obj);
        }
        zzei zzei = (zzei) obj;
        int size = size();
        if (size != zzei.size()) {
            return false;
        }
        int zzdr = zzdr();
        if (zzdr != zzei.zzdr()) {
            return entrySet().equals(zzei.entrySet());
        }
        for (int i = 0; i < zzdr; i++) {
            if (!zzak(i).equals(zzei.zzak(i))) {
                return false;
            }
        }
        return zzdr != size ? this.zzon.equals(zzei.zzon) : true;
    }

    public V get(Object obj) {
        Comparable comparable = (Comparable) obj;
        int zza = zza(comparable);
        return zza >= 0 ? ((zzep) this.zzom.get(zza)).getValue() : this.zzon.get(comparable);
    }

    public int hashCode() {
        int i = 0;
        for (int i2 = 0; i2 < zzdr(); i2++) {
            i += ((zzep) this.zzom.get(i2)).hashCode();
        }
        return this.zzon.size() > 0 ? this.zzon.hashCode() + i : i;
    }

    public final boolean isImmutable() {
        return this.zzgu;
    }

    public /* synthetic */ Object put(Object obj, Object obj2) {
        return zza((Comparable) obj, obj2);
    }

    public V remove(Object obj) {
        zzdu();
        Comparable comparable = (Comparable) obj;
        int zza = zza(comparable);
        return zza >= 0 ? zzal(zza) : this.zzon.isEmpty() ? null : this.zzon.remove(comparable);
    }

    public int size() {
        return this.zzom.size() + this.zzon.size();
    }

    public final V zza(K k, V v) {
        zzdu();
        int zza = zza((Comparable) k);
        if (zza >= 0) {
            return ((zzep) this.zzom.get(zza)).setValue(v);
        }
        zzdu();
        if (this.zzom.isEmpty() && !(this.zzom instanceof ArrayList)) {
            this.zzom = new ArrayList(this.zzol);
        }
        int i = -(zza + 1);
        if (i >= this.zzol) {
            return zzdv().put(k, v);
        }
        if (this.zzom.size() == this.zzol) {
            zzep zzep = (zzep) this.zzom.remove(this.zzol - 1);
            zzdv().put((Comparable) zzep.getKey(), zzep.getValue());
        }
        this.zzom.add(i, new zzep(this, k, v));
        return null;
    }

    public final Entry<K, V> zzak(int i) {
        return (Entry) this.zzom.get(i);
    }

    public final int zzdr() {
        return this.zzom.size();
    }

    public final Iterable<Entry<K, V>> zzds() {
        return this.zzon.isEmpty() ? zzem.zzdx() : this.zzon.entrySet();
    }

    final Set<Entry<K, V>> zzdt() {
        if (this.zzoq == null) {
            this.zzoq = new zzel();
        }
        return this.zzoq;
    }

    public void zzv() {
        if (!this.zzgu) {
            this.zzon = this.zzon.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(this.zzon);
            this.zzop = this.zzop.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(this.zzop);
            this.zzgu = true;
        }
    }
}
