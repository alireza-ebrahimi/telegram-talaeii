package com.google.android.gms.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class zzfhy extends zzfgn<Integer> implements zzfic, zzfjm, RandomAccess {
    private static final zzfhy zzpqk;
    private int size;
    private int[] zzpql;

    static {
        zzfgn zzfhy = new zzfhy();
        zzpqk = zzfhy;
        zzfhy.zzbkr();
    }

    zzfhy() {
        this(new int[10], 0);
    }

    private zzfhy(int[] iArr, int i) {
        this.zzpql = iArr;
        this.size = i;
    }

    private final void zzal(int i, int i2) {
        zzcxl();
        if (i < 0 || i > this.size) {
            throw new IndexOutOfBoundsException(zzmn(i));
        }
        if (this.size < this.zzpql.length) {
            System.arraycopy(this.zzpql, i, this.zzpql, i + 1, this.size - i);
        } else {
            Object obj = new int[(((this.size * 3) / 2) + 1)];
            System.arraycopy(this.zzpql, 0, obj, 0, i);
            System.arraycopy(this.zzpql, i, obj, i + 1, this.size - i);
            this.zzpql = obj;
        }
        this.zzpql[i] = i2;
        this.size++;
        this.modCount++;
    }

    public static zzfhy zzdad() {
        return zzpqk;
    }

    private final void zzmm(int i) {
        if (i < 0 || i >= this.size) {
            throw new IndexOutOfBoundsException(zzmn(i));
        }
    }

    private final String zzmn(int i) {
        return "Index:" + i + ", Size:" + this.size;
    }

    public final /* synthetic */ void add(int i, Object obj) {
        zzal(i, ((Integer) obj).intValue());
    }

    public final boolean addAll(Collection<? extends Integer> collection) {
        zzcxl();
        zzfhz.checkNotNull(collection);
        if (!(collection instanceof zzfhy)) {
            return super.addAll(collection);
        }
        zzfhy zzfhy = (zzfhy) collection;
        if (zzfhy.size == 0) {
            return false;
        }
        if (Integer.MAX_VALUE - this.size < zzfhy.size) {
            throw new OutOfMemoryError();
        }
        int i = this.size + zzfhy.size;
        if (i > this.zzpql.length) {
            this.zzpql = Arrays.copyOf(this.zzpql, i);
        }
        System.arraycopy(zzfhy.zzpql, 0, this.zzpql, this.size, zzfhy.size);
        this.size = i;
        this.modCount++;
        return true;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzfhy)) {
            return super.equals(obj);
        }
        zzfhy zzfhy = (zzfhy) obj;
        if (this.size != zzfhy.size) {
            return false;
        }
        int[] iArr = zzfhy.zzpql;
        for (int i = 0; i < this.size; i++) {
            if (this.zzpql[i] != iArr[i]) {
                return false;
            }
        }
        return true;
    }

    public final /* synthetic */ Object get(int i) {
        return Integer.valueOf(getInt(i));
    }

    public final int getInt(int i) {
        zzmm(i);
        return this.zzpql[i];
    }

    public final int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            i = (i * 31) + this.zzpql[i2];
        }
        return i;
    }

    public final /* synthetic */ Object remove(int i) {
        zzcxl();
        zzmm(i);
        int i2 = this.zzpql[i];
        System.arraycopy(this.zzpql, i + 1, this.zzpql, i, this.size - i);
        this.size--;
        this.modCount++;
        return Integer.valueOf(i2);
    }

    public final boolean remove(Object obj) {
        zzcxl();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Integer.valueOf(this.zzpql[i]))) {
                System.arraycopy(this.zzpql, i + 1, this.zzpql, i, this.size - i);
                this.size--;
                this.modCount++;
                return true;
            }
        }
        return false;
    }

    public final /* synthetic */ Object set(int i, Object obj) {
        int intValue = ((Integer) obj).intValue();
        zzcxl();
        zzmm(i);
        int i2 = this.zzpql[i];
        this.zzpql[i] = intValue;
        return Integer.valueOf(i2);
    }

    public final int size() {
        return this.size;
    }

    public final zzfic zzmk(int i) {
        if (i >= this.size) {
            return new zzfhy(Arrays.copyOf(this.zzpql, i), this.size);
        }
        throw new IllegalArgumentException();
    }

    public final void zzml(int i) {
        zzal(this.size, i);
    }

    public final /* synthetic */ zzfid zzmo(int i) {
        return zzmk(i);
    }
}
