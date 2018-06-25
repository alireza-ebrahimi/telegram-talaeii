package com.google.android.gms.internal.measurement;

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

class zzaba<K extends Comparable<K>, V> extends AbstractMap<K, V> {
    private boolean zzbme;
    private final int zzbuh;
    private List<zzabf> zzbui;
    private Map<K, V> zzbuj;
    private volatile zzabh zzbuk;
    private Map<K, V> zzbul;

    private zzaba(int i) {
        this.zzbuh = i;
        this.zzbui = Collections.emptyList();
        this.zzbuj = Collections.emptyMap();
        this.zzbul = Collections.emptyMap();
    }

    private final int zza(K k) {
        int compareTo;
        int size = this.zzbui.size() - 1;
        if (size >= 0) {
            compareTo = k.compareTo((Comparable) ((zzabf) this.zzbui.get(size)).getKey());
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
            compareTo = k.compareTo((Comparable) ((zzabf) this.zzbui.get(size)).getKey());
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

    static <FieldDescriptorType extends zzzq<FieldDescriptorType>> zzaba<FieldDescriptorType, Object> zzag(int i) {
        return new zzabb(i);
    }

    private final V zzai(int i) {
        zzuu();
        V value = ((zzabf) this.zzbui.remove(i)).getValue();
        if (!this.zzbuj.isEmpty()) {
            Iterator it = zzuv().entrySet().iterator();
            this.zzbui.add(new zzabf(this, (Entry) it.next()));
            it.remove();
        }
        return value;
    }

    private final void zzuu() {
        if (this.zzbme) {
            throw new UnsupportedOperationException();
        }
    }

    private final SortedMap<K, V> zzuv() {
        zzuu();
        if (this.zzbuj.isEmpty() && !(this.zzbuj instanceof TreeMap)) {
            this.zzbuj = new TreeMap();
            this.zzbul = ((TreeMap) this.zzbuj).descendingMap();
        }
        return (SortedMap) this.zzbuj;
    }

    public void clear() {
        zzuu();
        if (!this.zzbui.isEmpty()) {
            this.zzbui.clear();
        }
        if (!this.zzbuj.isEmpty()) {
            this.zzbuj.clear();
        }
    }

    public boolean containsKey(Object obj) {
        Comparable comparable = (Comparable) obj;
        return zza(comparable) >= 0 || this.zzbuj.containsKey(comparable);
    }

    public Set<Entry<K, V>> entrySet() {
        if (this.zzbuk == null) {
            this.zzbuk = new zzabh();
        }
        return this.zzbuk;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzaba)) {
            return super.equals(obj);
        }
        zzaba zzaba = (zzaba) obj;
        int size = size();
        if (size != zzaba.size()) {
            return false;
        }
        int zzus = zzus();
        if (zzus != zzaba.zzus()) {
            return entrySet().equals(zzaba.entrySet());
        }
        for (int i = 0; i < zzus; i++) {
            if (!zzah(i).equals(zzaba.zzah(i))) {
                return false;
            }
        }
        return zzus != size ? this.zzbuj.equals(zzaba.zzbuj) : true;
    }

    public V get(Object obj) {
        Comparable comparable = (Comparable) obj;
        int zza = zza(comparable);
        return zza >= 0 ? ((zzabf) this.zzbui.get(zza)).getValue() : this.zzbuj.get(comparable);
    }

    public int hashCode() {
        int i = 0;
        for (int i2 = 0; i2 < zzus(); i2++) {
            i += ((zzabf) this.zzbui.get(i2)).hashCode();
        }
        return this.zzbuj.size() > 0 ? this.zzbuj.hashCode() + i : i;
    }

    public final boolean isImmutable() {
        return this.zzbme;
    }

    public /* synthetic */ Object put(Object obj, Object obj2) {
        return zza((Comparable) obj, obj2);
    }

    public V remove(Object obj) {
        zzuu();
        Comparable comparable = (Comparable) obj;
        int zza = zza(comparable);
        return zza >= 0 ? zzai(zza) : this.zzbuj.isEmpty() ? null : this.zzbuj.remove(comparable);
    }

    public int size() {
        return this.zzbui.size() + this.zzbuj.size();
    }

    public final V zza(K k, V v) {
        zzuu();
        int zza = zza((Comparable) k);
        if (zza >= 0) {
            return ((zzabf) this.zzbui.get(zza)).setValue(v);
        }
        zzuu();
        if (this.zzbui.isEmpty() && !(this.zzbui instanceof ArrayList)) {
            this.zzbui = new ArrayList(this.zzbuh);
        }
        int i = -(zza + 1);
        if (i >= this.zzbuh) {
            return zzuv().put(k, v);
        }
        if (this.zzbui.size() == this.zzbuh) {
            zzabf zzabf = (zzabf) this.zzbui.remove(this.zzbuh - 1);
            zzuv().put((Comparable) zzabf.getKey(), zzabf.getValue());
        }
        this.zzbui.add(i, new zzabf(this, k, v));
        return null;
    }

    public final Entry<K, V> zzah(int i) {
        return (Entry) this.zzbui.get(i);
    }

    public void zzrp() {
        if (!this.zzbme) {
            this.zzbuj = this.zzbuj.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(this.zzbuj);
            this.zzbul = this.zzbul.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(this.zzbul);
            this.zzbme = true;
        }
    }

    public final int zzus() {
        return this.zzbui.size();
    }

    public final Iterable<Entry<K, V>> zzut() {
        return this.zzbuj.isEmpty() ? zzabc.zzuw() : this.zzbuj.entrySet();
    }
}
