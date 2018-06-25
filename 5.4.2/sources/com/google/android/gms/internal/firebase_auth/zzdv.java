package com.google.android.gms.internal.firebase_auth;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class zzdv extends zzbq<Long> implements zzdg<Long>, RandomAccess {
    private static final zzdv zzsw;
    private int size;
    private long[] zzsx;

    static {
        zzbq zzdv = new zzdv();
        zzsw = zzdv;
        zzdv.zzbs();
    }

    zzdv() {
        this(new long[10], 0);
    }

    private zzdv(long[] jArr, int i) {
        this.zzsx = jArr;
        this.size = i;
    }

    private final void zzh(int i) {
        if (i < 0 || i >= this.size) {
            throw new IndexOutOfBoundsException(zzi(i));
        }
    }

    private final String zzi(int i) {
        return "Index:" + i + ", Size:" + this.size;
    }

    private final void zzk(int i, long j) {
        zzbt();
        if (i < 0 || i > this.size) {
            throw new IndexOutOfBoundsException(zzi(i));
        }
        if (this.size < this.zzsx.length) {
            System.arraycopy(this.zzsx, i, this.zzsx, i + 1, this.size - i);
        } else {
            Object obj = new long[(((this.size * 3) / 2) + 1)];
            System.arraycopy(this.zzsx, 0, obj, 0, i);
            System.arraycopy(this.zzsx, i, obj, i + 1, this.size - i);
            this.zzsx = obj;
        }
        this.zzsx[i] = j;
        this.size++;
        this.modCount++;
    }

    public final /* synthetic */ void add(int i, Object obj) {
        zzk(i, ((Long) obj).longValue());
    }

    public final boolean addAll(Collection<? extends Long> collection) {
        zzbt();
        zzdd.checkNotNull(collection);
        if (!(collection instanceof zzdv)) {
            return super.addAll(collection);
        }
        zzdv zzdv = (zzdv) collection;
        if (zzdv.size == 0) {
            return false;
        }
        if (Integer.MAX_VALUE - this.size < zzdv.size) {
            throw new OutOfMemoryError();
        }
        int i = this.size + zzdv.size;
        if (i > this.zzsx.length) {
            this.zzsx = Arrays.copyOf(this.zzsx, i);
        }
        System.arraycopy(zzdv.zzsx, 0, this.zzsx, this.size, zzdv.size);
        this.size = i;
        this.modCount++;
        return true;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzdv)) {
            return super.equals(obj);
        }
        zzdv zzdv = (zzdv) obj;
        if (this.size != zzdv.size) {
            return false;
        }
        long[] jArr = zzdv.zzsx;
        for (int i = 0; i < this.size; i++) {
            if (this.zzsx[i] != jArr[i]) {
                return false;
            }
        }
        return true;
    }

    public final /* synthetic */ Object get(int i) {
        return Long.valueOf(getLong(i));
    }

    public final long getLong(int i) {
        zzh(i);
        return this.zzsx[i];
    }

    public final int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            i = (i * 31) + zzdd.zzk(this.zzsx[i2]);
        }
        return i;
    }

    public final /* synthetic */ Object remove(int i) {
        zzbt();
        zzh(i);
        long j = this.zzsx[i];
        if (i < this.size - 1) {
            System.arraycopy(this.zzsx, i + 1, this.zzsx, i, this.size - i);
        }
        this.size--;
        this.modCount++;
        return Long.valueOf(j);
    }

    public final boolean remove(Object obj) {
        zzbt();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Long.valueOf(this.zzsx[i]))) {
                System.arraycopy(this.zzsx, i + 1, this.zzsx, i, this.size - i);
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
        System.arraycopy(this.zzsx, i2, this.zzsx, i, this.size - i2);
        this.size -= i2 - i;
        this.modCount++;
    }

    public final /* synthetic */ Object set(int i, Object obj) {
        long longValue = ((Long) obj).longValue();
        zzbt();
        zzh(i);
        long j = this.zzsx[i];
        this.zzsx[i] = longValue;
        return Long.valueOf(j);
    }

    public final int size() {
        return this.size;
    }

    public final /* synthetic */ zzdg zzj(int i) {
        if (i >= this.size) {
            return new zzdv(Arrays.copyOf(this.zzsx, i), this.size);
        }
        throw new IllegalArgumentException();
    }

    public final void zzl(long j) {
        zzk(this.size, j);
    }
}
