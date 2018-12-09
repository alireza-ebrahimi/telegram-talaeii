package com.google.android.gms.internal.firebase_auth;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class zzeb<K, V> extends LinkedHashMap<K, V> {
    private static final zzeb zztf;
    private boolean zzmd = true;

    static {
        zzeb zzeb = new zzeb();
        zztf = zzeb;
        zzeb.zzmd = false;
    }

    private zzeb() {
    }

    private zzeb(Map<K, V> map) {
        super(map);
    }

    public static <K, V> zzeb<K, V> zzet() {
        return zztf;
    }

    private final void zzev() {
        if (!this.zzmd) {
            throw new UnsupportedOperationException();
        }
    }

    private static int zzh(Object obj) {
        if (obj instanceof byte[]) {
            return zzdd.hashCode((byte[]) obj);
        }
        if (!(obj instanceof zzde)) {
            return obj.hashCode();
        }
        throw new UnsupportedOperationException();
    }

    public final void clear() {
        zzev();
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
            i = (zzh(entry.getValue()) ^ zzh(entry.getKey())) + i;
        }
        return i;
    }

    public final boolean isMutable() {
        return this.zzmd;
    }

    public final V put(K k, V v) {
        zzev();
        zzdd.checkNotNull(k);
        zzdd.checkNotNull(v);
        return super.put(k, v);
    }

    public final void putAll(Map<? extends K, ? extends V> map) {
        zzev();
        for (Object next : map.keySet()) {
            zzdd.checkNotNull(next);
            zzdd.checkNotNull(map.get(next));
        }
        super.putAll(map);
    }

    public final V remove(Object obj) {
        zzev();
        return super.remove(obj);
    }

    public final void zza(zzeb<K, V> zzeb) {
        zzev();
        if (!zzeb.isEmpty()) {
            putAll(zzeb);
        }
    }

    public final void zzbs() {
        this.zzmd = false;
    }

    public final zzeb<K, V> zzeu() {
        return isEmpty() ? new zzeb() : new zzeb(this);
    }
}
