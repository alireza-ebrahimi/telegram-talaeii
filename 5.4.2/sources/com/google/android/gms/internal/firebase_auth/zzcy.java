package com.google.android.gms.internal.firebase_auth;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class zzcy extends zzbq<Float> implements zzdg<Float>, RandomAccess {
    private static final zzcy zzqt;
    private int size;
    private float[] zzqu;

    static {
        zzbq zzcy = new zzcy();
        zzqt = zzcy;
        zzcy.zzbs();
    }

    zzcy() {
        this(new float[10], 0);
    }

    private zzcy(float[] fArr, int i) {
        this.zzqu = fArr;
        this.size = i;
    }

    private final void zzc(int i, float f) {
        zzbt();
        if (i < 0 || i > this.size) {
            throw new IndexOutOfBoundsException(zzi(i));
        }
        if (this.size < this.zzqu.length) {
            System.arraycopy(this.zzqu, i, this.zzqu, i + 1, this.size - i);
        } else {
            Object obj = new float[(((this.size * 3) / 2) + 1)];
            System.arraycopy(this.zzqu, 0, obj, 0, i);
            System.arraycopy(this.zzqu, i, obj, i + 1, this.size - i);
            this.zzqu = obj;
        }
        this.zzqu[i] = f;
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
        zzc(i, ((Float) obj).floatValue());
    }

    public final boolean addAll(Collection<? extends Float> collection) {
        zzbt();
        zzdd.checkNotNull(collection);
        if (!(collection instanceof zzcy)) {
            return super.addAll(collection);
        }
        zzcy zzcy = (zzcy) collection;
        if (zzcy.size == 0) {
            return false;
        }
        if (Integer.MAX_VALUE - this.size < zzcy.size) {
            throw new OutOfMemoryError();
        }
        int i = this.size + zzcy.size;
        if (i > this.zzqu.length) {
            this.zzqu = Arrays.copyOf(this.zzqu, i);
        }
        System.arraycopy(zzcy.zzqu, 0, this.zzqu, this.size, zzcy.size);
        this.size = i;
        this.modCount++;
        return true;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzcy)) {
            return super.equals(obj);
        }
        zzcy zzcy = (zzcy) obj;
        if (this.size != zzcy.size) {
            return false;
        }
        float[] fArr = zzcy.zzqu;
        for (int i = 0; i < this.size; i++) {
            if (this.zzqu[i] != fArr[i]) {
                return false;
            }
        }
        return true;
    }

    public final /* synthetic */ Object get(int i) {
        zzh(i);
        return Float.valueOf(this.zzqu[i]);
    }

    public final int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            i = (i * 31) + Float.floatToIntBits(this.zzqu[i2]);
        }
        return i;
    }

    public final /* synthetic */ Object remove(int i) {
        zzbt();
        zzh(i);
        float f = this.zzqu[i];
        if (i < this.size - 1) {
            System.arraycopy(this.zzqu, i + 1, this.zzqu, i, this.size - i);
        }
        this.size--;
        this.modCount++;
        return Float.valueOf(f);
    }

    public final boolean remove(Object obj) {
        zzbt();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Float.valueOf(this.zzqu[i]))) {
                System.arraycopy(this.zzqu, i + 1, this.zzqu, i, this.size - i);
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
        System.arraycopy(this.zzqu, i2, this.zzqu, i, this.size - i2);
        this.size -= i2 - i;
        this.modCount++;
    }

    public final /* synthetic */ Object set(int i, Object obj) {
        float floatValue = ((Float) obj).floatValue();
        zzbt();
        zzh(i);
        float f = this.zzqu[i];
        this.zzqu[i] = floatValue;
        return Float.valueOf(f);
    }

    public final int size() {
        return this.size;
    }

    public final void zzc(float f) {
        zzc(this.size, f);
    }

    public final /* synthetic */ zzdg zzj(int i) {
        if (i >= this.size) {
            return new zzcy(Arrays.copyOf(this.zzqu, i), this.size);
        }
        throw new IllegalArgumentException();
    }
}
