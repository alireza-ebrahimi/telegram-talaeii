package com.google.android.gms.internal.firebase_auth;

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

class zzey<K extends Comparable<K>, V> extends AbstractMap<K, V> {
    private boolean zzob;
    private final int zzuk;
    private List<zzff> zzul;
    private Map<K, V> zzum;
    private volatile zzfh zzun;
    private Map<K, V> zzuo;
    private volatile zzfb zzup;

    private zzey(int i) {
        this.zzuk = i;
        this.zzul = Collections.emptyList();
        this.zzum = Collections.emptyMap();
        this.zzuo = Collections.emptyMap();
    }

    private final int zza(K k) {
        int compareTo;
        int size = this.zzul.size() - 1;
        if (size >= 0) {
            compareTo = k.compareTo((Comparable) ((zzff) this.zzul.get(size)).getKey());
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
            compareTo = k.compareTo((Comparable) ((zzff) this.zzul.get(size)).getKey());
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

    static <FieldDescriptorType extends zzcu<FieldDescriptorType>> zzey<FieldDescriptorType, Object> zzat(int i) {
        return new zzez(i);
    }

    private final V zzav(int i) {
        zzfr();
        V value = ((zzff) this.zzul.remove(i)).getValue();
        if (!this.zzum.isEmpty()) {
            Iterator it = zzfs().entrySet().iterator();
            this.zzul.add(new zzff(this, (Entry) it.next()));
            it.remove();
        }
        return value;
    }

    private final void zzfr() {
        if (this.zzob) {
            throw new UnsupportedOperationException();
        }
    }

    private final SortedMap<K, V> zzfs() {
        zzfr();
        if (this.zzum.isEmpty() && !(this.zzum instanceof TreeMap)) {
            this.zzum = new TreeMap();
            this.zzuo = ((TreeMap) this.zzum).descendingMap();
        }
        return (SortedMap) this.zzum;
    }

    public void clear() {
        zzfr();
        if (!this.zzul.isEmpty()) {
            this.zzul.clear();
        }
        if (!this.zzum.isEmpty()) {
            this.zzum.clear();
        }
    }

    public boolean containsKey(Object obj) {
        Comparable comparable = (Comparable) obj;
        return zza(comparable) >= 0 || this.zzum.containsKey(comparable);
    }

    public Set<Entry<K, V>> entrySet() {
        if (this.zzun == null) {
            this.zzun = new zzfh();
        }
        return this.zzun;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzey)) {
            return super.equals(obj);
        }
        zzey zzey = (zzey) obj;
        int size = size();
        if (size != zzey.size()) {
            return false;
        }
        int zzfo = zzfo();
        if (zzfo != zzey.zzfo()) {
            return entrySet().equals(zzey.entrySet());
        }
        for (int i = 0; i < zzfo; i++) {
            if (!zzau(i).equals(zzey.zzau(i))) {
                return false;
            }
        }
        return zzfo != size ? this.zzum.equals(zzey.zzum) : true;
    }

    public V get(Object obj) {
        Comparable comparable = (Comparable) obj;
        int zza = zza(comparable);
        return zza >= 0 ? ((zzff) this.zzul.get(zza)).getValue() : this.zzum.get(comparable);
    }

    public int hashCode() {
        int i = 0;
        for (int i2 = 0; i2 < zzfo(); i2++) {
            i += ((zzff) this.zzul.get(i2)).hashCode();
        }
        return this.zzum.size() > 0 ? this.zzum.hashCode() + i : i;
    }

    public final boolean isImmutable() {
        return this.zzob;
    }

    public /* synthetic */ Object put(Object obj, Object obj2) {
        return zza((Comparable) obj, obj2);
    }

    public V remove(Object obj) {
        zzfr();
        Comparable comparable = (Comparable) obj;
        int zza = zza(comparable);
        return zza >= 0 ? zzav(zza) : this.zzum.isEmpty() ? null : this.zzum.remove(comparable);
    }

    public int size() {
        return this.zzul.size() + this.zzum.size();
    }

    public final V zza(K k, V v) {
        zzfr();
        int zza = zza((Comparable) k);
        if (zza >= 0) {
            return ((zzff) this.zzul.get(zza)).setValue(v);
        }
        zzfr();
        if (this.zzul.isEmpty() && !(this.zzul instanceof ArrayList)) {
            this.zzul = new ArrayList(this.zzuk);
        }
        int i = -(zza + 1);
        if (i >= this.zzuk) {
            return zzfs().put(k, v);
        }
        if (this.zzul.size() == this.zzuk) {
            zzff zzff = (zzff) this.zzul.remove(this.zzuk - 1);
            zzfs().put((Comparable) zzff.getKey(), zzff.getValue());
        }
        this.zzul.add(i, new zzff(this, k, v));
        return null;
    }

    public final Entry<K, V> zzau(int i) {
        return (Entry) this.zzul.get(i);
    }

    public void zzbs() {
        if (!this.zzob) {
            this.zzum = this.zzum.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(this.zzum);
            this.zzuo = this.zzuo.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(this.zzuo);
            this.zzob = true;
        }
    }

    public final int zzfo() {
        return this.zzul.size();
    }

    public final Iterable<Entry<K, V>> zzfp() {
        return this.zzum.isEmpty() ? zzfc.zzfu() : this.zzum.entrySet();
    }

    final Set<Entry<K, V>> zzfq() {
        if (this.zzup == null) {
            this.zzup = new zzfb();
        }
        return this.zzup;
    }
}
