package com.google.android.gms.internal.firebase_auth;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class zzdc extends zzbq<Integer> implements zzdg<Integer>, RandomAccess {
    private static final zzdc zzrs;
    private int size;
    private int[] zzrt;

    static {
        zzbq zzdc = new zzdc();
        zzrs = zzdc;
        zzdc.zzbs();
    }

    zzdc() {
        this(new int[10], 0);
    }

    private zzdc(int[] iArr, int i) {
        this.zzrt = iArr;
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

    private final void zzo(int i, int i2) {
        zzbt();
        if (i < 0 || i > this.size) {
            throw new IndexOutOfBoundsException(zzi(i));
        }
        if (this.size < this.zzrt.length) {
            System.arraycopy(this.zzrt, i, this.zzrt, i + 1, this.size - i);
        } else {
            Object obj = new int[(((this.size * 3) / 2) + 1)];
            System.arraycopy(this.zzrt, 0, obj, 0, i);
            System.arraycopy(this.zzrt, i, obj, i + 1, this.size - i);
            this.zzrt = obj;
        }
        this.zzrt[i] = i2;
        this.size++;
        this.modCount++;
    }

    public final /* synthetic */ void add(int i, Object obj) {
        zzo(i, ((Integer) obj).intValue());
    }

    public final boolean addAll(Collection<? extends Integer> collection) {
        zzbt();
        zzdd.checkNotNull(collection);
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
        if (i > this.zzrt.length) {
            this.zzrt = Arrays.copyOf(this.zzrt, i);
        }
        System.arraycopy(zzdc.zzrt, 0, this.zzrt, this.size, zzdc.size);
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
        int[] iArr = zzdc.zzrt;
        for (int i = 0; i < this.size; i++) {
            if (this.zzrt[i] != iArr[i]) {
                return false;
            }
        }
        return true;
    }

    public final /* synthetic */ Object get(int i) {
        return Integer.valueOf(getInt(i));
    }

    public final int getInt(int i) {
        zzh(i);
        return this.zzrt[i];
    }

    public final int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            i = (i * 31) + this.zzrt[i2];
        }
        return i;
    }

    public final /* synthetic */ Object remove(int i) {
        zzbt();
        zzh(i);
        int i2 = this.zzrt[i];
        if (i < this.size - 1) {
            System.arraycopy(this.zzrt, i + 1, this.zzrt, i, this.size - i);
        }
        this.size--;
        this.modCount++;
        return Integer.valueOf(i2);
    }

    public final boolean remove(Object obj) {
        zzbt();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Integer.valueOf(this.zzrt[i]))) {
                System.arraycopy(this.zzrt, i + 1, this.zzrt, i, this.size - i);
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
        System.arraycopy(this.zzrt, i2, this.zzrt, i, this.size - i2);
        this.size -= i2 - i;
        this.modCount++;
    }

    public final /* synthetic */ Object set(int i, Object obj) {
        int intValue = ((Integer) obj).intValue();
        zzbt();
        zzh(i);
        int i2 = this.zzrt[i];
        this.zzrt[i] = intValue;
        return Integer.valueOf(i2);
    }

    public final int size() {
        return this.size;
    }

    public final void zzal(int i) {
        zzo(this.size, i);
    }

    public final /* synthetic */ zzdg zzj(int i) {
        if (i >= this.size) {
            return new zzdc(Arrays.copyOf(this.zzrt, i), this.size);
        }
        throw new IllegalArgumentException();
    }
}
