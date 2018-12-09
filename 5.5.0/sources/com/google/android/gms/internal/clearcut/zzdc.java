package com.google.android.gms.internal.clearcut;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class zzdc extends zzav<Long> implements zzcn<Long>, RandomAccess {
    private static final zzdc zzlw;
    private int size;
    private long[] zzlx;

    static {
        zzav zzdc = new zzdc();
        zzlw = zzdc;
        zzdc.zzv();
    }

    zzdc() {
        this(new long[10], 0);
    }

    private zzdc(long[] jArr, int i) {
        this.zzlx = jArr;
        this.size = i;
    }

    public static zzdc zzbx() {
        return zzlw;
    }

    private final void zzg(int i) {
        if (i < 0 || i >= this.size) {
            throw new IndexOutOfBoundsException(zzh(i));
        }
    }

    private final String zzh(int i) {
        return "Index:" + i + ", Size:" + this.size;
    }

    private final void zzk(int i, long j) {
        zzw();
        if (i < 0 || i > this.size) {
            throw new IndexOutOfBoundsException(zzh(i));
        }
        if (this.size < this.zzlx.length) {
            System.arraycopy(this.zzlx, i, this.zzlx, i + 1, this.size - i);
        } else {
            Object obj = new long[(((this.size * 3) / 2) + 1)];
            System.arraycopy(this.zzlx, 0, obj, 0, i);
            System.arraycopy(this.zzlx, i, obj, i + 1, this.size - i);
            this.zzlx = obj;
        }
        this.zzlx[i] = j;
        this.size++;
        this.modCount++;
    }

    public final /* synthetic */ void add(int i, Object obj) {
        zzk(i, ((Long) obj).longValue());
    }

    public final boolean addAll(Collection<? extends Long> collection) {
        zzw();
        zzci.checkNotNull(collection);
        if (!(collection instanceof zzdc)) {
            return super.addAll(collection);
        }
        zzdc zzdc = (zzdc) collection;
        if (zzdc.size == 0) {
            return false;
        }
        if (Integer.MAX_VALUE - this.size < zzdc.size) {
            throw new OutOfMemoryError();
        }
        int i = this.size + zzdc.size;
        if (i > this.zzlx.length) {
            this.zzlx = Arrays.copyOf(this.zzlx, i);
        }
        System.arraycopy(zzdc.zzlx, 0, this.zzlx, this.size, zzdc.size);
        this.size = i;
        this.modCount++;
        return true;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzdc)) {
            return super.equals(obj);
        }
        zzdc zzdc = (zzdc) obj;
        if (this.size != zzdc.size) {
            return false;
        }
        long[] jArr = zzdc.zzlx;
        for (int i = 0; i < this.size; i++) {
            if (this.zzlx[i] != jArr[i]) {
                return false;
            }
        }
        return true;
    }

    public final /* synthetic */ Object get(int i) {
        return Long.valueOf(getLong(i));
    }

    public final long getLong(int i) {
        zzg(i);
        return this.zzlx[i];
    }

    public final int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            i = (i * 31) + zzci.zzl(this.zzlx[i2]);
        }
        return i;
    }

    public final /* synthetic */ Object remove(int i) {
        zzw();
        zzg(i);
        long j = this.zzlx[i];
        if (i < this.size - 1) {
            System.arraycopy(this.zzlx, i + 1, this.zzlx, i, this.size - i);
        }
        this.size--;
        this.modCount++;
        return Long.valueOf(j);
    }

    public final boolean remove(Object obj) {
        zzw();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Long.valueOf(this.zzlx[i]))) {
                System.arraycopy(this.zzlx, i + 1, this.zzlx, i, this.size - i);
                this.size--;
                this.modCount++;
                return true;
            }
        }
        return false;
    }

    protected final void removeRange(int i, int i2) {
        zzw();
        if (i2 < i) {
            throw new IndexOutOfBoundsException("toIndex < fromIndex");
        }
        System.arraycopy(this.zzlx, i2, this.zzlx, i, this.size - i2);
        this.size -= i2 - i;
        this.modCount++;
    }

    public final /* synthetic */ Object set(int i, Object obj) {
        long longValue = ((Long) obj).longValue();
        zzw();
        zzg(i);
        long j = this.zzlx[i];
        this.zzlx[i] = longValue;
        return Long.valueOf(j);
    }

    public final int size() {
        return this.size;
    }

    public final /* synthetic */ zzcn zzi(int i) {
        if (i >= this.size) {
            return new zzdc(Arrays.copyOf(this.zzlx, i), this.size);
        }
        throw new IllegalArgumentException();
    }

    public final void zzm(long j) {
        zzk(this.size, j);
    }
}
