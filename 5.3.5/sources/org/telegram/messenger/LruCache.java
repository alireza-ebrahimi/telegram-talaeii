package org.telegram.messenger;

import android.graphics.drawable.BitmapDrawable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class LruCache {
    private final LinkedHashMap<String, BitmapDrawable> map;
    private final LinkedHashMap<String, ArrayList<String>> mapFilters;
    private int maxSize;
    private int size;

    public LruCache(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        this.maxSize = maxSize;
        this.map = new LinkedHashMap(0, 0.75f, true);
        this.mapFilters = new LinkedHashMap();
    }

    public final BitmapDrawable get(String key) {
        if (key == null) {
            throw new NullPointerException("key == null");
        }
        synchronized (this) {
            BitmapDrawable mapValue = (BitmapDrawable) this.map.get(key);
            if (mapValue != null) {
                return mapValue;
            }
            return null;
        }
    }

    public ArrayList<String> getFilterKeys(String key) {
        ArrayList<String> arr = (ArrayList) this.mapFilters.get(key);
        if (arr != null) {
            return new ArrayList(arr);
        }
        return null;
    }

    public BitmapDrawable put(String key, BitmapDrawable value) {
        if (key == null || value == null) {
            throw new NullPointerException("key == null || value == null");
        }
        BitmapDrawable previous;
        synchronized (this) {
            this.size += safeSizeOf(key, value);
            previous = (BitmapDrawable) this.map.put(key, value);
            if (previous != null) {
                this.size -= safeSizeOf(key, previous);
            }
        }
        String[] args = key.split("@");
        if (args.length > 1) {
            ArrayList<String> arr = (ArrayList) this.mapFilters.get(args[0]);
            if (arr == null) {
                arr = new ArrayList();
                this.mapFilters.put(args[0], arr);
            }
            if (!arr.contains(args[1])) {
                arr.add(args[1]);
            }
        }
        if (previous != null) {
            entryRemoved(false, key, previous, value);
        }
        trimToSize(this.maxSize, key);
        return previous;
    }

    private void trimToSize(int maxSize, String justAdded) {
        synchronized (this) {
            Iterator<Entry<String, BitmapDrawable>> iterator = this.map.entrySet().iterator();
            while (iterator.hasNext() && this.size > maxSize && !this.map.isEmpty()) {
                Entry<String, BitmapDrawable> entry = (Entry) iterator.next();
                String key = (String) entry.getKey();
                if (justAdded == null || !justAdded.equals(key)) {
                    BitmapDrawable value = (BitmapDrawable) entry.getValue();
                    this.size -= safeSizeOf(key, value);
                    iterator.remove();
                    String[] args = key.split("@");
                    if (args.length > 1) {
                        ArrayList<String> arr = (ArrayList) this.mapFilters.get(args[0]);
                        if (arr != null) {
                            arr.remove(args[1]);
                            if (arr.isEmpty()) {
                                this.mapFilters.remove(args[0]);
                            }
                        }
                    }
                    entryRemoved(true, key, value, null);
                }
            }
        }
    }

    public final BitmapDrawable remove(String key) {
        if (key == null) {
            throw new NullPointerException("key == null");
        }
        BitmapDrawable previous;
        synchronized (this) {
            previous = (BitmapDrawable) this.map.remove(key);
            if (previous != null) {
                this.size -= safeSizeOf(key, previous);
            }
        }
        if (previous != null) {
            String[] args = key.split("@");
            if (args.length > 1) {
                ArrayList<String> arr = (ArrayList) this.mapFilters.get(args[0]);
                if (arr != null) {
                    arr.remove(args[1]);
                    if (arr.isEmpty()) {
                        this.mapFilters.remove(args[0]);
                    }
                }
            }
            entryRemoved(false, key, previous, null);
        }
        return previous;
    }

    public boolean contains(String key) {
        return this.map.containsKey(key);
    }

    protected void entryRemoved(boolean evicted, String key, BitmapDrawable oldValue, BitmapDrawable newValue) {
    }

    private int safeSizeOf(String key, BitmapDrawable value) {
        int result = sizeOf(key, value);
        if (result >= 0) {
            return result;
        }
        throw new IllegalStateException("Negative size: " + key + "=" + value);
    }

    protected int sizeOf(String key, BitmapDrawable value) {
        return 1;
    }

    public final void evictAll() {
        trimToSize(-1, null);
    }

    public final synchronized int size() {
        return this.size;
    }

    public final synchronized int maxSize() {
        return this.maxSize;
    }
}
