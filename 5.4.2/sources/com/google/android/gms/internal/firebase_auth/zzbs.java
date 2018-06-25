package com.google.android.gms.internal.firebase_auth;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class zzbs extends zzbq<Boolean> implements zzdg<Boolean>, RandomAccess {
    private static final zzbs zzmg;
    private int size;
    private boolean[] zzmh;

    static {
        zzbq zzbs = new zzbs();
        zzmg = zzbs;
        zzbs.zzbs();
    }

    zzbs() {
        this(new boolean[10], 0);
    }

    private zzbs(boolean[] zArr, int i) {
        this.zzmh = zArr;
        this.size = i;
    }

    private final void zza(int i, boolean z) {
        zzbt();
        if (i < 0 || i > this.size) {
            throw new IndexOutOfBoundsException(zzi(i));
        }
        if (this.size < this.zzmh.length) {
            System.arraycopy(this.zzmh, i, this.zzmh, i + 1, this.size - i);
        } else {
            Object obj = new boolean[(((this.size * 3) / 2) + 1)];
            System.arraycopy(this.zzmh, 0, obj, 0, i);
            System.arraycopy(this.zzmh, i, obj, i + 1, this.size - i);
            this.zzmh = obj;
        }
        this.zzmh[i] = z;
        this.size++;
        this.modCount++;
    }

    private final void zzh(int i) {
        if (i < 0 || i >= this.size) {
            throw new IndexOutOfBoundsException(zzi(i));
        }
    }

    private final String zzi(int i) {
        return "Index:" + i + ", Size:" + this.size;
    }

    public final /* synthetic */ void add(int i, Object obj) {
        zza(i, ((Boolean) obj).booleanValue());
    }

    public final boolean addAll(Collection<? extends Boolean> collection) {
        zzbt();
        zzdd.checkNotNull(collection);
        if (!(collection instanceof zzbs)) {
            return super.addAll(collection);
        }
        zzbs zzbs = (zzbs) collection;
        if (zzbs.size == 0) {
            return false;
        }
        if (Integer.MAX_VALUE - this.size < zzbs.size) {
            throw new OutOfMemoryError();
        }
        int i = this.size + zzbs.size;
        if (i > this.zzmh.length) {
            this.zzmh = Arrays.copyOf(this.zzmh, i);
        }
        System.arraycopy(zzbs.zzmh, 0, this.zzmh, this.size, zzbs.size);
        this.size = i;
        this.modCount++;
        return true;
    }

    public final void addBoolean(boolean z) {
        zza(this.size, z);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzbs)) {
            return super.equals(obj);
        }
        zzbs zzbs = (zzbs) obj;
        if (this.size != zzbs.size) {
            return false;
        }
        boolean[] zArr = zzbs.zzmh;
        for (int i = 0; i < this.size; i++) {
            if (this.zzmh[i] != zArr[i]) {
                return false;
            }
        }
        return true;
    }

    public final /* synthetic */ Object get(int i) {
        zzh(i);
        return Boolean.valueOf(this.zzmh[i]);
    }

    public final int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            i = (i * 31) + zzdd.zzh(this.zzmh[i2]);
        }
        return i;
    }

    public final /* synthetic */ Object remove(int i) {
        zzbt();
        zzh(i);
        boolean z = this.zzmh[i];
        if (i < this.size - 1) {
            System.arraycopy(this.zzmh, i + 1, this.zzmh, i, this.size - i);
        }
        this.size--;
        this.modCount++;
        return Boolean.valueOf(z);
    }

    public final boolean remove(Object obj) {
        zzbt();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Boolean.valueOf(this.zzmh[i]))) {
                System.arraycopy(this.zzmh, i + 1, this.zzmh, i, this.size - i);
                this.size--;
                this.modCount++;
                return true;
            }
        }
        return false;
    }

    protected final void removeRange(int i, int i2) {
        zzbt();
        if (i2 < i) {
            throw new IndexOutOfBoundsException("toIndex < fromIndex");
        }
        System.arraycopy(this.zzmh, i2, this.zzmh, i, this.size - i2);
        this.size -= i2 - i;
        this.modCount++;
    }

    public final /* synthetic */ Object set(int i, Object obj) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        zzbt();
        zzh(i);
        boolean z = this.zzmh[i];
        this.zzmh[i] = booleanValue;
        return Boolean.valueOf(z);
    }

    public final int size() {
        return this.size;
    }

    public final /* synthetic */ zzdg zzj(int i) {
        if (i >= this.size) {
            return new zzbs(Arrays.copyOf(this.zzmh, i), this.size);
        }
        throw new IllegalArgumentException();
    }
}
