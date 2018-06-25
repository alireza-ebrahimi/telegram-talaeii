package com.p077f.p078a.p079a.p084b.p085a;

import android.graphics.Bitmap;
import com.p077f.p078a.p079a.p084b.C1536a;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;

/* renamed from: com.f.a.a.b.a.b */
public class C1538b implements C1536a {
    /* renamed from: a */
    private final LinkedHashMap<String, Bitmap> f4669a;
    /* renamed from: b */
    private final int f4670b;
    /* renamed from: c */
    private int f4671c;

    public C1538b(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        this.f4670b = i;
        this.f4669a = new LinkedHashMap(0, 0.75f, true);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    /* renamed from: a */
    private void m7643a(int r4) {
        /*
        r3 = this;
    L_0x0000:
        monitor-enter(r3);
        r0 = r3.f4671c;	 Catch:{ all -> 0x0033 }
        if (r0 < 0) goto L_0x0011;
    L_0x0005:
        r0 = r3.f4669a;	 Catch:{ all -> 0x0033 }
        r0 = r0.isEmpty();	 Catch:{ all -> 0x0033 }
        if (r0 == 0) goto L_0x0036;
    L_0x000d:
        r0 = r3.f4671c;	 Catch:{ all -> 0x0033 }
        if (r0 == 0) goto L_0x0036;
    L_0x0011:
        r0 = new java.lang.IllegalStateException;	 Catch:{ all -> 0x0033 }
        r1 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0033 }
        r1.<init>();	 Catch:{ all -> 0x0033 }
        r2 = r3.getClass();	 Catch:{ all -> 0x0033 }
        r2 = r2.getName();	 Catch:{ all -> 0x0033 }
        r1 = r1.append(r2);	 Catch:{ all -> 0x0033 }
        r2 = ".sizeOf() is reporting inconsistent results!";
        r1 = r1.append(r2);	 Catch:{ all -> 0x0033 }
        r1 = r1.toString();	 Catch:{ all -> 0x0033 }
        r0.<init>(r1);	 Catch:{ all -> 0x0033 }
        throw r0;	 Catch:{ all -> 0x0033 }
    L_0x0033:
        r0 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x0033 }
        throw r0;
    L_0x0036:
        r0 = r3.f4671c;	 Catch:{ all -> 0x0033 }
        if (r0 <= r4) goto L_0x0042;
    L_0x003a:
        r0 = r3.f4669a;	 Catch:{ all -> 0x0033 }
        r0 = r0.isEmpty();	 Catch:{ all -> 0x0033 }
        if (r0 == 0) goto L_0x0044;
    L_0x0042:
        monitor-exit(r3);	 Catch:{ all -> 0x0033 }
    L_0x0043:
        return;
    L_0x0044:
        r0 = r3.f4669a;	 Catch:{ all -> 0x0033 }
        r0 = r0.entrySet();	 Catch:{ all -> 0x0033 }
        r0 = r0.iterator();	 Catch:{ all -> 0x0033 }
        r0 = r0.next();	 Catch:{ all -> 0x0033 }
        r0 = (java.util.Map.Entry) r0;	 Catch:{ all -> 0x0033 }
        if (r0 != 0) goto L_0x0058;
    L_0x0056:
        monitor-exit(r3);	 Catch:{ all -> 0x0033 }
        goto L_0x0043;
    L_0x0058:
        r1 = r0.getKey();	 Catch:{ all -> 0x0033 }
        r1 = (java.lang.String) r1;	 Catch:{ all -> 0x0033 }
        r0 = r0.getValue();	 Catch:{ all -> 0x0033 }
        r0 = (android.graphics.Bitmap) r0;	 Catch:{ all -> 0x0033 }
        r2 = r3.f4669a;	 Catch:{ all -> 0x0033 }
        r2.remove(r1);	 Catch:{ all -> 0x0033 }
        r2 = r3.f4671c;	 Catch:{ all -> 0x0033 }
        r0 = r3.m7644b(r1, r0);	 Catch:{ all -> 0x0033 }
        r0 = r2 - r0;
        r3.f4671c = r0;	 Catch:{ all -> 0x0033 }
        monitor-exit(r3);	 Catch:{ all -> 0x0033 }
        goto L_0x0000;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.f.a.a.b.a.b.a(int):void");
    }

    /* renamed from: b */
    private int m7644b(String str, Bitmap bitmap) {
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    /* renamed from: a */
    public final Bitmap mo1217a(String str) {
        if (str == null) {
            throw new NullPointerException("key == null");
        }
        Bitmap bitmap;
        synchronized (this) {
            bitmap = (Bitmap) this.f4669a.get(str);
        }
        return bitmap;
    }

    /* renamed from: a */
    public Collection<String> mo1218a() {
        Collection hashSet;
        synchronized (this) {
            hashSet = new HashSet(this.f4669a.keySet());
        }
        return hashSet;
    }

    /* renamed from: a */
    public final boolean mo1219a(String str, Bitmap bitmap) {
        if (str == null || bitmap == null) {
            throw new NullPointerException("key == null || value == null");
        }
        synchronized (this) {
            this.f4671c += m7644b(str, bitmap);
            Bitmap bitmap2 = (Bitmap) this.f4669a.put(str, bitmap);
            if (bitmap2 != null) {
                this.f4671c -= m7644b(str, bitmap2);
            }
        }
        m7643a(this.f4670b);
        return true;
    }

    /* renamed from: b */
    public final Bitmap mo1220b(String str) {
        if (str == null) {
            throw new NullPointerException("key == null");
        }
        Bitmap bitmap;
        synchronized (this) {
            bitmap = (Bitmap) this.f4669a.remove(str);
            if (bitmap != null) {
                this.f4671c -= m7644b(str, bitmap);
            }
        }
        return bitmap;
    }

    public final synchronized String toString() {
        return String.format("LruCache[maxSize=%d]", new Object[]{Integer.valueOf(this.f4670b)});
    }
}
