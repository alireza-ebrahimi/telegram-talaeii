package com.google.android.gms.internal.measurement;

import java.util.Map.Entry;

final class zzabf implements Comparable<zzabf>, Entry<K, V> {
    private V value;
    private final K zzbuo;
    private final /* synthetic */ zzaba zzbup;

    zzabf(zzaba zzaba, K k, V v) {
        this.zzbup = zzaba;
        this.zzbuo = k;
        this.value = v;
    }

    zzabf(zzaba zzaba, Entry<K, V> entry) {
        this(zzaba, (Comparable) entry.getKey(), entry.getValue());
    }

    private static boolean equals(Object obj, Object obj2) {
        return obj == null ? obj2 == null : obj.equals(obj2);
    }

    public final /* synthetic */ int compareTo(Object obj) {
        return ((Comparable) getKey()).compareTo((Comparable) ((zzabf) obj).getKey());
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Entry)) {
            return false;
        }
        Entry entry = (Entry) obj;
        return equals(this.zzbuo, entry.getKey()) && equals(this.value, entry.getValue());
    }

    public final /* synthetic */ Object getKey() {
        return this.zzbuo;
    }

    public final V getValue() {
        return this.value;
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = this.zzbuo == null ? 0 : this.zzbuo.hashCode();
        if (this.value != null) {
            i = this.value.hashCode();
        }
        return hashCode ^ i;
    }

    public final V setValue(V v) {
        this.zzbup.zzuu();
        V v2 = this.value;
        this.value = v;
        return v2;
    }

    public final String toString() {
        String valueOf = String.valueOf(this.zzbuo);
        String valueOf2 = String.valueOf(this.value);
        return new StringBuilder((String.valueOf(valueOf).length() + 1) + String.valueOf(valueOf2).length()).append(valueOf).append("=").append(valueOf2).toString();
    }
}
