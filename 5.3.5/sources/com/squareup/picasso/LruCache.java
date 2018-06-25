package com.squareup.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class LruCache implements Cache {
    private int evictionCount;
    private int hitCount;
    final LinkedHashMap<String, Bitmap> map;
    private final int maxSize;
    private int missCount;
    private int putCount;
    private int size;

    public LruCache(Context context) {
        this(Utils.calculateMemoryCacheSize(context));
    }

    public LruCache(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("Max size must be positive.");
        }
        this.maxSize = maxSize;
        this.map = new LinkedHashMap(0, 0.75f, true);
    }

    public Bitmap get(String key) {
        if (key == null) {
            throw new NullPointerException("key == null");
        }
        synchronized (this) {
            Bitmap mapValue = (Bitmap) this.map.get(key);
            if (mapValue != null) {
                this.hitCount++;
                return mapValue;
            }
            this.missCount++;
            return null;
        }
    }

    public void set(String key, Bitmap bitmap) {
        if (key == null || bitmap == null) {
            throw new NullPointerException("key == null || bitmap == null");
        }
        synchronized (this) {
            this.putCount++;
            this.size += Utils.getBitmapBytes(bitmap);
            Bitmap previous = (Bitmap) this.map.put(key, bitmap);
            if (previous != null) {
                this.size -= Utils.getBitmapBytes(previous);
            }
        }
        trimToSize(this.maxSize);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void trimToSize(int r7) {
        /*
        r6 = this;
    L_0x0000:
        monitor-enter(r6);
        r3 = r6.size;	 Catch:{ all -> 0x0033 }
        if (r3 < 0) goto L_0x0011;
    L_0x0005:
        r3 = r6.map;	 Catch:{ all -> 0x0033 }
        r3 = r3.isEmpty();	 Catch:{ all -> 0x0033 }
        if (r3 == 0) goto L_0x0036;
    L_0x000d:
        r3 = r6.size;	 Catch:{ all -> 0x0033 }
        if (r3 == 0) goto L_0x0036;
    L_0x0011:
        r3 = new java.lang.IllegalStateException;	 Catch:{ all -> 0x0033 }
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0033 }
        r4.<init>();	 Catch:{ all -> 0x0033 }
        r5 = r6.getClass();	 Catch:{ all -> 0x0033 }
        r5 = r5.getName();	 Catch:{ all -> 0x0033 }
        r4 = r4.append(r5);	 Catch:{ all -> 0x0033 }
        r5 = ".sizeOf() is reporting inconsistent results!";
        r4 = r4.append(r5);	 Catch:{ all -> 0x0033 }
        r4 = r4.toString();	 Catch:{ all -> 0x0033 }
        r3.<init>(r4);	 Catch:{ all -> 0x0033 }
        throw r3;	 Catch:{ all -> 0x0033 }
    L_0x0033:
        r3 = move-exception;
        monitor-exit(r6);	 Catch:{ all -> 0x0033 }
        throw r3;
    L_0x0036:
        r3 = r6.size;	 Catch:{ all -> 0x0033 }
        if (r3 <= r7) goto L_0x0042;
    L_0x003a:
        r3 = r6.map;	 Catch:{ all -> 0x0033 }
        r3 = r3.isEmpty();	 Catch:{ all -> 0x0033 }
        if (r3 == 0) goto L_0x0044;
    L_0x0042:
        monitor-exit(r6);	 Catch:{ all -> 0x0033 }
        return;
    L_0x0044:
        r3 = r6.map;	 Catch:{ all -> 0x0033 }
        r3 = r3.entrySet();	 Catch:{ all -> 0x0033 }
        r3 = r3.iterator();	 Catch:{ all -> 0x0033 }
        r1 = r3.next();	 Catch:{ all -> 0x0033 }
        r1 = (java.util.Map.Entry) r1;	 Catch:{ all -> 0x0033 }
        r0 = r1.getKey();	 Catch:{ all -> 0x0033 }
        r0 = (java.lang.String) r0;	 Catch:{ all -> 0x0033 }
        r2 = r1.getValue();	 Catch:{ all -> 0x0033 }
        r2 = (android.graphics.Bitmap) r2;	 Catch:{ all -> 0x0033 }
        r3 = r6.map;	 Catch:{ all -> 0x0033 }
        r3.remove(r0);	 Catch:{ all -> 0x0033 }
        r3 = r6.size;	 Catch:{ all -> 0x0033 }
        r4 = com.squareup.picasso.Utils.getBitmapBytes(r2);	 Catch:{ all -> 0x0033 }
        r3 = r3 - r4;
        r6.size = r3;	 Catch:{ all -> 0x0033 }
        r3 = r6.evictionCount;	 Catch:{ all -> 0x0033 }
        r3 = r3 + 1;
        r6.evictionCount = r3;	 Catch:{ all -> 0x0033 }
        monitor-exit(r6);	 Catch:{ all -> 0x0033 }
        goto L_0x0000;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.picasso.LruCache.trimToSize(int):void");
    }

    public final void evictAll() {
        trimToSize(-1);
    }

    public final synchronized int size() {
        return this.size;
    }

    public final synchronized int maxSize() {
        return this.maxSize;
    }

    public final synchronized void clear() {
        evictAll();
    }

    public final synchronized void clearKeyUri(String uri) {
        boolean sizeChanged = false;
        int uriLength = uri.length();
        Iterator<Entry<String, Bitmap>> i = this.map.entrySet().iterator();
        while (i.hasNext()) {
            Entry<String, Bitmap> entry = (Entry) i.next();
            String key = (String) entry.getKey();
            Bitmap value = (Bitmap) entry.getValue();
            int newlineIndex = key.indexOf(10);
            if (newlineIndex == uriLength && key.substring(0, newlineIndex).equals(uri)) {
                i.remove();
                this.size -= Utils.getBitmapBytes(value);
                sizeChanged = true;
            }
        }
        if (sizeChanged) {
            trimToSize(this.maxSize);
        }
    }

    public final synchronized int hitCount() {
        return this.hitCount;
    }

    public final synchronized int missCount() {
        return this.missCount;
    }

    public final synchronized int putCount() {
        return this.putCount;
    }

    public final synchronized int evictionCount() {
        return this.evictionCount;
    }
}
