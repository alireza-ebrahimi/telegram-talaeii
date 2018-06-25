package com.google.android.gms.internal;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class zzfiw<K, V> extends LinkedHashMap<K, V> {
    private static final zzfiw zzprd;
    private boolean zzpnq = true;

    static {
        zzfiw zzfiw = new zzfiw();
        zzprd = zzfiw;
        zzfiw.zzpnq = false;
    }

    private zzfiw() {
    }

    private zzfiw(Map<K, V> map) {
        super(map);
    }

    private static int zzcs(Object obj) {
        if (obj instanceof byte[]) {
            return zzfhz.hashCode((byte[]) obj);
        }
        if (!(obj instanceof zzfia)) {
            return obj.hashCode();
        }
        throw new UnsupportedOperationException();
    }

    public static <K, V> zzfiw<K, V> zzdat() {
        return zzprd;
    }

    private final void zzdav() {
        if (!this.zzpnq) {
            throw new UnsupportedOperationException();
        }
    }

    public final void clear() {
        zzdav();
        super.clear();
    }

    public final Set<Entry<K, V>> entrySet() {
        return isEmpty() ? Collections.emptySet() : super.entrySet();
    }

    public final boolean equals(Object obj) {
        if (obj instanceof Map) {
            Object obj2;
            obj = (Map) obj;
            if (this != obj) {
                if (size() == obj.size()) {
                    for (Entry entry : entrySet()) {
                        if (!obj.containsKey(entry.getKey())) {
                            obj2 = null;
                            break;
                        }
                        boolean equals;
                        Object value = entry.getValue();
                        Object obj3 = obj.get(entry.getKey());
                        if ((value instanceof byte[]) && (obj3 instanceof byte[])) {
                            equals = Arrays.equals((byte[]) value, (byte[]) obj3);
                            continue;
                        } else {
                            equals = value.equals(obj3);
                            continue;
                        }
                        if (!equals) {
                            obj2 = null;
                            break;
                        }
                    }
                }
                obj2 = null;
                if (obj2 != null) {
                    return true;
                }
            }
            int i = 1;
            if (obj2 != null) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int i = 0;
        for (Entry entry : entrySet()) {
            i = (zzcs(entry.getValue()) ^ zzcs(entry.getKey())) + i;
        }
        return i;
    }

    public final boolean isMutable() {
        return this.zzpnq;
    }

    public final V put(K k, V v) {
        zzdav();
        zzfhz.checkNotNull(k);
        zzfhz.checkNotNull(v);
        return super.put(k, v);
    }

    public final void putAll(Map<? extends K, ? extends V> map) {
        zzdav();
        for (Object next : map.keySet()) {
            zzfhz.checkNotNull(next);
            zzfhz.checkNotNull(map.get(next));
        }
        super.putAll(map);
    }

    public final V remove(Object obj) {
        zzdav();
        return super.remove(obj);
    }

    public final void zza(zzfiw<K, V> zzfiw) {
        zzdav();
        if (!zzfiw.isEmpty()) {
            putAll(zzfiw);
        }
    }

    public final void zzbkr() {
        this.zzpnq = false;
    }

    public final zzfiw<K, V> zzdau() {
        return isEmpty() ? new zzfiw() : new zzfiw(this);
    }
}
