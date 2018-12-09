package com.google.android.gms.internal.firebase_auth;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class zzcl extends zzbq<Double> implements zzdg<Double>, RandomAccess {
    private static final zzcl zznq;
    private int size;
    private double[] zznr;

    static {
        zzbq zzcl = new zzcl();
        zznq = zzcl;
        zzcl.zzbs();
    }

    zzcl() {
        this(new double[10], 0);
    }

    private zzcl(double[] dArr, int i) {
        this.zznr = dArr;
        this.size = i;
    }

    private final void zzc(int i, double d) {
        zzbt();
        if (i < 0 || i > this.size) {
            throw new IndexOutOfBoundsException(zzi(i));
        }
        if (this.size < this.zznr.length) {
            System.arraycopy(this.zznr, i, this.zznr, i + 1, this.size - i);
        } else {
            Object obj = new double[(((this.size * 3) / 2) + 1)];
            System.arraycopy(this.zznr, 0, obj, 0, i);
            System.arraycopy(this.zznr, i, obj, i + 1, this.size - i);
            this.zznr = obj;
        }
        this.zznr[i] = d;
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
        zzc(i, ((Double) obj).doubleValue());
    }

    public final boolean addAll(Collection<? extends Double> collection) {
        zzbt();
        zzdd.checkNotNull(collection);
        if (!(collection instanceof zzcl)) {
            return super.addAll(collection);
        }
        zzcl zzcl = (zzcl) collection;
        if (zzcl.size == 0) {
            return false;
        }
        if (Integer.MAX_VALUE - this.size < zzcl.size) {
            throw new OutOfMemoryError();
        }
        int i = this.size + zzcl.size;
        if (i > this.zznr.length) {
            this.zznr = Arrays.copyOf(this.zznr, i);
        }
        System.arraycopy(zzcl.zznr, 0, this.zznr, this.size, zzcl.size);
        this.size = i;
        this.modCount++;
        return true;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzcl)) {
            return super.equals(obj);
        }
        zzcl zzcl = (zzcl) obj;
        if (this.size != zzcl.size) {
            return false;
        }
        double[] dArr = zzcl.zznr;
        for (int i = 0; i < this.size; i++) {
            if (this.zznr[i] != dArr[i]) {
                return false;
            }
        }
        return true;
    }

    public final /* synthetic */ Object get(int i) {
        zzh(i);
        return Double.valueOf(this.zznr[i]);
    }

    public final int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            i = (i * 31) + zzdd.zzk(Double.doubleToLongBits(this.zznr[i2]));
        }
        return i;
    }

    public final /* synthetic */ Object remove(int i) {
        zzbt();
        zzh(i);
        double d = this.zznr[i];
        if (i < this.size - 1) {
            System.arraycopy(this.zznr, i + 1, this.zznr, i, this.size - i);
        }
        this.size--;
        this.modCount++;
        return Double.valueOf(d);
    }

    public final boolean remove(Object obj) {
        zzbt();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Double.valueOf(this.zznr[i]))) {
                System.arraycopy(this.zznr, i + 1, this.zznr, i, this.size - i);
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
        System.arraycopy(this.zznr, i2, this.zznr, i, this.size - i2);
        this.size -= i2 - i;
        this.modCount++;
    }

    public final /* synthetic */ Object set(int i, Object obj) {
        double doubleValue = ((Double) obj).doubleValue();
        zzbt();
        zzh(i);
        double d = this.zznr[i];
        this.zznr[i] = doubleValue;
        return Double.valueOf(d);
    }

    public final int size() {
        return this.size;
    }

    public final void zzc(double d) {
        zzc(this.size, d);
    }

    public final /* synthetic */ zzdg zzj(int i) {
        if (i >= this.size) {
            return new zzcl(Arrays.copyOf(this.zznr, i), this.size);
        }
        throw new IllegalArgumentException();
    }
}
