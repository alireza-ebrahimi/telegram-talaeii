package com.google.android.gms.internal.clearcut;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class zzaz extends zzav<Boolean> implements zzcn<Boolean>, RandomAccess {
    private static final zzaz zzfg;
    private int size;
    private boolean[] zzfh;

    static {
        zzav zzaz = new zzaz();
        zzfg = zzaz;
        zzaz.zzv();
    }

    zzaz() {
        this(new boolean[10], 0);
    }

    private zzaz(boolean[] zArr, int i) {
        this.zzfh = zArr;
        this.size = i;
    }

    private final void zza(int i, boolean z) {
        zzw();
        if (i < 0 || i > this.size) {
            throw new IndexOutOfBoundsException(zzh(i));
        }
        if (this.size < this.zzfh.length) {
            System.arraycopy(this.zzfh, i, this.zzfh, i + 1, this.size - i);
        } else {
            Object obj = new boolean[(((this.size * 3) / 2) + 1)];
            System.arraycopy(this.zzfh, 0, obj, 0, i);
            System.arraycopy(this.zzfh, i, obj, i + 1, this.size - i);
            this.zzfh = obj;
        }
        this.zzfh[i] = z;
        this.size++;
        this.modCount++;
    }

    private final void zzg(int i) {
        if (i < 0 || i >= this.size) {
            throw new IndexOutOfBoundsException(zzh(i));
        }
    }

    private final String zzh(int i) {
        return "Index:" + i + ", Size:" + this.size;
    }

    public final /* synthetic */ void add(int i, Object obj) {
        zza(i, ((Boolean) obj).booleanValue());
    }

    public final boolean addAll(Collection<? extends Boolean> collection) {
        zzw();
        zzci.checkNotNull(collection);
        if (!(collection instanceof zzaz)) {
            return super.addAll(collection);
        }
        zzaz zzaz = (zzaz) collection;
        if (zzaz.size == 0) {
            return false;
        }
        if (Integer.MAX_VALUE - this.size < zzaz.size) {
            throw new OutOfMemoryError();
        }
        int i = this.size + zzaz.size;
        if (i > this.zzfh.length) {
            this.zzfh = Arrays.copyOf(this.zzfh, i);
        }
        System.arraycopy(zzaz.zzfh, 0, this.zzfh, this.size, zzaz.size);
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
        if (!(obj instanceof zzaz)) {
            return super.equals(obj);
        }
        zzaz zzaz = (zzaz) obj;
        if (this.size != zzaz.size) {
            return false;
        }
        boolean[] zArr = zzaz.zzfh;
        for (int i = 0; i < this.size; i++) {
            if (this.zzfh[i] != zArr[i]) {
                return false;
            }
        }
        return true;
    }

    public final /* synthetic */ Object get(int i) {
        zzg(i);
        return Boolean.valueOf(this.zzfh[i]);
    }

    public final int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            i = (i * 31) + zzci.zzc(this.zzfh[i2]);
        }
        return i;
    }

    public final /* synthetic */ Object remove(int i) {
        zzw();
        zzg(i);
        boolean z = this.zzfh[i];
        if (i < this.size - 1) {
            System.arraycopy(this.zzfh, i + 1, this.zzfh, i, this.size - i);
        }
        this.size--;
        this.modCount++;
        return Boolean.valueOf(z);
    }

    public final boolean remove(Object obj) {
        zzw();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Boolean.valueOf(this.zzfh[i]))) {
                System.arraycopy(this.zzfh, i + 1, this.zzfh, i, this.size - i);
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
        System.arraycopy(this.zzfh, i2, this.zzfh, i, this.size - i2);
        this.size -= i2 - i;
        this.modCount++;
    }

    public final /* synthetic */ Object set(int i, Object obj) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        zzw();
        zzg(i);
        boolean z = this.zzfh[i];
        this.zzfh[i] = booleanValue;
        return Boolean.valueOf(z);
    }

    public final int size() {
        return this.size;
    }

    public final /* synthetic */ zzcn zzi(int i) {
        if (i >= this.size) {
            return new zzaz(Arrays.copyOf(this.zzfh, i), this.size);
        }
        throw new IllegalArgumentException();
    }
}
