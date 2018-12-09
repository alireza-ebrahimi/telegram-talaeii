package com.google.android.gms.internal.clearcut;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class zzce extends zzav<Float> implements zzcn<Float>, RandomAccess {
    private static final zzce zzjm;
    private int size;
    private float[] zzjn;

    static {
        zzav zzce = new zzce();
        zzjm = zzce;
        zzce.zzv();
    }

    zzce() {
        this(new float[10], 0);
    }

    private zzce(float[] fArr, int i) {
        this.zzjn = fArr;
        this.size = i;
    }

    private final void zzc(int i, float f) {
        zzw();
        if (i < 0 || i > this.size) {
            throw new IndexOutOfBoundsException(zzh(i));
        }
        if (this.size < this.zzjn.length) {
            System.arraycopy(this.zzjn, i, this.zzjn, i + 1, this.size - i);
        } else {
            Object obj = new float[(((this.size * 3) / 2) + 1)];
            System.arraycopy(this.zzjn, 0, obj, 0, i);
            System.arraycopy(this.zzjn, i, obj, i + 1, this.size - i);
            this.zzjn = obj;
        }
        this.zzjn[i] = f;
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
        zzc(i, ((Float) obj).floatValue());
    }

    public final boolean addAll(Collection<? extends Float> collection) {
        zzw();
        zzci.checkNotNull(collection);
        if (!(collection instanceof zzce)) {
            return super.addAll(collection);
        }
        zzce zzce = (zzce) collection;
        if (zzce.size == 0) {
            return false;
        }
        if (Integer.MAX_VALUE - this.size < zzce.size) {
            throw new OutOfMemoryError();
        }
        int i = this.size + zzce.size;
        if (i > this.zzjn.length) {
            this.zzjn = Arrays.copyOf(this.zzjn, i);
        }
        System.arraycopy(zzce.zzjn, 0, this.zzjn, this.size, zzce.size);
        this.size = i;
        this.modCount++;
        return true;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzce)) {
            return super.equals(obj);
        }
        zzce zzce = (zzce) obj;
        if (this.size != zzce.size) {
            return false;
        }
        float[] fArr = zzce.zzjn;
        for (int i = 0; i < this.size; i++) {
            if (this.zzjn[i] != fArr[i]) {
                return false;
            }
        }
        return true;
    }

    public final /* synthetic */ Object get(int i) {
        zzg(i);
        return Float.valueOf(this.zzjn[i]);
    }

    public final int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            i = (i * 31) + Float.floatToIntBits(this.zzjn[i2]);
        }
        return i;
    }

    public final /* synthetic */ Object remove(int i) {
        zzw();
        zzg(i);
        float f = this.zzjn[i];
        if (i < this.size - 1) {
            System.arraycopy(this.zzjn, i + 1, this.zzjn, i, this.size - i);
        }
        this.size--;
        this.modCount++;
        return Float.valueOf(f);
    }

    public final boolean remove(Object obj) {
        zzw();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Float.valueOf(this.zzjn[i]))) {
                System.arraycopy(this.zzjn, i + 1, this.zzjn, i, this.size - i);
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
        System.arraycopy(this.zzjn, i2, this.zzjn, i, this.size - i2);
        this.size -= i2 - i;
        this.modCount++;
    }

    public final /* synthetic */ Object set(int i, Object obj) {
        float floatValue = ((Float) obj).floatValue();
        zzw();
        zzg(i);
        float f = this.zzjn[i];
        this.zzjn[i] = floatValue;
        return Float.valueOf(f);
    }

    public final int size() {
        return this.size;
    }

    public final void zzc(float f) {
        zzc(this.size, f);
    }

    public final /* synthetic */ zzcn zzi(int i) {
        if (i >= this.size) {
            return new zzce(Arrays.copyOf(this.zzjn, i), this.size);
        }
        throw new IllegalArgumentException();
    }
}
