package com.googlecode.mp4parser.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class RangeStartMap<K extends Comparable, V> implements Map<K, V> {
    TreeMap<K, V> base = new TreeMap(new C05961());

    /* renamed from: com.googlecode.mp4parser.util.RangeStartMap$1 */
    class C05961 implements Comparator<K> {
        C05961() {
        }

        public int compare(K o1, K o2) {
            return -o1.compareTo(o2);
        }
    }

    public RangeStartMap(K k, V v) {
        put((Comparable) k, (Object) v);
    }

    public int size() {
        return this.base.size();
    }

    public boolean isEmpty() {
        return this.base.isEmpty();
    }

    public boolean containsKey(Object key) {
        return this.base.get(key) != null;
    }

    public boolean containsValue(Object value) {
        return false;
    }

    public V get(Object k) {
        if (!(k instanceof Comparable)) {
            return null;
        }
        Comparable<K> key = (Comparable) k;
        if (isEmpty()) {
            return null;
        }
        Iterator<K> keys = this.base.keySet().iterator();
        K a = (Comparable) keys.next();
        while (keys.hasNext()) {
            if (key.compareTo(a) >= 0) {
                return this.base.get(a);
            }
            Comparable a2 = (Comparable) keys.next();
        }
        return this.base.get(a);
    }

    public V put(K key, V value) {
        return this.base.put(key, value);
    }

    public V remove(Object k) {
        if (!(k instanceof Comparable)) {
            return null;
        }
        Comparable<K> key = (Comparable) k;
        if (isEmpty()) {
            return null;
        }
        Iterator<K> keys = this.base.keySet().iterator();
        K a = (Comparable) keys.next();
        while (keys.hasNext()) {
            if (key.compareTo(a) >= 0) {
                return this.base.remove(a);
            }
            Comparable a2 = (Comparable) keys.next();
        }
        return this.base.remove(a);
    }

    public void putAll(Map<? extends K, ? extends V> m) {
        this.base.putAll(m);
    }

    public void clear() {
        this.base.clear();
    }

    public Set<K> keySet() {
        return this.base.keySet();
    }

    public Collection<V> values() {
        return this.base.values();
    }

    public Set<Entry<K, V>> entrySet() {
        return this.base.entrySet();
    }
}
